package com.ayan.game.logic;

import com.ayan.game.pieces.GamePiece;
import com.ayan.game.pieces.IGamePiece;

public class Board {
    int length = 10;
    int height = 20;
    String[][] board = new String[length][height];
    GamePiece currentPiece;
    GamePiece reservedPiece = null;
}
