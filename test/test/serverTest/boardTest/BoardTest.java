package test.serverTest.boardTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Board;
import server.board.Coordinates;
import server.player.Player;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(17,13);
    }

    @Test
    void executeMoveTest() {
        Player p = new Player(11, "test");
        Coordinates c = new Coordinates(3, 9);
        Coordinates d = new Coordinates(4, 10);
        board.getField(c).setPlayerOn(p);
        assertTrue(board.executeMove(p, c, d));
    }

    @Test
    void executeMoveTest2() {
        Player p = new Player(112, "test");
        Coordinates c = new Coordinates(3, 9);
        Coordinates d = new Coordinates(4, 10);
        board.getField(c).setPlayerOn(p);
        board.getField(d).setPlayerOn(p);
        assertFalse(board.executeMove(p, c, d));
    }

    @Test
    void getFieldTest() {
        Coordinates c = new Coordinates(1, 1);
        assertNull(board.getField(c));
    }

    @Test
    void getNTest() {
        assertEquals(17, board.getN());
    }

        @Test
    void getMTest() {
            assertEquals(13, board.getM());
    }

}