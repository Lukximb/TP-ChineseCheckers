package server.player;

import client.logic.MoveType;
import server.board.Coordinates;

/**
 * Intefrace which is implemented by Player class.
 */
public interface PlayerMBean extends PlayerTemplate{
    /**
     * Moves player.
     * @param currentCoordinates, destinationCoordinates
     */
    void move(Coordinates currentCoordinates, Coordinates destinationCoordinates);

    Coordinates getCurrentPosition();

    /**
     * Checking if move is correct using rulesManager class
     * @param currentCoordinates
     * @param destinationCoordinates
     */
    void checkMove(Coordinates currentCoordinates, Coordinates destinationCoordinates, MoveType moveType);

    /**
     * Adds new bot to lobby.
     * @param difficultLevel bot's difficulty.
     */
    void addBot(Difficult difficultLevel);

    /**
     * Surrender game.
     */
    void surrender();

    /**
     * Returns player's pid.
     * @return pid value
     */
    int getPid();

    /**
     * Ends player's turn in game.
     */
    void nextRound();

}
