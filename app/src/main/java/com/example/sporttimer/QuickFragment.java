package com.example.sporttimer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class QuickFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quick, container, false);
    }

    @Override
    public void onResume() {


        Button startButton = (Button) getView().findViewById(R.id.quickStartButtonId);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimerActivity.class);

                EditText setsEditText = (EditText) getView().findViewById(R.id.setsInputId);
                EditText workSecondsEditText = (EditText) getView().findViewById(R.id.workSecondsInputId);
                EditText workMinutesEditText = (EditText) getView().findViewById(R.id.workMinutesInputId);
                EditText restSecondsEditText = (EditText) getView().findViewById(R.id.restSecondsInputId);
                EditText restMinutesEditText = (EditText) getView().findViewById(R.id.restMinutesInputId);


                intent.putExtra("sets", setsEditText.getText().toString());
                intent.putExtra("workSeconds", workSecondsEditText.getText().toString());
                intent.putExtra("workMinutes", workMinutesEditText.getText().toString());
                intent.putExtra("restSeconds", restSecondsEditText.getText().toString());
                intent.putExtra("restMinutes", restMinutesEditText.getText().toString());
                startActivity(intent);

            }

        });

        Button saveButton = (Button) getView().findViewById(R.id.saveButtonId);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "working",
                        Toast.LENGTH_SHORT).show();
            }
        });

        super.onResume();
    }

}