package com.zzolta.parallax;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Zolta.Szekely on 2015-03-22.
 */
public class ParallaxScrollAppCompat extends ParallaxScroll {

    private ActionBar mActionBar;

    @Override
    public void initActionBar(Activity activity) {
        mActionBar = getActionBar(activity);
        super.initActionBar(activity);
    }

    @Override
    protected int getActionBarHeight() {
        return mActionBar.getHeight();
    }

    @Override
    protected boolean isActionBarNull() {
        return mActionBar == null;
    }

    @Override
    protected void setActionBarBackgroundDrawable(Drawable drawable) {
        mActionBar.setBackgroundDrawable(drawable);
    }

    private ActionBar getActionBar(Activity activity) {
        if (activity instanceof ActionBarActivity) {
            return ((ActionBarActivity) activity).getSupportActionBar();
        }
        final ActionBar actionBar = getActionBarWithReflection(activity, "getSupportActionBar");
        if (actionBar == null) {
            throw new RuntimeException("Activity should derive from ActionBarActivity "
                                       + "or implement a method called getSupportActionBar");
        }
        return actionBar;
    }
}
