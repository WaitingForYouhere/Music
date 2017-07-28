package com.example.lenovo.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Song;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class PlayingSongListAdapter extends ArrayAdapter<Song>{

    private int resourceId;
    private List<Song> list;
    private OnDeleteListener listener;

    public PlayingSongListAdapter(Context context, int resourceId, List<Song> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
        this.list=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Song song=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.songName= (TextView) convertView.findViewById(R.id.song_name);
            viewHolder.doManage= (ImageView) convertView.findViewById(R.id.do_manage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.songName.setText(song.getTitle());
        viewHolder.doManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ondelete(position);

            }
        });
        return convertView;
    }



    class ViewHolder{

        private TextView songName;
        private ImageView doManage;
    }
    public void setOnDeleteListener(OnDeleteListener omListener){
        this.listener=omListener;
    }
    public interface OnDeleteListener{
        void ondelete(int position);
    }
}
