package com.example.lenovo.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Daily;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class ZhiHuHotAdapter extends BaseAdapter{

    private int resourceId;
    private List<Daily> list;
    private Context context;


    public ZhiHuHotAdapter(Context context, int resourceId, List<Daily> objects) {
        super();
        this.context=context;
        this.resourceId=resourceId;
        this.list=objects;
    }

    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }else {
        return 0;}
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Daily daily=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.icon= (ImageView) convertView.findViewById(R.id.zhihu_hot_icon);
            viewHolder.title= (TextView) convertView.findViewById(R.id.zhihu_hot_title);
            viewHolder.title.setTag(2);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(daily.getImagePaht()==null){
            Glide.with(context).
                    load(R.drawable.heijiao).into(viewHolder.icon);
        }else {
        Glide.with(context).
                load(daily.getImagePaht()).into(viewHolder.icon);}
        viewHolder.title.setText(daily.getTitle());

        return convertView;
    }





}
