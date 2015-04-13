package com.zzolta.android.gfrecipes.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

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
public final class ApplicationRequestQueue {
    private static ApplicationRequestQueue instance;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;
    private final Context context;

    private ApplicationRequestQueue(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        imageLoader = new ImageLoader(requestQueue, new ImageCache() {
            private final LruCache<String, Bitmap> imageCache = new LruCache<>(100);

            @Override
            public Bitmap getBitmap(String url) {
                return imageCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                imageCache.put(url, bitmap);
            }
        });
    }

    public static synchronized ApplicationRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationRequestQueue(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        if (isNetworkConnected()) {
            this.requestQueue.add(request);
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private boolean isNetworkConnected() {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
    }
}
