package server.manager;

import server.player.PlayerTemplate;

public interface ManagerMBean {

    /**
     * Returns player witch specified pid value.
     * @param pid - player's pid
     * @return player
     */
    PlayerTemplate getPlayer(int pid);

    void setPlayerAsFree(int pid);

    void setPlayerAsInGame(int pid);

    void setPlayerManager(PlayerManager playerManager);

    void setLobbyManager(LobbyManager lobbyManager);

    /**
     * Invites player to lobby.
     * @param lobbyName - name of lobby.
     * @param playerName - name of inviting player.
     * @param invitedPlayerName - name of invited player.
     */
    void InvitePlayer(String lobbyName, String playerName, String invitedPlayerName);

    /**
     * Adds player to lobby.
     * @param lobbyName - name of lobby.
     * @param playerName - name of player.
     */
    void addPlayerToLobby(String lobbyName, String playerName);

    /**
     * Removes player from lobby.
     * @param lobbyName - lobby name.
     * @param playerName - player's name.
     */
    void removePlayerFromLobby(String lobbyName, String playerName);

    /**
     * Removes player from running game.
     * @param lobbyName - name of lobby.
     * @param playerName - name of player.
     */
    void removePlayerFromGame(String lobbyName, String playerName);

    /**
     * Sends list of players in lobby to specified player.
     * @param playerName - name of player reciving information.
     */
    void sendPlayersInLobbyList(String playerName);

    /**
     * Sends list of waiting lobbys to specified player.
     * @param playerName - name of player reciving information.
     */
    void sendWaitingLobbyList(String playerName);

    /**
     * Starts game for specified lobby.
     * @param lobbyName - name of lobby.
     */
    void startGame(String lobbyName);
}
