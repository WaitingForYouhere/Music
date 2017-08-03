package com.example.lenovo.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Daily;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class ZhiHuListAdapter extends ArrayAdapter<Daily> {

    private int resourceId;
    private List<Daily> list;
    OnListViewOverScrollListener listener;


    public ZhiHuListAdapter(Context context, int resourceId, List<Daily> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
        this.list=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Daily daily=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.icon= (ImageView) convertView.findViewById(R.id.zhihu_latest_icon);
            viewHolder.title= (TextView) convertView.findViewById(R.id.zhihu_latest_title);
            viewHolder.title.setTag(1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(daily.getImagePaht()==null){
            Glide.with(getContext()).
                    load(R.drawable.heijiao).into(viewHolder.icon);
        }else {
        Glide.with(getContext()).
                load(daily.getImagePaht()).into(viewHolder.icon);}
        viewHolder.title.setText(daily.getTitle());

        return convertView;
    }

    public void setOnListViewOverScrollListener(OnListViewOverScrollListener listener){
        this.listener=listener;
    }

    public interface OnListViewOverScrollListener{
        void onListViewOverScroll();
    }





}
