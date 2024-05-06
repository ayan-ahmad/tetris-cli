package com.ayan.store;

public class ConfigManagerStub implements IConfigManager{
    @Override
    public int getBoardHeight() {
        return 20;
    }

    @Override
    public int getBoardLength() {
        return 10;
    }

    @Override
    public int getGameTick() {
        return 1000;
    }
}
