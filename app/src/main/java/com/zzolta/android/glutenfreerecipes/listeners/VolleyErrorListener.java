package com.zzolta.android.glutenfreerecipes.listeners;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

/**
 * Created by Zolta.Szekely on 2015-03-28.
 */
public class VolleyErrorListener implements ErrorListener {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        throw new RuntimeException(volleyError);
    }
}
