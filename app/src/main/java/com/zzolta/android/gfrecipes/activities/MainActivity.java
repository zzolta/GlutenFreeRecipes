package com.zzolta.android.gfrecipes.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.fragments.*;
import com.zzolta.android.gfrecipes.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.zzolta.android.gfrecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.gfrecipes.listeners.RecipeQueryResultListener;
import com.zzolta.android.gfrecipes.listeners.VolleyErrorListener;
import com.zzolta.android.gfrecipes.net.ApplicationRequestQueue;
import com.zzolta.android.gfrecipes.net.GsonRequest;
import com.zzolta.android.gfrecipes.net.UriBuilder;
import com.zzolta.android.gfrecipes.providers.RecipeSearchRecentSuggestionsProvider;
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

    private NavigationDrawerFragment navigationDrawerFragment;

    private CharSequence lastScreenTitle;

    private SearchResultsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        lastScreenTitle = getTitle();

        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean fromSavedInstanceState) {
        if (!fromSavedInstanceState) {
            final FragmentManager fragmentManager = getFragmentManager();
            final Fragment fragment;
            switch (Section.values()[position]) {
                case RECIPE_OF_THE_DAY:
                    fragment = setupRecipeDetailFragment(position);
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case MY_RECIPES:
                    fragmentManager.beginTransaction().replace(R.id.container, new MyRecipesFragment()).commit();
                    break;
                case FEEDBACK:
                    fragment = setupFeedbackFragment(position);
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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

    private Fragment setupFeedbackFragment(int position) {
        final Fragment fragment = new FeedbackFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onSectionAttached(int position) {
        switch (Section.values()[position]) {
            case RECIPE_OF_THE_DAY:
                lastScreenTitle = getString(R.string.recipe_of_the_day_menu);
                break;
            case MY_RECIPES:
                lastScreenTitle = getString(R.string.my_recipes_menu);
                break;
            case FEEDBACK:
                lastScreenTitle = getString(R.string.feedback_menu);
                break;
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

            // Get the SearchView and set the searchable configuration
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            // Assumes current activity is the searchable activity
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
            searchView.setOnQueryTextListener(new OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    fragment = new SearchResultsFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, RecipeSearchRecentSuggestionsProvider.AUTHORITY, RecipeSearchRecentSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doSearch(query, ApplicationConstants.START_INDEX);
        }
    }

    public void doSearch(String query, String from) {
        final String url = UriBuilder.createQueryUri(query, from).toString();
        final GsonRequest<RecipeQueryResult> request = new GsonRequest<>(url, RecipeQueryResult.class, new RecipeQueryResultListener(fragment.getRecipeListAdapter()), new VolleyErrorListener());

        ApplicationRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            //startActivity(new Intent(this, SearchResultsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private enum Section {
        RECIPE_OF_THE_DAY,
        MY_RECIPES,
        FEEDBACK
    }
}
