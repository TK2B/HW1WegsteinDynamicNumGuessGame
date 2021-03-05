package com.spaceintruders.hw1wegsteindynamicnumguessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE = "com.spaceintruders.hw1wegsteindynamicnumguessgame.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Make a new Intent
        Intent intent = new Intent(this, MainActivity2.class);
        // Get Data you want
        EditText editText = (EditText) findViewById(R.id.myTextbox);
        String message = editText.getText().toString();
        Slider nob = (Slider) findViewById(R.id.slider_nob);
        int numberOfButtons = (int) nob.getValue();
        Slider range = (Slider) findViewById(R.id.slider_range);
        int rangeOfNumbers = (int) range.getValue();

        // Write Infos to intent to copy to other activity
        message = "Hello Player " + message ;
        intent.putExtra("nachricht", message);
        intent.putExtra("EXTRA_nob", numberOfButtons );
        intent.putExtra("EXTRA_ron", rangeOfNumbers );

        startActivity(intent);
    }


}