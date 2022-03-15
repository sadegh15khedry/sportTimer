package com.example.sporttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
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
    private Button pauseResumeButton;
    private Button exitButton;
    private MediaPlayer longBeepMediaPlayer;
    private MediaPlayer shortBeepMediaPlayer;

    private static final String CHANNEL_ID = "timer activity";

    private RemoteViews remoteViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //getting data from Main Activity
        sets = Integer.parseInt(getIntent().getStringExtra("sets"));
        workMinutes = Integer.parseInt(getIntent().getStringExtra("workMinutes"));
        workSeconds = Integer.parseInt(getIntent().getStringExtra("workSeconds"));
        restMinutes = Integer.parseInt(getIntent().getStringExtra("restMinutes"));
        restSeconds = Integer.parseInt(getIntent().getStringExtra("restSeconds"));

        //timer initialisation
        timerState = 0;
        isTimerRunning = true;
        timeLeftInMilliSeconds = 6000;

        //view initialisation
        stateTextView = findViewById(R.id.stateTextView);
        setsTextView = findViewById(R.id.timerSetsTextView);
        timerTimeRemainingTextView = findViewById(R.id.timerTimeRemainingTextView);
        setsTextView.setText(String.valueOf(sets));
        timerTimeRemainingTextView.setText((String.valueOf(timeLeftInMilliSeconds / 1000)));
        stateTextView.setText("آماده باش");
        pauseResumeButton = findViewById(R.id.pauseResumeButton);
        exitButton = findViewById(R.id.exitButton);

        //media player initialisation
        longBeepMediaPlayer = MediaPlayer.create(this, R.raw.long_beep);
        shortBeepMediaPlayer = MediaPlayer.create(this, R.raw.short_beep);
        //longBeepMediaPlayer.setVolume(20,20);


        startTimer();

    }

    @Override
    public void onResume() {




        longBeepMediaPlayer = MediaPlayer.create(this, R.raw.long_beep);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "test", NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null,null);
            channel.enableVibration(false);
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
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "برای خروج دکمه را نگه دارید",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {


        remoteViews = new RemoteViews(getPackageName(), R.layout.notificaion);

        remoteViews.setTextViewText(R.id.notificationStateText,stateTextView.getText());
        remoteViews.setTextViewText(R.id.notificationSets,String.valueOf(sets));
        remoteViews.setTextViewText(R.id.notificationTimeRemaining,timerTimeRemainingTextView.getText());

        Intent clickIntent = new Intent(this,NotificationReceiver.class);
        //clickIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        clickIntent.setAction(Intent.ACTION_MAIN);
        clickIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,0,clickIntent,0);
        remoteViews.setOnClickPendingIntent(R.id.notificationWrapper,clickPendingIntent);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(TimerActivity.this, CHANNEL_ID)
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.launcher_icon)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(clickPendingIntent)
                //.setDefaults(0)
                .setSound(null)
                ;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(TimerActivity.this);

        notificationManagerCompat.notify(1, builder.build());
            super.onStop();
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long newtTimeLeftInMilliSeconds) {
                timeLeftInMilliSeconds = newtTimeLeftInMilliSeconds;


                if (4000 > timeLeftInMilliSeconds && timeLeftInMilliSeconds > 1000)
                    shortBeepMediaPlayer.start();
                else if (timeLeftInMilliSeconds < 1000 && timeLeftInMilliSeconds>0)
                    longBeepMediaPlayer.start();

                updateDisplayedTime();

            }

            @Override
            public void onFinish() {
                TimerStateUpdate();
                shortBeepMediaPlayer.start();
                //mediaPlayer = MediaPlayer.create(this, R.raw.long_beep);

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
            //remoteViews.setTextViewText(R.id.notificationStateText,"تمرین");
            timeLeftInMilliSeconds = (long) ((workMinutes * 60) + (workSeconds + 1)) * 1000;
            startTimer();
        }

        //starting of rest
        else if (this.timerState == 1 && sets > 1) {
            timerState++;
            stateTextView.setText("استراحت");
            //remoteViews.setTextViewText(R.id.notificationStateText,"تمرین");
            timeLeftInMilliSeconds = (long) ((restMinutes * 60) + (restSeconds + 1)) * 1000;
            startTimer();
        } else if (this.timerState == 2) {
            timerState--;
            sets--;
            setsTextView.setText(String.valueOf(sets));
            stateTextView.setText("تمرین");
            //remoteViews.setTextViewText(R.id.notificationStateText,"تمرین");
            timeLeftInMilliSeconds = (long) ((workMinutes * 60) + (workSeconds + 1)) * 1000;
            startTimer();
        } else if (timerState == 1 && sets == 1) {
            timerState = 3;
            //remoteViews.setTextViewText(R.id.notificationStateText,"پایان");
            stateTextView.setText("پایان");
            finish();
        }
    }


}