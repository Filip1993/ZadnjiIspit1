package com.filipkesteli.zadnjiispit1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private HorizontalSlider hzProgress;
    private HorizontalSlider hzVolume;
    private Button btnToggle;

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        initMediaPlayer();
        initAudioManager();
        setupListeners();
    }

    private void initWidgets() {
        btnToggle = (Button) findViewById(R.id.btnToggle);
        hzProgress = (HorizontalSlider) findViewById(R.id.hzProgress);
        hzVolume = (HorizontalSlider) findViewById(R.id.hzVolume);
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.cent);
        hzProgress.setMax(mediaPlayer.getDuration());
    }

    private void initAudioManager() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        hzVolume.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        hzVolume.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    private void setupListeners() {
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnToggle.setText(R.string.play);
                } else {
                    mediaPlayer.start();
                    btnToggle.setText(R.string.stop);
                }
            }
        });
        hzProgress.setOnProgressChangedListener(new OnProgressChangedListener() {
            @Override
            public void onProgressChanged(View view, int progress) {
                mediaPlayer.seekTo(progress);
            }
        });
        hzVolume.setOnProgressChangedListener(new OnProgressChangedListener() {
            @Override
            public void onProgressChanged(View view, int progress) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }
}

