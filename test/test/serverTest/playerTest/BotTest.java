package test.serverTest.playerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Board;
import server.lobby.Lobby;
import server.lobby.LobbyMediator;
import server.player.Bot;
import server.player.Difficult;
import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    Lobby lobby;
    Bot bot;
    Bot emptyBot = null;

    @BeforeEach
    void setUp() {
        Board b = new Board(17, 13);
        lobby = new Lobby(6, 4, "testLobby",
                new Bot(Difficult.EASY), new LobbyMediator());
        lobby.board = b;
        lobby.addPlayer(new Bot(Difficult.HARD));
        lobby.addPlayer(new Bot(Difficult.HARD));
        lobby.addPlayer(new Bot(Difficult.HARD));
        lobby.addPlayer(new Bot(Difficult.HARD));
        bot = new Bot(Difficult.MEDIUM);
        lobby.addPlayer(bot);
        lobby.startGame();
    }

    @Test
    void exitFromLobby() {
        //method do nothing
        assertNull(emptyBot);
    }

    @Test
    void pass() {
        //method do nothing
        assertNull(emptyBot);
    }

    @Test
    void sendMessage() {
        //method do nothing
        assertNull(emptyBot);
    }

    @Test
    void yourTurn() {
        bot.myTurn = false;
        bot.yourTurn();
        assertTrue(bot.myTurn);
    }

//    @Test
//    void move() {
//
//    }

//    @Test
//    void getCurrentPosition() {
//    }

//    @Test
//    void surrender() {
//    }

}