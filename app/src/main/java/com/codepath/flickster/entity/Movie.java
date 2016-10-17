package com.codepath.flickster.entity;

import android.content.Intent;

import com.codepath.flickster.activity.YoutubePlayActivity;
import com.google.gson.annotations.SerializedName;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by mdathrika on 10/13/16.
 */
public class Movie implements Serializable{

    String title;

    String overview;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("release_date")
    String releaseDate;

    float popularity;

    @SerializedName("vote_average")
    float voteAvg;

    String movieId;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    List<Video> videos;

    public float getVoteAvg() {
        return voteAvg;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {

        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public static List<Movie> fromJson(JSONArray jsonArray) {

        List<Movie> movies = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                final Movie movie = new Movie();
                // Deserialize json into object fields

                movie.movieId = jsonObject.getString("id");
                movie.title = jsonObject.getString("title");
                movie.posterPath = "https://image.tmdb.org/t/p/w342" + jsonObject.getString("poster_path");
                movie.overview = jsonObject.getString("overview");

                movie.popularity = Float.parseFloat(jsonObject.getString("popularity"));
                movie.voteAvg = Float.parseFloat(jsonObject.getString("vote_average"));

                movie.releaseDate = jsonObject.getString("release_date");

                String url = jsonObject.getString("backdrop_path");
                if(url == null || url.trim().length() == 0|| url.equals("null"))
                    url = jsonObject.getString("poster_path");

                movie.backdropPath = "https://image.tmdb.org/t/p/w342" + url;

                String videoUrl = "https://api.themoviedb.org/3/movie/"+movie.getMovieId()+"/videos";
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed");
                client.get(videoUrl, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    List <Video> videos = Video.fromJson(response.getJSONArray("results"));
                                    movie.setVideos(videos);
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                );

                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Return new object
        return movies;
    }

}
