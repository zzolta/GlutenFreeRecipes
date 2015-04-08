package com.zzolta.android.glutenfreerecipes.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.RecipeDetailFragment;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class RecipeDetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        if (savedInstanceState == null) {
            if (findViewById(R.id.recipe_detail_container) != null) {
                //getFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, new RecipeDetailFragment()).commit();
            } else {
                getFragmentManager().beginTransaction().add(R.id.container, new RecipeDetailFragment()).commit();
            }
        }
    }
}
