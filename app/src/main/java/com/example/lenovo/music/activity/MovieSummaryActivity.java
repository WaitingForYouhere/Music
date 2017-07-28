package com.example.lenovo.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.music.R;
import com.example.lenovo.music.bean.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieSummaryActivity extends AppCompatActivity {

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
    @Bind(R.id.casts_image1)
    ImageView castsImage1;
    @Bind(R.id.casts_image2)
    ImageView castsImage2;
    @Bind(R.id.casts_image3)
    ImageView castsImage3;
    @Bind(R.id.sum_title_movie)
    TextView sumTitleMovie;
    @Bind(R.id.sum_summary_movie)
    TextView sumSummaryMovie;
    @Bind(R.id.activity_movie_summary)
    LinearLayout activityMovieSummary;

    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_summary);
        ButterKnife.bind(this);
        Intent intent=getIntent();

    }
}
