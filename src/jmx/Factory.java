package jmx;

import server.board.*;
import server.connection.Connection;
import server.core.Server;
import server.lobby.*;
import server.manager.*;
import server.player.*;

public class Factory implements FactoryMBean {
    private Server server;
    private Manager manager;
    private LobbyManager lobbyManager;
    private PlayerManager playerManager;
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

    private Factory() {//Server server) {
        //this.server = server;
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
        createLobbyManager();
        createPlayerManager();
        manager = Manager.getInstance();
        manager.setPlayerManager(playerManager);
        manager.setLobbyManager(lobbyManager);
        return manager;
    }

    @Override
    public void createLobbyManager() {
        lobbyManager = LobbyManager.getInstance();
    }

    @Override
    public void createPlayerManager() {
        playerManager = PlayerManager.getInstance();
    }

    @Override
    public RulesManager createRulesManager() {
        return null;
    }

    @Override
    public void createPlayer(int pid) {
        System.out.println(pid);
        playerManager.getNewPlayer(new Player(pid));
    }

    @Override
    public Bot createBot() {
        return null;
    }

    @Override
    public Lobby createLobby(String name) {
        System.out.println(">> Lobby created");
        return null;
    }

    @Override
    public Field createField(int n, int m) {
        return new Field(createCoordinates(n, m));
    }

    @Override
    public Board createBoard(int n, int m) {
        return null;
    }

    @Override
    public Chat createChat() {
        return new Chat();
    }

    @Override
    public Clock createClock() {
        return new Clock();
    }

    @Override
    public Coordinates createCoordinates(int n, int m) {
        return new Coordinates(n, m);
    }
}
