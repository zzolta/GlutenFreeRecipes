package com.zzolta.android.glutenfreerecipes.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.RecipeDetailFragment;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.DevelopmentConstants;
import com.zzolta.android.glutenfreerecipes.utils.OfflineRecipeDetailResult;

import java.sql.SQLException;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class RecipeDetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
                .commit();
        }
    }

    public static class PlaceholderFragment extends RecipeDetailFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = super.onCreateView(inflater, container, savedInstanceState);

            final String recipeID = getActivity().getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);
            final Recipe recipe = getRecipeFromDatabase(recipeID);
            if (DevelopmentConstants.OFFLINE) {
                if (recipe != null) {
                    loadData(recipe);
                } else {
                    loadData(new Gson().fromJson(OfflineRecipeDetailResult.pizzaFriesRecipeDetailResult, RecipeDetailResult.class));
                }
            } else {
                final Listener<RecipeDetailResult> listener = getRecipeQueryResultListener();

                final ErrorListener errorListener = getErrorListener();

                if (recipe != null) {
                    loadData(recipe);
                } else {
                    final String url = UriBuilder.createGetUri(recipeID).toString();

                    final GsonRequest<RecipeDetailResult> request = new GsonRequest<>(url, RecipeDetailResult.class, listener, errorListener);

                    ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
                }
            }
            return rootView;
        }

        private Recipe getRecipeFromDatabase(String recipeID) {
            //TODO: make db helper a singleton?
            final RecipeDBHelper recipeDBHelper = new RecipeDBHelper(getActivity().getApplicationContext());
            try {
                return recipeDBHelper.getDao().queryForId(recipeID);
            }
            catch (final SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
