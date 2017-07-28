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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.PlayingSongListAdapter;
import com.example.lenovo.music.bean.Song;

import java.util.List;

/**
 * Created by lenovo on 2017/7/27.
 */

public class PlaylistPopUpWindow extends PopupWindow {


        private TextView mode,collect,deleteAll;
        private ListView listView;
        private View mMenuView;

        public PlaylistPopUpWindow(Activity context, List<Song> list,PlayingSongListAdapter adapter,
                                   OnClickListener itemsOnClick, AdapterView.OnItemClickListener onItemClickListener,
                                   PlayingSongListAdapter.OnDeleteListener onDeleteListener) {
            super(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mMenuView = inflater.inflate(R.layout.playinglist_popupwindow, null);
            mode= (TextView) mMenuView.findViewById(R.id.play_mode);
            collect= (TextView) mMenuView.findViewById(R.id.collect);
            deleteAll= (TextView) mMenuView.findViewById(R.id.delede_all);
            listView= (ListView) mMenuView.findViewById(R.id.play_list_popup);
            listView.setAdapter(adapter);
            adapter.setOnDeleteListener(onDeleteListener);
            listView.setOnItemClickListener(onItemClickListener);
//            //取消按钮
//            btn_cancel.setOnClickListener(new OnClickListener() {
//
//                public void onClick(View v) {
//                    //销毁弹出框
//                    dismiss();
//                }
//            });
            //设置按钮监听
            mode.setOnClickListener(itemsOnClick);
            collect.setOnClickListener(itemsOnClick);
            deleteAll.setOnClickListener(itemsOnClick);
            //设置SelectPicPopupWindow的View
            this.setContentView(mMenuView);
            //设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(LayoutParams.FILL_PARENT);
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
                    Log.e("popHeight", "onTouch: "+height);
                    int y=(int) event.getY();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        if(y<height){
                            dismiss();
                        }
                    }
                    return true;
                }
            });

        }

    }