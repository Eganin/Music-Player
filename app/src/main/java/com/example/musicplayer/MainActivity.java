package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ImageView prevButtonImage;
    ImageView playButtonImage;
    ImageView nextButtonImage;
    SeekBar volumeSeekBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickedSeekBar();

        initMediaPlayer();

    }

    public void playHandler(View view){
        if (mediaPlayer.isPlaying()) { // если есть звук
            stopSound();
            setImagePlay();
        } else {// если нет звука
            playSound();
            setImagePause();
        }
    }

    public void prevHandler(View view){
    }

    public void nextHandler(View view){

    }

    public void playSound() {
        mediaPlayer.start();// проигрываем музыку
    }

    public void stopSound() {
        mediaPlayer.pause();// останавливаем
    }

    private void initMediaPlayer(){
        mediaPlayer = MediaPlayer.create(getApplicationContext(),
                R.raw.stuff);// указываем .mp3 mediaPlayer
    }


    private void setImagePlay(){
        playButtonImage = findViewById(R.id.playButtonImage);
        playButtonImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);

    }

    private void setImagePause(){
        playButtonImage = findViewById(R.id.playButtonImage);
        playButtonImage.setImageResource(R.drawable.ic_baseline_pause_24);
    }

    private void clickedSeekBar(){
        /*
        метод отвечает за изменение громкости от SeekBar- эквалайзера
         */
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);// вызов службы аудио
        // макс системное значение проигрывателя
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        volumeSeekBar.setMax(maxVolume); // устанавливаем макс звук
        // прослеживаем seekBar
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // измененение ползунка
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("Progress ",""+i);// logging debug
                // установка громкости
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            // касание ползунка
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // отпускание ползунка
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}