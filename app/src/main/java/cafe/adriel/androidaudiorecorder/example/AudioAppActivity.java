package cafe.adriel.androidaudiorecorder.example;

/**
 * Created by Allied Infosoft on 4/6/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.content.pm.PackageManager;
import android.widget.Button;

import java.io.IOException;

public class AudioAppActivity extends Activity {


    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    private static String audioFilePath;
     Button stopButton;
     Button playButton;
     Button recordButton;

    private boolean isRecording = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordaudio);

        recordButton = (Button) findViewById(R.id.recordButton);
        playButton = (Button) findViewById(R.id.playButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onStart();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playAudio();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopClicked();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();



        if (!hasMicrophone())
        {
            stopButton.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.INVISIBLE);
            recordButton.setVisibility(View.INVISIBLE);
        } else {
            playButton.setVisibility(View.INVISIBLE);
            stopButton.setVisibility(View.INVISIBLE);
        }

        audioFilePath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/myaudio.3gp";

    }



    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }
    public void recordAudio (View view) throws IOException
    {
        isRecording = true;
        stopButton.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.INVISIBLE);
        recordButton.setVisibility(View.INVISIBLE);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    public void stopClicked()
    {

        stopButton.setEnabled(false);
        playButton.setEnabled(true);

        if (isRecording)
        {
            recordButton.setVisibility(View.INVISIBLE);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            recordButton.setVisibility(View.VISIBLE);
        }
    }
    public void playAudio()
    {
        playButton.setVisibility(View.INVISIBLE);
        recordButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);

        mediaPlayer = new MediaPlayer();
        //mediaPlayer.setDataSource(audioFilePath);
        //mediaPlayer.prepare();
        mediaPlayer.start();
    }
}
