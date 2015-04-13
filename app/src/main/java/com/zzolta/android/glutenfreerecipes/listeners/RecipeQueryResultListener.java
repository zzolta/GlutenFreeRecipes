package com.zzolta.android.glutenfreerecipes.listeners;

import com.android.volley.Response.Listener;
import com.zzolta.android.glutenfreerecipes.adapters.RecipeListAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.Match;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.RecipeHelper;

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
public class RecipeQueryResultListener implements Listener<RecipeQueryResult> {
    private final RecipeListAdapter recipeListAdapter;

    public RecipeQueryResultListener(RecipeListAdapter recipeListAdapter) {
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
        recipeListAdapter.notifyDataSetChanged();
    }
}