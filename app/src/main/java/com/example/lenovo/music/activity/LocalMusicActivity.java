package com.example.lenovo.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.SongListAdapter;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.dao.MusicDao;
import com.example.lenovo.music.service.PlayService;
import com.example.lenovo.music.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicActivity extends AppCompatActivity implements SongListAdapter.OnDoMoreClickListener,AdapterView.OnItemClickListener{

    private ListView listView;
    private SongListAdapter adapter;
    private List<Song> list=new ArrayList<Song>();
    private int playingState=-1;
    private PlayService pservice;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        toolbar= (Toolbar) findViewById(R.id.local_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("本地音乐");
        listView= (ListView) findViewById(R.id.song_list);
//        getWindow().setWindowAnimations(R.style.Animation);
        adapter=new SongListAdapter(this,R.layout.song_item,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        adapter.setDoMoreClickListener(this);
        initData();

        Log.e("TAG", Tools.enCodeUTF("http://m10.music.126.net/20170724181113/22aef704181766b7be017b2163a041bc/ymusic/6181/60ce/9b79/e8f50a416652ca794c58914058973c89.mp3"));
    }

    private void initData() {
        list.addAll(MusicDao.getAllSongs(this));
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e("TAG", position + "");
        if (playingState == position) {
            Intent intent=new Intent(LocalMusicActivity.this,PlayingSongActivity.class);
            startActivity(intent);
        } else {
            ServiceConnection connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    PlayService.MyBinder binder = (PlayService.MyBinder) service;
                    pservice = binder.getService();
                    pservice.doPlay(list, position);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };
            playingState=position;
            Intent intent = new Intent(LocalMusicActivity.this, PlayService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }
    @Override
    public void doMoreClick() {
        Toast.makeText(this,"dianji",Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
