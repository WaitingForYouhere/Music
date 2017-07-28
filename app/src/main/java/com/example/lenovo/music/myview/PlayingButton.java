package com.example.lenovo.music.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.lenovo.music.R;

/**
 * Created by lenovo on 2017/7/26.
 */

public class PlayingButton extends View{
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private int width;
    private int height;
    private boolean isplaying=false;

    private int percentage=0;

    public PlayingButton(Context context) {
        super(context);
        init();
    }

    public PlayingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint1=new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(getResources().getColor(R.color.colorLightRed));
        paint1.setStrokeWidth(3);

        paint2=new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(getResources().getColor(R.color.colorGray));
        paint2.setStrokeWidth(3);
        paint3=new Paint();
        paint3.setAntiAlias(true);
        paint3.setColor(getResources().getColor(R.color.colorWhite));
        paint3.setStrokeWidth(3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF recf=new RectF(0,0,width,height);
        canvas.drawArc(recf,0,360,false,paint2);
        canvas.drawArc(recf,270,percentage*360/100,false,paint1);

        RectF recf1=new RectF(0,0,width,height);
        canvas.drawCircle(width/2,height/2,(width-6)/2,paint3);
        float heightoffset=(float)(Math.sqrt(3)*width/9);
        float[] pts={width/2-width/9,height/2-heightoffset,width/2+width*2/9,height/2,
                width/2+width*2/9,height/2,width/2-width/9,height/2+heightoffset,
                width/2-width/9,height/2+heightoffset,width/2-width/9,height/2-heightoffset
        };
        float[] pts1={width/2-width/9,height/2-heightoffset,width/2-width/9,height/2+heightoffset,
                width/2+width/9,height/2-heightoffset,width/2+width/9,height/2+heightoffset,
        };

        if(isplaying){
            canvas.drawLines(pts1,paint1);
        }else {

            canvas.drawLines(pts,paint2);
        }
    }


    public void setPercentage(int percentage) {
        this.percentage = percentage;
        invalidate();
    }

    public void setIsPlaying(boolean Playing){
            isplaying=Playing;
            invalidate();
    }
    public void setClick(){
        if(isplaying){
        isplaying=false;
        }else {
            isplaying=true;
        }
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setClick();
                break;
        }


        return super.onTouchEvent(event);
    }
}
