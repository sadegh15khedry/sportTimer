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
                EditText workEditText = (EditText) getView().findViewById(R.id.workSecondsInputId);
                EditText restEditText = (EditText) getView().findViewById(R.id.restSecondsInputId);

                long index = workEditText.getText().toString().indexOf(':');
                Toast.makeText(getContext(),String.valueOf(index),
                Toast.LENGTH_SHORT).show();
                if (index == -1){
                    intent.putExtra("sets", setsEditText.getText().toString());
                    intent.putExtra("work", workEditText.getText().toString());
                    intent.putExtra("rest", restEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });

        Button saveButton = (Button) getView().findViewById(R.id.saveButtonId);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"working",
                        Toast.LENGTH_SHORT).show();
            }
        });

        super.onResume();
    }
}