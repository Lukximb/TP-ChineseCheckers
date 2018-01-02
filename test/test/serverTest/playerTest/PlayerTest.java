package test.serverTest.playerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.lobby.Lobby;
import server.lobby.LobbyMediator;
import server.player.Player;

class PlayerTest {
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void move() {
//    }
//
//    @Test
//    void getCurrentPosition() {
//    }
//
      @Test
      void joinToLobbyTest() {
        Player admin = new Player(11111, "admin");
        LobbyMediator mediator = new LobbyMediator();
        Lobby lobby = new Lobby(2, 4, "lobbyName", admin, mediator);

        Player player = new Player(11112, "playerName");
        player.joinToLobby(lobby, 1);

        org.junit.jupiter.api.Assertions.assertEquals(lobby, player.lobby);
      }
//
//    @Test
//    void exitFromLobby() {
//    }
//
//    @Test
//    void createLobby() {
//    }
//
//    @Test
//    void addBot() {
//    }
//
//    @Test
//    void pass() {
//    }
//
//    @Test
//    void surrender() {
//    }
//
//    @Test
//    void getPid() {
//    }
//
//    @Test
//    void sendMessage() {
//    }
}