package com.ayan.game.logic;

import java.io.IOException;

public class GameTick implements Runnable {
    Board board;
    boolean canClear = true; // Depending on the console type it might not be able to clear

    public GameTick(Board board){
        this.board = board;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(canClear) clearConsole();
                board.render();
                board.getCurrentPiece().down(board);
                Thread.sleep(1000); // Sleep for 1 second
            }
        } catch (InterruptedException e) {
            System.out.println("[ERROR] The render thread was interrupted unexpectedly, exiting");
            System.exit(1);
        }
    }

    // This was taken from google
    private void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            canClear = false;
        }
    }
}
