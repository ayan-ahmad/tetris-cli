package com.ayan.game.pieces;

import com.ayan.game.SquareColour;
import com.ayan.game.Vector2;
import com.ayan.game.logic.Board;

import java.util.Arrays;

public abstract class GamePiece implements IGamePiece {
    protected final Vector2 position;

    protected GamePiece(boolean[][] shape) {
        this.shape = shape;
        System.out.println(Arrays.deepToString(shape));
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
        int[] offset = new int[shape.length];

        for (int x = 0; x < shape.length; x++) {
            offset[x] = 0;
            for (int y = shape[x].length - 1; y >= 0; y--) {
                if(shape[x][y]){
                    continue;
                }
                offset[x] = offset[x]++;
            }
        }

        int downCount = 0;

        if(Arrays.stream(offset).sum() == 0){
            int initialColumn = position.getX();
            int endColumn = initialColumn + shape.length;

            downCount = Integer.MAX_VALUE;
            for (int x = initialColumn; x < endColumn; x++) {
                int columnDownCount = 0;
                for (int y = position.getY() - 1; y >= 0; y--) {
                    if(board.getBoard()[x][y-offset[x-initialColumn]] != SquareColour.WHITE) {
                        downCount = Math.min(columnDownCount, downCount);
                        continue;
                    }
                    columnDownCount++;
                }
            }


        } else {
            int initialColumn = position.getX();
            int endColumn = initialColumn + shape.length;

            for (int x = initialColumn; x < endColumn; x++) {
                int columnDownCount = 0;
                for (int y = position.getY() - 1; y >= 0; y--) {
                    if(board.getBoard()[x][y-offset[x-initialColumn]] != SquareColour.WHITE) {
                        downCount = Math.max(columnDownCount, downCount);
                        continue;
                    }
                    columnDownCount++;
                }
            }
        }

        if (downCount == 0 | downCount == Integer.MAX_VALUE) {
            downCount = position.getY();
        }

        position.down(downCount);

        board.merge(this);
    }

    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;

        boolean[][] newShape = new boolean[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newShape[j][rows - 1 - i] = shape[i][j];
            }
        }

        shape = newShape;
    }
}

