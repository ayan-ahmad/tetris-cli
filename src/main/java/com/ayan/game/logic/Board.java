package com.ayan.game.logic;

import com.ayan.game.SquareColour;
import com.ayan.game.Test;
import com.ayan.game.pieces.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Board {
    private final int length = 10;
    private final int height = 20;

    private SquareColour[][] board = new SquareColour[length][height];
    private GamePiece currentPiece = this.nextPiece();
    private GamePiece reservedPiece = null;

    private static final Class<? extends GamePiece>[] possiblePieces = new Class[] { Square.class, Line.class, JBlock.class, LBlock.class, SBlock.class, ZBlock.class, TBlock.class };

    public Board() {
        // Initialize the board with all squares set to SquareColour.EMPTY
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = SquareColour.WHITE;
            }
        }

        Thread gameTick = new Thread(new GameTick(this));
        Thread gameController = new Thread(new GameController(this));
        gameController.start();
        gameTick.start();



//        currentPiece.place(this);
//
//        new JBlock().place(this);


//        new JBlock().place(this);
//        GamePiece zblock = new ZBlock();
//        zblock.rotate(this);
//        zblock.right(this);
//        currentPiece = zblock;
//        render();


//        new Line().place(this);
//        new SBlock().place(this);
//        new TBlock().place(this);
//        new ZBlock().place(this);
    }

    public void reserveCurrentPiece(GamePiece piece){
        GamePiece oldCurrentPiece = currentPiece;
        if(reservedPiece == null){
            currentPiece = nextPiece();
        } else {
            currentPiece = reservedPiece;
        }
        reservedPiece = oldCurrentPiece;
    }

    public synchronized void render(){
        // Loop through each row of the board
        System.out.println();
        for (int y = height - 1; y >= 0; y--) {
            // Print each square in the row
            for (int x = 0; x < length; x++) {
                if (currentPiece != null && x >= currentPiece.getPosition().getX() &&
                        x < currentPiece.getPosition().getX() + currentPiece.getShape().length &&
                        y >= currentPiece.getPosition().getY() &&
                        y < currentPiece.getPosition().getY() + currentPiece.getShape()[0].length) {
                    // This square is part of the current piece
                    int relativeX = x - currentPiece.getPosition().getX();
                    int relativeY = y - currentPiece.getPosition().getY();
                    if (currentPiece.getShape()[relativeX][relativeY]) {
                        System.out.print(currentPiece.getColour().getSquare());
                    } else {
                        System.out.print(board[x][y].getSquare());
                    }
                } else {
                    System.out.print(board[x][y].getSquare());
                }
            }
            System.out.println(); // Move to the next row
        }
    }

    public synchronized void merge(GamePiece piece) {
        int pieceX = piece.getPosition().getX();
        int pieceY = piece.getPosition().getY();
        boolean[][] shape = piece.getShape();
        for (int y = 0; y < shape[0].length; y++) {
            for (int x = 0; x < shape.length; x++) {
                if (shape[x][y]) {
                    int boardX = pieceX + x;
                    int boardY = pieceY + y;
                    if (boardX >= 0 && boardX < length && boardY >= 0 && boardY < height) {
                        board[boardX][boardY] = piece.getColour();
                    }
                }
            }
        }
        setCurrentPiece();
    }

    private GamePiece nextPiece(){
        int index = (int)(Math.random() * possiblePieces.length);
        try {
            return possiblePieces[index].getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.exit(1);
        }
        return null;
    }

    public synchronized SquareColour[][] getBoard() {
        return board;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public synchronized GamePiece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece() {
        this.currentPiece = nextPiece();
    }
}
