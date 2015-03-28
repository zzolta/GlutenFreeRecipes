package com.zzolta.android.glutenfreerecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.activities.MainActivity;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.listeners.RecipeDetailResultListener;
import com.zzolta.android.glutenfreerecipes.listeners.RecipeOfTheDayQueryResultListener;
import com.zzolta.android.glutenfreerecipes.listeners.VolleyErrorListener;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.RecipeDetailHelper;
import com.zzolta.parallax.ParallaxScrollAppCompat;

import java.sql.SQLException;

/**
 * Created by Zolta.Szekely on 2015-03-24.
 */
public class RecipeDetailFragment extends Fragment {
    private static final String LOG_TAG = RecipeDetailFragment.class.getName();
    private ParallaxScrollAppCompat parallaxScrollAppCompat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = parallaxScrollAppCompat.createView(inflater);
        final RecipeDetailHelper recipeDetailHelper = RecipeDetailHelper.getInstance();
        recipeDetailHelper.setActivity(getActivity());
        recipeDetailHelper.setView(view);

        final String recipeID = getActivity().getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);
        if (recipeID != null) {
            final Recipe recipe = getRecipeFromDatabase(recipeID);
            if (recipe != null) {
                recipeDetailHelper.loadData(recipe);
            } else {
                getRecipeUsingREST(recipeID);
            }
        } else {
            final String recipeOfTheDayId = this.getArguments().getString(ApplicationConstants.RECIPE_ID);
            if (recipeOfTheDayId == null) {
                loadRecipeOfTheDay();
            } else {
                recipeDetailHelper.loadData(getRecipeFromDatabase(recipeOfTheDayId));
            }
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parallaxScrollAppCompat = new ParallaxScrollAppCompat()
                                      .actionBarBackground(R.drawable.actionbar_background)
                                      .headerLayout(R.layout.header)
                                      .contentLayout(R.layout.parallax_listview)
                                      .lightActionBar(true);

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ApplicationConstants.ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parallaxScrollAppCompat.initActionBar(getActivity());
    }

    private void getRecipeUsingREST(String recipeID) {
        final String url = UriBuilder.createGetUri(recipeID).toString();

        final GsonRequest<RecipeDetailResult> request = new GsonRequest<>(url, RecipeDetailResult.class, new RecipeDetailResultListener(getActivity()), new VolleyErrorListener());

        ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private void loadRecipeOfTheDay() {
        final String url = UriBuilder.createCuisineOfTheDayUri().toString();

        final GsonRequest<RecipeQueryResult> request = new GsonRequest<>(url, RecipeQueryResult.class, new RecipeOfTheDayQueryResultListener(getActivity()), new VolleyErrorListener());

        ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private Recipe getRecipeFromDatabase(String recipeID) {
        try {
            return RecipeDBHelper.getInstance(getActivity().getApplicationContext()).getDao().queryForId(recipeID);
        }
        catch (final SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }
}
