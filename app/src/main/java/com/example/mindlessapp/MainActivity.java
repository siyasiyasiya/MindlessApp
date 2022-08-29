package com.example.mindlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Button> buttons;
    private boolean[][] accGrid = new boolean[7][5];
    private int level = 1;
    private int spots;
    private int gameTime = 4000;
    private int score = 0;

    private static final int[] BUTTON_IDS = {
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
            R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
            R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20,
            R.id.button21, R.id.button22, R.id.button23, R.id.button24, R.id.button25,
            R.id.button26, R.id.button27, R.id.button28, R.id.button29, R.id.button30,
            R.id.button31, R.id.button32, R.id.button33, R.id.button34, R.id.button35
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button arraylist
        buttons = new ArrayList<>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS) {
            Button button = findViewById(id);
            buttons.add(button);
        }

        generateGrid();
        for(Button x: buttons){
            x.setOnClickListener(view -> {
                //button changes color when clicked
                checkClick(buttons.indexOf((Button) view));
                view.setSelected(!view.isSelected());
                view.setEnabled(false);
            });
        }
    }

    public void generateGrid(){
        //clearing grid and setting all to false
        for (int i = 0; i < accGrid.length; i++) {
            for (int j = 0; j < accGrid[0].length; j++) {
                accGrid[i][j] = false;
            }
        }

        int r;
        int c;
        //depending on the level the harder the game will be
        if(level < 15){
            spots = level+6;
            gameTime += 25;
        } else {
            spots = randomNumber(18, 20);
            //once gametime is lower than a certain point no more decreasing.
            if(gameTime >= 300){
                gameTime -= 100;
            }
        }

        //randomly generate positions to color on the grid
        for (int i = 0; i < spots; i++) {
            do {
                r =  randomNumber(0, 6);
                c = randomNumber(0, 4);
            } while (accGrid[r][c]);
            accGrid[r][c] = true;
        }

        //color the positions
        for (int i = 0; i < accGrid.length; i++) {
            for (int j = 0; j < accGrid[0].length; j++) {
                buttons.get(5*i+j).setSelected(accGrid[i][j]);
            }
        }

        //dis-enable all the buttons so the user cannot click on them
        for(Button x: buttons){
            x.setEnabled(false);
        }

            //make the actual grid disappear after a set amount of time
        System.out.println(gameTime);
        new Handler().postDelayed(this::startGame, gameTime);
    }

    public void startGame(){
        for(Button x: buttons){
            x.setEnabled(true);
            x.setSelected(false);
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkClick(int place){
        final MediaPlayer correct = MediaPlayer.create(this, R.raw.right_choice);
        final MediaPlayer wrong = MediaPlayer.create(this, R.raw.wrong_choice);
        final MediaPlayer complete = MediaPlayer.create(this, R.raw.completion);
        TextView tv1 = findViewById(R.id.textView1);
        int r = place/5;
        int c = place - r*5;

        //if the button clicked is correct
        if(accGrid[r][c]){
            spots --;
            //if the grid is completed
            if(spots == 0){
                complete.start();
                level++;
                score += level*100;
                tv1.setText("Score: " + score);
                new Handler().postDelayed(this::generateGrid, 2000);
            } else {
                correct.start();
                score += 10 * level;
                tv1.setText("Score: " + score);
            }
            //if the button clicked is wrong
        } else {
            wrong.start();
            new Handler().postDelayed(this::switchActivities, 2000);
        }
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, GameOverActivity.class);
        startActivity(switchActivityIntent);
        finish();
    }

    public int randomNumber(int a, int b) {
        double x = Math.floor(Math.random() * (b - a + 1) + a);
        return (int) x;
    }
}