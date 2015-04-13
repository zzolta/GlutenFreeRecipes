package com.zzolta.android.gfrecipes.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.volley.Response.Listener;
import com.zzolta.android.gfrecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.gfrecipes.jsonparse.recipequery.Match;
import com.zzolta.android.gfrecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.gfrecipes.net.ApplicationRequestQueue;
import com.zzolta.android.gfrecipes.net.GsonRequest;
import com.zzolta.android.gfrecipes.net.UriBuilder;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;
import com.zzolta.android.gfrecipes.utils.ApplicationConstants;
import com.zzolta.android.gfrecipes.utils.CuisineHelper;
import com.zzolta.android.gfrecipes.utils.RecipeHelper;

import java.util.List;

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
public class RecipeOfTheDayQueryResultListener implements Listener<RecipeQueryResult> {
    private final Activity activity;

    public RecipeOfTheDayQueryResultListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(RecipeQueryResult recipeQueryResult) {
        final List<Match> matches = recipeQueryResult.getMatches();

        final List<Recipe> recipes = RecipeHelper.matchRecipes(matches);

        if (recipes.size() == 1) {
            final String recipeId = recipes.get(0).getId();
            saveRecipeIdOfTheDay(recipeId);
            final String url = UriBuilder.createGetUri(recipeId).toString();

            final GsonRequest<RecipeDetailResult> request = new GsonRequest<>(url, RecipeDetailResult.class, new RecipeDetailResultListener(activity), new VolleyErrorListener());

            ApplicationRequestQueue.getInstance(activity.getApplicationContext()).addToRequestQueue(request);
        } else {
            //no recipe!
        }
    }

    private void saveRecipeIdOfTheDay(String recipeId) {
        final SharedPreferences sharedPreferences = activity.getSharedPreferences(ApplicationConstants.PRIVATE_STORAGE, Context.MODE_PRIVATE);
        final Editor editor = sharedPreferences.edit();
        editor.putString(ApplicationConstants.DAY_OF_YEAR, String.valueOf(CuisineHelper.getDayOfYear()));
        editor.putString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, recipeId);
        editor.apply();
    }
}