package com.zzolta.android.gfrecipes.fragments;

import android.R.drawable;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.*;
import android.view.MenuItem.OnMenuItemClickListener;
import com.j256.ormlite.dao.Dao;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.activities.MainActivity;
import com.zzolta.android.gfrecipes.activities.MyRecipesActivity;
import com.zzolta.android.gfrecipes.activities.RecipeDetailActivity;
import com.zzolta.android.gfrecipes.activities.SearchResultsActivity;
import com.zzolta.android.gfrecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.gfrecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.gfrecipes.listeners.RecipeDetailResultListener;
import com.zzolta.android.gfrecipes.listeners.RecipeOfTheDayQueryResultListener;
import com.zzolta.android.gfrecipes.listeners.VolleyErrorListener;
import com.zzolta.android.gfrecipes.net.ApplicationRequestQueue;
import com.zzolta.android.gfrecipes.net.GsonRequest;
import com.zzolta.android.gfrecipes.net.UriBuilder;
import com.zzolta.android.gfrecipes.parallax.ParallaxScrollAppCompat;
import com.zzolta.android.gfrecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;
import com.zzolta.android.gfrecipes.providers.RecipeDetailShareActionProvider;
import com.zzolta.android.gfrecipes.providers.RecipeSearchRecentSuggestionsProvider;
import com.zzolta.android.gfrecipes.utils.ApplicationConstants;
import com.zzolta.android.gfrecipes.utils.RecipeDetailHelper;

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
public class RecipeDetailFragment extends Fragment {
    private static final String LOG_TAG = RecipeDetailFragment.class.getName();
    private ParallaxScrollAppCompat parallaxScrollAppCompat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = parallaxScrollAppCompat.createView(inflater);
        final RecipeDetailHelper recipeDetailHelper = RecipeDetailHelper.getInstance();
        final Activity activity = getActivity();
        recipeDetailHelper.setActivity(activity);
        recipeDetailHelper.setView(view);
        String recipeID = null;
        if (activity instanceof SearchResultsActivity) {
            if (((SearchResultsActivity) activity).isTwoPane()) {
                recipeID = getArguments().getString(ApplicationConstants.RECIPE_ID);
            } else {
                recipeID = activity.getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);
            }
        } else if (activity instanceof RecipeDetailActivity) {
            recipeID = activity.getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);
        } else if (activity instanceof MyRecipesActivity) {
            if (((MyRecipesActivity) activity).isTwoPane()) {
                recipeID = getArguments().getString(ApplicationConstants.RECIPE_ID);
            } else {
                recipeID = activity.getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);
            }
        } else {
            //it is a MainActivity
        }

        if (recipeID != null) {
            //recipe detail
            final Recipe recipe = getRecipeFromDatabase(recipeID);
            if (recipe != null) {
                recipeDetailHelper.loadData(recipe);
            } else {
                getRecipeUsingREST(recipeID);
            }
        } else {
            //recipe of the day
            final String recipeOfTheDayId = this.getArguments().getString(ApplicationConstants.RECIPE_OF_THE_DAY_ID);
            if (recipeOfTheDayId == null) {
                loadRecipeOfTheDay();
            } else {
                recipeDetailHelper.loadData(getRecipeFromDatabase(recipeOfTheDayId));
            }
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parallaxScrollAppCompat = new ParallaxScrollAppCompat()
                                      .actionBarBackground(R.drawable.ab_solid_example)
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);

        final MenuItem share = menu.findItem(R.id.action_share);
        RecipeDetailShareActionProvider.getInstance().setShareActionProvider((ShareActionProvider) MenuItemCompat.getActionProvider(share));

        final MenuItem clearSearchHistory = menu.findItem(R.id.action_clear_search_history);
        clearSearchHistory.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new Builder(getActivity())
                    .setTitle(getString(R.string.action_clear_search_history))
                    .setMessage(getString(R.string.confirmation_clear_search_history))
                    .setIcon(drawable.ic_dialog_alert)
                    .setPositiveButton(string.yes, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity(), RecipeSearchRecentSuggestionsProvider.AUTHORITY, RecipeSearchRecentSuggestionsProvider.MODE);
                            suggestions.clearHistory();
                        }
                    })
                    .setNegativeButton(string.no, null).show();
                return true;
            }
        });

        final MenuItem addToMyRecipes = menu.findItem(R.id.action_add_to_my_recipes);
        addToMyRecipes.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myRecipeHandler(true);
                return true;
            }
        });

        final MenuItem removeFromMyRecipes = menu.findItem(R.id.action_remove_from_my_recipes);
        removeFromMyRecipes.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                myRecipeHandler(false);
                return true;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        final String recipeId = RecipeDetailHelper.getInstance().getCurrentRecipeId();
        if (recipeId != null) {
            try {
                final Dao<Recipe, String> recipeDao = RecipeDBHelper.getInstance(getActivity().getApplicationContext()).getDao();
                final Recipe recipe = recipeDao.queryForId(recipeId);
                final MenuItem addToMyRecipes = menu.findItem(R.id.action_add_to_my_recipes);
                final MenuItem removeFromMyRecipes = menu.findItem(R.id.action_remove_from_my_recipes);
                if (recipe.getMyRecipe()) {
                    addToMyRecipes.setVisible(false);
                    removeFromMyRecipes.setVisible(true);
                } else {
                    addToMyRecipes.setVisible(true);
                    removeFromMyRecipes.setVisible(false);
                }
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }

    private void myRecipeHandler(boolean myRecipe) {
        final String recipeId = RecipeDetailHelper.getInstance().getCurrentRecipeId();
        if (recipeId != null) {
            try {
                final Dao<Recipe, String> recipeDao = RecipeDBHelper.getInstance(getActivity().getApplicationContext()).getDao();
                final Recipe recipe = recipeDao.queryForId(recipeId);
                recipe.setMyRecipe(myRecipe);
                recipeDao.update(recipe);
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
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
