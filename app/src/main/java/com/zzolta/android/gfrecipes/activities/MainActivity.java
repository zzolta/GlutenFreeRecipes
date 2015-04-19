package com.zzolta.android.gfrecipes.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.Menu;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.fragments.*;
import com.zzolta.android.gfrecipes.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.zzolta.android.gfrecipes.providers.SearchIntentProvider;
import com.zzolta.android.gfrecipes.utils.ApplicationConstants;
import com.zzolta.android.gfrecipes.utils.CuisineHelper;

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
public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    private final SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
    private NavigationDrawerFragment navigationDrawerFragment;
    private CharSequence lastScreenTitle;
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        lastScreenTitle = getTitle();

        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        twoPane = findViewById(R.id.recipe_detail) != null;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {
        if (!fromSavedInstanceState) {
            final FragmentManager fragmentManager = getFragmentManager();
            final Fragment fragment;
            switch (Section.values()[position]) {
                case HOME:
                    fragment = setupRecipeDetailFragment(position);
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case MY_RECIPES:
                    fragment = setupMyRecipesFragment(position);
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                    break;
                case FEEDBACK:
                    fragment = setupFeedbackFragment(position);
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                    break;
                default:
                    break;
            }
        }
    }

    private Fragment setupRecipeDetailFragment(int position) {
        final Fragment fragment = new RecipeDetailFragment();
        final Bundle bundle = new Bundle();
        final String recipeOfTheDay = getRecipeOfTheDay();
        if (recipeOfTheDay != null) {
            bundle.putString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, recipeOfTheDay);
        }
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Fragment setupMyRecipesFragment(int position) {
        final Fragment fragment = new MyRecipesFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Fragment setupFeedbackFragment(int position) {
        final Fragment fragment = new FeedbackFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onSectionAttached(int position) {
        switch (Section.values()[position]) {
            case HOME:
                lastScreenTitle = getString(R.string.home_menu);
                break;
            case MY_RECIPES:
                lastScreenTitle = getString(R.string.my_recipes_menu);
                break;
            case FEEDBACK:
                lastScreenTitle = getString(R.string.feedback_menu);
                break;
            case RECIPE_DETAIL:
                lastScreenTitle = getString(R.string.recipe_detail_menu);
            default:
                break;
        }
    }

    public void restoreActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(lastScreenTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_search, menu);

            final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            setupSearchView(searchView);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(SearchView searchView) {
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getFragmentManager().beginTransaction().replace(R.id.container, searchResultsFragment).addToBackStack(null).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnSuggestionListener(new OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                getFragmentManager().beginTransaction().replace(R.id.container, searchResultsFragment).addToBackStack(null).commit();
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                getFragmentManager().beginTransaction().replace(R.id.container, searchResultsFragment).addToBackStack(null).commit();
                return false;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchResultsFragment.getRecipeListAdapter().clear();
            SearchIntentProvider.getInstance().setSearchIntent(intent);
            searchResultsFragment.handleIntent(intent);
        }
    }

    @Nullable
    private String getRecipeOfTheDay() {
        String recipeOfTheDay = null;
        final SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.PRIVATE_STORAGE, Context.MODE_PRIVATE);
        final String dayOfYear = sharedPreferences.getString(ApplicationConstants.DAY_OF_YEAR, null);
        if (dayOfYear != null && dayOfYear.equals(String.valueOf(CuisineHelper.getDayOfYear()))) {
            recipeOfTheDay = sharedPreferences.getString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, null);
        }
        return recipeOfTheDay;
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            fragmentManager.popBackStack();
        }
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    public enum Section {
        HOME,
        MY_RECIPES,
        FEEDBACK,
        RECIPE_DETAIL
    }
}
