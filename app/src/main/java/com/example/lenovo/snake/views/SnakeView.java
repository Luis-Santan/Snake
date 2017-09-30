package com.example.lenovo.snake.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.lenovo.snake.Enums.Tiletype;

/**
 * Created by Lenovo on 22/9/2017.
 */

public class SnakeView extends View {

    private Paint mPaint = new Paint();
    private Tiletype snakeViewMap[][];

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSnakeViewMap (Tiletype [][] map ){
        this.snakeViewMap = map;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(snakeViewMap != null){
            float titleSizeX = canvas.getWidth() / snakeViewMap.length;
            float titleSizeY = canvas.getHeight() / snakeViewMap[0].length;

            float circleSize = Math.min(titleSizeX,titleSizeY) / 2;

            for (int x = 0; x < snakeViewMap.length; x++){
                for(int y = 0; y < snakeViewMap[x].length; y++){
                    switch (snakeViewMap[x][y]){

                        case Nothing:
                            mPaint.setColor(Color.WHITE);
                            break;
                        case Wall:
                            mPaint.setColor(Color.BLACK);
                            break;
                        case SnakeHead:
                            mPaint.setColor(Color.RED);
                            break;
                        case SnakeTall:
                            mPaint.setColor(Color.GREEN);
                            break;
                        case Apple:
                            mPaint.setColor(Color.RED);
                            break;
                    }

                    canvas.drawCircle(x * titleSizeX + titleSizeX / 2f + circleSize /2,y * titleSizeY + titleSizeY / 2f + circleSize /2,circleSize,mPaint);
                }

            }
        }


    }
}
