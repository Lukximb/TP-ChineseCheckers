package server.lobby;

import server.board.Coordinates;
import server.board.IBoard;
import jmx.Factory;

public class LobbyMediator {
    private Clock clock;
    private IRulesManager rulesManager;
    private IBoard board;
    private Lobby lobby;

    public LobbyMediator(Lobby lobby) {
        this.lobby = lobby;
        //clock = Factory.getInstance().createClock();
        //rulesManager = Factory.getInstance().createRulesManager();
        //board = Factory.getInstance().createBoard();
    }

    public long getRoundTime() {
        return clock.getRoundTime();
    }

    public void startRound() {
        clock.startRound();
    }

    public void endRound() {
        clock.endRound();
    }

    public boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid) {
        return rulesManager.checkMove(currentCoordinates, newCoordinates, pid);
    }
}
