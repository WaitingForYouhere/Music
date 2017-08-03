package com.example.lenovo.music.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.GridView;

/**
 * Created by lenovo on 2017/8/2.
 */

public class MyGridView extends GridView{

    private boolean isTop=true;
    private boolean isBottom=false;
    public MyGridView(Context context) {
        this(context,null);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isTop=firstVisibleItem==0?true:false;
                isBottom=firstVisibleItem+visibleItemCount==totalItemCount?true:false;
            }
        });
    }

    int lastY=0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();

        int y = (int) ev.getRawY();
        int top=this.getTop();
        int bottom=this.getBottom();
        Log.e("Tagh", "dispatchTo:top="+top+"bottom="+bottom +"y="+y);
        int count=this.getAdapter().getCount();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //不允许父容器拦截该事件
                //parent为该View需要拦截滑动事件的那个父容器的引用

                if(y>top&y<bottom){
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //这里的逻辑可以根据需要进行修改
                //parent为该View需要拦截滑动事件的那个父容器的引用
                int deltaY = y-lastY ;
                if (y>top&y<bottom) {
                    //不允许父容器拦截该事件
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(isTop&deltaY>0){
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }else if(isBottom&deltaY<0){
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        lastY=y;

        return super.dispatchTouchEvent(ev);
    }

}
