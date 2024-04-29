package com.ayan.game.pieces;

public class ZBlock extends GamePiece {
    public ZBlock() {
        boolean[][] shape = new boolean[][]{
                {false, true},
                {true, true},
                {true, false}
        };
        super(shape);
    }
}
