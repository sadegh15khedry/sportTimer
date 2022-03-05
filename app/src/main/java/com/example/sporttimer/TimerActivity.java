package com.example.sporttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TimerActivity extends AppCompatActivity {
    private int sets;
    private int workMinutes;
    private int workSeconds;
    private int restMinutes;
    private int restSeconds;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds;
    private int timerState; // 0 for preparation and 1 for work and 2 for rest and 3 for end
    private boolean isTimerRunning;

    private TextView timerTimeRemainingTextView;
    private TextView stateTextView;
    private TextView setsTextView;
    //private TextView notificationState;
    private Button pauseResumeButton;
    private static final String CHANNEL_ID = "timer activity";

    //private RemoteViews remoteViews;
    //private NotificationManagerCompat notificationManagerCompat;
    //private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sets = Integer.parseInt(getIntent().getStringExtra("sets"));
        workMinutes = Integer.parseInt(getIntent().getStringExtra("workMinutes"));
        workSeconds = Integer.parseInt(getIntent().getStringExtra("workSeconds"));
        restMinutes = Integer.parseInt(getIntent().getStringExtra("restMinutes"));
        restSeconds = Integer.parseInt(getIntent().getStringExtra("restSeconds"));

        timerState = 0;
        isTimerRunning = true;
        timeLeftInMilliSeconds = 6000;

        stateTextView = findViewById(R.id.stateTextView);
        setsTextView = findViewById(R.id.timerSetsTextView);

        timerTimeRemainingTextView = findViewById(R.id.timerTimeRemainingTextView);

        setsTextView.setText(String.valueOf(sets));
        timerTimeRemainingTextView.setText((String.valueOf(timeLeftInMilliSeconds / 1000)));
        stateTextView.setText("آماده باش");

        startTimer();


    }

    @Override
    public void onResume() {
        pauseResumeButton = findViewById(R.id.pauseResumeButton);
        Button exitButton = findViewById(R.id.exitButton);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "test", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }










        pauseResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning) {
                    pauseResumeButton.setText("ادامه");
                    isTimerRunning = false;
                    pauseTimer();
                } else {
                    pauseResumeButton.setText("توقف");
                    startTimer();
                    isTimerRunning = true;
                }

            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "برای خروج دکمه را نگه دارید",
                        Toast.LENGTH_SHORT).show();
            }
        });

        exitButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                finish();
                return false;
            }
        });

        super.onResume();
    }



    @Override
    protected void onStop() {


        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notificaion);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(TimerActivity.this, CHANNEL_ID)
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.launcher_icon)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(TimerActivity.this);
        notificationManagerCompat.notify(1, builder.build());
            super.onStop();
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long newtTimeLeftInMilliSeconds) {
                timeLeftInMilliSeconds = newtTimeLeftInMilliSeconds;
                updateDisplayedTime();
            }

            @Override
            public void onFinish() {
                TimerStateUpdate();
            }

        }.start();
    }

    private void pauseTimer() {

        countDownTimer.cancel();

    }


    private void updateDisplayedTime() {
        int seconds = (int) ((timeLeftInMilliSeconds / 1000) % 60);
        int minutes = (int) (((timeLeftInMilliSeconds / 1000) - seconds) / 60);
        if (minutes < 0)
            minutes = 0;
        NumberFormat formatter = new DecimalFormat("00");
        timerTimeRemainingTextView.setText(String.valueOf(formatter.format(minutes) + " : " + formatter.format(seconds)));
    }


    private void TimerStateUpdate() {
        //starting of work
        if (this.timerState == 0 && sets > 1) {
            timerState++;
            stateTextView.setText("تمرین");
            //notificationState.setText(" تمرین ");
            timeLeftInMilliSeconds = (long) ((workMinutes * 60) + (workSeconds + 1)) * 1000;
            startTimer();
        }

        //starting of rest
        else if (this.timerState == 1 && sets > 1) {
            timerState++;
            stateTextView.setText("استراحت");
           // notificationState.setText(" استراحت ");
            timeLeftInMilliSeconds = (long) ((restMinutes * 60) + (restSeconds + 1)) * 1000;
            startTimer();
        } else if (this.timerState == 2) {
            timerState--;
            sets--;
            setsTextView.setText(String.valueOf(sets));
            stateTextView.setText("تمرین");
            //notificationState.setText(" تمرین ");
            timeLeftInMilliSeconds = (long) ((workMinutes * 60) + (workSeconds + 1)) * 1000;
            startTimer();
        } else if (timerState == 1 && sets == 1) {
            timerState = 3;
            //notificationState.setText(" پایان ");
            stateTextView.setText("پایان");
            finish();
        }
    }


}