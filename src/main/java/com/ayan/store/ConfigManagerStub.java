package com.ayan.store;

public class ConfigManagerStub implements IConfigManager{
    private final int boardHeight;
    private final int boardLength;
    private final int gameTick;

    public ConfigManagerStub(int boardHeight, int boardLength, int gameTick) {
        this.boardHeight = boardHeight;
        this.boardLength = boardLength;
        this.gameTick = gameTick;
    }

    public ConfigManagerStub(){
        this.boardHeight = 20;
        this.boardLength = 10;
        this.gameTick = 1000;
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
