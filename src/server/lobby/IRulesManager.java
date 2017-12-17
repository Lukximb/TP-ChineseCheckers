package server.lobby;

import server.board.Coordinates;
import jmx.Player;

public interface IRulesManager {
    boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid);

    Player checkWinner();

    Player checkLooser();
}
