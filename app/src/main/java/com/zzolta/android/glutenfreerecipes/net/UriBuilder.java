package com.zzolta.android.glutenfreerecipes.net;

import android.net.Uri;
import android.net.Uri.Builder;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.CuisineHelper;

/*
 * Copyright (C) 2015 Zolta Szekely
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public final class UriBuilder {

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
    private static final String START = "start";
    private static final String RECIPE = "recipe";
    private static final String ALLOWED_CUISINE = "allowedCuisine[]";

    private UriBuilder() {
    }

    public static Uri createQueryUri(String query, String startValue) {
        return getBuilder()
                   .appendPath(RECIPES)
                   .appendQueryParameter(APP_ID, APP_ID_VALUE)
                   .appendQueryParameter(APP_KEY, APP_KEY_VALUE)
                   .appendQueryParameter(QUERY, query)
                   .appendQueryParameter(REQUIRE_PICTURES, TRUE)
                   .appendQueryParameter(ALLOWED_ALLERGY, GLUTEN_FREE)
                   .appendQueryParameter(MAX_RESULT, String.valueOf(ApplicationConstants.MAX_RESULT_VALUE))
                   .appendQueryParameter(START, startValue)
                   .build();
    }

    public static Uri createGetUri(String recipeId) {
        return getBuilder()
                   .appendPath(RECIPE)
                   .appendPath(recipeId)
                   .appendQueryParameter(APP_ID, APP_ID_VALUE)
                   .appendQueryParameter(APP_KEY, APP_KEY_VALUE)
                   .build();
    }

    public static Uri createCuisineOfTheDayUri() {
        return getBuilder()
                   .appendPath(RECIPES)
                   .appendQueryParameter(APP_ID, APP_ID_VALUE)
                   .appendQueryParameter(APP_KEY, APP_KEY_VALUE)
                   .appendQueryParameter(REQUIRE_PICTURES, TRUE)
                   .appendQueryParameter(ALLOWED_ALLERGY, GLUTEN_FREE)
                   .appendQueryParameter(ALLOWED_CUISINE, CuisineHelper.getCuisineOfTheDay())
                   .appendQueryParameter(MAX_RESULT, String.valueOf(1))
                   .appendQueryParameter(START, "0")
                   .build();
    }

    private static Builder getBuilder() {
        return new Builder()
                   .scheme(HTTP)
                   .authority(AUTHORITY)
                   .appendPath(VERSION)
                   .appendPath(API);
    }
}
