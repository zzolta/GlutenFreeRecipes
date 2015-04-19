package com.zzolta.android.gfrecipes.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.zzolta.android.gfrecipes.R;
import com.zzolta.android.gfrecipes.fragments.MyRecipesFragment;

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
        } else {
            twoPane = findViewById(R.id.recipe_list) != null;
        }

        getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ab_solid_example));
    }

    public boolean isTwoPane() {
        return twoPane;
    }
}
