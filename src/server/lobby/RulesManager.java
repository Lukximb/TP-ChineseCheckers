package server.lobby;

import server.board.Coordinates;
import jmx.Player;

public class RulesManager implements IRulesManager {
    @Override
    public boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid) {
        return true;
    }

    @Override
    public Player checkWinner() {
        return null;
    }

    @Override
    public Player checkLooser() {
        return null;
    }
}
