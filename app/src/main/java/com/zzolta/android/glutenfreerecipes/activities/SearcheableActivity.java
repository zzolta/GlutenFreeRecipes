package com.zzolta.android.glutenfreerecipes.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.jsonparse.RecipeQueryResult;
import org.json.JSONObject;

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
        Uri uri = new Builder()
                      .scheme("http")
                      .authority("api.yummly.com")
                      .appendPath("v1")
                      .appendPath("api")
                      .appendPath("recipes")
                      .appendQueryParameter("_app_id", "514ded55")
                      .appendQueryParameter("_app_key", "f73d3a223a64565997bf9a08c5f9cb8b")
                      .appendQueryParameter("q", query)
                      .appendQueryParameter("requirePictures", "true")
                      .appendQueryParameter("allowedAllergy[]", "393^Gluten-Free")
                      .appendQueryParameter("maxResult", "10")
                      .appendQueryParameter("start", "0")
                      .build();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                RecipeQueryResult recipeQueryResult = new Gson().fromJson(jsonObject.toString(), RecipeQueryResult.class);
                process(recipeQueryResult);
                //we will do something with this
            }

            private void process(RecipeQueryResult recipeQueryResult) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                throw new RuntimeException(volleyError);
            }
        });

        queue.add(request);
    }

    private void doSearch(String query) {
        final String[] recipes = {"Onion Soup", "Chicken soup", "Beans soup"};
        final List<String> recipeList = Arrays.asList(recipes);
        final ArrayAdapter<String> recipeAdapter = new ArrayAdapter<>(this, R.layout.list_item_recipe, R.id.list_item_recipe_textview, recipeList);
        final ListView listView = (ListView) findViewById(R.id.list_recipes);
        listView.setAdapter(recipeAdapter);
    }
}
