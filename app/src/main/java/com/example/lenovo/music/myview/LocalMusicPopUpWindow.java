package com.example.lenovo.music.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Song;

/**
 * Created by lenovo on 2017/7/27.
 */

public class LocalMusicPopUpWindow extends PopupWindow {




    private View mMenuView;
    private TextView songName;
    private ImageView ranklistIncludeImage1;
    private TextView ranklistIncludeText1;
    private ImageView ranklistIncludeImage2;
    private TextView ranklistIncludeText2;
    private ImageView ranklistIncludeImage3;
    private TextView ranklistIncludeText3;
    private ImageView ranklistIncludeImage4;
    private TextView ranklistIncludeText4;
    private ImageView ranklistIncludeImage5;
    private TextView ranklistIncludeText5;
    private ImageView ranklistIncludeImage6;
    private TextView ranklistIncludeText6;
    private ImageView ranklistIncludeImage7;
    private TextView ranklistIncludeText7;

    public LocalMusicPopUpWindow(Activity context, Song song, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.local_popupwindow, null);
        initView(itemsOnClick,song);


//            //取消按钮
//            btn_cancel.setOnClickListener(new OnClickListener() {
//
//                public void onClick(View v) {
//                    //销毁弹出框
//                    dismiss();
//                }
//            });

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击


        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pup_up_layout).getTop();
                Log.e("popHeight", "onTouch: " + height);
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
    private void initView(OnClickListener itemsOnClick,Song song){
        songName= (TextView) mMenuView.findViewById(R.id.ranklist_popup_songname);
        songName.setText("歌曲："+song.getFileName());
        LinearLayout item1= (LinearLayout) mMenuView.findViewById(R.id.locallist_1);
        LinearLayout item2= (LinearLayout) mMenuView.findViewById(R.id.locallist_2);
        LinearLayout item5= (LinearLayout) mMenuView.findViewById(R.id.locallist_5);
        LinearLayout item6= (LinearLayout) mMenuView.findViewById(R.id.locallist_6);
        LinearLayout item7= (LinearLayout) mMenuView.findViewById(R.id.locallist_7);
        //设置布局监听
        item1.setOnClickListener(itemsOnClick);
        item2.setOnClickListener(itemsOnClick);
        item5.setOnClickListener(itemsOnClick);
        item6.setOnClickListener(itemsOnClick);
        item7.setOnClickListener(itemsOnClick);
        ranklistIncludeImage1=(ImageView)item1.findViewById(R.id.ranklist_include_image);
        ranklistIncludeText1=(TextView) item1.findViewById(R.id.ranklist_include_text);
        ranklistIncludeImage1.setImageResource(R.drawable.next_play);
        ranklistIncludeText1.setText("下一首播放");
        ranklistIncludeImage2=(ImageView)item2.findViewById(R.id.ranklist_include_image);
        ranklistIncludeText2=(TextView) item2.findViewById(R.id.ranklist_include_text);
        ranklistIncludeImage2.setImageResource(R.drawable.collect_red);
        ranklistIncludeText2.setText("收藏到歌单");
        ranklistIncludeImage5=(ImageView)item5.findViewById(R.id.ranklist_include_image);
        ranklistIncludeText5=(TextView) item5.findViewById(R.id.ranklist_include_text);
        ranklistIncludeImage5.setImageResource(R.drawable.singer);
        ranklistIncludeText5.setText("歌手:"+song.getSinger());
        ranklistIncludeImage6=(ImageView)item6.findViewById(R.id.ranklist_include_image);
        ranklistIncludeText6=(TextView) item6.findViewById(R.id.ranklist_include_text);
        ranklistIncludeImage6.setImageResource(R.drawable.albums);
        ranklistIncludeText6.setText("专辑:"+song.getAlbum());
        ranklistIncludeImage7=(ImageView)item7.findViewById(R.id.ranklist_include_image);
        ranklistIncludeText7=(TextView) item7.findViewById(R.id.ranklist_include_text);
        ranklistIncludeImage7.setImageResource(R.drawable.delete_red);
        ranklistIncludeText7.setText("删除");

    }

}