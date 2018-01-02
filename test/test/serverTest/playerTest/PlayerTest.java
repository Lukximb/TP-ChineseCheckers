package test.serverTest.playerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.lobby.Lobby;
import server.lobby.LobbyMediator;
import server.player.Player;

class PlayerTest {
//    Player player;

    @BeforeAll
    static void setUp() {
//        int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
//        String name = "test";
//        Player player = new Player(pid, name);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void move() {
    }

    @Test
    void getCurrentPosition() {
    }

    @Test
    void joinToLobbyTest() {
        Player admin = new Player(11111, "admin");
        LobbyMediator mediator = new LobbyMediator();
        Lobby lobby = new Lobby(2, 4, "lobbyName", admin, mediator);

        Player player = new Player(11112, "playerName");
        player.joinToLobby(lobby, 1);

        org.junit.jupiter.api.Assertions.assertEquals(lobby, player.lobby);
    }

    @Test
    void exitFromLobby() {
        Player admin = new Player(11111, "admin");
        LobbyMediator mediator = new LobbyMediator();
        Lobby lobby = new Lobby(2, 4, "lobbyName", admin, mediator);
        admin.joinToLobby(lobby, 0);
        Player player = new Player(11112, "playerName");
        player.joinToLobby(lobby, 1);

        player.exitFromLobby();

        org.junit.jupiter.api.Assertions.assertNull(player.lobby);
    }

    @Test
    void createLobby() {
    }

    @Test
    void addBot() {
    }

    @Test
    void pass() {
    }

    @Test
    void surrender() {
    }

    @Test
    void getPid() {
    }

    @Test
    void sendMessage() {
    }

}