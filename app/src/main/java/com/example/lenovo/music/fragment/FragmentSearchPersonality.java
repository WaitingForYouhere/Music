package com.example.lenovo.music.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.activity.ZhiHuDailyActivity;
import com.example.lenovo.music.adapter.ViewHolder;
import com.example.lenovo.music.adapter.ZhiHuHotAdapter;
import com.example.lenovo.music.adapter.ZhiHuListAdapter;
import com.example.lenovo.music.bean.Daily;
import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.myview.C;
import com.example.lenovo.music.myview.ImageBanerFramLayout;
import com.example.lenovo.music.util.ZhiHuUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class FragmentSearchPersonality extends Fragment implements ImageBanerFramLayout.FramLayoutListener,
        AdapterView.OnItemClickListener{

    private ImageBanerFramLayout ivGroup;
    private List<Daily> bmlist=new ArrayList<Daily>();
    private Daily bitmap;
    ;
    private int[] pid=new int[]{
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5
    };
    private List<Song> list;
    private boolean isFirst=true;
    private View view;
    private ZhiHuUtil zhiHuUtil;
    private List<Daily> dlist=new ArrayList<Daily>();
    private ListView latestListView;
    private ZhiHuListAdapter adapter1;
    private GridView gridView;
    private ZhiHuHotAdapter adapterhot;
    private List<Daily> hotlist=new ArrayList<Daily>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_serch_personality, container, false);
        ivGroup= (ImageBanerFramLayout) view.findViewById(R.id.view_group);
        DisplayMetrics dm=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        C.witth=dm.widthPixels;


//        for(int i=0;i<pid.length;i++){
//            ImageView iv=new ImageView(this);
//            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            iv.setLayoutParams(new ViewGroup.LayoutParams(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//            iv.setImageResource(pid[i]);
//            ivGroup.addView(iv);
//        }
        ivGroup.setlistener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFirst) {
            //最新消息列表
            latestListView = (ListView) view.findViewById(R.id.zhihu_latest_list);
            adapter1 = new ZhiHuListAdapter(getContext().getApplicationContext(), R.layout.latest_item, dlist);
            latestListView.setAdapter(adapter1);
            latestListView.setOnItemClickListener(this);
            //热门消息列表
            gridView= (GridView) view.findViewById(R.id.zhihu_hot_gridview);
            adapterhot=new ZhiHuHotAdapter(getContext().getApplicationContext(),R.layout.hot_grid_item,hotlist);
            gridView.setAdapter(adapterhot);
            gridView.setOnItemClickListener(this);

            zhiHuUtil=ZhiHuUtil.getInstance(getContext().getApplicationContext());
            zhiHuUtil.getZhiHuLatest(getContext().getApplicationContext(), "latest", handler);
            zhiHuUtil.getZhiHuLatest(getContext().getApplicationContext(), "hot", handler);
            isFirst=false;
        }

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            if(bundle.getString("type").equals("latest")){
                dlist.clear();
                dlist.addAll((Collection<Daily>) msg.obj);
                adapter1.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(latestListView);
                bmlist.clear();
                for(int i=0;i<5;i++){
                    bitmap= dlist.get(i);
                    bmlist.add(bitmap);
                }
                ivGroup.addBitmaps(bmlist);
            }
            else if(bundle.getString("type").equals("hot")){
                hotlist.clear();
                hotlist.addAll((Collection<Daily>) msg.obj);
                Log.e("Framengperson", "handleMessage: "+hotlist.size() );
                adapterhot.notifyDataSetChanged();
                setGridViewHeightBasedOnChildren(gridView);
            }

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

    //轮播图点击
    @Override
    public void ClickImageIndex(int pos) {
        Log.e("TAG", "ClickImageIndex: "+pos );
        Intent intent=new Intent(getContext(), ZhiHuDailyActivity.class);
        intent.putExtra("id",bmlist.get(pos).getId());
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        int tag= (int) viewHolder.title.getTag();
        Intent intent=new Intent(getContext(), ZhiHuDailyActivity.class);
        switch (tag){
            case 1:
                //第一个listview点击事件
                intent.putExtra("id",dlist.get(position).getId());
                break;
            case 2:
                //第一个gridview点击事件
                intent.putExtra("id",hotlist.get(position).getId());
                break;
        }
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                view, "1234");
        startActivity(intent,options.toBundle());
    }
    public void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i=i+1) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
            totalHeight+=25;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len&i<4; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
