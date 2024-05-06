package com.ayan.store;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;

import static com.ayan.store.YamlHelper.getFile;

public class ConfigManager implements IConfigManager {
    private int boardHeight;
    private int boardLength;
    private int gameTick;

    public ConfigManager(){
        try {
            YamlReader reader = new YamlReader(new FileReader(getFile("config.yml")));
            Map<String, String> config = (Map<String, String>) reader.read();

            boardHeight = Integer.parseInt(config.get("board_height"));
            boardLength = Integer.parseInt(config.get("board_length"));
            gameTick = Integer.parseInt(config.get("game_tick"));
        } catch (FileNotFoundException e) {
            System.out.println("Could not find config file in resources");
        } catch (YamlException e) {
            System.out.println("Could not parse YAML config file, check formatting");
        } catch (NumberFormatException e){
            System.out.println("Values in config are not integers, all values should be integers");
        }
    }

    @Override
    public int getBoardHeight() {
        return boardHeight;
    }

    @Override
    public int getBoardLength() {
        return boardLength;
    }

    @Override
    public int getGameTick() {
        return gameTick;
    }
}
