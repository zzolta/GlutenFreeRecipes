package com.zzolta.android.glutenfreerecipes.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.DevelopmentConstants;
import com.zzolta.android.glutenfreerecipes.utils.OfflineRecipeDetailResult;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class RecipeDetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        final String recipeID = getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);

        if (DevelopmentConstants.OFFLINE) {
            load(new Gson().fromJson(OfflineRecipeDetailResult.pizzaFriesRecipeDetailResult, RecipeDetailResult.class));
        } else {
            final Listener<RecipeDetailResult> listener = getRecipeQueryResultListener();

            final ErrorListener errorListener = getErrorListener();

            final String url = UriBuilder.createGetUri(recipeID).toString();

            final GsonRequest<RecipeDetailResult> request = new GsonRequest<>(url, RecipeDetailResult.class, listener, errorListener);

            ApplicationRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(request);
        }
    }

    private Listener<RecipeDetailResult> getRecipeQueryResultListener() {
        return new Listener<RecipeDetailResult>() {
            @Override
            public void onResponse(RecipeDetailResult recipeDetailResult) {
                load(recipeDetailResult);
            }
        };
    }

    private void load(RecipeDetailResult recipeDetailResult) {
        final TextView viewById = (TextView) findViewById(R.id.recipe_id);
        viewById.setText(recipeDetailResult.getId());
    }

    private ErrorListener getErrorListener() {
        return new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                throw new RuntimeException(volleyError);
            }
        };
    }
}
