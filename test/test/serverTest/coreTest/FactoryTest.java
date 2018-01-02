package test.serverTest.coreTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Board;
import server.core.Factory;
import server.core.Server;
import server.lobby.IRulesManager;
import server.lobby.Lobby;
import server.manager.LobbyManager;
import server.manager.Manager;
import server.manager.PlayerManager;
import server.player.Player;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FactoryTest {
    Server server;
    Factory factory;

    @BeforeEach
    void setUp() {
        server = new Server(0);
        factory = Factory.getInstance(server);
        factory.server.connection = factory.createConnection();
    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
    @Test
    void getInstance() {
        assertNotNull(factory);
    }


    @Test
    void createConnectionTest() {
        assertNotNull(factory.server.connection);
    }


    @Test
    void createManager() {
        Manager m;
        m = factory.createManager();
        assertNotNull(m);
        assertNotNull(m.playerManager);
        assertNotNull(m.lobbyManager);
    }

    @Test
    void createLobbyManager() {
        factory.createLobbyManager();
        Field privateLM = null;
        try {
            privateLM = Factory.class.
                    getDeclaredField("lobbyManager");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateLM.setAccessible(true);

        try {
            assertNotNull((LobbyManager) privateLM.get(factory));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createPlayerManager() {
        factory.createPlayerManager();
        Field privatePM = null;
        try {
            privatePM = Factory.class.
                    getDeclaredField("playerManager");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privatePM.setAccessible(true);

        try {
            assertNotNull((PlayerManager) privatePM.get(factory));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createRulesManager() {
        IRulesManager rm;
        rm = factory.createRulesManager();
        assertNotNull(rm);
    }

    @Test
    void createPlayer() {
        Manager m;
        m = factory.createManager();
        Player player = null;
        factory.createPlayer(1, "testCreate");
        for(Player p : m.playerManager.playerFreeList) {
            if(p.getName().equals("testCreate")) {
                player = p;
                break;
            }
        }

        for(Player p : m.playerManager.playerInGameList) {
            if(p.getName().equals("testCreate")) {
                player = p;
                break;
            }
        }
        assertNotNull(player);
    }


    @Test
    void createLobby() {
        server.manager = factory.createManager();
        server.connection = factory.createConnection();
        factory.createPlayer(123, "testAdmin");
        factory.createLobby(3, 4, "lobbyTest", 123);
        Lobby lobby = null;

        for (Lobby l : server.manager.lobbyManager.waitingLobbyList) {
            if (l.name.equals("lobbyTest")) {
                lobby = l;
                break;
            }
        }

        assertNotNull(lobby);
    }

    @Test
    void createField() {
        server.board.Field f;
        f = factory.createField(2, 3);
        assertNotNull(f);
    }

    @Test
    void createBoard() {
        Board b;
        b = factory.createBoard(17, 13);
        assertNotNull(b);
    }

//    @Test
//    void createClock() {
//    }

}