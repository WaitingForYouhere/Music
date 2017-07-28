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
import com.example.lenovo.music.bean.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/18.
 */

public class MovieUtil {
    private Movie movie;
    private List<Movie> list;
    RequestQueue mQueue;
    private static MovieUtil movieUtil;
    public MovieUtil(Context context){
        mQueue = Volley.newRequestQueue(context);
    }
    public synchronized static MovieUtil getInstance(Context context){
        if(movieUtil==null){
            movieUtil=new MovieUtil(context);
        }
        return movieUtil;
    }

    public void getMovieRanking( Context context,final Handler hand){
        String url="https://api.douban.com/v2/movie/in_theaters";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str=Tools.enCodeStr(response);
//                        Log.e("TAG", "move");
                        Tools.enCodeUTF(str);

//                        Log.e("TAG", str);
                            try {
                                list=new ArrayList<Movie>();
                                JSONObject jsonObject=new JSONObject(str);
                                String outtitle=jsonObject.getString("title");
                                Tools.unicode2String(outtitle);
                                JSONArray jsonArray=jsonObject.getJSONArray("subjects");
                                for(int i=0;i<jsonArray.length();i++){
                                    movie=new Movie();
                                    JSONObject realjsonObject=jsonArray.getJSONObject(i);
                                    JSONArray jsonArray1=realjsonObject.getJSONArray("genres");
                                    StringBuffer sb=new StringBuffer();
                                    for (int j = 0; j <jsonArray1.length() ; j++) {
                                        String string= (String) jsonArray1.get(j);
                                        Tools.unicode2String(string);
//                                        Log.e("TAG", string);
                                        sb.append(string);
                                        if(j<jsonArray1.length()-1){
                                            sb.append("/");
                                        }
                                    }
                                    movie.setType(sb.toString());
                                    String title=realjsonObject.getString("title");
                                    Tools.unicode2String(title);
                                    movie.setTitle(title);
                                    String rating=realjsonObject.getJSONObject("rating").getString("average");
                                    Tools.unicode2String(rating);
                                    movie.setRating(rating);
                                    JSONArray castArray=realjsonObject.getJSONArray("casts");
                                    StringBuffer sb1=new StringBuffer();
                                    for(int k=0;k<castArray.length();k++){
                                        JSONObject castObject=castArray.getJSONObject(k);
                                        String name=castObject.getString("name");
                                        sb1.append(name);
                                        if(k<castArray.length()-1){
                                            sb1.append("/");
                                        }
                                    }
                                    movie.setCasts(sb1.toString());
                                    String collect_count=realjsonObject.getString("collect_count");
                                    Tools.unicode2String(collect_count);
                                    movie.setSawNum(collect_count);
                                    JSONObject directorObject=realjsonObject.getJSONArray("directors").getJSONObject(0);
                                    String director=directorObject.getString("name");
                                    Tools.unicode2String(director);
                                    movie.setDirector(director);

                                    JSONObject posterObject=realjsonObject.getJSONObject("images");
                                    String posterUrl=posterObject.getString("large");
                                    Tools.unicode2String(posterUrl);
                                    movie.setPosterUrl(posterUrl);
//                                    Log.e("TAG", title);
                                    list.add(movie);
                                }
                                Message message = new Message();
                                message.obj =list;
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
