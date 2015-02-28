package com.zzolta.android.glutenfreerecipes.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class RecipeDetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        TextView viewById = (TextView) findViewById(R.id.recipe_id);
        viewById.setText(getIntent().getStringExtra(ApplicationConstants.RECIPE_ID));
    }
}
