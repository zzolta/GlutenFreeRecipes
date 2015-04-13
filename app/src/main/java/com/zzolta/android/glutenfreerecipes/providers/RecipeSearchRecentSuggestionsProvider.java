package com.zzolta.android.glutenfreerecipes.providers;

import android.content.SearchRecentSuggestionsProvider;

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
public final class RecipeSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = RecipeSearchRecentSuggestionsProvider.class.getName();
    public static final int MODE = DATABASE_MODE_QUERIES;

    public RecipeSearchRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}