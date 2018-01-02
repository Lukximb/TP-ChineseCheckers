package server.lobby;

import client.logic.MoveType;
import server.board.Board;
import server.board.Coordinates;
import server.board.Field;
import server.manager.PlayerManager;
import server.player.Player;
import server.player.PlayerTemplate;

public class LobbyMediator {
//    private volatile Clock clock;
    private volatile Thread clockThread;
    private IRulesManager rulesManager;
    private Board board;
    private Lobby lobby;

    public LobbyMediator() {
    }

//    public synchronized long getRoundTime() {
//        return 30;
//        //return clock.getRoundTime();
//    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public synchronized void startRound() {
        lobby.sendTurnNotification();
       // clock.startRound();
    }

    public Field getField(Coordinates coordinates) {
        Field field = board.getField(coordinates);
        return field;
    }

//    public synchronized void endRound() {
//        //clock.endRound();
//    }

    public synchronized boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, MoveType moveType) {
        return rulesManager.checkMove(currentCoordinates, newCoordinates, moveType);
    }

    public synchronized boolean move(PlayerTemplate player, Coordinates currentCoordinates, Coordinates newCoordinates) {
        return board.executeMove(player, currentCoordinates, newCoordinates);
    }

//    public synchronized void endOfTime() {
//        lobby.nextRound();
//    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

//    public synchronized void setClock(Clock clock) {
//        if(this.clock == null) {
//            this.clock = clock;
//            clock.setMediator(this);
//
//            clockThread = new Thread(clock, "Clock");
//            clockThread.start();
//        }
//    }

    public void setRulesManager(IRulesManager rulesManager) {
        this.rulesManager = rulesManager;
    }

    public void checkWinner() {
        PlayerTemplate winner = rulesManager.checkWinner(lobby.round);
        if(winner != null) {
            int loosingCorner = (winner.getCorner() + 3) % 6;
            PlayerTemplate looser = null;
            for(PlayerTemplate p : lobby.players) {
                if(p.getCorner() == loosingCorner) {
                    looser = p;
                    break;
                }
            }

            for(int i=0; i<lobby.numberOfPlayers; i++) {
                if(lobby.players[i].equals(winner) || lobby.players[i].equals(looser)) {
                    lobby.players[i].setLobby(null);
                    lobby.players[i] = null;
                }
            }

            lobby.sendWinnerNotification(winner, looser);
            lobby.sendLooserNotification(looser, winner);

            PlayerManager playerManager = PlayerManager.getInstance();
            if(winner instanceof Player) {
                playerManager.movePlayerToFreeList((Player)winner);
            }
            if(looser instanceof Player) {
                playerManager.movePlayerToFreeList((Player)looser);
            }

            lobby.sendWinnerPopUpNotification(winner, looser);

            if(lobby.isEmpty()) {
                lobby.lobbyManager.removeLobby(lobby);
            }
        }
    }
}
