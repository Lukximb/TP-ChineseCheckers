package server.manager;

import jmx.Player;

public interface IManager {
    Player getPlayer(int pid);

    void setPlayerAsFree(int pid);

    void setPlayerAsInGame(int pid);

    void setPlayerManager(PlayerManager playerManager);

    void setLobbyManager(LobbyManager lobbyManager);
}
