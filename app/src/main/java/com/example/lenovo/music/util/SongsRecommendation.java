package com.example.lenovo.music.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.lenovo.music.bean.Song;
import com.example.lenovo.music.myview.C;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2017/7/24.
 */

public class SongsRecommendation {
    // http://music.baidu.com/top/new/?pst=shouyeTop
        private static final String URL = "http://music.baidu.com"
                + "/top/new/?pst=shouyeTop";
        private static SongsRecommendation sInstance;
        private static int FAILED=0;
        private static int SUCCESS=1;
        /**
         * 回调接口，传递数据给Activity或者Fragment
         * 非常好用的数据传递方式
         */
        private OnRecommendationListener mListener;

        private ExecutorService mThreadPool;

        public static SongsRecommendation getInstance() {
            if (sInstance == null)
                sInstance = new SongsRecommendation();
            return sInstance;
        }

        private Handler mHandler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==C.SUCCESS) {
                    if (mListener != null)
                        mListener
                                .onRecommend((ArrayList<Song>) msg.obj);
                }else if(msg.what==C.FAILED){

                        if (mListener != null)
                            mListener.onRecommend(null);
                }
            }
        };

        @SuppressLint("HandlerLeak")
        private SongsRecommendation() {
            // 创建单线程池
            mThreadPool = Executors.newSingleThreadExecutor();
        }

        /**
         * 设置回调接口OnRecommendationListener类的对象mListener
         *
         * @param l
         * @return
         */
        public SongsRecommendation setListener(OnRecommendationListener l) {
            mListener = l;
            return this;
        }
        /**
         * 真正执行网页解析的方法
         * 线程池中开启新的线程执行解析，解析完成之后发送消息
         * 将结果传递到主线程中
         */
        public void get() {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Song> result = getMusicList();
                    if (result == null) {
                        mHandler.sendEmptyMessage(C.FAILED);
                        return;
                    }
                    mHandler.obtainMessage(C.SUCCESS, result)
                            .sendToTarget();
                }
            });
        }

        private ArrayList<Song> getMusicList() {
            try {
                /**
                 * 一下方法调用请参考官网
                 * 说明：timeout设置请求时间，不宜过短。
                 * 时间过短导致异常，无法获取。
                 */
                Document doc = Jsoup
                        .connect(URL)
                        .userAgent(
                                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36"
                                        + " (KHTML, like Gecko) Chrome/42.0.2311.22 Safari/537.36")
                        .timeout(60 * 1000).get();
                //select为选择器，请参考官网说明
                Elements songTitles = doc.select("span.song-title");
                Elements artists = doc.select("span.author_list");
                ArrayList<Song> searchResults = new ArrayList<Song>();

                for (int i = 0; i < songTitles.size(); i++) {
                    Song searchResult = new Song();
                    Elements urls = songTitles.get(i).getElementsByTag("a");
                    searchResult.setFileUrl("http://music.baidu.com"+urls.get(0).attr("href"));
                    searchResult.setFileName(urls.get(0).text());

                    Elements artistElements = artists.get(i).getElementsByTag("a");
                    searchResult.setSinger(artistElements.get(0).text());
                    searchResult.setAlbum("最新推荐");
                    searchResults.add(searchResult);
                }
                return searchResults;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 回调接口 获取数据之后，通过该接口设置数据传递
         */
        public interface OnRecommendationListener {
            public void onRecommend(ArrayList<Song> results);
        }
    }
