package server.lobby;

import server.board.Coordinates;
import server.player.Player;

public interface IRulesManager {
    boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates);

    Player checkWinner();

    Player checkLooser();
}
