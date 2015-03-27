package com.zzolta.parallax;

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
import com.zzolta.android.glutenfreerecipes.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ParallaxScroll {

    private static final String LOG_TAG = ParallaxScroll.class.getName();
    private final Drawable.Callback mDrawableCallback = new Drawable.Callback() {
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
    };
    private Drawable mActionBarBackgroundDrawable;
    private FrameLayout mHeaderContainer;
    private int mActionBarBackgroundResId;
    private int mHeaderLayoutResId;
    private View mHeaderView;
    private int mContentLayoutResId;
    private View mContentView;
    private LayoutInflater mInflater;
    private boolean mLightActionBar;
    private int mLastDampedScroll;
    private int mLastHeaderHeight = -1;
    private boolean mFirstGlobalLayoutPerformed;
    private FrameLayout mMarginView;
    private View mListViewBackgroundView;
    private int mLastScrollPosition;
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final View topChild = view.getChildAt(0);
            if (topChild == null) {
                onNewScroll(0);
            } else if (!topChild.equals(mMarginView)) {
                onNewScroll(mHeaderContainer.getHeight());
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

            final int currentHeaderHeight = mHeaderContainer.getHeight();
            if (currentHeaderHeight != mLastHeaderHeight) {
                updateHeaderHeight(currentHeaderHeight);
            }

            final int headerHeight = currentHeaderHeight - getActionBarHeight();
            final float ratio = (float) Math.min(Math.max(scrollPosition, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);

            addParallaxEffect(scrollPosition);
        }

        private void addParallaxEffect(int scrollPosition) {
            final float damping = 0.5f;
            final int dampedScroll = (int) (scrollPosition * damping);
            int offset = mLastDampedScroll - dampedScroll;
            mHeaderContainer.offsetTopAndBottom(offset);

            if (mListViewBackgroundView != null) {
                offset = mLastScrollPosition - scrollPosition;
                mListViewBackgroundView.offsetTopAndBottom(offset);
            }

            if (mFirstGlobalLayoutPerformed) {
                mLastScrollPosition = scrollPosition;
                mLastDampedScroll = dampedScroll;
            }
        }
    };

    public static int getDisplayHeight(Context context) {
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        final int displayHeight = wm.getDefaultDisplay().getHeight();
        return displayHeight;
    }

    public final <T extends ParallaxScroll> T headerLayout(int layoutResId) {
        mHeaderLayoutResId = layoutResId;
        return (T) this;
    }

    public final <T extends ParallaxScroll> T contentLayout(int layoutResId) {
        mContentLayoutResId = layoutResId;
        return (T) this;
    }

    public final <T extends ParallaxScroll> T lightActionBar(boolean value) {
        mLightActionBar = value;
        return (T) this;
    }

    public final <T extends ParallaxScroll> T actionBarBackground(int drawableResId) {
        mActionBarBackgroundResId = drawableResId;
        return (T) this;
    }

    public final View createView(LayoutInflater inflater) {

        mInflater = inflater;
        if (mContentView == null) {
            mContentView = inflater.inflate(mContentLayoutResId, null);
        }

        final ListView listView = (ListView) mContentView.findViewById(R.id.parallaxList);
        final View root = createListView(listView);

        // Use measured height here as an estimate of the header height, later on after the layout is complete
        // we'll use the actual height
        final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.MATCH_PARENT, MeasureSpec.EXACTLY);
        final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
        mHeaderView.measure(widthMeasureSpec, heightMeasureSpec);
        updateHeaderHeight(mHeaderView.getMeasuredHeight());

        root.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int headerHeight = mHeaderContainer.getHeight();
                if (!mFirstGlobalLayoutPerformed && headerHeight != 0) {
                    updateHeaderHeight(headerHeight);
                    mFirstGlobalLayoutPerformed = true;
                }
            }
        });
        return root;
    }

    public void initActionBar(Activity activity) {
        if (mActionBarBackgroundDrawable == null) {
            mActionBarBackgroundDrawable = activity.getResources().getDrawable(mActionBarBackgroundResId);
        }
        setActionBarBackgroundDrawable(mActionBarBackgroundDrawable);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        }
        mActionBarBackgroundDrawable.setAlpha(0);
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

    protected abstract int getActionBarHeight();

    protected abstract void setActionBarBackgroundDrawable(Drawable drawable);

    private View createListView(ListView listView) {
        final ViewGroup contentContainer = (ViewGroup) mInflater.inflate(R.layout.parallax_listview_container, null);
        contentContainer.addView(mContentView);

        mHeaderContainer = (FrameLayout) contentContainer.findViewById(R.id.fab__header_container);
        initializeGradient(mHeaderContainer);
        addHeaderView(mHeaderContainer, mHeaderLayoutResId);

        mMarginView = new FrameLayout(listView.getContext());
        mMarginView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 0));
        listView.addHeaderView(mMarginView, null, false);

        // Make the background as high as the screen so that it fills regardless of the amount of scroll.
        mListViewBackgroundView = contentContainer.findViewById(R.id.fab__listview_background);
        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mListViewBackgroundView.getLayoutParams();
        params.height = getDisplayHeight(listView.getContext());
        mListViewBackgroundView.setLayoutParams(params);

        listView.setOnScrollListener(mOnScrollListener);
        return contentContainer;
    }

    private void addHeaderView(ViewGroup headerContainer, int headerLayoutResId) {
        if (mHeaderView == null) {
            mHeaderView = mInflater.inflate(headerLayoutResId, headerContainer, false);
        }
        headerContainer.addView(mHeaderView, 0);
    }

    private void updateHeaderHeight(int headerHeight) {
        final ViewGroup.LayoutParams params = mMarginView.getLayoutParams();
        params.height = headerHeight;
        mMarginView.setLayoutParams(params);
        if (mListViewBackgroundView != null) {
            final FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) mListViewBackgroundView.getLayoutParams();
            params2.topMargin = headerHeight;
            mListViewBackgroundView.setLayoutParams(params2);
        }
        mLastHeaderHeight = headerHeight;
    }

    private void initializeGradient(ViewGroup headerContainer) {
        final View gradientView = headerContainer.findViewById(R.id.fab__gradient);
        int gradient = R.drawable.parallax_gradient;
        if (mLightActionBar) {
            gradient = R.drawable.parallax_gradient_light;
        }
        gradientView.setBackgroundResource(gradient);
    }
}