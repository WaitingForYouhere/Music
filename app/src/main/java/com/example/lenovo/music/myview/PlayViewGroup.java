package com.example.lenovo.music.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by lenovo on 2017/5/24.
 */

public class PlayViewGroup extends ViewGroup{

    private OnClickAction onClickAction;

    public PlayViewGroup(Context context) {
        super(context);
    }

    public PlayViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
private float startx;
private float starty;
private float endy;
private float endx;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startx=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endx=event.getX();
                if(Math.abs(endx-startx)<30){
                    onClickAction.onClick();
                }
                break;
        }
        return true;
    }
    public void setOnClickAction(OnClickAction action){
        this.onClickAction=action;
    }
    public interface OnClickAction{
        void onClick();
    }
}
