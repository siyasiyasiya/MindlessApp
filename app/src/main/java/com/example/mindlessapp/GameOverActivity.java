package com.example.mindlessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        backButton = findViewById(R.id.button);
        backButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(GameOverActivity.this, MainActivity.class);
            startActivity(switchActivityIntent);
            finish();
        });
    }
}