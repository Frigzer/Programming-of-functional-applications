package com.example.lab_10;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private ImageView ballImage;
    private ImageView goalImage;
    private TextView timerTextView;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timerTextView = findViewById(R.id.timerTextView);

        ballImage = findViewById(R.id.ballImage);
        goalImage = findViewById(R.id.goalImage);
        gameView = new GameView(this, ballImage, goalImage);

        // Uruchomienie timera
        seconds = 0;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                seconds++;
                timerTextView.setText("Time: " + seconds + "s");
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.postDelayed(timerRunnable, 1000);

        // Rozpocznij grę
        gameView.startGame();
    }

    public void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        saveHighScore(seconds);
    }

    private void saveHighScore(int seconds) {
        SharedPreferences prefs = getSharedPreferences("game_scores", MODE_PRIVATE);
        String highScores = prefs.getString("highScores", "");
        SharedPreferences.Editor editor = prefs.edit();
        highScores += "Time: " + seconds + "s\n";
        editor.putString("highScores", highScores);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            gameView.jumpBall(); // Wywołanie metody skoku piłki
            return true;
        }
        return super.onTouchEvent(event);
    }
}
