package server.lobby;

import server.board.IBoard;
import server.player.Bot;
import server.player.Player;

import java.util.HashMap;
import java.util.Map;

public class Lobby {
    public String name;
    public Player admin;
    public Map<Integer, Player> players;
    public IBoard board;
    public int numberOfPlayers;
    public Player round;
    public LobbyMediator mediator;
    public Chat chat;

    public Lobby(String name, Player admin) {
        this.name = name;
        this.admin = admin;
        players = new HashMap<>();
    }

    public void startGame() {

    }

    public void endGame() {

    }

    public void addPlayer(Player player, int corner) {

    }

    public void addBot(Bot bot) {

    }

    public void removePlayer(Player player) {

    }

    public void removeBot(Bot bot) {

    }

    public void getRoundTime() {

    }

    public void nextRound() {

    }

    public void printMessage(Player player, String message) {
        chat.printMessage(player, message);
    }
}
