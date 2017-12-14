package server.manager;

import jmx.Player;
import server.lobby.Lobby;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Manager extends NotificationBroadcasterSupport implements ManagerMBean {
    public PlayerManager playerManager;
    public LobbyManager lobbyManager;
    private static volatile Manager instance = null;

    public static Manager getInstance() {
        if (instance == null) {
            synchronized (Manager.class) {
                if (instance == null) {
                    instance = new Manager();
                }
            }
        }
        return instance;
    }

    private Manager() {
    }

    @Override
    public Player getPlayer(int pid) {
        return null;
    }

    @Override
    public void setPlayerAsFree(int pid) {

    }

    @Override
    public void setPlayerAsInGame(int pid) {

    }

    @Override
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void setLobbyManager(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    @Override
    public void addPlayerToLobby(String lobbyName, String playerName) {
        Player player = playerManager.getPlayerFromFreeList(playerName);
        lobbyManager.addPlayerToLobby(lobbyName, player);
        playerManager.movePlayerToInGameList(player);
        System.out.println("Player " + player.name + " in lobby " + player.lobby.name);
    }

    @Override
    public void removePlayerFromLobby(String lobbyName, String playerName) {
        Player player = playerManager.getPlayerFromInGameList(playerName);
        lobbyManager.removePlayerFromLobby(lobbyName, player);
        playerManager.movePlayerToFreeList(player);
        System.out.println("Player " + player.name + " removed from lobby " + player.lobby.name);
    }

    @Override
    public void sendPlayersInLobbyList(String playerName) {
        Player player = null;
        for(Player p: playerManager.playerInGameList) {
            if(p.name == playerName) {
                player = p;
            }
        }
        if(player != null) {
            String playersNames = player.getPlayersNames();
            sendNotification(new Notification(String.valueOf(player.pid), player, 001100110011, "P"+playersNames));
        }
    }

    @Override
    public void sendWaitingLobbyList(String playerName) {
        Player player = null;
        for(Player p: playerManager.playerInGameList) {
            if(p.name == playerName) {
                player = p;
            }
        }
        String waitingLobbyList = lobbyManager.getWaitingLobbyList();
        sendNotification(new Notification(String.valueOf(player.pid), this, 001100110011, "W"+waitingLobbyList));
    }
}
