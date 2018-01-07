package com.fonix.qr;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Hasan Yousef on 6/15/2016.
 */
public class WebViewActivity extends Activity {

    // private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView)findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        webView.loadUrl("file:///android_asset/myFile.html");
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                view.loadUrl("javascript:init('" + url + "')");
            }
        });



     //   webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        //webView.loadUrl("http://www.google.com");

        // String customHtml = "<html><body>" +
        //        ""+
        //        "</body></html>";
        // webView.loadData(customHtml, "text/html", "UTF-8");

    }
}
