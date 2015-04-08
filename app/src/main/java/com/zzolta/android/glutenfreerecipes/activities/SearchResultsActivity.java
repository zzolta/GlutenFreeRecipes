package com.zzolta.android.glutenfreerecipes.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.SearchResultsFragment;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public class SearchResultsActivity extends ActionBarActivity {
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            if (findViewById(R.id.recipe_list) != null) {
                twoPane = true;
                final SearchResultsFragment searchResultsFragment = ((SearchResultsFragment) getFragmentManager().findFragmentById(R.id.recipe_list));
                getFragmentManager().beginTransaction().replace(R.id.recipe_list, searchResultsFragment).commit();
            } else {
                twoPane = false;
                getFragmentManager().beginTransaction().add(R.id.container, new SearchResultsFragment()).commit();
            }
        }

        getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.color.list_row));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        return true;
    }

    public boolean isTwoPane() {
        return twoPane;
    }
}
