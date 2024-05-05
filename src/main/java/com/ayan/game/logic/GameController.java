package com.ayan.game.logic;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class GameController implements Runnable {
    Board board;

    public GameController(Board board){
        this.board = board;
    }

    @Override
    public void run() {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            terminal.enterRawMode();
            NonBlockingReader reader = terminal.reader();
            while (true) {
                char input = (char) reader.read();
                switch (input){
                    case 'a':
                        board.getCurrentPiece().left(board);
                        board.render();
                        break;
                    case 's':
                        board.getCurrentPiece().down(board);
                        board.render();
                        break;
                    case 'd':
                        board.getCurrentPiece().right(board);
                        board.render();
                        break;
                    case 'z':
                        board.getCurrentPiece().rotate(board);
                        board.render();
                        break;
                    case ' ':
                        board.getCurrentPiece().place(board);
                        board.render();
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Unexpected I/O error, exiting");
            System.exit(1);
        }
    }
}
