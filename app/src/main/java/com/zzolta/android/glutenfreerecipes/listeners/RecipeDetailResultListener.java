package com.zzolta.android.glutenfreerecipes.listeners;

import android.app.Activity;
import android.util.Log;
import com.android.volley.Response.Listener;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.RecipeDetailHelper;
import com.zzolta.android.glutenfreerecipes.utils.RecipeHelper;

import java.sql.SQLException;

/**
 * Created by Zolta.Szekely on 2015-03-28.
 */
public class RecipeDetailResultListener implements Listener<RecipeDetailResult> {
    private static final String LOG_TAG = RecipeDetailResultListener.class.getName();
    private final Activity activity;

    public RecipeDetailResultListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(RecipeDetailResult recipeDetailResult) {
        saveRecipe(RecipeHelper.convertRecipe(recipeDetailResult));
        RecipeDetailHelper.getInstance().loadData(recipeDetailResult);
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