package server.manager;

import server.player.Player;

public interface ManagerMBean {
    Player getPlayer(int pid);

    void setPlayerAsFree(int pid);

    void setPlayerAsInGame(int pid);

    void setPlayerManager(PlayerManager playerManager);

    void setLobbyManager(LobbyManager lobbyManager);

    void InvitePlayer(String lobbyName, String playerName, String invitedPlayerName);

    void addPlayerToLobby(String lobbyName, String playerName);

    void removePlayerFromLobby(String lobbyName, String playerName);

    void sendPlayersInLobbyList(String playerName);

    void sendWaitingLobbyList(String playerName);

    void startGame(String lobbyName);
}
