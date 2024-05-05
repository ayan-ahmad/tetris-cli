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

    private int score;

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

    private synchronized void merge(GamePiece piece) {
        System.out.println("merged");
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
    }

    private void collapseRow(int rowIndex) {
        // Shift down all rows above rowIndex
        for (int y = rowIndex+1; y < height; y++) {
            for (int x = 0; x < length; x++) {
                board[x][y-1] = board[x][y];
            }
        }
        // Fill the top row with WHITE squares
        for (int x = 0; x < length; x++) {
            board[x][height-1] = SquareColour.WHITE;
        }
    }

    // This checks the rows and adds scores for collapsed rows
    private synchronized void collapseRows(){
        int currentScore = 0;
        int linesCleared = 0;
        for (int i = height - 1; i >= 0; i--) {
            boolean isRowFull = true;
            for (int j = 0; j < length; j++) {
                if (board[j][i] == SquareColour.WHITE) {
                    isRowFull = false;
                    break;
                }
            }
            if (isRowFull) {
                collapseRow(i);
                linesCleared++;
                currentScore += 100;
            }
        }
        if (linesCleared > 1) {
            currentScore += (linesCleared - 1) * 100;
        }
        score = currentScore;
        System.out.println("Score: " + score);
    }

    private synchronized boolean isGameOver(GamePiece piece){
        if(piece.getPosition().getY() + piece.getShape()[0].length > height-1){
            System.out.println("Game Over");
            return true;
        }
        return false;
    }

    public synchronized void addPiece(GamePiece piece){
        merge(piece);
        if(isGameOver(piece)) System.exit(1);
        setCurrentPiece();
        collapseRows();
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
