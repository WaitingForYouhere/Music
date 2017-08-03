package com.example.lenovo.music.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2017/8/2.
 */

public class MyHorizontalView extends LinearLayout {

    private int screenWidth;
    private int screenHeight;

    public MyHorizontalView(Context context) {
        this(context,null);
    }

    public MyHorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm=getResources().getDisplayMetrics();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count=this.getChildCount();
        for(int i=0;i<count;i++){
            View view=this.getChildAt(i);
            if(view.getLeft()<screenWidth/2&view.getRight()>screenWidth/2){
                view.layout(view.getLeft(),this.getBottom()-500-view.getHeight(),view.getRight(),this.getBottom()-500);
            }
        }
        super.onLayout(changed, l, t, r, b);

    }
}
