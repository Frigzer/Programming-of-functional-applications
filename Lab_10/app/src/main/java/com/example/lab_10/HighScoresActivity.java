package com.example.lab_10;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HighScoresActivity extends AppCompatActivity {
    private TextView highScoresTextView;
    private Button clearScoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        highScoresTextView = findViewById(R.id.highScoresTextView);
        clearScoresButton = findViewById(R.id.clearScoresButton);

        SharedPreferences prefs = getSharedPreferences("game_scores", MODE_PRIVATE);
        String highScores = prefs.getString("highScores", "No scores yet");
        highScoresTextView.setText(highScores);

        clearScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHighScores();
            }
        });


    }

    private void clearHighScores() {
        SharedPreferences prefs = getSharedPreferences("game_scores", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("highScores");
        editor.apply();

        highScoresTextView.setText("No scores yet");
    }
}
