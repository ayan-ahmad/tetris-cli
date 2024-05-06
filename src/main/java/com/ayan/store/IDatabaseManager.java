package com.ayan.store;

import java.util.List;
import java.util.Map;

public interface IDatabaseManager {
    public List<Score> getScores();
    public boolean isHighScore(int currentScore);
    public void addScore(String name, int currentScore);
}
