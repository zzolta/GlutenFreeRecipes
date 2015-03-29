package com.zzolta.android.glutenfreerecipes.providers;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Zolta.Szekely on 2015-03-29.
 */
public class RecipeSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = RecipeSearchRecentSuggestionsProvider.class.getName();
    public static final int MODE = DATABASE_MODE_QUERIES;

    public RecipeSearchRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}