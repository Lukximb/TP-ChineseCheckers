package server.player;

import server.board.Coordinates;
import server.lobby.Lobby;

import java.util.ArrayList;

public interface PlayerTemplate {
    int pid = 0;
    String name = "";
    Lobby lobby = null;
    int corner = -1;
    ArrayList<Coordinates> destinationCoordinates = new ArrayList<>();
    ArrayList<Coordinates> currentCoordinates = new ArrayList<>();

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
     * Find player by name in lobbys' player list
     * @return name
     */
    String getPlayersNames();

    String getName();

    /**
     * Returns player's pid
     * @return pid
     */
    int getPid();

    /**
     * Checks if Player is bot
     * @return true if is bot
     * @return false if is not bot
     */
    boolean isBot();

    /**
     * Adds single coordinate to currentCoordinates list
     * @param x - x value of added coordinate
     * @param y - y value of added coordinate
     */
    void addCurrentCoordinates(int x, int y);

    /**
     * Adds single coordinate to destinationCoordinates list
     * @param x - x value of added coordinate
     * @param y - y value of added coordinate
     */
    void addDestinationCoordinates(int x, int y);

    void start();

    /**
     * Compares coordinates in currentCoordinates and destinationCoordinates lists
     * @return true if all coordinates are the same for both lists
     */
    boolean compareCoordinates();

    /**
     *Returns player's corner on the board.
     * @return corner
     */
    int getCorner();

    /**
     * Sets player's lobby
     * @param lobby - new lobby
     */
    void setLobby(Lobby lobby);

    //void sortDestinationCoordinates();
}
