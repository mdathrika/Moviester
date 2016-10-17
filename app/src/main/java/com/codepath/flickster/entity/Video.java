package com.codepath.flickster.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdathrika on 10/16/16.
 */
public class Video implements Serializable{
    String id;
    String site;
    String key;
    String type;
    int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static List<Video> fromJson(JSONArray jsonArray) {

        List<Video> videos = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Video video = new Video();
                // Deserialize json into object fields

                video.id = jsonObject.getString("id");
                video.key = jsonObject.getString("key");
                video.size = Integer.parseInt(jsonObject.getString("size"));
                video.type = jsonObject.getString("type");

                videos.add(video);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Return new object
        return videos;
    }
}
