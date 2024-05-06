package com.ayan.game.logic;

import com.ayan.game.SquareColour;
import com.ayan.game.pieces.*;
import com.ayan.store.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;

public class Board {
    private final int length;
    private final int height;

    private final SquareColour[][] board;
    GamePiece currentPiece = this.nextPiece();
    private GamePiece reservedPiece = null;

    private int score = 2000;

    GameTick gameTick;
    GameController gameController;

    private volatile boolean started;

    private IDatabaseManager dbManager;

    private static final Class<? extends GamePiece>[] possiblePieces = new Class[] { Square.class, Line.class, JBlock.class, LBlock.class, SBlock.class, ZBlock.class, TBlock.class };

    public Board(IConfigManager config, IDatabaseManager dbManager) {
        this.length = config.getBoardLength();
        this.height = config.getBoardHeight();
        this.board = new SquareColour[length][height];

        this.dbManager = dbManager;

        // Initialize the board with all squares set to SquareColour.EMPTY
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.height; j++) {
                board[i][j] = SquareColour.WHITE;
            }
        }

        this.gameTick = new GameTick(this, config.getGameTick());
        this.gameController = new GameController(this);
        this.started = true;

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

        while (started) {
            Thread.onSpinWait();
            // This holds the thread
        }
    }

    public Board(IConfigManager config, IDatabaseManager dbManager, boolean dontRunThreads) {
        this.length = config.getBoardLength();
        this.height = config.getBoardHeight();
        this.board = new SquareColour[length][height];

        this.dbManager = dbManager;

        // Initialize the board with all squares set to SquareColour.EMPTY
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.height; j++) {
                board[i][j] = SquareColour.WHITE;
            }
        }

        this.gameTick = new GameTick(this, config.getGameTick());
        this.gameController = new GameController(this);


        if (dontRunThreads) {
            this.started = false;
            this.gameTick.stopThread();
            this.gameController.stopThread();
        } else {
            this.started = true;
            while (started) {
                Thread.onSpinWait();
                // This holds the thread
            }
        }
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
        if(!started ){
            // prevents rendering after closed
            return;
        }
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
        this.score += currentScore;
    }

    private synchronized boolean isGameOver(GamePiece piece){
        if(piece.getPosition().getY() + piece.getShape()[0].length > height-1){
            System.out.println("Game Over");
            return true;
        }
        return false;
    }

    @lombok.SneakyThrows
    public void gameOver(){
        gameTick.stopThread();
        gameController.stopThread();
        Thread.sleep(1000);
        if(dbManager.isHighScore(this.score)){
            System.out.println("=====================");
            System.out.println("    NEW HIGH SCORE   ");
            System.out.println("=====================");
        }
        System.out.println("You got a score of: " + this.score);
        List<Score> scores = dbManager.getScores();
        scores = scores.stream().sorted().toList();
        if(scores.isEmpty() || scores.get(Math.min(scores.size() - 1, 9)).getScore() < this.score){
            Scanner scanner = new Scanner(System.in);
            System.out.println("What name do you want for the leaderboard?");
            String name = scanner.nextLine();
            dbManager.addScore(name, this.score);
        }
        this.started = false;
    }

    public synchronized void addPiece(GamePiece piece){
        if(!started){
            // Sometimes this gets called twice
            return;
        }
        merge(piece);
        if(isGameOver(piece)) {
            gameOver();
            return;
        }
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
