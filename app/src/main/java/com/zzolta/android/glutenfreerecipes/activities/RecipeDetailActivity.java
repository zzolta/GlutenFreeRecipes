package com.zzolta.android.glutenfreerecipes.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.adapters.GroupingAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.Image;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.DevelopmentConstants;
import com.zzolta.android.glutenfreerecipes.utils.OfflineRecipeDetailResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class RecipeDetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        final String recipeID = getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);

        if (DevelopmentConstants.OFFLINE) {
            loadData(new Gson().fromJson(OfflineRecipeDetailResult.pizzaFriesRecipeDetailResult, RecipeDetailResult.class));
        } else {
            final Listener<RecipeDetailResult> listener = getRecipeQueryResultListener();

            final ErrorListener errorListener = getErrorListener();

            final String url = UriBuilder.createGetUri(recipeID).toString();

            final GsonRequest<RecipeDetailResult> request = new GsonRequest<>(url, RecipeDetailResult.class, listener, errorListener);

            ApplicationRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(request);
        }
    }

    private Listener<RecipeDetailResult> getRecipeQueryResultListener() {
        return new Listener<RecipeDetailResult>() {
            @Override
            public void onResponse(RecipeDetailResult recipeDetailResult) {
                loadData(recipeDetailResult);
            }
        };
    }

    private void loadData(RecipeDetailResult recipeDetailResult) {
        loadImage(recipeDetailResult);

        loadGroups(recipeDetailResult);
    }

    private void loadGroups(RecipeDetailResult recipeDetailResult) {
        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.details);

        final List<String> groups = new ArrayList<>(Arrays.asList(getString(R.string.ingredients), getString(R.string.details)));
        final List<List<String>> groupedItems = new ArrayList<>(2);
        groupedItems.add(recipeDetailResult.getIngredientLines());
        groupedItems.add(new ArrayList<String>(0));

        final ExpandableListAdapter groupingAdapter = createExpandableListAdapter(groups, groupedItems);
        expandableListView.setAdapter(groupingAdapter);
    }

    private ExpandableListAdapter createExpandableListAdapter(List<String> groups, List<List<String>> groupedItems) {
        return new GroupingAdapter()
                   .setGroups(groups)
                   .setGroupLayoutResource(R.layout.group)
                   .setGroupedItems(groupedItems)
                   .setGroupedItemsLayoutResource(R.layout.row)
                   .setGroupedItemId(R.id.groupedItem)
                   .setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    private void loadImage(RecipeDetailResult recipeDetailResult) {
        final ImageView recipeImage = (ImageView) findViewById(R.id.recipe_image);
        final List<Image> images = recipeDetailResult.getImages();
        //TODO: consider using ApplicationRequestQueue Volley implementation instead Picasso
        //TODO: only positive flow is implemented
        Picasso.with(this).load(images.get(0).getHostedLargeUrl()).placeholder(R.mipmap.recipe_big_placeholder).into(recipeImage);
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
