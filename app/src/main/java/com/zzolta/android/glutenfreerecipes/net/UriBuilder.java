package com.zzolta.android.glutenfreerecipes.net;

import android.net.Uri;
import android.net.Uri.Builder;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class UriBuilder {

    private static final String HTTP = "http";
    private static final String AUTHORITY = "api.yummly.com";
    private static final String VERSION = "v1";
    private static final String API = "api";
    private static final String RECIPES = "recipes";
    private static final String APP_ID = "_app_id";
    private static final String APP_ID_VALUE = "514ded55";
    private static final String APP_KEY = "_app_key";
    private static final String APP_KEY_VALUE = "f73d3a223a64565997bf9a08c5f9cb8b";
    private static final String QUERY = "q";
    private static final String REQUIRE_PICTURES = "requirePictures";
    private static final String TRUE = "true";
    private static final String ALLOWED_ALLERGY = "allowedAllergy[]";
    private static final String GLUTEN_FREE = "393^Gluten-Free";
    private static final String MAX_RESULT = "maxResult";
    private static final String MAX_RESULT_VALUE = "10";
    private static final String START = "start";
    private static final String START_VALUE = "0";

    private UriBuilder() {
    }

    public static Uri createUri(String query) {
        return new Builder()
                   .scheme(HTTP)
                   .authority(AUTHORITY)
                   .appendPath(VERSION)
                   .appendPath(API)
                   .appendPath(RECIPES)
                   .appendQueryParameter(APP_ID, APP_ID_VALUE)
                   .appendQueryParameter(APP_KEY, APP_KEY_VALUE)
                   .appendQueryParameter(QUERY, query)
                   .appendQueryParameter(REQUIRE_PICTURES, TRUE)
                   .appendQueryParameter(ALLOWED_ALLERGY, GLUTEN_FREE)
                   .appendQueryParameter(MAX_RESULT, MAX_RESULT_VALUE)
                   .appendQueryParameter(START, START_VALUE)
                   .build();
    }
}