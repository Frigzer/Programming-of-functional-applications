package com.example.lab_10;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View implements Runnable, SensorEventListener, View.OnTouchListener {
    private Thread gameThread = null;
    private boolean isPlaying = false;
    private float ballSpeedX, ballSpeedY;
    private float gravity = 0.5f; // Stała grawitacji
    private float bounceFactor = 0.7f; // Współczynnik odbicia (70% energii zachowanej)
    private float airResistance = 0.99f; // Opór powietrza (99% prędkości zachowanej)
    private float minSpeed = 0.5f; // Minimalna prędkość, przy której piłka przestaje się odbijać
    private SensorManager sensorManager;
    private ImageView ballImage;
    private ImageView goalImage;
    private boolean gameStarted = false;
    private boolean isPhoneVertical = false;
    private Context context;
    private List<View> obstacles = new ArrayList<>();
    private GameActivity gameActivity;
    private Handler wallHandler;

    public GameView(Context context, ImageView ballImage, ImageView goalImage) {
        super(context);
        this.context = context;
        this.ballImage = ballImage;
        this.goalImage = goalImage;
        this.gameActivity = (GameActivity) context;
        setOnTouchListener(this); // Ustawienie listenera dotyku
        init(context);

        obstacles.get(1).setVisibility(View.INVISIBLE);
        obstacles.get(1).setTag(false);

        wallHandler = new Handler();
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(6), false), 5000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(1), true), 5000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(12), false), 10000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(12), true), 20000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(15), false), 20000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(17), false), 10000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(18), false), 10000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(19), false), 10000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(20), false), 10000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(21), false), 10000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(27), false), 30000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(28), false), 30000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(31), true), 30000);
        wallHandler.postDelayed(() -> setWallActive(obstacles.get(31), false), 10000);
    }

    private void init(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        obstacles.add(((GameActivity) context).findViewById(R.id.wall1));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall2));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall3));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall4));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall5));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall6));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall7));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall8));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall9));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall10));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall11));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall12));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall13));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall14));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall15));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall16));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall17));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall18));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall19));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall20));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall21));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall22));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall23));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall24));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall25));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall26));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall27));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall28));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall29));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall30));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall31));
        obstacles.add(((GameActivity) context).findViewById(R.id.wall32));

        for(int i = 0; i< obstacles.size() ; i++) {
            obstacles.get(i).setTag(true);
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            try {
                Thread.sleep(17); // Kontrola prędkości aktualizacji
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (gameStarted) {
            if (isPhoneVertical) {
                ballSpeedY += gravity; // Dodanie grawitacji do prędkości tylko, gdy telefon jest pionowy
            } else {
                // Zatrzymanie piłki, gdy telefon jest poziomy
                ballSpeedX *= 0.8;
                ballSpeedY *= 0.8;
                if (Math.abs(ballSpeedX) < minSpeed) ballSpeedX = 0;
                if (Math.abs(ballSpeedY) < minSpeed) ballSpeedY = 0;
            }

            float ballX = ballImage.getX() + ballSpeedX;
            float ballY = ballImage.getY() + ballSpeedY;

            // Pobranie widocznego obszaru ekranu
            View rootView = ballImage.getRootView();
            Rect visibleFrame = new Rect();
            rootView.getWindowVisibleDisplayFrame(visibleFrame);
            int visibleHeight = visibleFrame.height();

            // Sprawdzanie kolizji z krawędziami ekranu
            if (ballX < 0) {
                ballX = 0;
                ballSpeedX = 0;
            } else if (ballX + ballImage.getWidth() > rootView.getWidth()) {
                ballX = rootView.getWidth() - ballImage.getWidth();
                ballSpeedX = 0;
            }

            if (ballY < 0) {
                ballY = 0;
                ballSpeedY = -ballSpeedY * bounceFactor;
            } else if (ballY + ballImage.getHeight() > visibleHeight) {
                ballY = visibleHeight - ballImage.getHeight();
                ballSpeedY = -ballSpeedY * bounceFactor;
            }

            ballSpeedX *= airResistance;
            ballSpeedY *= airResistance;

            // Sprawdzanie kolizji z przeszkodami
            Rect ballRect = new Rect((int) ballX, (int) ballY, (int) (ballX + ballImage.getWidth()), (int) (ballY + ballImage.getHeight()));
            for (View obstacle : obstacles) {
                if ((boolean) obstacle.getTag()) { // Sprawdzanie, czy przeszkoda jest aktywna
                    Rect obstacleRect = new Rect((int) obstacle.getX(), (int) obstacle.getY(), (int) (obstacle.getX() + obstacle.getWidth()), (int) (obstacle.getY() + obstacle.getHeight()));
                    if (Rect.intersects(ballRect, obstacleRect)) {
                        // Sprawdzenie, z której strony nastąpiła kolizja
                        float overlapLeft = ballRect.right - obstacleRect.left;
                        float overlapRight = obstacleRect.right - ballRect.left;
                        float overlapTop = ballRect.bottom - obstacleRect.top;
                        float overlapBottom = obstacleRect.bottom - ballRect.top;

                         if (overlapTop < overlapLeft && overlapTop < overlapRight && overlapTop < overlapBottom) {
                            // Kolizja z góry przeszkody
                            ballY = obstacleRect.top - ballImage.getHeight();
                            ballSpeedY = -ballSpeedY * bounceFactor;
                        } else if (overlapBottom < overlapLeft && overlapBottom < overlapRight && overlapBottom < overlapTop) {
                            // Kolizja z dołu przeszkody
                            ballY = obstacleRect.bottom;
                            ballSpeedY = -ballSpeedY * bounceFactor;
                        } else if (overlapLeft < overlapRight && overlapLeft < overlapTop && overlapLeft < overlapBottom) {
                             // Kolizja z lewej strony przeszkody
                             ballX = obstacleRect.left - ballImage.getWidth();
                             ballSpeedX = 0;
                         } else if (overlapRight < overlapLeft && overlapRight < overlapTop && overlapRight < overlapBottom) {
                             // Kolizja z prawej strony przeszkody
                             ballX = obstacleRect.right;
                             ballSpeedX = 0;
                         }

                        // Tłumienie prędkości odbicia, aby zatrzymać lekkie podskakiwanie
                        if (Math.abs(ballSpeedX) < minSpeed) ballSpeedX = 0;
                        if (Math.abs(ballSpeedY) < minSpeed) ballSpeedY = 0;
                    }
                }
            }

            // Sprawdzanie kolizji z punktem docelowym
            Rect goalRect = new Rect((int) goalImage.getX(), (int) goalImage.getY(), (int) (goalImage.getX() + goalImage.getWidth()), (int) (goalImage.getY() + goalImage.getHeight()));
            if (Rect.intersects(ballRect, goalRect)) {
                winGame();
            }

            ballImage.setX(ballX);
            ballImage.setY(ballY);

            Log.d("GameView", "Ball position: (" + ballX + ", " + ballY + "), speed: (" + ballSpeedX + ", " + ballSpeedY + ")");
        }
    }

    private void winGame() {
        isPlaying = false;
        gameActivity.runOnUiThread(() -> {
            Toast.makeText(context, "You win!", Toast.LENGTH_LONG).show();
            gameActivity.stopTimer(); // Zatrzymanie timera po wygraniu gry
        });
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        ballImage.post(() -> {
            ballImage.setX(ballImage.getRootView().getWidth() / 2 - ballImage.getWidth() / 2);
            ballImage.setY(50); // Początkowa pozycja piłki
            ballSpeedX = 0;
            ballSpeedY = 0;
            gameStarted = true;
            Log.d("GameView", "Game started with ball at: (" + ballImage.getX() + ", " + ballImage.getY() + ")");
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (gameStarted) {
            // Wychylenie urządzenia wpływa na prędkość piłki
            ballSpeedX = -event.values[0] * 5; // Inwersja osi X
            // Sprawdzenie, czy telefon jest pionowy
            isPhoneVertical = Math.abs(event.values[1]) > Math.abs(event.values[0]);
            if (isPhoneVertical) {
                ballSpeedY += event.values[1] * 0.1; // Normalna oś Y, mniejszy wpływ żyroskopu na prędkość w pionie
            }
            Log.d("GameView", "Sensor changed: ballSpeedX=" + ballSpeedX + ", ballSpeedY=" + ballSpeedY + ", isPhoneVertical=" + isPhoneVertical);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ballSpeedY = -20; // Ustaw prędkość w pionie, aby piłka podskoczyła
            return true;
        }
        return false;
    }

    public void jumpBall() {
        ballSpeedY = -20; // Ustaw prędkość w pionie, aby piłka podskoczyła
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nie musisz nic tu robić
    }

    private void setWallActive(View wall, boolean active) {
        if (active) {
            wall.setVisibility(View.VISIBLE);
            wall.setTag(true); // true oznacza, że ściana jest aktywna
        } else {
            wall.setVisibility(View.INVISIBLE);
            wall.setTag(false); // false oznacza, że ściana jest nieaktywna
        }
    }
}
