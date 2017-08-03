package com.example.lenovo.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Movie;
import com.example.lenovo.music.bean.MovieDetail;
import com.example.lenovo.music.myview.MyHorizontalView;
import com.example.lenovo.music.util.MovieUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieSummaryActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    @Bind(R.id.summary_image_movie)
    ImageView summaryImageMovie;
    @Bind(R.id.sum_original_movie)
    TextView sumOriginalMovie;
    @Bind(R.id.sum_rating_movie)
    TextView sumRatingMovie;
    @Bind(R.id.sum_saw_movie)
    TextView sumSawMovie;
    @Bind(R.id.sum_type_movie)
    TextView sumTypeMovie;
    @Bind(R.id.sum_year_movie)
    TextView sumYearMovie;
    @Bind(R.id.sum_locate_movie)
    TextView sumLocateMovie;
    @Bind(R.id.star_srcollView)
    HorizontalScrollView starScrollView;
    @Bind(R.id.sum_title_movie)
    TextView sumTitleMovie;
    @Bind(R.id.sum_summary_movie)
    TextView sumSummaryMovie;
    @Bind(R.id.activity_movie_summary)
    LinearLayout activityMovieSummary;
    @Bind(R.id.scroll_layout)
    MyHorizontalView scrollLayout;
    @Bind(R.id.movie_detail_title)
    TextView title;
    @Bind(R.id.movie_detail_tool)
    Toolbar toolbar;


    private Movie movie;
    private MovieDetail movieDetail;
    private MovieUtil movieUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_summary);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(this);
        Intent intent=getIntent();
        movie= (Movie) intent.getExtras().getSerializable("movie");
        movieUtil=MovieUtil.getInstance(getApplicationContext());
        movieUtil.getMovieDetail(getApplicationContext(),movie.getId(),hand);

    }
    private Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            movieDetail= (MovieDetail) msg.obj;
            title.setText(movieDetail.getName());
            Glide.with(getApplicationContext()).load(movieDetail.getImageUrl()).into(summaryImageMovie);
            sumOriginalMovie.setText("原名："+movieDetail.getOriginName());
            sumRatingMovie.setText("评分："+movieDetail.getAverage());
            sumSawMovie.setText(movieDetail.getComments_count()+"人评分");
            sumTypeMovie.setText("类型:"+movieDetail.getType());
            sumYearMovie.setText("上映日期："+movieDetail.getYear());
            sumLocateMovie.setText("制作国家/地区：["+movieDetail.getCountries());
            sumSummaryMovie.setText(movieDetail.getSummary());
            for(String url:movieDetail.getPiclist()){
                if(url!=null){
                    ImageView iv=new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(500,800);
                    lp.setMargins(10,30,10,10);
                    iv.setLayoutParams(lp);
                    iv.setScaleType(ImageView.ScaleType. CENTER_CROP);

                    Glide.with(getApplicationContext()).load(url).into(iv);
                    scrollLayout.addView(iv);
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.test11){
        Intent intent=new Intent(MovieSummaryActivity.this,WebViewActivity.class);
        intent.putExtra("url",movieDetail.getMobileUrl());
        startActivity(intent);}
        return false;
    }
}
