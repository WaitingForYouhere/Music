package com.example.lenovo.music.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.music.bean.Daily;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/18.
 */

public class ZhiHuUtil {
    private Daily daily;
    private List<Daily> list;
    RequestQueue mQueue;
    private static ZhiHuUtil movieUtil;
    public ZhiHuUtil(Context context){
        mQueue = Volley.newRequestQueue(context);
    }
    public synchronized static ZhiHuUtil getInstance(Context context){
        if(movieUtil==null){
            movieUtil=new ZhiHuUtil(context);
        }
        return movieUtil;
    }

    public void getZhiHuLatest(Context context, final String type, final Handler hand){
        String url="http://news-at.zhihu.com/api/4/news/"+type;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            try {
                                list=new ArrayList<Daily>();
                                Log.e("TAG", response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray;
                                if(type.equals("latest")) {
                                    jsonArray = jsonObject.getJSONArray("stories");}else {
                                    jsonArray = jsonObject.getJSONArray("recent");
                                }
//                                if(type.equals("hot"))
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        daily = new Daily();
                                        JSONObject realjsonObject = jsonArray.getJSONObject(i);
                                        String title = realjsonObject.getString("title");
                                        daily.setTitle(title);
                                        if(type.equals("latest")){
                                            String picurl = realjsonObject.getJSONArray("images").getString(0);
                                            daily.setImagePaht(picurl);
                                            String id = realjsonObject.getString("id");
                                            daily.setId(id);
                                        }else {
                                            String picurl = realjsonObject.getString("thumbnail");
                                            daily.setImagePaht(picurl);
                                            String id = realjsonObject.getString("news_id");
                                            daily.setId(id);
                                        }
                                        list.add(daily);

                                }
                                Bundle bundle=new Bundle();
                                bundle.putString("type",type);
                                Message message = new Message();
                                message.obj =list;
                                message.setData(bundle);
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
    public void getZhihuStory( Context context,String stroyid,final Handler hand){
        String url="http://news-at.zhihu.com/api/4/news/"+stroyid;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", response);
                            try {
                                JSONObject realjsonObject=new JSONObject(response);
                                daily=new Daily();
                                String title=realjsonObject.getString("title");
                                daily.setTitle(title);
                                String body=realjsonObject.getString("body");
                                daily.setBody(body);
                                String picurl=realjsonObject.getJSONArray("images").getString(0);
                                daily.setImagePaht(picurl);
                                String id=realjsonObject.getString("id");
                                daily.setId(id);
                                String shareUrl=realjsonObject.getString("share_url");
                                daily.setShareUrl(shareUrl);
                                String imageSource=realjsonObject.getString("image_source");
                                daily.setImageSource(imageSource);
                                Message message = new Message();
                                message.obj =daily;
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


}
