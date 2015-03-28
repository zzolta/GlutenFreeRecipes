package com.zzolta.android.glutenfreerecipes.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.adapters.RecipeListAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.Match;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipequery.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.listeners.EndlessScrollListener;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.DevelopmentConstants;
import com.zzolta.android.glutenfreerecipes.utils.OfflineRecipeQueryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class SearchActivity extends ActionBarActivity {

    private RecipeListAdapter recipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        recipeListAdapter = new RecipeListAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.list_recipes);
        listView.setAdapter(recipeListAdapter);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                final String query = getIntent().getStringExtra(SearchManager.QUERY);
                doSearch(query, String.valueOf(page * ApplicationConstants.MAX_RESULT_VALUE));
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Recipe recipe = (Recipe) recipeListAdapter.getItem(position);
                final Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra(ApplicationConstants.RECIPE_ID, recipe.getId());
                startActivity(intent);
            }
        });

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            if (DevelopmentConstants.OFFLINE) {
                updateSearchList(new Gson().fromJson(OfflineRecipeQueryResult.pizzaRecipeQueryResult, RecipeQueryResult.class));
            } else {
                final String query = intent.getStringExtra(SearchManager.QUERY);
                doSearch(query, ApplicationConstants.START_INDEX);
            }
        }
    }

    private void doSearch(String query, String from) {

        final Listener<RecipeQueryResult> listener = getRecipeQueryResultListener();

        final ErrorListener errorListener = getErrorListener();

        final String url = UriBuilder.createQueryUri(query, from).toString();

        final GsonRequest<RecipeQueryResult> request = new GsonRequest<>(url, RecipeQueryResult.class, listener, errorListener);

        ApplicationRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

    private Listener<RecipeQueryResult> getRecipeQueryResultListener() {
        return new RecipeQueryResultListener();
    }

    private void updateSearchList(RecipeQueryResult recipeQueryResult) {
        final List<Match> matches = recipeQueryResult.getMatches();

        final List<Recipe> recipes = matchRecipes(matches);

        if (recipes.size() > 0) {
            recipeListAdapter.addRecipes(recipes);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recipeListAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<Recipe> matchRecipes(List<Match> matches) {
        final List<Recipe> recipes = new ArrayList<>(matches.size());
        for (final Match match : matches) {
            recipes.add(new Recipe()
                            .setId(match.getId())
                            .setName(match.getRecipeName())
                            .setImagePath(match.getImageUrlsBySize().get90())
                            .setRating(match.getRating())
                            .setTotalTimeInSeconds(match.getTotalTimeInSeconds()));
        }
        return recipes;
    }

    private ErrorListener getErrorListener() {
        return new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                throw new RuntimeException(volleyError);
            }
        };
    }

    private class RecipeQueryResultListener implements Listener<RecipeQueryResult> {
        @Override
        public void onResponse(RecipeQueryResult recipeQueryResult) {
            updateSearchList(recipeQueryResult);
        }
    }
}
