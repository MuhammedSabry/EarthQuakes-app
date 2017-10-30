package com.example.android.quakereport;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Muhammed on 9/25/2017.
 */

/**
 * Created by Muhammed on 9/25/2017.
 */

public class Earthquake {
    private double mag;
    private String place,date,time,URL;
    public Earthquake(double x,String p,String d , String t,String u)
    {
        mag=x;
        place=p;
        time = t;
        date = d;
        URL = u;
    }

    public double getMag() {
        return mag;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }
    public String getTime()
    {return time;}

    public String getURL() {
        return URL;
    }
}

