package com.ayan.game.pieces;

public class Square extends GamePiece {
    public Square(){
        boolean[][] shape = new boolean[][]{
                {true, true},
                {true, true}
        };
        super(shape);
    }
}
