package com.example.sporttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds;
    private TextView timerTimeRemainingTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        String sets = getIntent().getStringExtra("sets");
        String work = getIntent().getStringExtra("work");
        String rest = getIntent().getStringExtra("rest");

        timeLeftInMilliSeconds = Integer.parseInt(work);
        timeLeftInMilliSeconds = timeLeftInMilliSeconds * 1000;

        TextView setsTextView = findViewById(R.id.timerSetsTextView);
        timerTimeRemainingTextView = findViewById(R.id.timerTimeRemainingTextView);

        setsTextView.setText(sets);
        timerTimeRemainingTextView.setText(work);
        startTimer();

        setsTextView.setText(sets);

    }


    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long newtTimeLeftInMilliSeconds) {
//                timeLeftInMilliSeconds = newtTimeLeftInMilliSeconds;
//                timerTimeRemainingTextView.setText(String.valueOf(timeLeftInMilliSeconds/1000));
                timeLeftInMilliSeconds = timeLeftInMilliSeconds - 1000;

//                Toast.makeText(getApplicationContext(),"working",
//                Toast.LENGTH_SHORT).show();
                timerTimeRemainingTextView.setText(String.valueOf(timeLeftInMilliSeconds/1000));

            }

            @Override
            public void onFinish() { }

        }.start();
    }

    private void pauseTimer(){}
}