package test.serverTest.lobbyTest;

import client.logic.MoveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Board;
import server.board.Coordinates;
import server.lobby.IRulesManager;
import server.lobby.LobbyMediator;
import server.lobby.RulesManager;
import server.player.Player;

import static org.junit.jupiter.api.Assertions.*;

class RulesManagerTest {
    IRulesManager rm;
    Board b;
    LobbyMediator lm;

    @BeforeEach
    void setUp() {
        b = new Board(17, 13);
        rm = new RulesManager();
        lm = new LobbyMediator();
        lm.setBoard(b);
        rm.setMediator(lm);
    }

    @Test
    void checkMoveSingleTest() {
        Coordinates c = new Coordinates(3, 9);
        Coordinates d = new Coordinates(4, 10);
        assertTrue(rm.checkMove(c, d, MoveType.SINGLE));
    }

    @Test
    void checkMoveSingleTest1() {
        Coordinates c = new Coordinates(3, 9);
        Coordinates d = new Coordinates(4, 10);
        b.getField(d).setPlayerOn(new Player(0, "testPlayer"));
        assertFalse(rm.checkMove(c, d, MoveType.SINGLE));
    }

    @Test
    void checkMoveJumpTest1() {
        Coordinates c = new Coordinates(8, 12);
        Coordinates m = new Coordinates(7, 11);
        Coordinates d = new Coordinates(6, 10);
        b.getField(m).setPlayerOn(new Player(0, "testPlayer"));
        assertTrue(rm.checkMove(c, d, MoveType.JUMP));
    }

    @Test
    void checkMoveJumpTest2() {
        Coordinates c = new Coordinates(8, 12);
        Coordinates m = new Coordinates(7, 13);
        Coordinates d = new Coordinates(6, 14);
        b.getField(m).setPlayerOn(new Player(0, "testPlayer"));
        assertTrue(rm.checkMove(c, d, MoveType.JUMP));
    }

    @Test
    void checkMoveJumpTest3() {
        Coordinates c = new Coordinates(8, 12);
        Coordinates m = new Coordinates(8, 10);
        Coordinates d = new Coordinates(8, 8);
        b.getField(m).setPlayerOn(new Player(0, "testPlayer"));
        assertTrue(rm.checkMove(c, d, MoveType.JUMP));
    }

    @Test
    void checkMoveJumpTest4() {
        Coordinates c = new Coordinates(8, 12);
        Coordinates m = new Coordinates(8, 14);
        Coordinates d = new Coordinates(8, 16);
        b.getField(m).setPlayerOn(new Player(0, "testPlayer"));
        assertTrue(rm.checkMove(c, d, MoveType.JUMP));
    }

    @Test
    void checkMoveJumpTest5() {
        Coordinates c = new Coordinates(8, 12);
        Coordinates m = new Coordinates(9, 11);
        Coordinates d = new Coordinates(10, 10);
        b.getField(m).setPlayerOn(new Player(0, "testPlayer"));
        assertTrue(rm.checkMove(c, d, MoveType.JUMP));
    }

    @Test
    void checkMoveJumpTest6() {
        Coordinates c = new Coordinates(8, 12);
        Coordinates m = new Coordinates(9, 13);
        Coordinates d = new Coordinates(10, 14);
        b.getField(m).setPlayerOn(new Player(0, "testPlayer"));
        assertTrue(rm.checkMove(c, d, MoveType.JUMP));
    }
//
//    @Test
//    void checkWinner() {
//    }
//
//    @Test
//    void checkLooser() {
//    }

}