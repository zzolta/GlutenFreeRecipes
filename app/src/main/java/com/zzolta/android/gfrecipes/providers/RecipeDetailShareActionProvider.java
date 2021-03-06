package com.zzolta.android.gfrecipes.providers;

import android.support.v7.widget.ShareActionProvider;

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
