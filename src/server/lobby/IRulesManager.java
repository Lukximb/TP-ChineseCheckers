package server.lobby;

import client.logic.MoveType;
import server.player.Player;
import server.board.Coordinates;

import java.util.ArrayList;

public interface IRulesManager {
    boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, MoveType moveType);

    Player checkWinner(Player player);

    Player checkLooser(Player winner);

    void setMediator(LobbyMediator mediator);
}
