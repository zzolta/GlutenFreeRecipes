package com.zzolta.android.glutenfreerecipes.net;

import java.util.Calendar;

/**
 * Created by Zolta.Szekely on 2015-03-28.
 */
public final class CuisineHelper {

    private static final String CUISINE = "cuisine^";
    private static final String[] CUISINES = {
                                                 "cuisine-american",
                                                 "cuisine-italian",
                                                 "cuisine-asian",
                                                 "cuisine-mexican",
                                                 "cuisine-french",
                                                 "cuisine-indian",
                                                 "cuisine-chinese",
                                                 "cuisine-english",
                                                 "cuisine-mediterranean",
                                                 "cuisine-greek",
                                                 "cuisine-spanish",
                                                 "cuisine-german",
                                                 "cuisine-thai",
                                                 "cuisine-moroccan",
                                                 "cuisine-irish",
                                                 "cuisine-japanese",
                                                 "cuisine-cuban",
                                                 "cuisine-hawaiin",
                                                 "cuisine-swedish",
                                                 "cuisine-hungarian",
                                                 "cuisine-portugese"
    };

    private CuisineHelper() {
    }

    public static String getCuisineOfTheDay() {
        final int dayOfYear = getDayOfYear();
        final int index = dayOfYear % (CUISINES.length);
        return CUISINE + CUISINES[index];
    }

    public static int getDayOfYear() {
        final Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
}
