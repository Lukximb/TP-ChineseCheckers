package server.player;

import javafx.scene.paint.Color;
import server.lobby.Lobby;

public interface PlayerTemplate {
    int pid = 0;
    String name = "";
    Lobby lobby = null;
    Color color = null;
    int corner = -1;
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

    /**
     * Set player pawn color
     */
    void setColor(Color color);

    /**
     * Find player by name in lobbys' player list
     * @return name
     */
    String getPlayersNames();

    String getName();

    int getPid();
}
