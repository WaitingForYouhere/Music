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
import com.example.lenovo.music.bean.Billboard;
import com.example.lenovo.music.bean.Song;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class BillBoardAdapter extends ArrayAdapter<Billboard> {

    private int resourceId;
    private List<Billboard> list;
    private List<Song> slist;

    public BillBoardAdapter(Context context, int resourceId, List<Billboard> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
        this.list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Billboard billboard=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.pic= (ImageView) convertView.findViewById(R.id.bill_pic);
            viewHolder.song1= (TextView) convertView.findViewById(R.id.song_1);
            viewHolder.song2= (TextView) convertView.findViewById(R.id.song_2);
            viewHolder.song3= (TextView) convertView.findViewById(R.id.song_3);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
//        Log.e("slist",billboard.getPicUrl());

        Glide.with(getContext()).
                load(billboard.getPicUrl()).into(viewHolder.pic);
        slist=billboard.getSongList();
        viewHolder.song1.setText("1、"+slist.get(0).getTitle());
        viewHolder.song2.setText("2、"+slist.get(1).getTitle());
        viewHolder.song3.setText("3、"+slist.get(2).getTitle());
        return convertView;
    }

    class ViewHolder{
        private ImageView pic;
        private TextView song1;
        private TextView song2;
        private TextView song3;

    }
}
