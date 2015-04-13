package com.zzolta.android.gfrecipes.listeners;

import android.app.Activity;
import android.util.Log;
import com.android.volley.Response.Listener;
import com.zzolta.android.gfrecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.gfrecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;
import com.zzolta.android.gfrecipes.utils.RecipeDetailHelper;
import com.zzolta.android.gfrecipes.utils.RecipeHelper;

import java.sql.SQLException;

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
public class RecipeDetailResultListener implements Listener<RecipeDetailResult> {
    private static final String LOG_TAG = RecipeDetailResultListener.class.getName();
    private final Activity activity;

    public RecipeDetailResultListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(RecipeDetailResult recipeDetailResult) {
        RecipeDetailHelper.getInstance().loadData(recipeDetailResult);
        saveRecipe(RecipeHelper.convertRecipe(recipeDetailResult));
    }

    private void saveRecipe(Recipe recipe) {
        try {
            RecipeDBHelper.getInstance(activity.getApplicationContext()).getDao().createOrUpdate(recipe);
        }
        catch (final SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}