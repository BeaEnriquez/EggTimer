package com.bea.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    SeekBar timerSeekbar;
    TextView timerTextView;
    Button controllreButton;
    Boolean counterActive = false; //Counter is active = countdown
    CountDownTimer countDownTimer;

    public void resetTimer () {
        timerTextView.setText("0:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        controllreButton.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterActive = false;
    }

    public void updateTimer (int secondsLeft) {
        int minutes = (int) secondsLeft / 60; //getting the minutes
        int seconds = secondsLeft - minutes * 60; //getting the seconds

        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {

            secondString = "0" + secondString;

        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
        //updating the text view with the minutes and seconds given/running
    }

    public void controlTimer (View view) {
        if (counterActive == false) {
            counterActive = true;
            timerSeekbar.setEnabled(false);
            controllreButton.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisecondsUntilFinished) {
                    updateTimer((int) millisecondsUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                }
            }.start();

        } else {
           resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllreButton = (Button) findViewById(R.id.controllerButton);

        timerSeekbar.setMax(600); //10 minutes (maximum time)
        timerSeekbar.setProgress(30); //current

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
