package com.codepath.flickster.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flickster.entity.Movie;
import com.codepath.flickster.R;
import com.codepath.flickster.adapter.MovieListAdapter;
import com.loopj.android.http.*;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private MovieListAdapter adapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final ListView listView = (ListView)findViewById(R.id.moviesList);
        final Context context = this;
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNowPlayingMovies(listView, context);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie", movies.get(pos));
                System.out.println("****Before TITLE****"+movies.get(pos).getTitle());
                startActivity(intent);
            }
        });

        fetchNowPlayingMovies(listView, this);
    }

    private void fetchNowPlayingMovies(final ListView listView, final Context context) {
        System.out.println("******fetchNowPlayingMovies*****");
        String url = "https://api.themoviedb.org/3/movie/now_playing";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed");
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            movies = Movie.fromJson(response.getJSONArray("results"));
                            if(adapter == null) {
                                adapter = new MovieListAdapter(context, movies);
                                listView.setAdapter(adapter);
                            } else {
                                adapter.clear();
                                adapter.addAll(movies);
                            }

                        } catch(Exception e) {
                            e.printStackTrace();
                        } finally {
                            swipeContainer.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        swipeContainer.setRefreshing(false);

                    }
                }
        );
    }

}
