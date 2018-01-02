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

    int getPid();

    boolean isBot();

    void addCurrentCoordinates(int x, int y);

    void addDestinationCoordinates(int x, int y);

    void start();

    //void sortDestinationCoordinates();
}
