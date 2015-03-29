package com.zzolta.android.glutenfreerecipes.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.NavigationDrawerFragment;
import com.zzolta.android.glutenfreerecipes.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.zzolta.android.glutenfreerecipes.fragments.RecipeDetailFragment;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        final FragmentManager fragmentManager = getFragmentManager();
        final Fragment fragment;
        switch (position) {
            case 0:
                fragment = setupRecipeDetailFragment(position);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    private Fragment setupRecipeDetailFragment(int position) {
        final Fragment fragment = new RecipeDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(ApplicationConstants.RECIPE_ID, getRecipeOfTheDay());
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onSectionAttached(int position) {
        switch (position) {
            case 0:
                mTitle = getString(R.string.recipe_of_the_day_menu);
                break;
            case 1:
                mTitle = getString(R.string.search_menu);
                break;
            case 2:
                mTitle = getString(R.string.help_and_feedback_menu);
                break;
            default:
                break;
        }
    }

    public void restoreActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getRecipeOfTheDay() {
        final SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.PRIVATE_STORAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, null);
    }
}
