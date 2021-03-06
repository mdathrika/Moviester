package com.codepath.flickster.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.activity.YoutubePlayActivity;
import com.codepath.flickster.entity.Movie;
import com.codepath.flickster.R;
import com.codepath.flickster.helper.DeviceDimensionsHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by mdathrika on 10/13/16.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private static class ViewHolder {
        ImageView poster;
        ImageView play;
        TextView title;
        TextView oview;
    }


    public MovieListAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.movie, movies);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie, parent, false);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.posterpath);
            viewHolder.play = (ImageView) convertView.findViewById(R.id.playbtn);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.oview = (TextView) convertView.findViewById(R.id.overview);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.play.setVisibility(View.GONE);

        int height = DeviceDimensionsHelper.getDisplayHeight(context);
        int width = DeviceDimensionsHelper.getDisplayWidth(context);

        int orientation = context.getResources().getConfiguration().orientation;
        String imagePath = "";
        int widthFactor=12, heightFactor=18;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePath = movie.getPosterPath();
            widthFactor = 12;
            heightFactor = 18;
        }  else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imagePath = movie.getBackdropPath();
            widthFactor = 18;
            heightFactor = 12;
        }

        Picasso.with(context).load(imagePath)
                .resize(width/3, height/3)
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.progress_animation)
                .into(viewHolder.poster);

        if(movie.getPopularity() > 5.0) {
            Picasso.with(context).load(R.drawable.play_circle)
                    .resize((width/widthFactor), (height/heightFactor))
                    .into(viewHolder.play);
            viewHolder.play.setVisibility(View.VISIBLE);

            viewHolder.play.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    movie.getMovieId();

                    Intent intent = new Intent(context, YoutubePlayActivity.class);
                    intent.putExtra("videoid",movie.getVideos().get(0).getKey());
                    //intent.putExtra("videosList",videos);
                    context.startActivity(intent);

                }
            });
        }

        viewHolder.title.setText(movie.getTitle());
        viewHolder.oview.setText(movie.getOverview());

        return convertView;
    }
}
