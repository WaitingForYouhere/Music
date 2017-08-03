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
import com.example.lenovo.music.bean.NBAData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2017/7/18.
 */

public class NBAUtil {


    RequestQueue mQueue;
    private static NBAUtil NBAUtil;
    public NBAUtil(Context context){
        mQueue = Volley.newRequestQueue(context);
    }
    public synchronized static NBAUtil getInstance(Context context){
        if(NBAUtil==null){
            NBAUtil=new NBAUtil(context);
        }
        return NBAUtil;
    }

    public void getNBAList(final Handler hand){
        String url="http://c.m.163.com/nc/article/list/T1348649145984/0-20.html";

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("T1348649145984");
                            Message message = new Message();
                            message.obj =GsonUtil.parseJsonWithGson(jsonArray.toString(), NBAData.class);
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
