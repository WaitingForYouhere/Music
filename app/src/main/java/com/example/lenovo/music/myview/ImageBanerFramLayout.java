package com.example.lenovo.music.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.music.R;

import java.util.List;

/**
 * Created by lenovo on 2017/2/21.
 */

public class ImageBanerFramLayout extends FrameLayout implements ImageBanerViewGroup.ImageBanerViewGroupListener,ImageBanerViewGroup.ImageBannerListener{

    private ImageBanerViewGroup imageBanerViewGroup;
    private LinearLayout linearLayout;
    private FramLayoutListener listener;

    public FramLayoutListener getListener() {
        return listener;
    }
    public void setlistener(FramLayoutListener listener){
        this.listener=listener;
    }

    public ImageBanerFramLayout(Context context) {
        super(context);
        InitImageBanerViewGroup();
        InitDotLinearLayout();
    }

    public ImageBanerFramLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitImageBanerViewGroup();
        InitDotLinearLayout();
    }

    public ImageBanerFramLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitImageBanerViewGroup();
        InitDotLinearLayout();
    }
    public void addBitmaps(List<Bitmap> list){
        for(int i=0;i<list.size();i++){
            Bitmap bitmap=list.get(i);
            addBitmapsToImagebanerFramLayout(bitmap);
            addDotToImageBanerFramLayout();
        }

    }

    private void addDotToImageBanerFramLayout() {
        ImageView iv=new ImageView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.point_normal);
        linearLayout.addView(iv);
    }

    private void addBitmapsToImagebanerFramLayout(Bitmap bitmap) {
        ImageView iv=new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new ViewGroup.LayoutParams(C.witth, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageBitmap(bitmap);
        imageBanerViewGroup.addView(iv);
    }

    private void InitImageBanerViewGroup(){
        imageBanerViewGroup=new ImageBanerViewGroup(getContext());
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        imageBanerViewGroup.setLayoutParams(lp);
        imageBanerViewGroup.setImageBanerViewGroupListener(this);
        imageBanerViewGroup.setListener(this);
        addView(imageBanerViewGroup);
    }
    private void InitDotLinearLayout(){
        linearLayout=new LinearLayout(getContext());
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
//        linearLayout.setBackgroundColor(Color.RED);
        addView(linearLayout);
        FrameLayout.LayoutParams lp= (LayoutParams) linearLayout.getLayoutParams();
        lp.gravity= Gravity.BOTTOM;
        linearLayout.setLayoutParams(lp);
        linearLayout.setAlpha(0.6f);

    }

    @Override
    public void SelectImage(int Index) {
        int pointCount=linearLayout.getChildCount();
        for(int i=0;i<pointCount;i++){
            ImageView iv= (ImageView)linearLayout.getChildAt(i);
            if(i==Index){
                iv.setImageResource(R.drawable.point_select);
            }else {
                iv.setImageResource(R.drawable.point_normal);
            }
        }
    }

    @Override
    public void ClickImageIndex(int pos) {
        listener.ClickImageIndex(pos);
    }
    public interface FramLayoutListener{
        void ClickImageIndex(int pos);
    }
}
