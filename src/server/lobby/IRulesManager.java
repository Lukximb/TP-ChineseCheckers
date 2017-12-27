package server.lobby;

import client.logic.MoveType;
import server.player.Player;
import server.board.Coordinates;

public interface IRulesManager {
    boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, int pid, MoveType moveType);

    Player checkWinner();

    Player checkLooser();

    void setMediator(LobbyMediator mediator);
}
