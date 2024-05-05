package com.ayan.game.pieces;

import com.ayan.game.SquareColour;
import com.ayan.game.Vector2;
import com.ayan.game.logic.Board;

import java.util.Arrays;

public abstract class GamePiece implements IGamePiece {
    protected final Vector2 position;

    protected GamePiece(boolean[][] shape) {
        this.shape = shape;
        int height = 0;
        for(boolean[] column: this.shape){
            height = Math.max(height, column.length);
        }
        this.position = Vector2.top(20, height);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    protected final SquareColour colour = SquareColour.getRandomBlock();

    public SquareColour getColour() {
        return colour;
    }

    protected boolean[][] shape;

    public boolean[][] getShape(){
        return shape;
    }

    public void place(Board board) {
        for (int i = 0; i < board.getHeight() - this.getPosition().getY(); i++) {
            if(this.down(board)) break;
        }
    }

    public void rotate(Board board) {
        int rows = shape.length;
        int cols = shape[0].length;

        boolean[][] newShape = new boolean[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newShape[j][rows - 1 - i] = shape[i][j];
            }
        }

        if(checkCollision(board, newShape)){
            return;
        }

        shape = newShape;
    }

    public boolean checkCollision(Board board, boolean[][] shape){
        for (int y = board.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < board.getLength(); x++) {
                if (x >= getPosition().getX() &&
                        x < getPosition().getX() + shape.length &&
                        y >= getPosition().getY() &&
                        y < getPosition().getY() + shape[0].length) {
                    int relativeX = x - getPosition().getX();
                    int relativeY = y - getPosition().getY();
                    if (shape[relativeX][relativeY] && board.getBoard()[x][y] != SquareColour.WHITE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public synchronized boolean down(Board board){
        getPosition().down();
        if(getPosition().getY() == 0){
            board.addPiece(this);
            return true;
        }else if(checkCollision(board, getShape())){
            getPosition().up();
            board.addPiece(this);
            return true;
        }
        return false;
    }

    public synchronized void left(Board board){
        getPosition().left();
        if(checkCollision(board, getShape()) || getPosition().getX() < 0){
            getPosition().right();
        }
    }

    public synchronized void right(Board board){
        getPosition().right();
        if(checkCollision(board, getShape()) || getPosition().getX() > board.getLength() - shape.length){
            getPosition().left();
        }
    }
}

