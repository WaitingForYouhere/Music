package com.example.lenovo.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.service.PlayService;

import java.util.List;

/**
 * Created by lenovo on 2017/8/3.
 */

    //定义适配器类
    public  class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>
    {
        private Context mContext;
        private List<Song> mDatas;
        private LayoutInflater mInflater;
        private  OnItemClickLitener mOnItemClickLitener;
        private PlayService playService;





            //定义点击事件的接口
        public interface OnItemClickLitener
        {
            void onItemClick(View view, int position);
        }

        public MainRecyclerAdapter(Context mContext, List<Song> mDatas, PlayService playService) {
            this.mContext = mContext;
            this.mDatas = mDatas;
            this.playService=playService;
            mInflater = LayoutInflater.from(this.mContext);

        }




        public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
        {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int i)
        {
            View view = mInflater.inflate(R.layout.main_bottem_recycler_item,null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position)
        {
            String name=mDatas.get(position).getFileName();
            viewHolder.tv.setText(name.substring(0,name.length()-4));

            if(mDatas.get(position).getPicUrl()!=null){
            Glide.with(mContext).load(mDatas.get(position).getPicUrl()).into(viewHolder.iv);
            }else {
                Glide.with(mContext).load(R.drawable.heijiao).into(viewHolder.iv);
            }


            if (mOnItemClickLitener != null){
                viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick( viewHolder.iv,pos);
                    }
                });
                viewHolder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick( viewHolder.iv,pos);
                    }
                });
            }

        }

        public class ViewHolder extends RecyclerView.ViewHolder  //我们需要inflate的item布局需要传入
        {
            ImageView iv;
            TextView tv;
            public ViewHolder(View view)
            {
                super(view);
                iv = (ImageView) view.findViewById(R.id.main_song_icon);
                tv= (TextView) view.findViewById(R.id.main_song_name);
            }

        }
    }