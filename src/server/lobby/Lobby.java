package server.lobby;

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
