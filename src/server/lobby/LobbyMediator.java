package server.lobby;

import client.logic.MoveType;
import server.player.Player;
import server.board.Coordinates;
import server.board.Field;
import server.board.IBoard;

import java.util.ArrayList;

public class LobbyMediator {
    private Clock clock;
    private Thread clockThread;
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
        lobby.sendTurnNotification();
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

    public void endOfTime() {
        lobby.nextRound();
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void setClock(Clock clock) {
        if(this.clock == null) {
            this.clock = clock;
            clock.setMediator(this);

            clockThread = new Thread(clock, "Clock");
            clockThread.start();
        }
    }

    public void setRulesManager(IRulesManager rulesManager) {
        this.rulesManager = rulesManager;
    }

    public void checkWinner() {
        Player winner = rulesManager.checkWinner(lobby.round);
        if(winner != null) {
            //TODO - send popup to winner
            int loosingCorner = (winner.corner + 3) % 6;
            Player looser = null;
            for(Player p : lobby.players) {
                if(p.corner == loosingCorner) {
                    looser = p;
                    break;
                }
            }
            //TODO - send popup to looser
        }
    }
}
