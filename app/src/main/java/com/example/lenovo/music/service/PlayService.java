package com.example.lenovo.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.lenovo.music.bean.Song;

import java.util.ArrayList;
import java.util.List;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener{
    private MediaPlayer mp;
    private int playingState=-1;
    private MyBinder mbinder=new MyBinder();
    private List<Song> onlist=new ArrayList<Song>();
    private int position=0;
    private int mediaposition;

    public PlayService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mp==null){
            mp=new MediaPlayer();}
        mp.setOnCompletionListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mbinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        mp.stop();
        return super.onUnbind(intent);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        PlayNext();
    }

    public class MyBinder extends Binder{
        public PlayService getService(){
            return PlayService.this;
        }
    }
    public int getPercentage(){
        mediaposition=mp.getCurrentPosition();
        return mediaposition;
    }
    public void doPlay(List<Song> list,int position){
        onlist.clear();
        onlist.addAll(list);
        this.position=position;
        onPlay(position,list);
    }
    public void addPlay(Song song){
        onlist.add(song);
        onPlay(onlist.size()-1,onlist);
    }
    //下一首
    public void PlayNext(){
        if(playingState<onlist.size()-1){
        playingState++;}else {
            playingState=0;
        }
        position=playingState;
        onPlay(playingState,onlist);
    }
//    上一首
    public void PlayLast(){
        if(playingState!=0){
            playingState--;
        }else {
            playingState=onlist.size()-1;
        }
        position=playingState;
        onPlay(playingState,onlist);
    }
    //播发
    public void start(){
        mp.start();
    }
    //获取文件名
    public String getName(){

        if(onlist.size()!=0){
        return onlist.get(position).getFileName();}else {
            return "未知";
        }
    }
    public boolean isPlaying(){
        return mp.isPlaying();
    }
    public void pause(){
        mp.pause();
    }
    public void seek(int progress){
        mp.seekTo(progress*mp.getDuration()/100);
        Log.e("pservice", "seek: "+progress*mp.getDuration()/100+"/"+mp.getDuration());
    }
    public int getLength(){
        return mp.getDuration();
    }
//    public MediaTimestamp getTimeStamp(){
//        return mp.getTimestamp();
//    }
    //初始化媒体播放器
    private void initMediaPlayer(MediaPlayer mp, int state,List<Song> list) {
        try {
            mp.setDataSource(list.get(state).getFileUrl());
//            mp.setDataSource("http://zhangmenshiting.baidu.com/data2/music/123943004/123943004.mp3?xcode=67dd47e441086517718197b4a0f16666");
//            mp.setDataSource("http://m10.music.126.net/20170724161103/ecc95c1a29a247a8f2f5f259ea6d9079/ymusic/6978/6254/c6d3/1dddad944ea5add781ddf74e5359e2e7.mp3");
            Log.e("pathnow",list.get(0).getFileUrl()+"");
            mp.prepare();
            playingState=state;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //播放录音
    public void onPlay(int state,List<Song> list) {


//        Log.e("ChatActivity",list.get(state).getFileUrl());
        if(playingState==-1){
            initMediaPlayer(mp,state,list);
            mp.start();
        }else {
            mp.stop();
            mp.reset();
            initMediaPlayer(mp,state,list);
            mp.start();
        }
    }
}
