package server.core;

import server.board.*;
import server.connection.Connection;
import server.lobby.*;
import server.manager.*;
import server.player.*;

public class Factory implements IFactory {
    private static volatile Factory instance = null;

    public static Factory getInstance() {
        if (instance == null) {
            synchronized (Factory.class) {
                if (instance == null) {
                    instance = new Factory();
                }
            }
        }
        return instance;
    }

    private Factory() {
    }

    @Override
    public Connection createConnection() {
        Connection connection = Connection.getInstance();
        connection.createRegistry();
        connection.createMBeanServer();
        connection.createDomain();
        return connection;
    }

    @Override
    public Manager createManager() {
        LobbyManager lobbyManager = createLobbyManager();
        PlayerManager playerManager = createPlayerManager();
        Manager manager = Manager.getInstance();
        manager.setPlayerManager(playerManager);
        manager.setLobbyManager(lobbyManager);
        return manager;
    }

    @Override
    public LobbyManager createLobbyManager() {
        LobbyManager lobbyManager = LobbyManager.getInstance();
        return lobbyManager;
    }

    @Override
    public PlayerManager createPlayerManager() {
        PlayerManager playerManager = PlayerManager.getInstance();
        return playerManager;
    }

    @Override
    public RulesManager createRulesManager() {
        return null;
    }

    @Override
    public Player createPlayer() {
        return null;
    }

    @Override
    public Bot createBot() {
        return null;
    }

    @Override
    public Lobby createLobby() {
        return null;
    }

    @Override
    public Field createField() {
        return null;
    }

    @Override
    public Board createBoard() {
        return null;
    }

    @Override
    public Chat createChat() {
        return null;
    }

    @Override
    public Clock createClock() {
        return null;
    }
}
