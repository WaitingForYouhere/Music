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
import com.example.lenovo.music.bean.MovieDetail;

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
    private MovieDetail movieDetail;
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

                        Log.e("TAG", str);
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
                                    String id=realjsonObject.getString("id");
                                    movie.setId(id);
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
    public void getMovieDetail( Context context,String id,final Handler hand){
        String url="https://api.douban.com/v2/movie/subject/" + id;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String str=Tools.enCodeStr(response);
//                        Log.e("TAG", "move");
                        Tools.enCodeUTF(str);
                        Log.e("TAG", str);
                        try {
                            list=new ArrayList<Movie>();
                            JSONObject jsonObject=new JSONObject(str);
                                movieDetail=new MovieDetail();
                            String year=jsonObject.getString("year");
                            movieDetail.setYear(year);
                            String mobileUrl=jsonObject.getString("mobile_url");
                            movieDetail.setMobileUrl(mobileUrl);
                            String title=jsonObject.getString("title");
                            Tools.unicode2String(title);
                            movieDetail.setName(title);
                            String originTitle=jsonObject.getString("original_title");
                            Tools.unicode2String(originTitle);
                            movieDetail.setOriginName(originTitle);
                            String comments_count=jsonObject.getString("comments_count");
                            movieDetail.setComments_count(comments_count);
                            String images=jsonObject.getJSONObject("images").getString("large");
                            movieDetail.setImageUrl(images);
                            String countrys=jsonObject.getJSONArray("countries").getString(0);
                            movieDetail.setCountries(countrys);
                            String summary=jsonObject.getString("summary");
                            Tools.unicode2String(summary);
                            movieDetail.setSummary(summary);
                            String avarage=jsonObject.getJSONObject("rating").getString("average");
                            movieDetail.setAverage(avarage);


                            JSONArray jsonArray2=jsonObject.getJSONArray("genres");
                            StringBuffer sb1=new StringBuffer();
                            for (int j = 0; j <jsonArray2.length() ; j++) {
                                String string= (String) jsonArray2.get(j);
                                Tools.unicode2String(string);
//                                        Log.e("TAG", string);
                                sb1.append(string);
                                if(j<jsonArray2.length()-1){
                                    sb1.append("/");
                                }
                            }
                            movieDetail.setType(sb1.toString());

                            JSONArray jsonArray1=jsonObject.getJSONArray("casts");
                                StringBuffer sb=new StringBuffer();
                                String[] pic=new String[10];
                                String[] altlist=new String[10];
                                for (int j = 0; j <jsonArray1.length() ; j++) {
                                    JSONObject jsonObject2=jsonArray1.getJSONObject(j);
                                    String string= jsonObject2.getJSONObject("avatars").getString("large");
                                    Tools.unicode2String(string);
                                    String alt=jsonObject2.getString("alt");
                                    altlist[j]=alt;
                                    pic[j]=string;
                                }
                            movieDetail.setPiclist(pic);

                            Message message = new Message();
                            message.obj =movieDetail;
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
