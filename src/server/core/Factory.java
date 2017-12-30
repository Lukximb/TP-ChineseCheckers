package server.core;

import server.player.Player;
import server.board.Board;
import server.board.Coordinates;
import server.board.Field;
import server.connection.Connection;
import server.lobby.*;
import server.manager.LobbyManager;
import server.manager.Manager;
import server.manager.PlayerManager;
import server.player.Bot;
import server.player.PlayerTemplate;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Factory extends NotificationBroadcasterSupport implements FactoryMBean {
    private Server server;
    private Manager manager;
    private LobbyManager lobbyManager;
    private PlayerManager playerManager;
    private static volatile Factory instance = null;

    public static Factory getInstance(Server server) {
        if (instance == null) {
            synchronized (Factory.class) {
                if (instance == null) {
                    instance = new Factory(server);
                }
            }
        }
        return instance;
    }

    private Factory(Server server) {
        this.server = server;
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
    public IRulesManager createRulesManager() {
        return new RulesManager();
    }

    @Override
    public void createPlayer(int pid, String name) {
        System.out.println(pid);
        Player player = new Player(pid, name);


        server.connection.createMBeanMainObject("server.player.Player", "Player"+pid, String.valueOf(pid), player);
        playerManager.getNewPlayer(player);
        sendNotification(new Notification(String.valueOf(pid), this, 001100110011, "####hello player created: " + pid));
    }

    public void deletePlayer(int pid) {
        int value = playerManager.checkPlayerStatus(pid);
        int index = 0;
        if(value == 1) {
            for(Player p: playerManager.playerFreeList) {
                if(p.pid == pid) {
                    break;
                }
                index++;
            }
            playerManager.playerFreeList.remove(index);
        }
        else if(value == 2) {
            for(Player p: playerManager.playerInGameList) {
                if(p.pid == pid) {
                    break;
                }
                index++;
            }
            playerManager.playerInGameList.remove(index);
        }
        System.out.println(">> Delete player: " + pid + " from playerList");
    }

    @Override
    public Bot createBot() {
        return null;
    }

    @Override
    public void createLobby(int playerNum, int rowNumber, String lobbyName, int adminPid) {
        Player admin;
        LobbyMediator lobbyMediator= createLobbyMediator();
        int value = playerManager.checkPlayerStatus(adminPid);
        int index = 0;
        for(Player p: playerManager.playerFreeList) {
            if(p.pid == adminPid) {
                break;
            }
            index++;
        }
        admin = playerManager.playerFreeList.get(index);
        playerManager.playerFreeList.remove(index);
        playerManager.playerInGameList.add(admin);
        System.out.println(">> Lobby " + lobbyName + " created");
        Lobby lobby = new Lobby(playerNum, rowNumber, lobbyName, admin, lobbyMediator);
        lobby.board = createBoard(4*rowNumber+1, 3*rowNumber+1);
        lobbyMediator.setBoard(lobby.board);
        admin.lobby = lobby;
        lobbyManager.waitingLobbyList.add(lobby);
        server.connection.createMBeanMainObject("lobby.Lobby", lobby.name, "L", lobby);
    }

    @Override
    public LobbyMediator createLobbyMediator(){
        LobbyMediator mediator = new LobbyMediator();
        IRulesManager rules = new RulesManager();
        //Clock clock = createClock();
        rules.setMediator(mediator);
        mediator.setRulesManager(rules);
        //mediator.setClock(clock);
        return mediator;
    }

    @Override
    public Field createField(int n, int m) {
        return new Field(createCoordinates(n, m));
    }

    @Override
    public Board createBoard(int n, int m) {
        return new Board(n, m);
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
