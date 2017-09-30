package com.example.lenovo.snake;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.lenovo.snake.Enums.Direction;
import com.example.lenovo.snake.Enums.GameState;
import com.example.lenovo.snake.Enums.Tiletype;
import com.example.lenovo.snake.engine.GameEngine;
import com.example.lenovo.snake.views.SnakeView;

public class Juego extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long uptadeDelay = 150;

    private float prevX, prevY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        gameEngine = new GameEngine();
        gameEngine.InitGame();
        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

    startUptadeHandler();

    }
    private void startUptadeHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.Uptade();

                if(gameEngine.getCurrentGameState() == GameState.Running){
                    handler.postDelayed(this,uptadeDelay);
                }
                if (gameEngine.getCurrentGameState() == GameState.Lost){
                    OnGameLost();
                }
                snakeView.setSnakeViewMap(gameEngine.getmap());
                snakeView.invalidate();

            }
        },uptadeDelay);
    }

    public void OnGameLost(){
        Intent intent = new Intent(Juego.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this,"You Lost, Play again!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = motionEvent.getX();
                prevY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newX = motionEvent.getX();
                float newY = motionEvent.getY();

                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    //IZQUIERDA-DERECHA
                    if (newX > prevX){
                        //DERECHA
                        gameEngine.UptadeDirection(Direction.East);
                    }else {
                        //IZQUIERDA
                        gameEngine.UptadeDirection(Direction.West);
                    }
                }else {
                    //ARRIBA-ABAJO
                    if (newY > prevY){
                        //ABAJO
                        gameEngine.UptadeDirection(Direction.South);

                    }else {
                        //ARRIBA
                        gameEngine.UptadeDirection(Direction.North);
                    }
                }
                break;

        }
        return true;
    }
}
