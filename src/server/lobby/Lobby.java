package server.lobby;

import server.board.IBoard;
import server.player.Player;

import java.util.Map;

public class Lobby {
    public String name;
    public Map<Integer, Player> players;
    public IBoard board;
    public int numberOfPlayers;
    public Player round;
    public LobbyMediator mediator;
    public Chat chat;

    public Lobby(String name) {
        this.name = name;
    }

}
