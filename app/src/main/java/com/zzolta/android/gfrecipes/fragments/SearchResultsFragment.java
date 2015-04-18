package com.zzolta.android.gfrecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.activities.MainActivity;
import com.zzolta.android.gfrecipes.activities.RecipeDetailActivity;
import com.zzolta.android.gfrecipes.adapters.RecipeListAdapter;
import com.zzolta.android.gfrecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.gfrecipes.listeners.EndlessScrollListener;
import com.zzolta.android.gfrecipes.listeners.RecipeQueryResultListener;
import com.zzolta.android.gfrecipes.listeners.VolleyErrorListener;
import com.zzolta.android.gfrecipes.net.ApplicationRequestQueue;
import com.zzolta.android.gfrecipes.net.GsonRequest;
import com.zzolta.android.gfrecipes.net.UriBuilder;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;
import com.zzolta.android.gfrecipes.providers.RecipeSearchRecentSuggestionsProvider;
import com.zzolta.android.gfrecipes.utils.ApplicationConstants;

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
public class SearchResultsFragment extends Fragment {
    private RecipeListAdapter recipeListAdapter;
    private Intent searchIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listView = (ListView) inflater.inflate(R.layout.recipe_list, container, false);

        recipeListAdapter = new RecipeListAdapter(getActivity());
        listView.setAdapter(recipeListAdapter);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                final String query = searchIntent.getStringExtra(SearchManager.QUERY);
                doSearch(query, String.valueOf(page * ApplicationConstants.MAX_RESULT_VALUE));
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() instanceof MainActivity) {
                    final Recipe recipe = (Recipe) recipeListAdapter.getItem(position);

                    final Intent intent = new Intent(getActivity().getApplicationContext(), RecipeDetailActivity.class);
                    intent.putExtra(ApplicationConstants.RECIPE_ID, recipe.getId());
                    startActivity(intent);
                }
            }
        });

        return listView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        if (activity instanceof ActionBarActivity) {
            ((ActionBarActivity) activity).getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ab_solid_example));
        }
    }

    public RecipeListAdapter getRecipeListAdapter() {
        return recipeListAdapter;
    }

    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchIntent = intent;
            final String query = intent.getStringExtra(SearchManager.QUERY);
            final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getActivity().getApplicationContext(), RecipeSearchRecentSuggestionsProvider.AUTHORITY, RecipeSearchRecentSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doSearch(query, ApplicationConstants.START_INDEX);
        }
    }

    private void doSearch(String query, String from) {
        final String url = UriBuilder.createQueryUri(query, from).toString();

        final GsonRequest<RecipeQueryResult> request = new GsonRequest<>(url, RecipeQueryResult.class, new RecipeQueryResultListener(recipeListAdapter), new VolleyErrorListener());

        ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }
}
