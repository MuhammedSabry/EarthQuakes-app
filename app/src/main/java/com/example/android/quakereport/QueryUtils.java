package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String url) {
        Date date;
        URL queriedurl = createURL(url);

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject quakejsonobj = new JSONObject(connectTo(queriedurl));
            JSONArray quakejsonarr = quakejsonobj.getJSONArray("features");

            for (int i = 0; i
                    < quakejsonarr.length(); i++) {
                JSONObject temp = quakejsonarr.getJSONObject(i);
                JSONObject temp2 = temp.getJSONObject("properties");
                date = new Date(temp2.getLong("time"));
                String magnitude = magnitudeFormat.format(temp2.getDouble("mag"));
                earthquakes.add(new Earthquake(Double.parseDouble(magnitude)
                        , temp2.getString("place")
                        , formatDate(date), formatTime(date), temp2.getString("url")));
            }
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private static URL createURL(String s) {
        URL url=null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            Log.d("QUery","can't create URL");
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String connectTo(URL url) {
        String jsn = "";
        InputStream input = null;
        HttpURLConnection connection = null;
        if (url == null) {
            return jsn;
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                input = connection.getInputStream();
                jsn = readFromStream(input);
            } else {
                Log.e("QueryUtils", "Error response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
            }
        }
        return jsn;
    }
}
