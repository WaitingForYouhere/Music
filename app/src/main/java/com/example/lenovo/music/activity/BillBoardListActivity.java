package com.example.lenovo.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.SongListAdapter;
import com.example.lenovo.music.bean.Billboard;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.service.PlayService;
import com.example.lenovo.music.util.SongUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillBoardListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,SongListAdapter.OnDoMoreClickListener {

    @Bind(R.id.bill_board_head)
    RelativeLayout billBoardHead;
    @Bind(R.id.bill_board_head_pic)
    ImageView billBoardHeadPic;
    private ListView billList;
    private SongListAdapter adapter;
    private List<Song> list = new ArrayList<>();
    private SongUtil songUtil;
    private Billboard billboard;
    private int playingState=-1;
    private PlayService pservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board_list);
        int height=getWindowManager().getDefaultDisplay().getHeight();
        ButterKnife.bind(this);
        billList = (ListView) findViewById(R.id.bill_listview);
        billList.setOnItemClickListener(this);
//        billList.setMinimumHeight(height);
        adapter = new SongListAdapter(this, R.layout.song_item, list);
        billList.setAdapter(adapter);


    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("bill_type", Context.MODE_PRIVATE);

        String type = sp.getString("type","");
        Log.e("type",type);

        songUtil = SongUtil.getInstance(getApplicationContext());
        songUtil.getSongRanking(this, hand, 20, type);
    }

    Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            billboard = (Billboard) msg.obj;
            list.clear();
            list.addAll(billboard.getSongList());
            adapter.notifyDataSetChanged();
            Glide.with(getApplicationContext()).
                    load(billboard.getPicUrl()).into(billBoardHeadPic);
            setListViewHeightBasedOnChildren(billList);
        }
    };
    Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String Url = (String) msg.obj;
            Song song=list.get(playingState);
            song.setFileUrl(Url);
            pservice.addPlay(song);
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e("TAG", position + "");

            ServiceConnection connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    PlayService.MyBinder binder = (PlayService.MyBinder) service;
                    pservice = binder.getService();
                    songUtil.getSongUrl(hand1,list.get(position).getFileUrl());
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            playingState=position;
            Intent intent = new Intent(BillBoardListActivity.this, PlayService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }

    @Override
    public void doMoreClick() {
        Toast.makeText(this,"diankjdwi",Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
