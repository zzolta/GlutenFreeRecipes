package com.zzolta.android.gfrecipes.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.net.ApplicationRequestQueue;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;

import java.util.ArrayList;
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
public class RecipeListAdapter extends BaseAdapter {
    private static final int SECONDS_IN_MINUTE = 60;
    private final Activity activity;
    private final List<Recipe> recipes = new ArrayList<>(0);
    private LayoutInflater inflater;

    public RecipeListAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addRecipes(final List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_recipe, null);
        }
        final NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        final TextView recipeName = (TextView) convertView.findViewById(R.id.recipe_name);
        final TextView rating = (TextView) convertView.findViewById(R.id.rating);
        final TextView totalTime = (TextView) convertView.findViewById(R.id.total_time);

        final Recipe recipe = recipes.get(position);

        thumbNail.setImageUrl(recipe.getImagePath(), ApplicationRequestQueue.getInstance(activity.getApplicationContext()).getImageLoader());
        recipeName.setText(recipe.getName());
        rating.setText(String.format("Rating: %d", recipe.getRating()));
        totalTime.setText(getTotalTimeInMinutes(recipe.getTotalTimeInSeconds()));

        return convertView;
    }

    private String getTotalTimeInMinutes(@Nullable Integer totalTimeInSeconds) {
        return totalTimeInSeconds != null ? String.format("Total time: %d minutes", totalTimeInSeconds / SECONDS_IN_MINUTE) : "";
    }
}
