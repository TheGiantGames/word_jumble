package com.thegiantgames.wordjumble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class play_activity extends AppCompatActivity {

    LinearLayout layout;
    String word = "";
    String  word_typed;
    String charAtPostion;
    ArrayList<TextView> arrayList_textview;
    int index = 0; // getting array element at index
    int chance = 3; //Number of attempt to give correct answer
    ImageView heart_three , heart_two, heart_one;
    Button  btn_home , btn_playAgain;
    Dialog clue_dialog , gameOver_dialog;
    TextView score_Textview;
    GridView gridView;
    ArrayList<String> letters;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getSupportActionBar().hide();



        score_Textview = (TextView) findViewById(R.id.score_Textview); //Finding Score Textview in GameOver Dialog
        word_typed  = getIntent().getStringExtra("word").toUpperCase(Locale.ROOT); //Getting the Word form mainActivity




        //Clue Dialog
        ImageView clueImage = (ImageView) findViewById(R.id.clue_imageview);
        clueImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clue = getIntent().getStringExtra("clue"); //getting the Clue Intent form MainActivity
                clue_dialog = new Dialog(play_activity.this);
                clue_dialog.setContentView(R.layout.clue_dialog_layout);
                TextView clue_textview =  clue_dialog.findViewById(R.id.clueTextView);
                Button okay_btn =  clue_dialog.findViewById(R.id.okay_btn);
                clue_textview.setText(clue);
                clue_dialog.show();
                clue_dialog.setCancelable(false); //preventing the Clue dialog to dismiss
                Window window =  clue_dialog.getWindow();
                window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT , ConstraintLayout.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // Making the Okay Button to  dismiss clue Dialog
                okay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         clue_dialog.dismiss();
                    }
                });
            }
        });




        layout = (LinearLayout) findViewById(R.id.word); //layout for showing answer given by player
        layout.setPadding(12,0,0,0);




        //Setting up the letters in the GridView
        letters = new ArrayList<>();
        String[] charArray_word = word_typed.split("(?!^)");
        for (int i = 0 ;i<word_typed.length() ; i++){
            letters.add(charArray_word[i]);
        }
        for (int i  = 0 ;i < 16-charArray_word.length ; i++){
            letters.add(gridElements());
        }
        Collections.shuffle(letters);



        //Setting Up the Grid
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new grid_adaptor(this ,letters ));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setBackground(getDrawable(R.drawable.after_chick_gridview_background)); //Changing the background after click



                MediaPlayer.create(play_activity.this , R.raw.click_sound).start(); //Click Sound




                    charAtPostion = (String) gridView.getItemAtPosition(position);//getting the Character from the Item clicked on gridView
                    word = word + (String) gridView.getItemAtPosition(position); //Getting the input from the user to check the correctness of the answer



                arrayElement(arrayList_textview).get(index).setText(charAtPostion +" ");
                index = index +1;



                //Making the click ability of gridview false
                if(word_typed.length() == word.length()){
                    gridView.setEnabled(false);
                }
            }
        });






        TextView btn_check = (TextView) findViewById(R.id.btn_check);
        TextView btn_reset = (TextView) findViewById(R.id.btn_reset);
        heart_one = (ImageView) findViewById(R.id.heart_one);
        heart_two = (ImageView) findViewById(R.id.heart_two);
        heart_three = (ImageView) findViewById(R.id.heart_three);




        //Functionality of Check Button
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(word_typed.equals(word)){

                    gameOverDialog(); //Creating Game Over Dialog

                    MediaPlayer mediaPlayer = MediaPlayer.create(play_activity.this , R.raw.win);
                    mediaPlayer.start();

                }
                else {


                    MediaPlayer mediaPlayer = MediaPlayer.create(play_activity.this , R.raw.error);
                    MediaPlayer mediaPlayer2 = MediaPlayer.create(play_activity.this , R.raw.failure);

                    // Switch case for making yellow heart black
                    switch (chance){
                        case 3: heart_three.setImageResource(R.drawable.heart_black);
                            mediaPlayer.start();
                            break;
                        case 2: heart_two.setImageResource(R.drawable.heart_black);
                            mediaPlayer.start();
                            break;
                        case 1: heart_one.setImageResource(R.drawable.heart_black);
                        mediaPlayer2.start();
                            break;
                    }
                    chance =chance-1; //updating the chance variable


                    //Game over if correct answer not given by user after three attempts
                    if(chance==0){
                        gameOverDialog();
                    }

                    //Making the game to play again after lose of one chance
                    resetGridview();

                }
            }
        });



        //Functionality of Reset Button
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetGridview();

            }
        });


        //creating layout for showing answer given by user
        create_Array();


        // Override the onBackPressed() method to disable backPress during play
        onBackPressed();
    }





    //method for creating Layout for showing answer input by user
    public void create_Array(){
        arrayList_textview = new ArrayList<TextView>();
        TextView textView = null;
        for (int i =0; i<word_typed.length();i++){
            textView = new TextView(play_activity.this);
            textView.setText("_ ");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
            textView.setTextColor(getResources().getColor(R.color.white));
            layout.addView(textView);
            arrayList_textview.add(textView);
            if (word_typed.length()> 12){
                textView.setTextSize(30);
            }
        }
    }





    public ArrayList<TextView> arrayElement(ArrayList<TextView> array){
        array = arrayList_textview;
        return array;
    }





     // Method for setting up random letters in gridView
    public String gridElements(){
        String  alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        int randomChar = random.nextInt(alphabets.length());
        char  ran = alphabets.charAt(randomChar);
        String  a = String.valueOf(ran);
        return a;
    }





    //Override the backPressed method for disabling the backpress
    @Override
    public void onBackPressed(){
        return;
    }




    //Creating Game Over Dialog
    public void gameOverDialog(){


        //creting dialog
        gameOver_dialog = new Dialog(play_activity.this);
        gameOver_dialog.setContentView(R.layout.game_over_dialog_layout);
        gameOver_dialog.show();
        gameOver_dialog.setCancelable(false);
        Window window = gameOver_dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT , ConstraintLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        //Setting Home Button
        btn_home = (Button) gameOver_dialog.findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOver_dialog.dismiss();
                Intent intent =  new Intent(play_activity.this , MainActivity.class);
                startActivity(intent);
            }
        });



        //Setting Play Again Button
        btn_playAgain = (Button) gameOver_dialog.findViewById(R.id.btn_playAgain);
        btn_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOver_dialog.dismiss();
                Intent intent =  new Intent(play_activity.this , MainActivity.class);
                startActivity(intent);
            }
        });




        //Setting up the Score
        score_Textview = (TextView) gameOver_dialog.findViewById(R.id.score_Textview);
        if(word_typed.equals(word)){
            String score =""+ word_typed.length() *100 * chance;
            score_Textview.setText("Your Score: " + score);
        }else {
            score_Textview.setText("Try Again");
        }
    }




    //Making the grid to play again after one chance lose
    public void resetGridview(){
        Collections.shuffle(letters);
        gridView.setAdapter(new grid_adaptor(play_activity.this ,letters));
        arrayList_textview.clear();
        index = 0;
        layout.removeAllViews();
        create_Array();
        gridView.setEnabled(true);
        word = "";
    }



}