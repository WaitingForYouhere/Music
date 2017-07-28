package com.example.lenovo.music.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.myview.C;
import com.example.lenovo.music.myview.ImageBanerFramLayout;
import com.example.lenovo.music.service.PlayService;
import com.example.lenovo.music.util.SongsRecommendation;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearchPersonality extends Fragment implements ImageBanerFramLayout.FramLayoutListener,SongsRecommendation.OnRecommendationListener {

    private ImageBanerFramLayout ivGroup;
    private List<Bitmap> bmlist=new ArrayList<Bitmap>();
    ;
    private int[] pid=new int[]{
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5
    };
    private List<Song> list;
    private SongsRecommendation songsRecommendation;
    private PlayService pservice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_serch_personality, container, false);
        ivGroup= (ImageBanerFramLayout) view.findViewById(R.id.view_group);
        DisplayMetrics dm=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        C.witth=dm.widthPixels;

        for(int i=0;i<pid.length;i++){
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),pid[i]);
            bmlist.clear();
            bmlist.add(bitmap);
        }

        ivGroup.addBitmaps(bmlist);
//        for(int i=0;i<pid.length;i++){
//            ImageView iv=new ImageView(this);
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            iv.setLayoutParams(new ViewGroup.LayoutParams(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//            iv.setImageResource(pid[i]);
//            ivGroup.addView(iv);
//        }
        ivGroup.setlistener(this);
        songsRecommendation=SongsRecommendation.getInstance();
        songsRecommendation.setListener(this);
        songsRecommendation.get();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void ClickImageIndex(int pos) {

    }
    //加载网络数据
    @Override
    public void onRecommend(ArrayList<Song> results) {
        list=results;
        Log.e("TAG","加载完了"+list.get(0).getFileUrl());



        //绑定播放服务
//        ServiceConnection connection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                PlayService.MyBinder binder = (PlayService.MyBinder) service;
//                pservice = binder.getService();
//                pservice.doPlay(list, 0);
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        };
//
//        Intent intent = new Intent(getContext(), PlayService.class);
//        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
