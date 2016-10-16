package com.codepath.flickster.entity;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mdathrika on 10/13/16.
 */
public class Movie {

    String title;

    String overview;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("release_date")
    Date releaseDate;

    float popularity;

    @SerializedName("vote_average")
    float voteAvg;

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

    public void setReleaseDate(Date releaseDate) {
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public static List<Movie> fromJson(JSONArray jsonArray) {

        List<Movie> movies = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                // Deserialize json into object fields

                movie.title = jsonObject.getString("title");
                movie.posterPath = "https://image.tmdb.org/t/p/w342" + jsonObject.getString("poster_path");
                movie.overview = jsonObject.getString("overview");

                movie.popularity = Float.parseFloat(jsonObject.getString("popularity"));
                movie.voteAvg = Float.parseFloat(jsonObject.getString("vote_average"));

                System.out.println(movie.title + " :: " +jsonObject.getString("popularity") +" :: "+ movie.popularity);

                String url = jsonObject.getString("backdrop_path");
                if(url == null || url.trim().length() == 0|| url.equals("null"))
                    url = jsonObject.getString("poster_path");

                movie.backdropPath = "https://image.tmdb.org/t/p/w342" + url;
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Return new object
        return movies;
    }

}
