package com.zzolta.android.glutenfreerecipes.net;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Zolta.Szekely on 2015-02-28.
 */
public final class ApplicationRequestQueue {
    private static ApplicationRequestQueue instance;
    private final RequestQueue requestQueue;

    private ApplicationRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ApplicationRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationRequestQueue(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        this.requestQueue.add(request);
    }
}
