package com.example.lenovo.music.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.lenovo.music.service.DownLoadService;
import com.example.lenovo.music.service.PlayService;

/**
 * Created by lenovo on 2017/7/23.
 */

public class App extends Application {
    public static Context sContext;
    public static int sScreenWidth;
    public static int sScreenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        startService(new Intent(this, PlayService.class));
        startService(new Intent(this, DownLoadService.class));

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        sScreenWidth = dm.widthPixels;
        sScreenHeight = dm.heightPixels;
    }

}
