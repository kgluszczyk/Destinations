package com.kgluszczyk.destinations.view;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;
import com.kgluszczyk.destinations.R;

public class ShowMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokaz_filml);
        VideoView videoview = (VideoView) findViewById(R.id.film);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.giphy);
        videoview.setVideoURI(uri);
        videoview.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videoview.start();
    }
}
