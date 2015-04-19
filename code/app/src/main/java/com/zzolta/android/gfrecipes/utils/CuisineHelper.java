package com.zzolta.android.gfrecipes.utils;

import java.util.Calendar;

/*
 * Copyright (C) 2015 Zolta Szekely
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
