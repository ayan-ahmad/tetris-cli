package com.ayan.game.logic;

import java.io.IOException;

public class GameTick implements Runnable {
    private Board board;
    private int tick;
    private boolean canClear = true; // Depending on the console type it might not be able to clear
    private boolean started = true;

    public GameTick(Board board, int tick){
        this.board = board;
        this.tick = tick;

        Thread thread = new Thread(this);
        started = true;
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (started) {
                if(canClear) clearConsole();
                board.render();
                board.getCurrentPiece().down(board);
                Thread.sleep(tick); // Sleep for 1 second
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

    public void stopThread(){
        started = false;
    }
}
