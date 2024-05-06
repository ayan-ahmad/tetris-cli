package com.ayan.menu;

import com.ayan.store.DatabaseManager;
import com.ayan.store.IDatabaseManager;
import com.ayan.store.Score;

import java.util.List;

public class HighScoreMenu {
    public static void displayHighScores(IDatabaseManager dbManager){
        List<Score> scores = dbManager.getScores();
        scores = scores.stream().sorted().toList();
        System.out.println("High Scores:");
        System.out.println("-------------------------");
        if(scores.isEmpty()){
            System.out.println("There are no scores. Be the first!");
            return;
        }
        for (int i = 0; i < Math.min(scores.size(), 10); i++) {
            Score score = scores.get(i);
            System.out.printf("%d. %-15s %d%n", i + 1, score.getName(), score.getScore());
        }
    }
}
