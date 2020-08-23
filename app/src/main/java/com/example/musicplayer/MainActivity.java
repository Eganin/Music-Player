package com.example.musicplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

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

    public void playHandler(View view) {
        if (mediaPlayer.isPlaying()) { // если есть звук
            stopSound();
            setImagePlay();
        } else {// если нет звука
            playSound();
            setImagePause();
        }
    }

    public void prevHandler(View view) {
        /*
        Метод отвечает за передвижение ползунка в начало
         */
        volumeSeekBar.setProgress(0);// ползунок в 0
        mediaPlayer.seekTo(0); // и музыка с начала
        stopSound();
        setImagePlay();
    }

    public void nextHandler(View view) {
        /*
        Метод отвечает за передвижение ползунка в конец
         */
        volumeSeekBar.setProgress(mediaPlayer.getDuration()-1);// ползунок в длительность трэка
        mediaPlayer.seekTo(mediaPlayer.getDuration()-1); // и музыка с конца
        stopSound();
        setImagePlay();
    }

    public void playSound() {
        mediaPlayer.start();// проигрываем музыку
    }

    public void stopSound() {
        mediaPlayer.pause();// останавливаем
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(),
                R.raw.stuff);// указываем .mp3 mediaPlayer
    }


    private void setImagePlay() {
        playButtonImage = findViewById(R.id.playButtonImage);
        playButtonImage.setImageResource(R.drawable.ic_baseline_play_arrow_24);

    }

    private void setImagePause() {
        playButtonImage = findViewById(R.id.playButtonImage);
        playButtonImage.setImageResource(R.drawable.ic_baseline_pause_24);
    }

    private void clickedSeekBar() {
        /*
        метод отвечает за перемещение SeekBar- эквалайзера в зависимости от пройденного
        времени трэка
         */
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);// вызов службы аудио
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        // устанавливаем макс звук как длительность трэка
        initMediaPlayer();
        volumeSeekBar.setMax(mediaPlayer.getDuration());
        // реализуем прокрутку трэка с помощью seekbar
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){ // если пользователь взаимодействует с seekbar
                    mediaPlayer.seekTo(i);// перематываем трэк
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // для работы с переодичными действиями
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // устанавливвем на seekbar пройденное время трэка
                volumeSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);//задержка , переодичность

    }
}