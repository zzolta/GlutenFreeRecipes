package com.zzolta.android.gfrecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.adapters.RecipeListAdapter;
import com.zzolta.android.gfrecipes.content.Contract;
import com.zzolta.android.gfrecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.gfrecipes.persistence.database.entities.Recipe;
import com.zzolta.android.gfrecipes.utils.ApplicationConstants;

import java.sql.SQLException;
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
public class MyRecipesFragment extends Fragment {
    private static final String LOG_TAG = MyRecipesFragment.class.getName();
    private RecipeListAdapter recipeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listView = (ListView) inflater.inflate(R.layout.recipe_list, container, false);

        recipeListAdapter = new RecipeListAdapter(getActivity());
        listView.setAdapter(recipeListAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Recipe recipe = (Recipe) recipeListAdapter.getItem(position);

                final Bundle bundle = new Bundle();
                bundle.putString(ApplicationConstants.RECIPE_ID, recipe.getId());
                final RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                recipeDetailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, recipeDetailFragment).commit();
            }
        });

        addRecipes(search());

        return listView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        if (activity instanceof ActionBarActivity) {
            ((ActionBarActivity) activity).getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ab_solid_example));
        }
    }

    private void addRecipes(List<Recipe> recipes) {
        if (recipes != null && recipes.size() > 0) {
            recipeListAdapter.addRecipes(recipes);
            recipeListAdapter.notifyDataSetChanged();
        }
    }

    private List<Recipe> search() {
        try {
            final Dao<Recipe, String> dao = RecipeDBHelper.getInstance(getActivity().getApplicationContext()).getDao();
            final QueryBuilder<Recipe, String> recipeStringQueryBuilder = dao.queryBuilder();
            recipeStringQueryBuilder.where().eq(Contract.Recipe.MY_RECIPE, true);
            recipeStringQueryBuilder.orderBy(Contract.Recipe._ID, true);
            return dao.query(recipeStringQueryBuilder.prepare());
        }
        catch (final SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
