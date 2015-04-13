package com.zzolta.android.glutenfreerecipes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import com.android.volley.toolbox.NetworkImageView;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.activities.DirectionsActivity;
import com.zzolta.android.glutenfreerecipes.adapters.GroupingAdapter;
import com.zzolta.android.glutenfreerecipes.jsonparse.recipedetail.RecipeDetailResult;
import com.zzolta.android.glutenfreerecipes.net.ApplicationRequestQueue;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.providers.RecipeDetailShareActionProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public final class RecipeDetailHelper {
    private static final RecipeDetailHelper INSTANCE = new RecipeDetailHelper();
    private Activity mActivity;
    private View mView;
    private String currentRecipeId;

    private RecipeDetailHelper() {
    }

    public static RecipeDetailHelper getInstance() {
        return INSTANCE;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public void loadData(Recipe recipe) {
        currentRecipeId = recipe.getId();
        loadImage(recipe.getImagePath());

        loadRecipeData(recipe.getName(), recipe.getIngredients(), recipe.getSourceRecipeUrl());

        final ShareActionProvider shareActionProvider = RecipeDetailShareActionProvider.getInstance().getShareActionProvider();
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(createShareRecipeIntent(recipe.getSourceRecipeUrl()));
        }
    }

    public void loadData(RecipeDetailResult recipeDetailResult) {
        loadData(RecipeHelper.convertRecipe(recipeDetailResult));
    }

    public String getCurrentRecipeId() {
        return currentRecipeId;
    }

    private Intent createShareRecipeIntent(String sourceRecipeUrl) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sourceRecipeUrl);
        return shareIntent;
    }

    private void loadImage(String imagePath) {
        final NetworkImageView recipeImage = (NetworkImageView) mView.findViewById(R.id.recipe_image);
        recipeImage.setDefaultImageResId(R.mipmap.recipe_placeholder);
        if (imagePath != null) {
            recipeImage.setImageUrl(imagePath, ApplicationRequestQueue.getInstance(mActivity.getApplicationContext()).getImageLoader());
        }
    }

    private void loadRecipeData(final String name, List<String> ingredients, final String sourceRecipeUrl) {
        final ExpandableListView expandableListView = (ExpandableListView) mView.findViewById(R.id.parallaxList);

        final List<String> groups = new ArrayList<>(Arrays.asList(name, mActivity.getResources().getString(R.string.ingredients), mActivity.getResources().getString(R.string.directions)));
        final List<List<String>> groupedItems = new ArrayList<>(3);
        groupedItems.add(Collections.<String>emptyList());
        groupedItems.add(ingredients);
        groupedItems.add(Collections.<String>emptyList());

        final ExpandableListAdapter groupingAdapter = createExpandableListAdapter(groups, groupedItems);
        expandableListView.setAdapter(groupingAdapter);
        expandableListView.expandGroup(1);

        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition == 2) {
                    final Intent intent = new Intent(mActivity, DirectionsActivity.class);
                    intent.putExtra(ApplicationConstants.URL, sourceRecipeUrl);
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    private ExpandableListAdapter createExpandableListAdapter(List<String> groups, List<List<String>> groupedItems) {
        return new GroupingAdapter()
                   .setGroups(groups)
                   .setGroupLayoutResource(R.layout.group)
                   .setGroupedItems(groupedItems)
                   .setGroupedItemsLayoutResource(R.layout.row)
                   .setGroupedItemId(R.id.groupedItem)
                   .setInflater((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }
}
