package com.zzolta.android.glutenfreerecipes.content;

import android.provider.BaseColumns;

/**
 * Created by Zolta.Szekely on 2015-03-01.
 */
public final class Contract {

    private Contract() {
    }

    public static class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String NAME = "name";
        public static final String INGREDIENTS = "ingredients";
        public static final String TOTAL_TIME_IN_SECONDS = "total_time_in_seconds";
        public static final String RATING = "rating";
        public static final String IMAGE_PATH = "imagePath";
    }
}