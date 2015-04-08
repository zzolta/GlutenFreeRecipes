package com.zzolta.android.glutenfreerecipes.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.FeedbackFragment;
import com.zzolta.android.glutenfreerecipes.fragments.NavigationDrawerFragment;
import com.zzolta.android.glutenfreerecipes.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.zzolta.android.glutenfreerecipes.fragments.RecipeDetailFragment;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;
import com.zzolta.android.glutenfreerecipes.utils.CuisineHelper;

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
        switch (Section.values()[position]) {
            case RECIPE_OF_THE_DAY:
                fragment = setupRecipeDetailFragment(position);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                break;
            case SEARCH:
                startActivity(new Intent(getApplicationContext(), SearchResultsActivity.class));
                break;
            case MY_RECIPES:
                startActivity(new Intent(getApplicationContext(), MyRecipesActivity.class));
                break;
            case FEEDBACK:
                fragment = setupFeedbackFragment(position);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                break;
            default:
                break;
        }
    }

    private Fragment setupRecipeDetailFragment(int position) {
        final Fragment fragment = new RecipeDetailFragment();
        final Bundle bundle = new Bundle();
        final String recipeOfTheDay = getRecipeOfTheDay();
        if (recipeOfTheDay != null) {
            bundle.putString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, recipeOfTheDay);
        }
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Fragment setupFeedbackFragment(int position) {
        final Fragment fragment = new FeedbackFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(ApplicationConstants.ARG_SECTION_NUMBER, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onSectionAttached(int position) {
        switch (Section.values()[position]) {
            case RECIPE_OF_THE_DAY:
                mTitle = getString(R.string.recipe_of_the_day_menu);
                break;
            case SEARCH:
                mTitle = getString(R.string.search_menu);
                break;
            case MY_RECIPES:
                mTitle = getString(R.string.my_recipes_menu);
                break;
            case FEEDBACK:
                mTitle = getString(R.string.feedback_menu);
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
            startActivity(new Intent(this, SearchResultsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    private String getRecipeOfTheDay() {
        String recipeOfTheDay = null;
        final SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.PRIVATE_STORAGE, Context.MODE_PRIVATE);
        final String dayOfYear = sharedPreferences.getString(ApplicationConstants.DAY_OF_YEAR, null);
        if (dayOfYear != null && dayOfYear.equals(String.valueOf(CuisineHelper.getDayOfYear()))) {
            recipeOfTheDay = sharedPreferences.getString(ApplicationConstants.RECIPE_OF_THE_DAY_ID, null);
        }
        return recipeOfTheDay;
    }

    private enum Section {
        RECIPE_OF_THE_DAY,
        SEARCH,
        MY_RECIPES,
        FEEDBACK
    }
}
