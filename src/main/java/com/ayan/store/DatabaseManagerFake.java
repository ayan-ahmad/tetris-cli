package com.ayan.store;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManagerFake implements IDatabaseManager {
    private List<Score> scores = new ArrayList<>();

    @Override
    public List<Score> getScores() {
        return scores;
    }

    @Override
    public boolean isHighScore(int currentScore) {
        if (scores.isEmpty()) {
            return true;
        }

        int highestScore = 0;
        for (Score score : scores) {
            if (score.getScore() > highestScore) {
                highestScore = score.getScore();
            }
        }
        return currentScore > highestScore;
    }

    @Override
    public void addScore(String name, int currentScore) {
        scores.add(new Score(name, currentScore));
    }
}
