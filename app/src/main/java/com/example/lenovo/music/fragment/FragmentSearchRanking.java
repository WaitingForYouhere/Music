package com.example.lenovo.music.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.adapter.MovieInTheatersAdapter;
import com.example.lenovo.music.bean.Movie;
import com.example.lenovo.music.util.MovieUtil;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearchRanking extends Fragment {

    private ListView listView;
    private MovieInTheatersAdapter adapter;
    private List<Movie> list=new ArrayList<Movie>();
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
        MovieUtil.getInstance(getContext().getApplicationContext()).getMovieRanking(getContext(),handler);
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
