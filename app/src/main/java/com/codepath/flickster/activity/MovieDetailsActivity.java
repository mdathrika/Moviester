package com.codepath.flickster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.entity.Movie;
import com.codepath.flickster.helper.DeviceDimensionsHelper;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mdathrika on 10/16/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        Movie movie = (Movie)intent.getSerializableExtra("movie");
        ImageView imgView = (ImageView)findViewById(R.id.details_img);

        TextView date = (TextView)findViewById(R.id.details_releasedate);
        TextView title = (TextView)findViewById(R.id.details_title);
        TextView overview = (TextView)findViewById(R.id.details_overview);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        date.setText("Release Date: " + movie.getReleaseDate());

        int height = DeviceDimensionsHelper.getDisplayHeight(this);
        int width = DeviceDimensionsHelper.getDisplayWidth(this);

        Picasso.with(this).load(movie.getBackdropPath())
                .resize(width, height/3)
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.progress_animation)
                .into(imgView);


    }
}
