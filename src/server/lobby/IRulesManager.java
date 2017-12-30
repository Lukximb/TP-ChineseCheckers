package server.lobby;

import client.logic.MoveType;
import server.player.Player;
import server.board.Coordinates;
import server.player.PlayerTemplate;

import java.util.ArrayList;

public interface IRulesManager {
    boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, MoveType moveType);

    PlayerTemplate checkWinner(PlayerTemplate player);

    PlayerTemplate checkLooser(PlayerTemplate winner);

    void setMediator(LobbyMediator mediator);
}
