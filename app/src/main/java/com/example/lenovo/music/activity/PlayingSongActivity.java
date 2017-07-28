package com.example.lenovo.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.service.PlayService;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingSongActivity extends AppCompatActivity {

    @Bind(R.id.plagying_head)
    ImageView plagyingHead;
    @Bind(R.id.song_time)
    TextView songTime;
    @Bind(R.id.song_progressBar)
    SeekBar songProgressBar;
    @Bind(R.id.song_length)
    TextView songLength;
    @Bind(R.id.playing_cycle)
    Button playingCycle;
    @Bind(R.id.playing_last)
    Button playingLast;
    @Bind(R.id.playing_play)
    Button playingPlay;
    @Bind(R.id.playing_next)
    Button playingNext;
    @Bind(R.id.playing_list)
    Button playingList;
    @Bind(R.id.playing_song_toolbar)
    Toolbar toolBar;

    private PlayService pservice;
    private Timer timer;
    private int length;
    private int progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_song);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.MyBinder binder = (PlayService.MyBinder) service;
                pservice = binder.getService();
                length = pservice.getLength() / 1000;
                songLength.setText(String.format("%02d", length / 60) + ":" + String.format("%02d", length % 60));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(PlayingSongActivity.this, PlayService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1 = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("TAG", progress1 + "");
                pservice.seek(progress1);
            }
        });
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (pservice != null) {
                    Message message = new Message();
                    message.obj = pservice.getName();
                    message.arg1 = pservice.getLength() ;
                    message.arg2=pservice.getPercentage();
                    handler.sendMessage(message);
                }
            }
        };
        timer.schedule(task, 1, 1000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int now =msg.arg2 ;
            int progress=now*100/msg.arg1;
            String title=(String) msg.obj;
            toolBar.setTitle(title.substring(0,title.length()-4));

            songLength.setText(String.format("%02d", msg.arg1 / 60/ 1000) + ":" + String.format("%02d", msg.arg1 / 1000% 60));
            songTime.setText(String.format("%02d", now/1000/ 60) + ":" + String.format("%02d", now/1000% 60));
            songProgressBar.setProgress(progress);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
        }
    };

    @OnClick({R.id.song_progressBar, R.id.playing_cycle, R.id.playing_last, R.id.playing_play, R.id.playing_next, R.id.playing_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.song_progressBar:

                break;
            case R.id.playing_cycle:
                int position = pservice.getPercentage();
                Log.e("TAG", position + "");
                break;
            case R.id.playing_last:
                pservice.PlayLast();
                break;
            case R.id.playing_play:
                if (pservice.isPlaying()) {
                    pservice.pause();
                } else {
                    pservice.start();
                }
                break;
            case R.id.playing_next:
                pservice.PlayNext();
                break;
            case R.id.playing_list:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
