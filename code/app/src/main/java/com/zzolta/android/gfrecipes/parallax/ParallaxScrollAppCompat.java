package com.zzolta.android.gfrecipes.parallax;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/*
 * Copyright (C) 2015 Zolta Szekely
 * Copyright (C) 2013 Manuel Peinado
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
public class ParallaxScrollAppCompat extends ParallaxScroll {

    private ActionBar actionBar;

    @Override
    public void initActionBar(Activity activity) {
        actionBar = getActionBar(activity);
        super.initActionBar(activity);
    }

    @Override
    protected boolean isActionBarNull() {
        return actionBar == null;
    }

    @Override
    protected void setActionBarBackgroundDrawable(Drawable drawable) {
        actionBar.setBackgroundDrawable(drawable);
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
