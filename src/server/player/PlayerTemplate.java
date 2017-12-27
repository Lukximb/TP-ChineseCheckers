package server.player;

import server.lobby.Lobby;

public interface PlayerTemplate {
    /**
     * Adds player to lobby.
     * @param lobby name of lobby which player is joining.
     * @param corner number of corner where player start game
     */
    void joinToLobby(Lobby lobby, int corner);

    /**
     * Removes player from lobby.
     */
    void exitFromLobby();

    /**
     * Pass the move.
     */
    void pass();

    /**
     * Sends message.
     * @param message contents of message
     */
    void sendMessage(String message);

    /**
     * Informs player, that he can choose and execute move.
     */
    void yourTurn();
}
