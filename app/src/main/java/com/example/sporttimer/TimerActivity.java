package com.example.sporttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private int sets;
    private int workMinutes ;
    private int workSeconds;
    private int restMinutes;
    private int restSeconds;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds;
    private int timerState; // 0 for preparation and 1 for work and 2 for rest and 3 for end

    private TextView timerTimeRemainingTextView;
    private TextView stateTextView;
    private TextView setsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sets = Integer.parseInt(getIntent().getStringExtra("sets"));
        workMinutes = Integer.parseInt(getIntent().getStringExtra("workMinutes")) ;
        workSeconds = Integer.parseInt( getIntent().getStringExtra("workSeconds"));
        restMinutes = Integer.parseInt( getIntent().getStringExtra("restMinutes"));
        restSeconds = Integer.parseInt( getIntent().getStringExtra("restSeconds"));

        timerState = 0;
        timeLeftInMilliSeconds = 6000;

        stateTextView = findViewById(R.id.stateTextView);
        setsTextView = findViewById(R.id.timerSetsTextView);
        timerTimeRemainingTextView = findViewById(R.id.timerTimeRemainingTextView);

        setsTextView.setText(String.valueOf(sets));
        timerTimeRemainingTextView.setText((String.valueOf(timeLeftInMilliSeconds/ 1000)));
        stateTextView.setText("آماده باش");
        startTimer();

        //setsTextView.setText(sets);

    }


    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long newtTimeLeftInMilliSeconds) {
                timeLeftInMilliSeconds = timeLeftInMilliSeconds - 1000;
                updateDisplayedTime();
            }

            @Override
            public void onFinish() {

                TimerStateUpdate();
            }

        }.start();
    }

    private void updateDisplayedTime() {
        int seconds = (int) ((timeLeftInMilliSeconds/1000) % 60);
        int minutes = (int) (((timeLeftInMilliSeconds/1000)-seconds)/60);
        if (minutes < 0)
            minutes = 0;
        NumberFormat formatter = new DecimalFormat("00");
        timerTimeRemainingTextView.setText(String.valueOf(formatter.format(minutes)+" : "+formatter.format(seconds)));
    }


    private  void TimerStateUpdate(){
        //starting of work
        if (this.timerState == 0 && sets > 1){
            timerState++;
            stateTextView.setText("تمرین");
            timeLeftInMilliSeconds =(long) ((workMinutes*60)+(workSeconds+1))*1000;
            startTimer();
        }

        //starting of rest
        else if (this.timerState == 1 && sets > 1){
            timerState++;
            stateTextView.setText("استراحت");
            timeLeftInMilliSeconds =(long) ((restMinutes*60)+(restSeconds+1))*1000;
            startTimer();
        }
        else if (this.timerState == 2){
            timerState--;
            sets--;
            setsTextView.setText(String.valueOf(sets));
            stateTextView.setText("تمرین");
            timeLeftInMilliSeconds =(long) ((workMinutes*60)+(workSeconds+1))*1000;
            startTimer();
        }

        else if (timerState == 1 && sets == 1){
            timerState = 3;
            stateTextView.setText("پایان");
            finish();
        }

    }

    private void pauseTimer(){}
}