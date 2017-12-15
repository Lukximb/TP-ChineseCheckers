package server.lobby;

import server.board.Coordinates;
import server.board.IBoard;
import jmx.Factory;

public class LobbyMediator {
    private Clock clock;
    private IRulesManager rulesManager;
    private IBoard board;
    private Lobby lobby;

    public LobbyMediator() {
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

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setRulesManager(IRulesManager rulesManager) {
        this.rulesManager = rulesManager;
    }
}
