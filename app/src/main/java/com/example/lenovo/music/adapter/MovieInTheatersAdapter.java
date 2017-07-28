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
import com.example.lenovo.music.bean.Movie;

import java.util.List;

/**
 * Created by lenovo on 2017/3/22.
 */

public class MovieInTheatersAdapter extends ArrayAdapter<Movie> {

    private int resourceId;
    private List<Movie> list;

    public MovieInTheatersAdapter(Context context, int resourceId, List<Movie> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
        this.list=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.poster= (ImageView) convertView.findViewById(R.id.movie_poster_in);
            viewHolder.title= (TextView) convertView.findViewById(R.id.movie_title_in);
            viewHolder.rating= (TextView) convertView.findViewById(R.id.movie_rating_in);
            viewHolder.director= (TextView) convertView.findViewById(R.id.movie_director_in);
            viewHolder.casts= (TextView) convertView.findViewById(R.id.movie_casts_in);
            viewHolder.sawNum= (TextView) convertView.findViewById(R.id.movie_saw_in);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Glide.with(getContext()).
                load(movie.getPosterUrl()).into(viewHolder.poster);
        viewHolder.title.setText(movie.getTitle());
        viewHolder.rating.setText("评分:"+movie.getRating());
        viewHolder.director.setText("导演："+movie.getDirector());
        viewHolder.casts.setText("主演："+movie.getCasts());
        viewHolder.sawNum.setText(movie.getSawNum()+"人看过");
        return convertView;
    }

    class ViewHolder{
        private ImageView poster;
        private TextView rating;
        private TextView director;
        private TextView casts;
        private TextView sawNum;
        private TextView title;
    }
}
