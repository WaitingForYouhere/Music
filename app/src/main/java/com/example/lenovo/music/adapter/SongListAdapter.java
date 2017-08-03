package com.example.lenovo.music.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Song;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by lenovo on 2017/3/22.
 */

public class SongListAdapter extends ArrayAdapter<Song>{

    private int resourceId;
    private List<Song> list;
    private OnDoMoreClickListener listener;

    public SongListAdapter(Context context, int resourceId, List<Song> objects) {
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
            viewHolder.playing= (ImageView) convertView.findViewById(R.id.song_playing);
            viewHolder.songName= (TextView) convertView.findViewById(R.id.song_name);
            viewHolder.singer= (TextView) convertView.findViewById(R.id.singer);
            viewHolder.doManage= (Button) convertView.findViewById(R.id.do_manage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(song.getPicUrl()==null){
            Glide.with(getContext()).
                    load(R.drawable.heijiao).bitmapTransform(new RoundedCornersTransformation(getContext(),
                    80,0, RoundedCornersTransformation.CornerType.ALL)).into(viewHolder.playing);
        }else {
        Glide.with(getContext()).
                load(song.getPicUrl()).bitmapTransform(new RoundedCornersTransformation(getContext(),
                80,0, RoundedCornersTransformation.CornerType.ALL)).into(viewHolder.playing);}
        viewHolder.songName.setText(song.getTitle());
        viewHolder.singer.setText(song.getSinger());
        viewHolder.doManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.doMoreClick(position);
            }
        });
        return convertView;
    }



    class ViewHolder{
        private ImageView playing;
        private TextView songName;
        private TextView singer;
        private Button doManage;
    }
    public void setDoMoreClickListener(OnDoMoreClickListener omListener){
        this.listener=omListener;
    }
    public interface OnDoMoreClickListener{
        void doMoreClick(int position);
    }
}
