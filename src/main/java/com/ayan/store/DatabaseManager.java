package com.ayan.store;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.*;
import java.util.*;

import static com.ayan.store.YamlHelper.getFile;

public class DatabaseManager implements IDatabaseManager {
    @Override
    public List<Score> getScores() {
        List<Score> scores = new ArrayList<>();
        try (Reader reader = new FileReader(getFile("scores.yml"))) {
            YamlReader yamlReader = new YamlReader(reader);
            while (true) {
                Score score = yamlReader.read(Score.class);
                if (score == null) break;
                scores.add(score);
            }
        } catch (YamlException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return scores;
    }

    @Override
    public boolean isHighScore(int currentScore) {
        List<Score> scores = getScores();
        Score highestScore = scores.stream().max(Comparator.comparingInt(Score::getScore)).orElse(null);
        return highestScore == null || currentScore > highestScore.getScore();
    }

    @Override
    public void addScore(String name, int currentScore) {
        List<Score> scores = getScores();
        scores.add(new Score(name, currentScore));
        try {
            YamlWriter writer = new YamlWriter(new FileWriter(getFile("scores.yml")));
            for (Score score : scores) {
                writer.write(score);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
