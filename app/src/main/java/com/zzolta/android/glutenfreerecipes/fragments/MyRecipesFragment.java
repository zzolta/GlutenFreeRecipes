package com.zzolta.android.glutenfreerecipes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.activities.MainActivity;
import com.zzolta.android.glutenfreerecipes.activities.RecipeDetailActivity;
import com.zzolta.android.glutenfreerecipes.adapters.RecipeListAdapter;
import com.zzolta.android.glutenfreerecipes.content.Contract;
import com.zzolta.android.glutenfreerecipes.persistence.database.RecipeDBHelper;
import com.zzolta.android.glutenfreerecipes.persistence.database.entities.Recipe;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Zolta.Szekely on 2015-04-02.
 */
public class MyRecipesFragment extends Fragment {
    private static final String LOG_TAG = MyRecipesFragment.class.getName();
    private RecipeListAdapter recipeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ListView listView = (ListView) inflater.inflate(R.layout.activity_search, container, false);

        recipeListAdapter = new RecipeListAdapter(getActivity());
        listView.setAdapter(recipeListAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Recipe recipe = (Recipe) recipeListAdapter.getItem(position);
                final Intent intent = new Intent(getActivity().getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra(ApplicationConstants.RECIPE_ID, recipe.getId());
                startActivity(intent);
            }
        });

        addRecipes(search());

        return listView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ApplicationConstants.ARG_SECTION_NUMBER));
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