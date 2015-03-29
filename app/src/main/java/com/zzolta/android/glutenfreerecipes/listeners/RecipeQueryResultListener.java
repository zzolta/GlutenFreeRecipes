package com.zzolta.android.glutenfreerecipes.listeners;

import android.support.v7.app.ActionBarActivity;
import com.android.volley.Response.Listener;
import com.zzolta.android.glutenfreerecipes.adapters.RecipeListAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.Match;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.RecipeHelper;

import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-28.
 */
public class RecipeQueryResultListener implements Listener<RecipeQueryResult> {
    private final ActionBarActivity searchActivity;
    private final RecipeListAdapter recipeListAdapter;

    public RecipeQueryResultListener(ActionBarActivity searchActivity, RecipeListAdapter recipeListAdapter) {
        this.searchActivity = searchActivity;
        this.recipeListAdapter = recipeListAdapter;
    }

    @Override
    public void onResponse(RecipeQueryResult recipeQueryResult) {
        updateSearchList(recipeQueryResult);
    }

    private void updateSearchList(RecipeQueryResult recipeQueryResult) {
        final List<Match> matches = recipeQueryResult.getMatches();

        final List<Recipe> recipes = RecipeHelper.matchRecipes(matches);

        if (recipes.size() > 0) {
            recipeListAdapter.addRecipes(recipes);
        }
        searchActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recipeListAdapter.notifyDataSetChanged();
            }
        });
    }
}