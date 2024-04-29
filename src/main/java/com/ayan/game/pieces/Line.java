package com.ayan.game.pieces;

public class Line extends GamePiece {
    public Line(){
        boolean[][] shape = new boolean[][]{
                {true},
                {true},
                {true},
                {true}
        };
        super(shape);
    }
}
