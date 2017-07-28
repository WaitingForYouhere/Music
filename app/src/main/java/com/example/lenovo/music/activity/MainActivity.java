package com.example.lenovo.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.FragmentViewPagerAdapter;
import com.example.lenovo.music.adapter.PlayingSongListAdapter;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.dao.MusicDao;
import com.example.lenovo.music.fragment.FragmentListen;
import com.example.lenovo.music.fragment.FragmentMoments;
import com.example.lenovo.music.fragment.FragmentSearch;
import com.example.lenovo.music.myview.NestedViewPager;
import com.example.lenovo.music.myview.PlayViewGroup;
import com.example.lenovo.music.myview.PlayingButton;
import com.example.lenovo.music.myview.PlaylistPopUpWindow;
import com.example.lenovo.music.service.PlayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        PlayViewGroup.OnClickAction,AdapterView.OnItemClickListener ,PlayingSongListAdapter.OnDeleteListener{

    private NestedViewPager nestedViewPager;
    private List<Fragment> fragmentList=new ArrayList<Fragment>();
    private PlayingButton playingButton;
    private Button bt_play_list;
    private Toolbar toolbar;
    private PlayService pservice;
    private PlayViewGroup playList;
    private List<Song> locallist=new ArrayList<Song>();
    private PlaylistPopUpWindow popUpWindow;
    private PlayingSongListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.main_tool);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.caidan);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("菜单", "onClick: " );
            }
        });
        playList= (PlayViewGroup) findViewById(R.id.playing_list_main);
        playList.setOnClickAction(this);
        bt_play_list= (Button) findViewById(R.id.bt_play_list);
        bt_play_list.setOnClickListener(this);
        nestedViewPager= (NestedViewPager) findViewById(R.id.main_viewpager);
        fragmentList.add(new FragmentListen());
        fragmentList.add(new FragmentSearch());
        fragmentList.add(new FragmentMoments());
        FragmentViewPagerAdapter adapter=new FragmentViewPagerAdapter(this.getSupportFragmentManager(),
                nestedViewPager,fragmentList);
        playingButton= (PlayingButton) findViewById(R.id.bt_play_bottom);
        playingButton.setOnClickListener(this);

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder binder = (PlayService.MyBinder) service;
                pservice = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(getApplicationContext(), PlayService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                if(pservice!=null){
                    handler.sendEmptyMessage(0);
                }
            }
        };
        Timer timer=new Timer();
        timer.schedule(task,0,1000);
        initDataWindow();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(pservice.isPlaying()) {
                playingButton.setIsPlaying(pservice.isPlaying());
//            Log.e("percent",pservice.getPercentage()+"");
                playingButton.setPercentage(pservice.getPercentage() * 100 / pservice.getLength());
            }
        }
    };
        //获取本地音乐
    private void initDataWindow() {
        locallist.addAll(MusicDao.getAllSongs(this));
        adapter=new PlayingSongListAdapter(MainActivity.this,R.layout.song_play_item,locallist);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_play_bottom:
                if(pservice.isPlaying()){
                pservice.pause();}else {
                    pservice.start();
                }
                break;
            case R.id.bt_play_list:
                locallist.size();
                popUpWindow=new PlaylistPopUpWindow(MainActivity.this,locallist,adapter,this,this,this);
                //显示窗口

                popUpWindow.showAtLocation(MainActivity.this.findViewById(R.id.activity_main),
                        Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                popUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.play_mode:
                popUpWindow.dismiss();
                //循环模式
                break;
            case R.id.collect:
                popUpWindow.dismiss();

                //收藏
                break;
            case R.id.delede_all:
                popUpWindow.dismiss();

                //清空列表
                break;

        }
    }
    //点击播放列表
    @Override
    public void onClick() {
        Intent intent=new Intent(MainActivity.this,PlayingSongActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pservice.doPlay(locallist,position);
    }


    @Override
    public void ondelete(int position) {
        locallist.remove(position);
        adapter.notifyDataSetChanged();
    }
}
