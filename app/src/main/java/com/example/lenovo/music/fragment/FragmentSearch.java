package com.example.lenovo.music.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearch extends Fragment {

    private List<Fragment> fragmentList=new ArrayList<Fragment>();
    private ViewPager vpSearch;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);

        vpSearch= (ViewPager) view.findViewById(R.id.vp_search);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_search);
        tabLayout.addTab(tabLayout.newTab().setText("个性推荐"));
        tabLayout.addTab(tabLayout.newTab().setText("歌单"));
        tabLayout.addTab(tabLayout.newTab().setText("金曲排行榜"));
        tabLayout.addTab(tabLayout.newTab().setText("同步院线"));
        vpSearch.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.e("TAG",tab.getPosition()+"");
                vpSearch.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragmentList.add(new FragmentSearchPersonality());
        fragmentList.add(new FragmentSearchSongs());
        fragmentList.add(new FragmentSearchRadio());
        fragmentList.add(new FragmentSearchRanking());
        FragmentViewPagerAdapter adapter=new FragmentViewPagerAdapter(this.getFragmentManager(),
                vpSearch,fragmentList);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
