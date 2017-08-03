package com.example.lenovo.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.activity.WebViewActivity;
import com.example.lenovo.music.bean.NBAData;
import com.example.lenovo.music.myview.OnItemClickListener;
import com.example.lenovo.music.util.NBAUtil;

import java.util.ArrayList;
import java.util.List;


public class FragmentMoments extends Fragment implements OnItemClickListener {

    private List<NBAData> mlist=new ArrayList<NBAData>();
    private NBAUtil nbaUtil;
    private RecyclerView recyclerView;
    HomeAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_moments, container, false);
        nbaUtil=NBAUtil.getInstance(getContext().getApplicationContext());
        nbaUtil.getNBAList(handler);
        recyclerView= (RecyclerView) view.findViewById(R.id.nba_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new HomeAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mlist.clear();
            List<NBAData> data=(List<NBAData>) msg.obj;
            mlist.addAll(data);
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    //recyclerview点击事件响应
    @Override
    public void itemClick(int position) {
//        Log.e("TAG", "itemClick: "+position);
        Intent intent=new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url",mlist.get(position).getUrl_3w());
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private OnItemClickListener listener;
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.nba_recyclerview_item, parent,
                    false));
            return holder;
        }

        public void setListener(OnItemClickListener listener){
            this.listener=listener;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            ViewGroup.LayoutParams lp=holder.iv.getLayoutParams();
            lp.height=300+(int)(300*Math.random());
            holder.iv.setLayoutParams(lp);
            holder.tv.setText(mlist.get(position).getTitle());
            Glide.with(getContext()).load(mlist.get(position).getImgsrc()).into(holder.iv);
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(position);
                }
            });
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(position);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return mlist.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tv;
            ImageView iv;

            public MyViewHolder(View view)
            {
                super(view);
                iv= (ImageView) view.findViewById(R.id.nba_item_icon);
                tv = (TextView) view.findViewById(R.id.nba_item_title);
            }



        }
    }

}

