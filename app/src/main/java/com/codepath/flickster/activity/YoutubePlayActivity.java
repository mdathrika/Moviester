package com.codepath.flickster.activity;

import android.content.Intent;
import android.os.Bundle;

import com.codepath.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by mdathrika on 10/15/16.
 */
public class YoutubePlayActivity extends YouTubeBaseActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_youtube_player);

        YouTubePlayerView playerView = (YouTubePlayerView)findViewById(R.id.player);
        Intent intent = getIntent();
        final String videoId = intent.getStringExtra("videoid");
        System.out.println("***********videoId :: "+videoId);
        playerView.initialize("AIzaSyBjr9cfvkUmym-9zh5W0DqA80XFWweBWdQ",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(videoId);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
