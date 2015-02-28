package com.zzolta.android.glutenfreerecipes.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.jsonparse.RecipeQueryResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class SearcheableActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

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
            String query = intent.getStringExtra(SearchManager.QUERY);
            doVolley(query);
        }
    }

    private void doVolley(String query) {

        final Listener<RecipeQueryResult> listener = new Listener<RecipeQueryResult>() {
            @Override
            public void onResponse(RecipeQueryResult recipeQueryResult) {

                process(recipeQueryResult);
                //we will do something with this
            }

            private void process(RecipeQueryResult recipeQueryResult) {

            }
        };

        final ErrorListener errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                throw new RuntimeException(volleyError);
            }
        };

        GsonRequest request = new GsonRequest(UriBuilder.createUri(query).toString(), RecipeQueryResult.class, listener, errorListener);

        ApplicationRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

    private void doSearch(String query) {
        final String[] recipes = {"Onion Soup", "Chicken soup", "Beans soup"};
        final List<String> recipeList = Arrays.asList(recipes);
        final ArrayAdapter<String> recipeAdapter = new ArrayAdapter<>(this, R.layout.list_item_recipe, R.id.list_item_recipe_textview, recipeList);
        final ListView listView = (ListView) findViewById(R.id.list_recipes);
        listView.setAdapter(recipeAdapter);
    }
}
