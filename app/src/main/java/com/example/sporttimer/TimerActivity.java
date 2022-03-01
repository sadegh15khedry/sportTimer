package com.example.sporttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        String sets = getIntent().getStringExtra("sets");
        String work = getIntent().getStringExtra("work");
        String rest = getIntent().getStringExtra("rest");

        TextView setsTextView = findViewById(R.id.timerSetsTextView);
        TextView timerTimeRemainingTextView = findViewById(R.id.timerTimeRemainingTextView);

        setsTextView.setText(sets);
        timerTimeRemainingTextView.setText(work);

//        Toast.makeText(this,sets,
//                Toast.LENGTH_SHORT).show();
    }
}