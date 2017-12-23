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
    public void InvitePlayer(String lobbyName, String playerName, String invitedPlayerName) {
        Player player = null;
        for(Player p: playerManager.playerFreeList) {
            if(p.name.equals(invitedPlayerName)) {
                player = p;
            }
        }
        sendNotification(new Notification(String.valueOf(player.pid), this,
                001100101010, "I,"+playerName+","+lobbyName));
    }

    @Override
    public void addPlayerToLobby(String lobbyName, String playerName) {
        Player player = playerManager.getPlayerFromFreeList(playerName);
        if(player != null) {
            lobbyManager.addPlayerToLobby(lobbyName, player);
            playerManager.movePlayerToInGameList(player);
            sendNotification(new Notification(String.valueOf(lobbyName), this,
                    001100101010, "A,"+playerName+","+lobbyName));
            System.out.println("Player " + player.name + " in lobby " + player.lobby.name);
        } else {
            System.out.println("Error, can't add player: " + playerName);
        }
    }

    @Override
    public void removePlayerFromLobby(String lobbyName, String playerName) {
        Player player = playerManager.getPlayerFromInGameList(playerName);
        if(player != null) {
            lobbyManager.removePlayerFromLobby(lobbyName, player);
            playerManager.movePlayerToFreeList(player);
            System.out.println("Player " + player.name + " removed from lobby " + player.lobby.name);
        } else {
            System.out.println("Error, can't remove player: " + playerName);
        }
    }

    @Override
    public void sendPlayersInLobbyList(String playerName) {
        Player player = null;
        for(Player p: playerManager.playerInGameList) {
            if(p.name.equals(playerName)) {
                player = p;
            }
        }
        if(player != null) {
            System.out.println("Player is not NULL");
            String playersNames = player.getPlayersNames();
            sendNotification(new Notification(String.valueOf(player.lobby), this,
                    001100101010, "P"+playersNames));
        }
        else {
            System.out.println("Player is NULL");
        }
    }

    @Override
    public void sendWaitingLobbyList(String playerName) {
        Player player = null;
        for(Player p: playerManager.playerInGameList) {
            if(p.name.equals(playerName)) {
                player = p;
            }
        }
        if(player != null) {
            System.out.println("Player is not NULL");
            String waitingLobbyList = lobbyManager.getWaitingLobbyList();
            sendNotification(new Notification(String.valueOf(player.pid), this,
                    001100110001, "W" + waitingLobbyList));
        }
        else {
            System.out.println("Player is NULL");
        }
    }
}
