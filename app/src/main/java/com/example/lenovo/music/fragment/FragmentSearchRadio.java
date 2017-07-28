package com.example.lenovo.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.activity.BillBoardListActivity;
import com.example.lenovo.music.adapter.BillBoardAdapter;
import com.example.lenovo.music.bean.Billboard;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.util.SongUtil;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearchRadio extends Fragment implements AdapterView.OnItemClickListener{

    private SongUtil songUtil;
    private List<Billboard> blist=new ArrayList<Billboard>();
    private List<Song> slist=new ArrayList<Song>();
    private ListView billboard;
    private BillBoardAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_serch_radio, container, false);
        billboard= (ListView) view.findViewById(R.id.bill_board_list);
        adapter=new BillBoardAdapter(getContext(),R.layout.billboard_item,blist);
        billboard.setAdapter(adapter);
        billboard.setOnItemClickListener(this);
        songUtil=SongUtil.getInstance(getContext().getApplicationContext());
        songUtil.getAllRank(getContext(),handler,3);
        return view;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            blist.add((Billboard) msg.obj);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(), BillBoardListActivity.class);
        SharedPreferences share= getActivity().getSharedPreferences("bill_type",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("type",blist.get(position).getType()+"");
        editor.commit();
        startActivity(intent);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
