package com.example.lenovo.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.myview.MyWebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView= (WebView) findViewById(R.id.webview_all);
        webView.setWebViewClient(new MyWebViewClient());
        Intent intent=getIntent();
        String url=intent.getExtras().getString("url");
        webView.loadUrl(url);

    }
}
