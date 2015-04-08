package com.zzolta.android.glutenfreerecipes.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.MyRecipesFragment;

/**
 * Created by Zolta.Szekely on 2015-04-08.
 */
public class MyRecipesActivity extends ActionBarActivity {
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_recipes);

        if (savedInstanceState == null) {
            if (findViewById(R.id.recipe_list) != null) {
                twoPane = true;
                final MyRecipesFragment myRecipesFragment = ((MyRecipesFragment) getFragmentManager().findFragmentById(R.id.recipe_list));
                getFragmentManager().beginTransaction().replace(R.id.recipe_list, myRecipesFragment).commit();
            } else {
                twoPane = false;
                getFragmentManager().beginTransaction().add(R.id.container, new MyRecipesFragment()).commit();
            }
        }

        getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.color.list_row));
    }

    public boolean isTwoPane() {
        return twoPane;
    }
}
