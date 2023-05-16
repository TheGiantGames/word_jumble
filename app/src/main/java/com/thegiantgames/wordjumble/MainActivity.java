package com.thegiantgames.wordjumble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText clue_editText; //Field to write clue!!
    EditText word_editText; //Field to write Word!!
    int check = 0;  // Counter to give the functionality that Clue and Word EditText cannot be Empty!!
    MediaPlayer mediaPlayer; // MediaPlayer for notifying sound



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        clue_editText = findViewById(R.id.clue_EditText); //Finding the Clue Edittext !!
        word_editText = findViewById(R.id.wordEditText);   //Finding the Word Edittext !!



        View view = (View) findViewById(R.id.start_btn); // Start Button View!!
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Clue = clue_editText.getText().toString(); //Storing the clue!!


                Intent intent = new Intent(MainActivity.this ,play_activity.class);
                // Passing the Clue intent to the Play Activity
                intent.putExtra("clue" , Clue);


                String word = word_editText.getText().toString(); // Storing the Word!!


                // Passing the Word intent to the Play Activity
                intent.putExtra("word" , word);


                //Preventing Empty Word  Edittext
                if (word.isEmpty() & check == 0){
                    Toast.makeText(MainActivity.this, "Enter the Word", Toast.LENGTH_SHORT).show();
                    mediaPlayer = MediaPlayer.create(MainActivity.this , R.raw.notify);
                    mediaPlayer.start();
                }else {
                    check = 1;
                }



                //Preventing Empty Clue Edittext
                if (Clue.isEmpty() & check == 1){
                    Toast.makeText(MainActivity.this, "Enter the Clue", Toast.LENGTH_SHORT).show();
                    mediaPlayer = MediaPlayer.create(MainActivity.this , R.raw.notify);
                    mediaPlayer.start();
                }



                //Starting the Play Activity
                if (!Clue.isEmpty() & !word.isEmpty()){
                    startActivity(intent);
                }

            }
        });
    }
}