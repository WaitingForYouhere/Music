package com.example.lenovo.music.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.music.bean.Billboard;
import com.example.lenovo.music.bean.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/18.
 */

public class SongUtil {
    private Song song;
    private Billboard billboard;
    private List<Song> list;
    private List<Billboard> blist;
    RequestQueue mQueue;
    private static SongUtil SongUtil;
    private String[] type={"1","2","11","21","22","23","24","25"};

    public SongUtil(Context context){
        mQueue = Volley.newRequestQueue(context);
    }
    public synchronized static SongUtil getInstance(Context context){
        if(SongUtil==null){
            SongUtil=new SongUtil(context);
        }
        return SongUtil;
    }

    public void getSongRanking(final Context context, final Handler hand, int num, String type1) {
//        blist = new ArrayList<Billboard>();


            String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?size=" +
                    num + "&type=" + type1 +
                    "&callback=cb_list&_t=1468380543284&format=json&method=baidu.ting.billboard.billList";
            StringRequest stringRequest = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str = Tools.enCodeStr(response);
                            Tools.enCodeUTF(str);
                            str = str.substring(12, str.length());
                            str = str.substring(0, str.length() - 2);

//                            Log.e("TAG", str);
                            try {
                                list = new ArrayList<Song>();

                                billboard = new Billboard();
                                JSONObject jsonObject = new JSONObject(str);
                                JSONArray jsonArray = jsonObject.getJSONArray("song_list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    song = new Song();
                                    JSONObject realjsonObject = jsonArray.getJSONObject(i);
                                    String lrcLink = realjsonObject.getString("lrclink");
                                    Tools.unicode2String(lrcLink);
                                    song.setLrcLink(lrcLink);
                                    String title = realjsonObject.getString("title");
                                    Tools.unicode2String(title);
                                    song.setTitle(title);
                                    String singer = realjsonObject.getString("artist_name");
                                    Tools.unicode2String(singer);
                                    song.setSinger(singer);
                                    String hot = realjsonObject.getString("hot");
                                    Tools.unicode2String(hot);
                                    song.setHot(hot);
                                    String picUrl = realjsonObject.getString("pic_big");
                                    Tools.unicode2String(picUrl);
                                    song.setPicUrl(picUrl);
                                    String year = realjsonObject.getString("publishtime");
                                    Tools.unicode2String(year);
                                    song.setYear(year);
                                    String album = realjsonObject.getString("album_title");
                                    Tools.unicode2String(album);
                                    song.setAlbum(album);
                                    String fileUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?songid="
                                            + realjsonObject.getString("song_id") + "&method=baidu.ting.song.play";
                                    Tools.unicode2String(fileUrl);
                                    song.setFileUrl(fileUrl);
//                                    Log.e("util",song.getFileUrl());
                                    list.add(song);
//                                    Handler hand1 = new Handler() {
//                                        @Override
//                                        public void handleMessage(Message msg) {
//                                            super.handleMessage(msg);
//                                            String Url = (String) msg.obj;
//                                            song.setFileUrl(Url);
//                                            Log.e("util",song.getFileUrl());
//                                            list.add(song);
////                                            this.notifyAll();
//                                        }
//                                    };
//                                    getSongUrl(hand1,fileUrl);

                                }
                                billboard.setSongList(list);
                                JSONObject billboardjsonobject = jsonObject.getJSONObject("billboard");
                                int type = billboardjsonobject.getInt("billboard_type");
                                billboard.setType(type);
                                String bill_no = billboardjsonobject.getString("billboard_no");
                                Tools.unicode2String(bill_no);
                                billboard.setBillboard_no(bill_no);
                                String update_date = billboardjsonobject.getString("update_date");
                                Tools.unicode2String(update_date);
                                billboard.setUpdateDate(update_date);
                                String name = billboardjsonobject.getString("name");
                                Tools.unicode2String(name);
//                                Log.e("bname",name);
                                billboard.setName(name);
                                String comment = billboardjsonobject.getString("comment");
                                Tools.unicode2String(comment);
                                billboard.setComment(comment);
                                String pic_s192 = billboardjsonobject.getString("pic_s192");
                                Tools.unicode2String(pic_s192);
                                billboard.setPicUrl(pic_s192);
                                String billboard_songnum = billboardjsonobject.getString("billboard_songnum");
                                Tools.unicode2String(billboard_songnum);
                                billboard.setBillboard_songnum(billboard_songnum);


                                Message message = new Message();
                                message.obj = billboard;
                                hand.sendMessage(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);

    }    public void getSongUrl( final Handler hand,String url) {

//            String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?songid=" +
//                    "100575177" + "&method=baidu.ting.song.play";
            StringRequest stringRequest = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String str = Tools.enCodeStr(response);
                            Tools.enCodeUTF(str);

                            try {
                                JSONObject jsonObject = new JSONObject(str);
                                JSONObject realjson0bject = jsonObject.getJSONObject("bitrate");
                                    String fileUrl = realjson0bject.getString("show_link");
                                    Tools.unicode2String(fileUrl);
                                Message message = new Message();
                                message.obj = fileUrl;
                                hand.sendMessage(message);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);

    }
    public void getAllRank(Context context,final Handler hand,int num){
        for (int i = 0; i < type.length; i++) {
//            Log.e("TAG",type[i]);
            getSongRanking(context,hand,num,type[i]);
        }
    }

}
