package tn.dwc.smartshopping.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import tn.dwc.smartshopping.R;

public class Web_act extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_act);

        Intent intent = getIntent();
        String id = intent.getStringExtra("url");

        // Uri myUri = Uri.parse(extras.getString("url"));

        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webView.loadUrl(id);
    }

}
