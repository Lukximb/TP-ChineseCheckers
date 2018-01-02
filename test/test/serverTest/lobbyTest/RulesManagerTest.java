package test.serverTest.lobbyTest;

import client.logic.MoveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Board;
import server.board.Coordinates;
import server.lobby.IRulesManager;
import server.lobby.LobbyMediator;
import server.lobby.RulesManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
//
//    @Test
//    void checkWinner() {
//    }
//
//    @Test
//    void checkLooser() {
//    }

}