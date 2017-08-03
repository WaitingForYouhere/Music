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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.FragmentViewPagerAdapter;
import com.example.lenovo.music.adapter.MainRecyclerAdapter;
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
        PlayViewGroup.OnClickAction,AdapterView.OnItemClickListener ,PlayingSongListAdapter.OnDeleteListener,
        MainRecyclerAdapter.OnItemClickLitener{

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
    private RadioGroup radioGroup;
    private RadioButton rbt_listen;
    private RadioButton rbt_find;
    private RadioButton rbt_more;
    private LinearLayout main_bottom_layout;
    private RecyclerView main_bottom_recycle;
    private MainRecyclerAdapter rectadapter;
    private LinearLayoutManager linearLayoutManager;
    private int index=0;
    private boolean isShowing=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.main_tool);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.titlebar_menu);
        toolbar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("菜单", "onClick: " );
            }
        });
//        playList= (PlayViewGroup) findViewById(R.id.playing_list_main);
//        playList.setOnClickAction(this);
        nestedViewPager= (NestedViewPager) findViewById(R.id.main_viewpager);
        fragmentList.add(new FragmentListen());
        fragmentList.add(new FragmentSearch());
        fragmentList.add(new FragmentMoments());
        FragmentViewPagerAdapter adapter=new FragmentViewPagerAdapter(this.getSupportFragmentManager(),
                nestedViewPager,fragmentList);
        nestedViewPager.setAdapter(adapter);
        nestedViewPager.setCurrentItem(1);

        //底部播放播放区
        main_bottom_layout= (LinearLayout) findViewById(R.id.main_bottom_layout);
        main_bottom_recycle= (RecyclerView) findViewById(R.id.playing_list_main);
        linearLayoutManager =  new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        main_bottom_recycle.setLayoutManager(linearLayoutManager);
        main_bottom_recycle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(main_bottom_recycle.getChildCount()>0){
                        main_bottom_recycle.getChildAt(0).requestFocus();
                    }
                }
            }
        });
        main_bottom_recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dx>0){
                    pservice.PlayNext();
                }else if(dx<0){
                    pservice.PlayLast();
                }
            }
        });


        bt_play_list = (Button) findViewById(R.id.bt_play_list);
        bt_play_list.setOnClickListener(this);
        playingButton= (PlayingButton) findViewById(R.id.bt_play_bottom);
        playingButton.setOnClickListener(this);
        main_bottom_layout.setVisibility(View.GONE);

        radioGroup= (RadioGroup) findViewById(R.id.rg_home_viewpager_contorl);
        rbt_listen= (RadioButton) findViewById(R.id.rb_listen_pager);
        rbt_find= (RadioButton) findViewById(R.id.rb_find_pager);
        rbt_more= (RadioButton) findViewById(R.id.rb_more_pager);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_listen_pager:
                        nestedViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_find_pager:
                        nestedViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_more_pager:
                        nestedViewPager.setCurrentItem(2);
                        break;
                }
            }
        });
        nestedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbt_listen.setChecked(true);
                        break;
                    case 1:
                        rbt_find.setChecked(true);
                        break;
                    case 2:
                        rbt_more.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //播放按钮


        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder binder = (PlayService.MyBinder) service;
                pservice = binder.getService();
                rectadapter=new MainRecyclerAdapter(getApplicationContext(),locallist,pservice);
                main_bottom_recycle.setAdapter(rectadapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(rectadapter!=null){
        rectadapter.setmOnItemClickLitener(this);}
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(pservice.isPlaying()) {
                main_bottom_layout.setVisibility(View.VISIBLE);
                playingButton.setIsPlaying(pservice.isPlaying());
//            Log.e("percent",pservice.getPercentage()+"");
                playingButton.setPercentage(pservice.getPercentage() * 100 / pservice.getLength());

                    main_bottom_recycle.scrollToPosition(pservice.getPosition());


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
    //popupwindow点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pservice.doPlay(locallist,position);
    }


    @Override
    public void ondelete(int position) {
        locallist.remove(position);
        adapter.notifyDataSetChanged();
    }

    //底部播放栏歌单点击事件
    @Override
    public void onItemClick(View view, int position) {
        Log.e("tag",position+"");
        Intent intent=new Intent(MainActivity.this,PlayingSongActivity.class);
        startActivity(intent);
    }
}
