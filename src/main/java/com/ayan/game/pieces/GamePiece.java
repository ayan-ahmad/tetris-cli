package com.ayan.game.pieces;

import com.ayan.game.SquareColour;
import com.ayan.game.Vector2;

public abstract class GamePiece implements IGamePiece {
    protected final Vector2 position = Vector2.zero();
    protected final SquareColour colour = SquareColour.getRandomBlock();
    protected int[][] shape;
}
