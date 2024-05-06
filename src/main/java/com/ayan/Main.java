package com.ayan;

import com.ayan.game.logic.Board;
import com.ayan.store.ConfigManager;
import com.ayan.store.DatabaseManager;

import java.util.Scanner;

import static com.ayan.menu.HighScoreMenu.displayHighScores;
import static com.ayan.menu.MainMenu.getMainMenuChoice;

public class Main {
    public static void main(String[] args) {
        ConfigManager config = new ConfigManager();
        DatabaseManager dbManager = new DatabaseManager();
        while (true) {
            int choice = getMainMenuChoice();
            switch (choice){
                case 1:
                    new Board(config, dbManager);
                    break;
                case 2:
                    displayHighScores(dbManager);
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("\nPress enter to continue.");
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Thanks for playing!");
                    System.exit(1);
                    break;
            }
        }
    }
}