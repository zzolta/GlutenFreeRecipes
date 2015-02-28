package com.zzolta.android.glutenfreerecipes.tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class QueryRecipes extends AsyncTask<String, Void, String[]> {

    @Override
    protected String[] doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        final String recipeQuery = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        return new String[0];
    }
}
