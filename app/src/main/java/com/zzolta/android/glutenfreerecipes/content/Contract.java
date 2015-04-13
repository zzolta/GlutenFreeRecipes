package com.zzolta.android.glutenfreerecipes.content;

import android.provider.BaseColumns;

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
        public static final String SOURCE_RECIPE_URL = "sourceRecipeUrl";
        public static final String MY_RECIPE = "myRecipe";
    }
}