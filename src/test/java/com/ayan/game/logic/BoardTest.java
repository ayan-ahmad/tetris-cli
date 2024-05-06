package com.ayan.game.logic;

import com.ayan.game.Vector2;
import com.ayan.game.pieces.Square;
import com.ayan.store.ConfigManagerStub;
import com.ayan.store.DatabaseManagerFake;
import com.ayan.store.IDatabaseManager;
import com.ayan.store.Score;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BoardTest {
    // Test for Stub
    @Test
    public void getBoardDimensionsTest(){
        Board board = new Board(new ConfigManagerStub(), new DatabaseManagerFake(), true);
        assertEquals(20, board.getHeight());
        assertEquals(10, board.getLength());
    }

    // Test for fake
    @Test
    public void gameOverTop10Test() throws NoSuchFieldException, IllegalAccessException {
        DatabaseManagerFake dbManager = Mockito.spy(DatabaseManagerFake.class);
        dbManager.scores = new ArrayList<>(List.of(
                new Score("John", 1100),
                new Score("James", 1000),
                new Score("Jamie-lee", 900),
                new Score("Jason", 800),
                new Score("Jemma", 700),
                new Score("Jillian", 600),
                new Score("Jared", 500),
                new Score("Joshua", 400),
                new Score("Josh", 300),
                new Score("Jane", 200),
                new Score("Jeb", 100)
        ));
        Board board = new Board(new ConfigManagerStub(20, 10, Integer.MAX_VALUE),dbManager,true);

        // Got from StackOverflow https://stackoverflow.com/a/31635737/12827591
        String input = "Winner";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Field scoreField = Board.class.getDeclaredField("score");
        scoreField.setAccessible(true);
        scoreField.set(board, 900);

        board.gameOver();

        verify(dbManager, times(1)).addScore(any(), eq(900));
    }

    // Mock of setCurrentPiece()
    @Test
    public void setCurrentPieceTest(){
        Board board = Mockito.spy(new Board(new ConfigManagerStub(),new DatabaseManagerFake(),true));
        Mockito.doAnswer(invocationOnMock -> {
            board.currentPiece = new Square();
            return null;
        }).when(board).setCurrentPiece();

        board.setCurrentPiece();

        assertSame(board.currentPiece.getClass(), Square.class);
        assertEquals(3, board.currentPiece.getPosition().getX());
        assertEquals(18, board.currentPiece.getPosition().getY());
    }
}
