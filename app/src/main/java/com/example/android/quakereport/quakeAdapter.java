package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammed on 9/25/2017.
 */

public class quakeAdapter extends ArrayAdapter<Earthquake> {

    String places, KM, placeMain;

    public quakeAdapter(Context context, ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_list, parent, false);
        }
        TextView mag = (TextView) rootView.findViewById(R.id.mag);
        TextView place= (TextView) rootView.findViewById(R.id.place);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        TextView time = (TextView) rootView.findViewById(R.id.time);
        TextView km = (TextView) rootView.findViewById(R.id.km);
        Earthquake earthQuake = getItem(position);
        places = earthQuake.getPlace();
        int i = places.indexOf("of");
        if(i!=-1)
        {
            placeMain = places.substring( i+2);
            KM = places.substring(0,i);
            KM.trim();
        }
        else
        {
            KM="Near the";
            placeMain = places;
        }

        mag.setText(String.valueOf(earthQuake.getMag()));
        GradientDrawable circleColor = (GradientDrawable) mag.getBackground();
        circleColor.setColor(getMagnitudeColor(earthQuake.getMag()));
        place.setText(placeMain);
        km.setText(KM);
        date.setText(earthQuake.getDate());
        time.setText(earthQuake.getTime());
        return rootView;
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
