package com.zzolta.android.gfrecipes.parallax;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.zzolta.android.gfrecipes.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
public abstract class ParallaxScroll {

    private static final String LOG_TAG = ParallaxScroll.class.getName();

    private final Drawable.Callback parallaxDrawableCallback = new ParallaxDrawableCallback();
    private final OnScrollListener parallaxScrollListener = new ParallaxScrollListener();

    private Drawable actionBarBackgroundDrawable;
    private int actionBarBackgroundResId;
    private FrameLayout headerContainer;
    private int headerLayoutResId;
    private View headerView;
    private int contentLayoutResId;
    private View contentView;
    private LayoutInflater layoutInflater;
    private int lastDampedScroll;
    private int lastHeaderHeight = -1;
    private boolean firstGlobalLayoutPerformed;
    private FrameLayout marginView;
    private View listViewBackgroundView;
    private int lastScrollPosition;

    public static int getDisplayHeight(Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        final int displayHeight = windowManager.getDefaultDisplay().getHeight();
        return displayHeight;
    }

    public final <T extends ParallaxScroll> T headerLayout(int layoutResId) {
        headerLayoutResId = layoutResId;
        return (T) this;
    }

    public final <T extends ParallaxScroll> T contentLayout(int layoutResId) {
        contentLayoutResId = layoutResId;
        return (T) this;
    }

    public final <T extends ParallaxScroll> T lightActionBar(boolean value) {
        return (T) this;
    }

    public final <T extends ParallaxScroll> T actionBarBackground(int drawableResId) {
        actionBarBackgroundResId = drawableResId;
        return (T) this;
    }

    public final View createView(LayoutInflater inflater) {

        layoutInflater = inflater;
        if (contentView == null) {
            contentView = inflater.inflate(contentLayoutResId, null);
        }

        final ListView listView = (ListView) contentView.findViewById(R.id.parallaxList);
        final View root = createListView(listView);

        // Use measured height here as an estimate of the header height, later on after the layout is complete
        // we'll use the actual height
        final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.MATCH_PARENT, MeasureSpec.EXACTLY);
        final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
        headerView.measure(widthMeasureSpec, heightMeasureSpec);
        updateHeaderHeight(headerView.getMeasuredHeight());

        root.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int headerHeight = headerContainer.getHeight();
                if (!firstGlobalLayoutPerformed && headerHeight != 0) {
                    updateHeaderHeight(headerHeight);
                    firstGlobalLayoutPerformed = true;
                }
            }
        });
        return root;
    }

    public void initActionBar(Activity activity) {
        if (actionBarBackgroundDrawable == null) {
            actionBarBackgroundDrawable = activity.getResources().getDrawable(actionBarBackgroundResId);
        }
        setActionBarBackgroundDrawable(actionBarBackgroundDrawable);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            actionBarBackgroundDrawable.setCallback(parallaxDrawableCallback);
        }
        actionBarBackgroundDrawable.setAlpha(255);
    }

    @Nullable
    protected <T> T getActionBarWithReflection(Activity activity, String methodName) {
        try {
            final Method method = activity.getClass().getMethod(methodName);
            return (T) method.invoke(activity);
        }
        catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassCastException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }

    protected abstract boolean isActionBarNull();

    protected abstract void setActionBarBackgroundDrawable(Drawable drawable);

    private View createListView(ListView listView) {
        final ViewGroup contentContainer = (ViewGroup) layoutInflater.inflate(R.layout.parallax_listview_container, null);
        contentContainer.addView(contentView);

        headerContainer = (FrameLayout) contentContainer.findViewById(R.id.parallax_header_container);
        addHeaderView(headerContainer, headerLayoutResId);

        marginView = new FrameLayout(listView.getContext());
        marginView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 0));
        listView.addHeaderView(marginView, null, false);

        // Make the background as high as the screen so that it fills regardless of the amount of scroll.
        listViewBackgroundView = contentContainer.findViewById(R.id.parallax_listview_background);
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) listViewBackgroundView.getLayoutParams();
        params.height = getDisplayHeight(listView.getContext());
        listViewBackgroundView.setLayoutParams(params);

        listView.setOnScrollListener(parallaxScrollListener);
        return contentContainer;
    }

    private void addHeaderView(ViewGroup headerContainer, int headerLayoutResId) {
        if (headerView == null) {
            headerView = layoutInflater.inflate(headerLayoutResId, headerContainer, false);
        }
        headerContainer.addView(headerView, 0);
    }

    private void updateHeaderHeight(int headerHeight) {
        final ViewGroup.LayoutParams params = marginView.getLayoutParams();
        params.height = headerHeight;
        marginView.setLayoutParams(params);
        if (listViewBackgroundView != null) {
            final FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) listViewBackgroundView.getLayoutParams();
            params2.topMargin = headerHeight;
            listViewBackgroundView.setLayoutParams(params2);
        }
        lastHeaderHeight = headerHeight;
    }

    private class ParallaxDrawableCallback implements Drawable.Callback {
        @Override
        public void invalidateDrawable(Drawable who) {
            setActionBarBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    }

    private class ParallaxScrollListener implements OnScrollListener {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final View topChild = view.getChildAt(0);
            if (topChild == null) {
                onNewScroll(0);
            } else if (!topChild.equals(marginView)) {
                onNewScroll(headerContainer.getHeight());
            } else {
                onNewScroll(-topChild.getTop());
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        private void onNewScroll(int scrollPosition) {
            if (isActionBarNull()) {
                return;
            }

            final int currentHeaderHeight = headerContainer.getHeight();
            if (currentHeaderHeight != lastHeaderHeight) {
                updateHeaderHeight(currentHeaderHeight);
            }

            actionBarBackgroundDrawable.setAlpha(255);

            addParallaxEffect(scrollPosition);
        }

        private void addParallaxEffect(int scrollPosition) {
            final float damping = 0.5f;
            final int dampedScroll = (int) (scrollPosition * damping);
            int offset = lastDampedScroll - dampedScroll;
            headerContainer.offsetTopAndBottom(offset);

            if (listViewBackgroundView != null) {
                offset = lastScrollPosition - scrollPosition;
                listViewBackgroundView.offsetTopAndBottom(offset);
            }

            if (firstGlobalLayoutPerformed) {
                lastScrollPosition = scrollPosition;
                lastDampedScroll = dampedScroll;
            }
        }
    }
}