package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.List;

/**
 * Created by Muhammed on 9/27/2017.
 */

public class earthloader extends AsyncTaskLoader<List<Earthquake>> {
    private String mUrl;
    public earthloader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        return QueryUtils.extractEarthquakes(mUrl);
    }
}
