package com.zzolta.android.glutenfreerecipes.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.android.volley.Response.Listener;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.Match;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.RecipeHelper;

import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-28.
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
        editor.putString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, recipeId);
        editor.apply();
    }
}