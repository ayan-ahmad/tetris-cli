package com.ayan.game.pieces;

public class LBlock extends GamePiece {
    public LBlock(){
        boolean[][] shape = new boolean[][]{
                {true, true},
                {true, false},
                {true, false}
        };
        super(shape);
    }
}
