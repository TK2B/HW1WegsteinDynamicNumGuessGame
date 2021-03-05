package com.spaceintruders.hw1wegsteindynamicnumguessgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import static com.google.android.material.internal.ContextUtils.getActivity;


// TODO extract Winning and Lose Methods and call them from wher you need it (redundance)

public class MainActivity2 extends AppCompatActivity {
    // TODO 4 Score / Time over all // Wrong/right picks
    private int overallTime = 0;
    private int goodPicks = 0;
    private int wrongPicks = 0;
    private static int Score = 0;
    private String helloPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Get the Intent that started this activity and extract the string
        Intent intentRES = getIntent();
        String message = intentRES.getStringExtra("nachricht");
        //TODO Remove after fitting in game
        int nob = intentRES.getIntExtra("EXTRA_nob", 4);
        int ron = intentRES.getIntExtra("EXTRA_ron",2 );
        Log.i("NOB", String.valueOf(nob));
        Log.i("RON", String.valueOf(ron));

        // Capture the layout's TextView and set the string as its text
        TextView textViewPAge2 = (TextView) findViewById(R.id.textView3);
        textViewPAge2.setText(message);
        helloPlayer = message;
        // Set Score to 0

        TextView scoreDisplay = (TextView) findViewById(R.id.ScoreView);
        scoreDisplay.setText("  Score " +Score + " ");


        // Start a Game on Load
        startaGame();
    }


    public void makePopup(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity2.this, R.style.AlertDialogStyle).create();
        alertDialog.setTitle("EndGame");
        alertDialog.setMessage(helloPlayer + " \nYou had " + goodPicks + " right picks / " + wrongPicks + " Wrong picks and \nYou are playing " + overallTime + " seconds");


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You clicked on OK - continue ", Toast.LENGTH_SHORT).show();
            }
        });


        alertDialog.show();
    }

    public void startaGame() {
        //makePopup();
        //TODO Elmente aus View in Vars schreiben
        Button stopgame = (Button) findViewById(R.id.stop_button);
        TextView myText = (TextView) findViewById(R.id.textViewTimer);
        // TODO UPDATE Capture the layout's TextView and set the string as its text
        TextView textViewPAge2 = (TextView) findViewById(R.id.textView3);
        textViewPAge2.setText(helloPlayer + " \nYou had " + goodPicks + " right picks / " + wrongPicks + " Wrong picks and \nYou are playing " + overallTime + " seconds");
        Log.i ("rightpicks", String.valueOf(goodPicks));
        Log.i ("wrong picks", String.valueOf(wrongPicks));

        //TODO Clear Layouts (neccesary for more than 1 game )
        LinearLayout left = (LinearLayout) findViewById(R.id.row1);
        LinearLayout right = (LinearLayout) findViewById(R.id.row2);
        left.removeAllViews();
        right.removeAllViews();

        // TODO Countdowntimer
        // Create Var to get time out of countdowntimer
        final int[] progress = new int[1];
        CountDownTimer mytimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                myText.setText("Seconds remaining: " + millisUntilFinished / 1000); //TODO mTextField erstellen
                progress[0] = (int) (millisUntilFinished/1000);

            }

            public void onFinish() {
                myText.setText("done! your are to slow");
                overallTime += 30- progress[0];
                Score -= progress[0];
                TextView scoreDisplay = (TextView) findViewById(R.id.ScoreView);
                scoreDisplay.setText("Score " +Score);
                wrongPicks += 1;
                startaGame();

            }
        };

        mytimer.start();
        // TODO get vars 4 generating Numbers an Buttons
        Intent intentRES = getIntent();
        int nob = intentRES.getIntExtra("EXTRA_nob", 4);
        int ron = intentRES.getIntExtra("EXTRA_ron",2 );


        // TODO generate radnom Numbers and calculate AVG and distanze rand to AVG
        List<Integer> arrayWithNumbers =  new ArrayList<Integer>();
        int avg = 0 ;
        for (int i = 0 ; i <nob ;i++){
            // fill random numbers
            int aNumber = new Random().nextInt(ron);
            Log.i("RandomNumber = " , String.valueOf(aNumber));
            arrayWithNumbers.add(aNumber);
            //
            avg += arrayWithNumbers.get(i);
        }
        // divide
        avg = avg / nob;  // TODO ?  SizeOf? Lenght?

        Log.i("Avarage: ", String.valueOf(avg));
        Log.i("lenght of array: ", String.valueOf(arrayWithNumbers.size()));

        // Find winning number  (distance AVG to number )
        int winning = 61;
        int won = 0;
        for (int i = 0 ; i <nob ;i++){
            int distance = Math.abs(avg- arrayWithNumbers.get(i));
            if (distance < winning){
                winning = distance;
                won = arrayWithNumbers.get(i);
            }
        }
        int finalWon = won;
        Log.i("Winning Number" , String.valueOf(won));
        //TODO Generate Array List of Buttons (ok instantly place it in Arrays

        List<Button> arrayWithButtons = new ArrayList<>();

        for (int i = 0 ; i <nob ;i++){

            // create Buttons
            //Button aButton = new Button(this, null, R.style.myButtonStyle);
            // aButton.setBackgroundColor(#283593);
            Button aButton = new Button(this);
            aButton.setText(String.valueOf(arrayWithNumbers.get(i)));

            Log.i ("my_i", String.valueOf(i));
            Log.i ("my_button", String.valueOf(aButton));

            //create Listener


            int finalI1 = arrayWithNumbers.get(i);
            aButton.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {
                    //check won
                    if (finalI1== finalWon){
                        // timer stop
                        Score += progress[0];
                        overallTime += 30- progress[0];
                        goodPicks += 1;
                        TextView scoreDisplay = (TextView) findViewById(R.id.ScoreView);
                        scoreDisplay.setText("  Score " +Score + " ");
                        mytimer.cancel();
                        Toast.makeText(getApplicationContext(), "You Won ! Good Guess", Toast.LENGTH_SHORT).show();
                        //myText.setText(" You Won ! Good Guess");


                        startaGame();
                    }
                    else {

                        overallTime += 30- progress[0];
                        Score -= progress[0];
                        TextView scoreDisplay = (TextView) findViewById(R.id.ScoreView);
                        scoreDisplay.setText("  Score " +Score + " ");
                        wrongPicks += 1;
                        mytimer.cancel();
                        Toast.makeText(getApplicationContext(), " Fool  ! you dont like numbers?", Toast.LENGTH_SHORT).show();
                        //myText.setText(" Fool  ! you dont like numbers?");


                        startaGame();
                    }
                }
            });


            // add Buttons to Linear Layouts
            if (i%2 != 0){
                left.addView(aButton);
            }
            else{
                right.addView(aButton);
            }

            // !!!! for loop ends here
        }
        //TODO Eventlistener para StopButton should call a Popup/Allert
        stopgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call here the Popupmagic
                makePopup();
            }
        });
    }

}