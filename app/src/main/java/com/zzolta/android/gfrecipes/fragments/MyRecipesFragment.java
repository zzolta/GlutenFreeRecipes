package com.zzolta.android.gfrecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.activities.MainActivity;
import com.zzolta.android.gfrecipes.activities.MainActivity.Section;
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
    private boolean twoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listView = (ListView) inflater.inflate(R.layout.recipe_list, container, false);

        final Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            twoPane = ((MainActivity) activity).isTwoPane();
        }
        if (twoPane) {
            final View viewById = activity.findViewById(R.id.recipe_detail);
            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewById.getLayoutParams();
            params.weight = 4;
            viewById.setLayoutParams(params);
        }
        recipeListAdapter = new RecipeListAdapter(activity);
        listView.setAdapter(recipeListAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Recipe recipe = (Recipe) recipeListAdapter.getItem(position);

                final Bundle bundle = new Bundle();
                bundle.putString(ApplicationConstants.RECIPE_ID, recipe.getId());
                bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, Section.RECIPE_DETAIL.ordinal());
                final RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                recipeDetailFragment.setArguments(bundle);
                if (twoPane) {
                    getFragmentManager().beginTransaction().replace(R.id.recipe_detail, recipeDetailFragment).commit();
                } else {
                    getFragmentManager().beginTransaction().replace(R.id.container, recipeDetailFragment).addToBackStack(null).commit();
                }
            }
        });

        addRecipes(search());

        setHasOptionsMenu(true);

        return listView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            final MainActivity mainActivity = (MainActivity) activity;
            mainActivity.onSectionAttached(getArguments().getInt(ApplicationConstants.ARG_SECTION_NUMBER));
            mainActivity.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ab_solid_example));
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        final MenuItem searchMenu = menu.findItem(R.id.search);
        if (searchMenu != null) {
            searchMenu.setVisible(false);
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
