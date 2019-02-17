package com.kgluszczyk.destinations.view;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;
import com.kgluszczyk.destinations.R;
import com.kgluszczyk.destinations.databinding.ActivityShowMovieBinding;

public class ShowMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityShowMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_movie);
        VideoView videoview = binding.film;
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.giphy);
        videoview.setVideoURI(uri);
        videoview.setOnPreparedListener(mp -> mp.setLooping(true));
        videoview.start();
    }
}
