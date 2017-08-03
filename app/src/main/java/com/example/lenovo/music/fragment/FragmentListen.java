package com.example.lenovo.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.activity.LocalMusicActivity;


public class FragmentListen extends Fragment implements View.OnClickListener{

    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private View view5;
    private ImageView music_click_image1;
    private TextView music_click_text1;
    private TextView music_click_count1;
    private ImageView music_click_where1;
    private ImageView music_click_image2;
    private TextView music_click_text2;
    private TextView music_click_count2;
    private ImageView music_click_where2;
    private ImageView music_click_image3;
    private TextView music_click_text3;
    private TextView music_click_count3;
    private ImageView music_click_where3;
    private ImageView music_click_image4;
    private TextView music_click_text4;
    private TextView music_click_count4;
    private ImageView music_click_where4;
    private ImageView music_click_image5;
    private TextView music_click_text5;
    private TextView music_click_count5;
    private ImageView music_click_where5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_listen, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        View view1=view.findViewById(R.id.local_music);
        music_click_image1= (ImageView) view1.findViewById(R.id.music_click_image);
        music_click_text1= (TextView) view1.findViewById(R.id.music_click_text);
        music_click_count1= (TextView) view1.findViewById(R.id.music_click_count);
        music_click_where1= (ImageView) view1.findViewById(R.id.music_click_where);
        View view2=view.findViewById(R.id.local_latest_play);
        music_click_image2= (ImageView) view2.findViewById(R.id.music_click_image);
        music_click_image2.setImageResource(R.drawable.latest);
        music_click_text2= (TextView) view2.findViewById(R.id.music_click_text);
        music_click_text2.setText("最近播放");
        music_click_count2= (TextView) view2.findViewById(R.id.music_click_count);

        music_click_where2= (ImageView) view2.findViewById(R.id.music_click_where);
        View view3=view.findViewById(R.id.local_download_manager);
        music_click_image3= (ImageView) view3.findViewById(R.id.music_click_image);
        music_click_image3.setImageResource(R.drawable.download_manage);
        music_click_text3= (TextView) view3.findViewById(R.id.music_click_text);
        music_click_text3.setText("下载管理");
        music_click_count3= (TextView) view3.findViewById(R.id.music_click_count);
        music_click_where3= (ImageView) view3.findViewById(R.id.music_click_where);
        View view4=view.findViewById(R.id.local_myRadio);
        music_click_image4= (ImageView) view4.findViewById(R.id.music_click_image);
        music_click_image4.setImageResource(R.drawable.radio);
        music_click_text4= (TextView) view4.findViewById(R.id.music_click_text);
        music_click_text4.setText("我的电台");
        music_click_count4= (TextView) view4.findViewById(R.id.music_click_count);
        music_click_where4= (ImageView) view4.findViewById(R.id.music_click_where);
        View view5=view.findViewById(R.id.local_myCollection);
        music_click_image5= (ImageView) view5.findViewById(R.id.music_click_image);
        music_click_image5.setImageResource(R.drawable.collection);
        music_click_text5= (TextView) view5.findViewById(R.id.music_click_text);
        music_click_text5.setText("我的收藏");
        music_click_count5= (TextView) view5.findViewById(R.id.music_click_count);
        music_click_count5.setText("(专辑/歌手/MV/专栏");
        music_click_where5= (ImageView) view5.findViewById(R.id.music_click_where);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.local_music:
                Intent intent=new Intent(getContext(), LocalMusicActivity.class);
                startActivity(intent);
                break;
            case R.id.local_latest_play:

                break;

            case R.id.local_download_manager:

                break;
            case R.id.local_myRadio:

                break;
            case R.id.local_myCollection:
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
