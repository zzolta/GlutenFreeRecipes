package com.zzolta.android.glutenfreerecipes.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.adapters.GroupingAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.Image;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
public class RecipeDetailFragment extends Fragment {
    private static final String FONTS_SNICKLES_TTF = "fonts/Snickles.ttf";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_recipe_detail, container, false);
        return rootView;
    }

    protected Listener<RecipeDetailResult> getRecipeQueryResultListener() {
        return new Listener<RecipeDetailResult>() {
            @Override
            public void onResponse(RecipeDetailResult recipeDetailResult) {
                loadData(recipeDetailResult);
            }
        };
    }

    protected void loadData(RecipeDetailResult recipeDetailResult) {
        setName(recipeDetailResult.getName());

        loadImage(recipeDetailResult.getImages());

        loadGroups(recipeDetailResult.getIngredientLines());
    }

    protected void loadData(Recipe recipe) {
        setName(recipe.getName());

        //TODO: save image too so that it can be loaded offline
        loadImage(null);

        loadGroups(recipe.getIngredients());
    }

    protected ErrorListener getErrorListener() {
        return new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                throw new RuntimeException(volleyError);
            }
        };
    }

    private void setName(String name) {
        final TextView recipeName = (TextView) rootView.findViewById(R.id.recipe_name);
        recipeName.setText(name);
        final Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), FONTS_SNICKLES_TTF);
        recipeName.setTypeface(typeFace);
    }

    private void loadGroups(List<String> ingredientLines) {
        final ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.details);

        final List<String> groups = new ArrayList<>(Arrays.asList(getString(R.string.ingredients), getString(R.string.details)));
        final List<List<String>> groupedItems = new ArrayList<>(2);
        groupedItems.add(ingredientLines);
        groupedItems.add(new ArrayList<String>(0));

        final ExpandableListAdapter groupingAdapter = createExpandableListAdapter(groups, groupedItems);
        expandableListView.setAdapter(groupingAdapter);
        expandableListView.expandGroup(0);
    }

    private ExpandableListAdapter createExpandableListAdapter(List<String> groups, List<List<String>> groupedItems) {
        return new GroupingAdapter()
                   .setGroups(groups)
                   .setGroupLayoutResource(R.layout.group)
                   .setGroupedItems(groupedItems)
                   .setGroupedItemsLayoutResource(R.layout.row)
                   .setGroupedItemId(R.id.groupedItem)
                   .setInflater((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    private void loadImage(List<Image> images) {
        final ImageView recipeImage = (ImageView) rootView.findViewById(R.id.recipe_image);
        //TODO: consider using ApplicationRequestQueue Volley implementation instead Picasso
        //TODO: only positive flow is implemented
        if (images != null && images.size() > 0) {
            Picasso.with(getActivity()).load(images.get(0).getHostedLargeUrl()).placeholder(R.mipmap.recipe_big_placeholder).into(recipeImage);
        } else {
            Picasso.with(getActivity()).load(R.mipmap.recipe_big_placeholder).into(recipeImage);
        }
    }
}
