package server.lobby;

import client.logic.MoveType;
import server.board.Coordinates;
import jmx.Player;

public class RulesManager implements IRulesManager {
    private LobbyMediator mediator;

    @Override
    public boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid, MoveType moveType) {
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

    public void setMediator(LobbyMediator mediator) {
        this.mediator = mediator;
    }
}
