package com.zzolta.android.glutenfreerecipes.providers;

import android.support.v7.widget.ShareActionProvider;

/**
 * Created by Zolta.Szekely on 2015-03-31.
 */
public class RecipeDetailShareActionProvider {
    private ShareActionProvider shareActionProvider;

    private RecipeDetailShareActionProvider() {
    }

    public static RecipeDetailShareActionProvider getInstance() {
        return RecipeDetailShareActionProviderHolder.INSTANCE;
    }

    public ShareActionProvider getShareActionProvider() {
        return shareActionProvider;
    }

    public void setShareActionProvider(ShareActionProvider shareActionProvider) {
        this.shareActionProvider = shareActionProvider;
    }

    private static final class RecipeDetailShareActionProviderHolder {
        private static final RecipeDetailShareActionProvider INSTANCE = new RecipeDetailShareActionProvider();
    }
}
