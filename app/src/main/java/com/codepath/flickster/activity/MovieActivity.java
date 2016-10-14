package com.codepath.flickster.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.codepath.flickster.entity.Movie;
import com.codepath.flickster.R;
import com.codepath.flickster.adapter.MovieListAdapter;
import com.loopj.android.http.*;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ListView listView = (ListView)findViewById(R.id.moviesList);


        fetchNowPlayingMovies(listView, this);
    }

    private void fetchNowPlayingMovies(final ListView listView, final Context context) {
        String url = "https://api.themoviedb.org/3/movie/now_playing";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed");
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            List<Movie> movies = Movie.fromJson(response.getJSONArray("results"));
                            //System.out.println("***Length***"+movies.size());

                            MovieListAdapter adapter = new MovieListAdapter(context, movies);
                            listView.setAdapter(adapter);

                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                }
        );
    }
}
