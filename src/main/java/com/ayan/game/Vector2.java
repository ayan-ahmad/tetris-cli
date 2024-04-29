package com.ayan.game;

public class Vector2 {
    private int x;
    private int y;
    void down(){
        this.y--;
    }
    void left(){
        this.x--;
    }
    void right(){
        this.x++;
    }
    Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
    public static Vector2 zero(){
        return new Vector2(0,0);
    }
}
