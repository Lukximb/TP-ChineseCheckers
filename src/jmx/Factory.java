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

    public void deletePlayer(int pid) {
        if(playerManager.checkPlayerStatus(pid) == 1) {
            int index = 0;
            for(Player p: playerManager.playerFreeList) {
                if(p.pid == pid) {
                    break;
                }
                index++;
            }
            playerManager.playerFreeList.remove(index);
        }
        else if(playerManager.checkPlayerStatus(pid) == 2) {
            int index = 0;
            for(Player p: playerManager.playerInGameList) {
                if(p.pid == pid) {
                    break;
                }
                index++;
            }
            playerManager.playerInGameList.remove(index);
        }
        System.out.println("Usunieto");
        for(Player p: playerManager.playerFreeList){
            System.out.print(p.pid + " ");
        }
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
