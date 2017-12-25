package server.board;

import jmx.Player;

/**
 * Interface which is implemented by Board class.
 */
public interface IBoard {
    /**
     * Vertical board size.
     */
    int n = 0;
    /**
     * Horizontal board size.
     */
    int m = 0;
    /**
     * Array contains board fields.
     */
    Field[][] fieldList = null;

    /**
     * Moves player.
     * @param currentCoordinates current player position
     * @param newCoordinates new player position
     */
    boolean executeMove(Player player, Coordinates currentCoordinates, Coordinates newCoordinates);

    void fillFieldList();

    int getN();

    int getM();

    /**
     * Returns field with specified coordinates.
     * @param coordinates field's coordinates
     * @return
     */
    Field getField(Coordinates coordinates);
}
