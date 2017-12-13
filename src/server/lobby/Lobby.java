package server.lobby;

import server.board.Coordinates;
import server.board.IBoard;
import server.player.Bot;
import server.player.Player;

import java.util.HashMap;
import java.util.Map;

public class Lobby implements Runnable{
    public String name;
    public Player admin;
    public Player[] players;
    public IBoard board;
    public int numberOfPlayers;
    public Player round;
    public int roundCorner;
    public LobbyMediator mediator;
    public Chat chat;

    public Lobby(int playerNum, int rowNumber, String lobbyName, Player admin) {
        numberOfPlayers = playerNum;
        this.name = lobbyName;
        this.admin = admin;
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        addPlayer(admin, 0);
    }

    @Override
    public void run() {
        while(true) {
            /*System.out.println("Watek "+id);
            try {
                //usypiamy wątek na 100 milisekund
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public void startGame() {
        mediator = new LobbyMediator(this);
        initPlayersOnBoard();
        round = players[0];
        roundCorner = 0;
    }

    public void endGame() {

    }

    public void addPlayer(Player player, int corner) {
        if(corner < numberOfPlayers) {
            players[corner] = player;
        }
    }

    public void addBot(Bot bot) {

    }

    public void removePlayer(Player player) {
        int i = 0;
        int next = 0;
        while(i<numberOfPlayers) {
            if(players[i].equals(player)) {
                players[i] = null;
                next = i+1;
            }
        }
        while(next < numberOfPlayers || players[next] != null) {
            players[next-1] = players[next];
            players[next] = null;
            next++;
        }
    }

    public void removeBot(Bot bot) {

    }

    public void initPlayersOnBoard() {

        for(int i=0; i<numberOfPlayers; i++) {
            if(numberOfPlayers == 6) {
//                players[i].setColor();
                putPawnsOnBoard(players[i], i);
            } else if(numberOfPlayers == 4) {
                if(i%2 == 0) {
                    putPawnsOnBoard(players[i], i*2);
                }
                else {
                    putPawnsOnBoard(players[i], i*2-1);
                }
            } else if(numberOfPlayers == 2) {
                putPawnsOnBoard(players[i], i*3);
            }
        }
    }

    public void putPawnsOnBoard(Player player, int corner) {
        int rows = board.n - board.m;
        int n, m;
        if(corner == 0) {
            n = 3 * rows + 1;
            m = 2 * rows + 1;
        } else if(corner == 1) {
            n = 3 * rows;
            m = 0;
        } else if(corner == 2) {
            n = rows;
            m = 0;
        } else if(corner == 3) {
            n = rows - 1;
            m = 2 * rows + 1;
        } else if(corner == 4) {
            n = rows;
            m = 4 * rows + 2;
        } else if(corner == 5) {
            n = 3 * rows;
            m = 4 * rows + 2;
        } else {
            return;
        }
        if(corner == 0 || corner == 2 || corner == 4) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows; j++) {
                    if(i%2 == 0) {
                        board.getField(new Coordinates(n+i, m+2*j)).setPlayerOn(player);
                    }
                    else {
                        board.getField(new Coordinates(n+i, m+2*j-1)).setPlayerOn(player);
                    }
                }
            }
        }
        else {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows; j++) {
                    if(i%2 == 0) {
                        board.getField(new Coordinates(n-i, m+2*j)).setPlayerOn(player);
                    }
                    else {
                        board.getField(new Coordinates(n-i, m+2*j-1)).setPlayerOn(player);
                    }
                }
            }
        }
    }

    public void getRoundTime() {
        mediator.getRoundTime();
    }

    public void nextRound() {
        roundCorner++;
        if(roundCorner == numberOfPlayers) {
            roundCorner = 0;
        }
        round = players[roundCorner];
        mediator.startRound();

    }

    public void printMessage(Player player, String message) {
        chat.printMessage(player, message);
    }
}
