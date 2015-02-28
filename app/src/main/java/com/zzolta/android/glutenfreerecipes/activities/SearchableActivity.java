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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.jsonparse.Match;
import com.zzolta.android.glutenfreerecipes.jsonparse.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.DevelopmentConstants;
import com.zzolta.android.glutenfreerecipes.utils.OfflineRecipeQueryResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class SearchableActivity extends ActionBarActivity {

    private ArrayAdapter<String> recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        recipeAdapter = new ArrayAdapter<>(this, R.layout.list_item_recipe, R.id.list_item_recipe_textview, new ArrayList<String>(10));
        final ListView listView = (ListView) findViewById(R.id.list_recipes);
        listView.setAdapter(recipeAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String recipe = recipeAdapter.getItem(position);
                final Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra(ApplicationConstants.RECIPE_ID, recipe);
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
                doSearch(query);
            }
        }
    }

    private void doSearch(String query) {

        final Listener<RecipeQueryResult> listener = getRecipeQueryResultListener();

        final ErrorListener errorListener = getErrorListener();

        final String url = UriBuilder.createUri(query).toString();

        final GsonRequest<RecipeQueryResult> request = new GsonRequest<>(url, RecipeQueryResult.class, listener, errorListener);

        ApplicationRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

    private Listener<RecipeQueryResult> getRecipeQueryResultListener() {
        return new Listener<RecipeQueryResult>() {
            @Override
            public void onResponse(RecipeQueryResult recipeQueryResult) {
                updateSearchList(recipeQueryResult);
            }
        };
    }

    private void updateSearchList(RecipeQueryResult recipeQueryResult) {
        final List<Match> recipes = recipeQueryResult.getMatches();
        final Collection<String> recipeIDs = new ArrayList<>(recipes.size());
        for (final Match recipe : recipes) {
            recipeIDs.add(recipe.getId());
        }
        if (recipeIDs.size() > 0) {
            recipeAdapter.clear();
            recipeAdapter.addAll(recipeIDs);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recipeAdapter.notifyDataSetChanged();
            }
        });
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