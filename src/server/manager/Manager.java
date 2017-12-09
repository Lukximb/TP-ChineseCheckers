package server.manager;

import server.player.Player;

public class Manager implements IManager {
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
}
