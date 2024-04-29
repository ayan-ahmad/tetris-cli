package com.ayan.game;

public class Vector2 {
    private int x;
    private int y;

    public static Vector2 top(int boardHeight, int blockHeight){
        return new Vector2(3, boardHeight-blockHeight);
    }

    public void down(){
        if(this.y - 1 < 0){
            return;
        }
        this.y--;
    }

    public void down(int i){
        if(this.y - 1 < 0 && this.y >= i){
            return;
        }
        this.y-=i;
    }

    public void up(){
        this.y++;
    }
    public void left(){
        this.x--;
    }
    public void right(){
        this.x++;
    }

    Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
    public static Vector2 zero(){
        return new Vector2(0,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return "( " + x + ", " + y + " )";
    }
}
