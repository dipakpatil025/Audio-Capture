package com.example.aoudio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_MICROPHONE = 1 ;

    private static final int REQUEST_CODE =1 ;
    private static final int WRITE_EXTERNAL_STORAG = 1;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private boolean isRecording = false;
    private static String audioFilePath;
  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myaudio.3gp";

      if (ContextCompat.checkSelfPermission(MainActivity.this,
              Manifest.permission.RECORD_AUDIO)  + ContextCompat.checkSelfPermission(MainActivity.this,
              Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {


          ActivityCompat.requestPermissions(MainActivity.this,
                  new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                  REQUEST_CODE);

      }


    }
    public void record(View v)
    {
        isRecording = true;
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            Toast.makeText(this, "Recording is started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }
    public void stopRecording(View v)
    {
        if (isRecording)
        {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            Toast.makeText(this, "Recording is stop", Toast.LENGTH_SHORT).show();
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    public void play(View v) throws IOException {
        Toast.makeText(this, "Recording is playing", Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

}
