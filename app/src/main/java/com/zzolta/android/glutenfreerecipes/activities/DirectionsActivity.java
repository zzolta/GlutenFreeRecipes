package com.zzolta.android.glutenfreerecipes.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import com.zzolta.android.glutenfreerecipes.R;
import com.zzolta.android.glutenfreerecipes.utils.ApplicationConstants;

/**
 * Created by Zolta.Szekely on 2015-03-27.
 */
public class DirectionsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions_webview);

        final WebView webView = (WebView) findViewById(R.id.directions_webview);
        webView.loadUrl(getIntent().getStringExtra(ApplicationConstants.URL));
    }
}