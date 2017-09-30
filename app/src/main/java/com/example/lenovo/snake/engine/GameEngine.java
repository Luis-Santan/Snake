package com.example.lenovo.snake.engine;

import android.content.Intent;

import com.example.lenovo.snake.Classes.Coordinate;
import com.example.lenovo.snake.Enums.Direction;
import com.example.lenovo.snake.Enums.GameState;
import com.example.lenovo.snake.Enums.Tiletype;
import com.example.lenovo.snake.Juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 22/9/2017.
 */

public class GameEngine {
    private static final int GameWidth = 28;
    private static final int GameHeigth = 42;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apples = new ArrayList<>();

    private Random random = new Random();
    private boolean increaseTail = false;

    private Direction currentDirection = Direction.East;

    private GameState currentGameState = GameState.Running;

    private Coordinate getSnakeHead(){

        return snake.get(0);
    }


    public GameEngine() {

    }

    public void InitGame(){
        AddSnake();
        AddWalls();
        AddApple();
    }

    private void AddApple() {
        Coordinate coordinate = null;

        boolean added = false;

        while (!added){
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeigth - 2);

            coordinate = new Coordinate(x,y);
            boolean collision = false;

            for (Coordinate s: snake){
                if (s.equals(coordinate)){
                    collision = true;
                    // break;
                }
            }

            for (Coordinate a:apples){
                if (a.equals(coordinate)) {
                    collision = true;
                   // break;
                }
            }
            added = !collision;
        }
        apples.add(coordinate);
    }

    public  void UptadeDirection(Direction newDirection){

        if (Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }

    }

    public void Uptade(){
        switch (currentDirection){

            case North:
                UptadeSnake(0,-1);
                break;
            case East:
                UptadeSnake(1,0);
                break;
            case South:
                UptadeSnake(0,1);
                break;
            case West:
                UptadeSnake(-1,0);
                break;
        }

        for (Coordinate w: walls){
            if (snake.get(0).equals(w)){
                currentGameState = GameState.Lost;

            }
        }

        for (int i =1; i < snake.size(); i++){
            if (getSnakeHead().equals(snake.get(i))){
                currentGameState = GameState.Lost;
                return;
            }
        }

       Coordinate appleToRemove = null;
        for (Coordinate apple: apples){
            if (getSnakeHead().equals(apple)){
                appleToRemove = apple;
                increaseTail = true;


            }
        }

        if (appleToRemove != null){
            apples.remove(appleToRemove);
            AddApple();
        }
    }



    public Tiletype[][] getmap() {
    Tiletype[][] map = new Tiletype[GameWidth][GameHeigth];

        for (int i = 0; i < GameWidth; i++) {
            for (int j = 0; j < GameHeigth; j++) {
                map[i][j]= Tiletype.Nothing;
            }

        }

        for (Coordinate wall: walls){
            map[wall.getX()][wall.getY()] = Tiletype.Wall;
        }
        for (Coordinate s: snake){
            map[s.getX()][s.getY()] = Tiletype.SnakeTall;
        }
        for (Coordinate a:apples){
            map[a.getX()][a.getY()] = Tiletype.Apple;
        }
        map[snake.get(0).getX()][snake.get(0).getY()] = Tiletype.SnakeHead;

        return map;
    }

    private  void UptadeSnake(int x, int y){
        int newX = snake.get(snake.size() - 1).getX();
        int newY = snake.get(snake.size() -1 ).getY();

        for (int i = snake.size()-1; i > 0; i--) {
            snake.get(i).setX( snake.get(i-1).getX() );
            snake.get(i).setY( snake.get(i-1).getY() );

        }
        if (increaseTail){
            snake.add(new Coordinate(newX,newY));
            increaseTail = false;
        }

        snake.get(0).setX(snake.get(0).getX()+x);
        snake.get(0).setY(snake.get(0).getY()+y);

    }

    private void AddSnake() {
        snake.clear();

        snake.add(new Coordinate(7,7));
        snake.add(new Coordinate(6,7));
        snake.add(new Coordinate(5,7));
        snake.add(new Coordinate(4,7));
        snake.add(new Coordinate(3,7));
        snake.add(new Coordinate(2,7));

    }

    private void AddWalls() {
        for (int i = 0; i < GameWidth; i++) {
            walls.add(new Coordinate(i,0));
            walls.add(new Coordinate(i,GameHeigth-1));

        }
        for (int i = 1; i < GameHeigth; i++) {
            walls.add(new Coordinate(0,i));
            walls.add(new Coordinate(GameWidth-1,i));

        }
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }
}
