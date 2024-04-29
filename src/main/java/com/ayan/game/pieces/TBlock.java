package com.ayan.game.pieces;

public class TBlock extends GamePiece {
    public TBlock() {
        boolean[][] shape = new boolean[][]{
                {true, false},
                {true, true},
                {true, false}
        };
        super(shape);
    }
}
