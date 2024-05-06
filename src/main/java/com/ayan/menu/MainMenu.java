package com.ayan.menu;

import java.util.Scanner;

public class MainMenu {

    public static int getMainMenuChoice(){
        displayTitle();
        displayMenu();
        return getUserInput();
    }

    private static void displayTitle() {
        System.out.println("  _____ _____ _____ ____  ___ ____  ");
        System.out.println(" |_   _| ____|_   _|  _ \\|_ _/ ___| ");
        System.out.println("   | | |  _|   | | | |_) || |\\___ \\ ");
        System.out.println("   | | | |___  | | |  _ < | | ___) |");
        System.out.println("   |_| |_____| |_| |_| \\_\\___|____/ ");
        System.out.println("                                   ");
    }

    private static void displayMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Start Game");
        System.out.println("2. Leaderboard");
        System.out.println("3. Exit");
    }

    private static int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    validInput = true;
                } else {
                    System.out.println("Invalid option. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }
}
