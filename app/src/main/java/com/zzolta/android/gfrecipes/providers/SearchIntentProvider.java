package com.zzolta.android.gfrecipes.providers;

import android.content.Intent;

/**
 * Created by Zolta.Szekely on 2015-04-18.
 */
public class SearchIntentProvider {
    private Intent searchIntent;

    private SearchIntentProvider() {
    }

    public static SearchIntentProvider getInstance() {
        return SearchIntentProviderHolder.INSTANCE;
    }

    public Intent getSearchIntent() {
        return searchIntent;
    }

    public void setSearchIntent(Intent searchIntent) {
        this.searchIntent = searchIntent;
    }

    private static final class SearchIntentProviderHolder {
        private static final SearchIntentProvider INSTANCE = new SearchIntentProvider();
    }
}
