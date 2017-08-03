package com.example.lenovo.music.myview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 2017/2/20.
 */

public class ImageBanerViewGroup extends ViewGroup {

    private int mWidth;
    private int mHeight;
    private int children;
    private int cWidth;
    private int cHeight;
    private int x;
    private int index=0;

    private Scroller scroller;

    private boolean isClick;
    private  ImageBannerListener listener;

    public ImageBannerListener getListener() {
        return listener;
    }

    public void setListener(ImageBannerListener listener) {
        this.listener = listener;
    }

    private ImageBanerViewGroupListener imageBanerViewGroupListener;

    public void setImageBanerViewGroupListener(ImageBanerViewGroupListener imageBanerViewGroupListener) {
        this.imageBanerViewGroupListener = imageBanerViewGroupListener;
    }

    public ImageBanerViewGroupListener getImageBanerViewGroupListener() {
        return imageBanerViewGroupListener;
    }

    public interface ImageBannerListener{
        public void ClickImageIndex(int pos);
    }

    private boolean isAuto=true;
    private Timer timer=new Timer();
    private TimerTask task;

    private Handler autoHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
                    if (++index>=children){
                    index=0;
                    } scrollTo(cWidth*index,0);
                    imageBanerViewGroupListener.SelectImage(index);
                    break;
                default:break;
            }
        }
    };
    public void StartAuto(){
        isAuto=true;
    }
    public void StopAuto(){
        isAuto=false;
    }

    public ImageBanerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initobj();
    }



    public ImageBanerViewGroup(Context context) {
        super(context);
        initobj();

    }

    public ImageBanerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initobj();

    }
    private void initobj() {
        scroller=new Scroller(getContext());
        task=new TimerTask() {
            @Override
            public void run() {
                if(isAuto){
                    autoHandler.sendEmptyMessage(0);
                }

            }
        };
        timer.schedule(task,100,5000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }
    int lastX;
    int lastY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int top=this.getTop();
        int bottom=this.getBottom();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //不允许父容器拦截该事件
                //parent为该View需要拦截滑动事件的那个父容器的引用
                lastX= (int) ev.getX();
                lastY= (int) ev.getY();
                if(y>top&y<bottom){
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int dx=(int) ev.getX()-lastX;
                int dy=(int) ev.getY()-lastY;

                if(Math.abs(dx)<Math.abs(dy)){
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }else
                if(y>top&y<bottom){
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }

                lastX= (int) ev.getX();
                lastY= (int) ev.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }


        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        children=getChildCount();
        if(0==children){
            setMeasuredDimension(0,0);
        }
        else{
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            View view=getChildAt(0);
            cWidth=view.getMeasuredWidth();
            cHeight=view.getMeasuredHeight();
            mWidth=cWidth*children;
            mHeight=cHeight;
            setMeasuredDimension(mWidth,mHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed){
            int leftMargin=0;
            for(int i=0;i<children;i++){
                View a=getChildAt(i);
                a.layout(leftMargin,0,leftMargin+cWidth,cHeight);
                leftMargin+=cWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                StopAuto();
                isClick=true;
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                x= (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int movex=(int) event.getX();

                int distance=movex-x;
                if(Math.abs(distance)>10){
                    isClick=false;
                }
                scrollBy(-distance,0);
                x=movex;
                break;
            case MotionEvent.ACTION_UP:
                int scrollx=getScrollX();
                index=(scrollx+cWidth/2)/cWidth;
                if(index<0)index=0;
            else if(index>children-1){
                index=children-1;
            }
                if(isClick){
                    listener.ClickImageIndex(index);
                }else {

                    int dx=index*cWidth-scrollx;
                    scroller.startScroll(scrollx,0,dx,0);
                    postInvalidate();
                    imageBanerViewGroupListener.SelectImage(index);
                }


                StartAuto();

//                scrollTo(cWidth*index,0);
                break;
            default:break;


        }
        return true;
    }
    public interface ImageBanerViewGroupListener{
        public void SelectImage(int Index);
    }
}
