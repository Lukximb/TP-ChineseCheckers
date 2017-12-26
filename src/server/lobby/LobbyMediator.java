package server.lobby;

import client.logic.MoveType;
import jmx.Player;
import server.board.Coordinates;
import server.board.Field;
import server.board.IBoard;

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

    public void setBoard(IBoard board) {
        this.board = board;
    }

    public void startRound() {
        clock.startRound();
    }

    public Field getField(Coordinates coordinates) {
        Field field = board.getField(coordinates);
        return field;
    }

    public void endRound() {
        clock.endRound();
    }

    public boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid, MoveType moveType) {
        return rulesManager.checkMove(currentCoordinates, newCoordinates, pid, moveType);
    }

    public boolean move(Player player, Coordinates currentCoordinates, Coordinates newCoordinates) {
        return board.executeMove(player, currentCoordinates, newCoordinates);
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
