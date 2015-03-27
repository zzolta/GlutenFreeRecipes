package com.zzolta.android.glutenfreerecipes.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.adapters.GroupingAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.Image;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.ImageUrlsBySize;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.net.GsonRequest;
import com.zzolta.android.glutenfreerecipes.net.UriBuilder;
import com.zzolta.android.glutenfreerecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.DevelopmentConstants;
import com.zzolta.android.glutenfreerecipes.utils.OfflineRecipeDetailResult;
import com.zzolta.parallax.ParallaxScrollAppCompat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-03-24.
 */
public class RecipeDetailFragment extends Fragment {
    public static final String LOG_TAG = RecipeDetailFragment.class.getName();
    private ParallaxScrollAppCompat parallaxScrollAppCompat;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = parallaxScrollAppCompat.createView(inflater);

        final String recipeID = getActivity().getIntent().getStringExtra(ApplicationConstants.RECIPE_ID);
        final Recipe recipe = getRecipeFromDatabase(recipeID);
        if (recipe != null) {
            loadData(recipe);
        } else {
            if (DevelopmentConstants.OFFLINE) {
                loadData(new Gson().fromJson(OfflineRecipeDetailResult.pizzaFriesRecipeDetailResult, RecipeDetailResult.class));
            } else {
                final String url = UriBuilder.createGetUri(recipeID).toString();

                final GsonRequest<RecipeDetailResult> request = new GsonRequest<>(url, RecipeDetailResult.class, getRecipeQueryResultListener(), getErrorListener());

                ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
            }
        }

        return view;
    }

    protected Listener<RecipeDetailResult> getRecipeQueryResultListener() {
        return new RecipeDetailResultListener();
    }

    protected void loadData(RecipeDetailResult recipeDetailResult) {
        setName(recipeDetailResult.getName());

        loadImage(getImage(recipeDetailResult));

        addHyperlinkToImage(recipeDetailResult.getSource().getSourceRecipeUrl());

        loadIngredients(recipeDetailResult.getIngredientLines());
    }

    protected void loadData(Recipe recipe) {
        setName(recipe.getName());

        loadImage(recipe.getImagePath());

        addHyperlinkToImage(recipe.getSourceRecipeUrl());

        loadIngredients(recipe.getIngredients());
    }

    private void setName(String name) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(name);
    }

    private void loadImage(String imagePath) {
        final NetworkImageView recipeImage = (NetworkImageView) view.findViewById(R.id.recipe_image);
        recipeImage.setDefaultImageResId(R.mipmap.recipe_big_placeholder);
        if (imagePath != null) {
            recipeImage.setImageUrl(imagePath, ApplicationRequestQueue.getInstance(getActivity().getApplicationContext()).getImageLoader());
        }
    }

    private void addHyperlinkToImage(final String sourceRecipeUrl) {
        final ImageView recipeImage = (ImageView) view.findViewById(R.id.recipe_image);
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

    private void loadIngredients(List<String> ingredients) {
        final ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.parallaxList);

        final List<String> groups = new ArrayList<>(Arrays.asList(getString(R.string.ingredients), getString(R.string.details)));
        final List<List<String>> groupedItems = new ArrayList<>(2);
        groupedItems.add(ingredients);
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

    protected ErrorListener getErrorListener() {
        return new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                throw new RuntimeException(volleyError);
            }
        };
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        parallaxScrollAppCompat = new ParallaxScrollAppCompat()
                            .actionBarBackground(R.drawable.actionbar_background)
                            .headerLayout(R.layout.header)
                            .contentLayout(R.layout.parallax_listview)
                            .lightActionBar(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        parallaxScrollAppCompat.initActionBar(getActivity());
    }

    private Recipe getRecipeFromDatabase(String recipeID) {
        try {
            return RecipeDBHelper.getInstance(getActivity().getApplicationContext()).getDao().queryForId(recipeID);
        }
        catch (final SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
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
                Log.e(LOG_TAG, e.getMessage());
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
