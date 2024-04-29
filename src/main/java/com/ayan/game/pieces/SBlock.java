package com.ayan.game.pieces;

public class SBlock extends GamePiece {

    public SBlock() {
        boolean[][] shape = new boolean[][]{
                {true, false},
                {true, true},
                {false, true}
        };
        super(shape);
    }
}
