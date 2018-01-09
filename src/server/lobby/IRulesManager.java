package server.lobby;

import client.logic.MoveType;
import server.board.Coordinates;
import server.player.PlayerTemplate;

public interface IRulesManager {
    boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, MoveType moveType);

    /**
     * Checks if player wins.
     * @param player - player to check
     * @return winner or null
     */
    PlayerTemplate checkWinner(PlayerTemplate player);

    /**
     * Finds winner's opponent.
     * @param winner
     * @return looser
     */
    PlayerTemplate checkLooser(PlayerTemplate winner);

    /**
     * Sets LobbyMediator.
     * @param mediator - new mediator
     */
    void setMediator(LobbyMediator mediator);
}
