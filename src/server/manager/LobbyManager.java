package server.manager;

import server.lobby.Lobby;

import java.util.ArrayList;

public class LobbyManager implements ILobbyManager {
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

    @Override
    public void createLobby() {

    }

    @Override
    public void removeLobby() {

    }

    @Override
    public void setLobbyAsWaiting(Lobby lobby) {

    }

    @Override
    public void setLobbyAsRunning(Lobby lobby) {

    }

    @Override
    public void runLobby(Lobby lobby) {
        /*Runnable[] runners = new Runnable[10];
        Thread[] threads = new Thread[10];

        for(int i=0; i<10; i++) {
            runners[i] = new MyRun(i);
        }

        for(int i=0; i<10; i++) {
            threads[i] = new Thread(runners[i]);
        }

        for(int i=0; i<10; i++) {
            threads[i].start();
        }*/
    }
}
