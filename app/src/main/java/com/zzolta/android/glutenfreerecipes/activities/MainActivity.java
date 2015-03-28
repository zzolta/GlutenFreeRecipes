package com.zzolta.android.glutenfreerecipes.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.fragments.NavigationDrawerFragment;
import com.zzolta.android.glutenfreerecipes.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;
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
        // update the main content by replacing fragments
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.container, MainFragment.newInstance(position + 1))
                       .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_my_recipes);
                break;
            case 3:
                mTitle = getString(R.string.title_settings);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainFragment extends Fragment {

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MainFragment newInstance(int sectionNumber) {
            final MainFragment fragment = new MainFragment();
            final Bundle args = new Bundle();
            args.putInt(ApplicationConstants.ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ApplicationConstants.ARG_SECTION_NUMBER));
        }
    }
}
