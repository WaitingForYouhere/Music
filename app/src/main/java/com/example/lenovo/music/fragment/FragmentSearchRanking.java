package com.example.lenovo.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.activity.MovieSummaryActivity;
import com.example.lenovo.music.adapter.MovieInTheatersAdapter;
import com.example.lenovo.music.bean.Movie;
import com.example.lenovo.music.util.MovieUtil;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearchRanking extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private MovieInTheatersAdapter adapter;
    private List<Movie> list=new ArrayList<Movie>();
    private boolean isInit=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_serch_ranking, container, false);
        listView= (ListView) view.findViewById(R.id.list_in_theatre);
        adapter=new MovieInTheatersAdapter(getContext(),R.layout.in_theaters_item,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list.addAll((List<Movie>) msg.obj);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(isInit){
            MovieUtil.getInstance(getContext().getApplicationContext()).getMovieRanking(getContext(),handler);
            isInit=false;
        }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("TAG", "onItemClickRanking: "+position);
        Intent intent=new Intent(getActivity(), MovieSummaryActivity.class);
        intent.putExtra("movie",list.get(position));
        startActivity(intent);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
