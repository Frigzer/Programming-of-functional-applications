package com.example.lab_10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView gifBackground = findViewById(R.id.gifBackground);
        Glide.with(this).load(R.drawable.background1).into(gifBackground);

        Button startButton = findViewById(R.id.startButton);
        Button exitButton = findViewById(R.id.exitButton);
        Button highScoresButton = findViewById(R.id.highScoresButton);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        exitButton.setOnClickListener(v -> {
            finish(); // Zakończ aplikację
            System.exit(0); // Opcjonalnie wymuś zakończenie
        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HighScoresActivity.class));
            }
        });
    }
}
