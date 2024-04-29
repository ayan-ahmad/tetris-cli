package com.ayan.game.pieces;

public class JBlock extends GamePiece {
    public JBlock(){
        boolean[][] shape = new boolean[][]{
                {true, false},
                {true, false},
                {true, true}
        };
        super(shape);
    }
}
