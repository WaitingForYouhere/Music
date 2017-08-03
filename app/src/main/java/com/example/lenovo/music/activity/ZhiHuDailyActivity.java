package com.example.lenovo.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Daily;
import com.example.lenovo.music.myview.MyWebViewClient;
import com.example.lenovo.music.util.ZhiHuUtil;

public class ZhiHuDailyActivity extends AppCompatActivity {

    private ZhiHuUtil zhiHuUtil;
    private WebView webView;
    private Daily daily;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhi_hu_daily);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        webView= (WebView) findViewById(R.id.zhihu_daily_webview);
        webView.setWebViewClient(new MyWebViewClient());
        zhiHuUtil=ZhiHuUtil.getInstance(getApplicationContext());
        zhiHuUtil.getZhihuStory(getApplicationContext(),id,handler);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            daily= (Daily) msg.obj;
            webView.loadUrl(daily.getShareUrl());
        }
    };

}
