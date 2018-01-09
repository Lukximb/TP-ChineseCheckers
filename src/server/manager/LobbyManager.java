package server.manager;

import server.lobby.Lobby;
import server.player.Player;
import server.player.PlayerTemplate;

import java.util.ArrayList;

public class LobbyManager {
    private Manager manager;
    public ArrayList<Lobby> runningLobbyList;
    public ArrayList<Lobby> waitingLobbyList;
    private static volatile LobbyManager instance = null;

    public static LobbyManager getInstance() {
        if (instance == null) {
            synchronized (LobbyManager.class) {
                if (instance == null) {
                    instance = new LobbyManager();
                }
            }
        }
        return instance;
    }

    private LobbyManager() {
        runningLobbyList = new ArrayList<>();
        waitingLobbyList = new ArrayList<>();
    }

    public void createLobby() {

    }

    public void removeLobby(Lobby lobby) {
        setLobbyAsWaiting(lobby);
        waitingLobbyList.remove(lobby);
        System.out.println("Lobby deleted");
        manager.unregisterLobby(lobby);
    }

    public void setLobbyAsWaiting(Lobby lobby) {
        if(runningLobbyList.contains(lobby)) {
            runningLobbyList.remove(lobby);
            waitingLobbyList.add(lobby);
        }
    }

    public void setLobbyAsRunning(Lobby lobby) {
        if(waitingLobbyList.contains(lobby)) {
            waitingLobbyList.remove(lobby);
            runningLobbyList.add(lobby);
        }
    }

    public void addPlayerToLobby(String lobbyName, PlayerTemplate player) {
        Lobby lobby = null;

        for(Lobby l: waitingLobbyList) {
            if(l.name.equals(lobbyName)) {
                lobby = l;
                break;
            }
        }
        if(lobby != null) {
            lobby.addPlayer(player);
        }
    }

    public boolean removePlayerFromLobby(String lobbyName, String playerName) {
        Lobby lobby = null;

        for(Lobby l: waitingLobbyList) {
            if(l.name.equals(lobbyName)) {
                lobby = l;
                break;
            }
        }
        if(lobby != null) {
            lobby.removePlayer(playerName);
            if(lobby.isEmpty()) {
                removeLobby(lobby);
            }
            return true;
        }
        return false;
    }

    public void removePlayerFromGame(String lobbyName, Player player) {
        Lobby lobby = null;

        for(Lobby l: runningLobbyList) {
            if(l.name.equals(lobbyName)) {
                lobby = l;
                break;
            }
        }
        if(lobby != null) {
//            lobby.removePlayer(player);
            for(PlayerTemplate p : lobby.players) {
                if(p.equals(player)) {
                    p = null;
                }
            }
            if(lobby.isEmpty()) {
                removeLobby(lobby);
            }
        }
    }

    public String getWaitingLobbyList() {
        String lobbyList = "";
        for(Lobby l: waitingLobbyList) {
            lobbyList = lobbyList.concat((","+l.name));
        }
        return lobbyList;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
