package com.ayan.game;

import java.util.Arrays;
import java.util.List;

public enum SquareColour {

// None don't work on Windows automatically uncomment if emojis work for better experience
    RED("🟥", true),
    ORANGE("🟧", true),
    YELLOW("🟨", true),
    GREEN("🟩", true),
    BLUE("🟦", true),
    PURPLE("🟪", true),
    BROWN("🟫", true),
    BLACK("⬛", false),
    WHITE("⬜", false),
    EMPTY(" ", false);

//    FILLED("O", true),
//    WHITE("X", false);

    private String square;
    private boolean isBlock;

    SquareColour(String square, boolean isBlock){
        this.square = square;
        this.isBlock = isBlock;
    }

    public String getSquare() {
        return square;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public static List<SquareColour> getBlocks(){
        return Arrays.stream(SquareColour.values()).filter(SquareColour::isBlock).toList();
    }

    public static SquareColour getRandomBlock(){
        List<SquareColour> blocks = SquareColour.getBlocks();
        int index = (int)(Math.random() * blocks.size());
        return blocks.get(index);
    }
}
