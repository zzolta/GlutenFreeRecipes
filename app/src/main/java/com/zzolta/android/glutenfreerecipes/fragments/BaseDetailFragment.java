package com.zzolta.android.glutenfreerecipes.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.adapters.GroupingAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.Image;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.ImageUrlsBySize;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
public class BaseDetailFragment extends Fragment {
    private static final String FONTS_SNICKLES_TTF = "fonts/Snickles.ttf";
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_recipe_detail, container, false);
        return rootView;
    }

    protected Listener<RecipeDetailResult> getRecipeQueryResultListener() {
        return new RecipeDetailResultListener();
    }

    protected void loadData(RecipeDetailResult recipeDetailResult) {
        setName(recipeDetailResult.getName());

        loadImage(getImage(recipeDetailResult));

        addHyperlinkToImage(recipeDetailResult.getSource().getSourceRecipeUrl());

        loadGroups(recipeDetailResult.getIngredientLines());
    }

    protected void loadData(Recipe recipe) {
        setName(recipe.getName());

        loadImage(recipe.getImagePath());

        addHyperlinkToImage(recipe.getSourceRecipeUrl());

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

    private void addHyperlinkToImage(final String sourceRecipeUrl) {
        final ImageView recipeImage = (ImageView) rootView.findViewById(R.id.recipe_image);
        recipeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(sourceRecipeUrl));
                startActivity(intent);
            }
        });
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

    private void loadImage(String imagePath) {
        final NetworkImageView recipeImage = (NetworkImageView) rootView.findViewById(R.id.recipe_image);
        recipeImage.setDefaultImageResId(R.mipmap.recipe_big_placeholder);
        if (imagePath != null) {
            recipeImage.setImageUrl(imagePath, ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).getImageLoader());
        }
    }

    @Nullable
    private String getImage(RecipeDetailResult recipeDetailResult) {
        String imageUrlsBySize360 = null;
        final List<Image> images = recipeDetailResult.getImages();
        if (images != null && images.size() > 0) {
            final Image image = images.get(0);
            if (image != null) {
                final ImageUrlsBySize imageUrlsBySize = image.getImageUrlsBySize();
                if (imageUrlsBySize != null) {
                    imageUrlsBySize360 = imageUrlsBySize.get360();
                }
            }
        }
        return imageUrlsBySize360;
    }

    private class RecipeDetailResultListener implements Listener<RecipeDetailResult> {
        @Override
        public void onResponse(RecipeDetailResult recipeDetailResult) {
            saveRecipe(convertRecipe(recipeDetailResult));
            loadData(recipeDetailResult);
        }

        private void saveRecipe(Recipe recipe) {
            try {
                RecipeDBHelper.getInstance(getActivity().getApplicationContext()).getDao().createOrUpdate(recipe);
            }
            catch (final SQLException e) {
                e.printStackTrace();
            }
        }

        private Recipe convertRecipe(RecipeDetailResult recipeDetailResult) {
            final Recipe recipe = new Recipe()
                                      .setId(recipeDetailResult.getId())
                                      .setName(recipeDetailResult.getName())
                                      .setIngredients(new ArrayList<>(recipeDetailResult.getIngredientLines()))
                                      .setRating(recipeDetailResult.getRating())
                                      .setTotalTimeInSeconds(recipeDetailResult.getTotalTimeInSeconds())
                                      .setImagePath(recipeDetailResult.getSource().getSourceRecipeUrl());

            final String imageUrlsBySize360 = getImage(recipeDetailResult);

            if (imageUrlsBySize360 != null) {
                recipe.setImagePath(imageUrlsBySize360);
            }

            return recipe;
        }
    }
}
