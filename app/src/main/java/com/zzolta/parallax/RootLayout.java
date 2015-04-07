package com.zzolta.parallax;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.zzolta.android.glutenfreerecipes.R;

public class RootLayout extends FrameLayout {

    private View headerContainer;
    private View listViewBackground;
    private boolean isInitialized;

    public RootLayout(Context context) {
        super(context);
    }

    public RootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //at first find headerViewContainer and listViewBackground
        if (headerContainer == null) {
            headerContainer = findViewById(R.id.parallax_header_container);
        }
        if (listViewBackground == null) {
            listViewBackground = findViewById(R.id.parallax_listview_background);
        }

        //if there's no headerViewContainer then fallback to standard FrameLayout
        if (headerContainer == null) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }

        if (!isInitialized) {
            super.onLayout(changed, left, top, right, bottom);
            //if listViewBackground not exists or listViewBackground exists
            //and its top is at headercontainer height then view is initialized
            if (listViewBackground == null || listViewBackground.getTop() == headerContainer.getHeight()) {
                isInitialized = true;
            }
            return;
        }

        //get last header and listViewBackground position
        final int headerTopPrevious = headerContainer.getTop();
        final int listViewBackgroundTopPrevious = listViewBackground != null ? listViewBackground.getTop() : 0;

        //relayout
        super.onLayout(changed, left, top, right, bottom);

        //revert header top position
        final int headerTopCurrent = headerContainer.getTop();
        if (headerTopCurrent != headerTopPrevious) {
            headerContainer.offsetTopAndBottom(headerTopPrevious - headerTopCurrent);
        }
        //revert listViewBackground top position
        final int listViewBackgroundTopCurrent = listViewBackground != null ? listViewBackground.getTop() : 0;
        if (listViewBackgroundTopCurrent != listViewBackgroundTopPrevious) {
            listViewBackground.offsetTopAndBottom(listViewBackgroundTopPrevious - listViewBackgroundTopCurrent);
        }
    }
}