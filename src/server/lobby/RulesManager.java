package server.lobby;

import client.logic.MoveType;
import server.board.Field;
import server.player.Player;
import server.board.Coordinates;
import server.player.PlayerTemplate;

import java.util.ArrayList;

public class RulesManager implements IRulesManager {
    private LobbyMediator mediator;

    @Override
    public boolean checkMove(Coordinates currentCoordinates, Coordinates newCoordinates, MoveType moveType) {
        if (moveType == MoveType.SINGLE) {
            //System.out.println("Check move for single");
            Field field = mediator.getField(newCoordinates);
            if (field != null && field.getPlayerOn() == null) {
                return true;
            } else {
                return false;
            }
        } else {
            int cX = currentCoordinates.getX();
            int cY = currentCoordinates.getY();
            int dX = newCoordinates.getX();
            int dY = newCoordinates.getY();
            boolean emptyDestination = false;
            boolean correctMove = false;
            //System.out.println("Check move for jump");
            Field field = mediator.getField(newCoordinates);
            if (field != null && field.getPlayerOn() == null) {
                emptyDestination = true;
            } else {
                emptyDestination = false;
            }

            if (Math.abs(cX - dX) == 2 && Math.abs(cY - dY) == 2 && emptyDestination) {
                if (cX < dX && cY < dY) {
                    field = mediator.getField(new Coordinates(dX-1, dY-1));
                    if (field != null && field.getPlayerOn() != null) {
                        correctMove = true;
                    }
                } else if (cX < dX && cY > dY) {
                    field = mediator.getField(new Coordinates(dX-1, dY+1));
                    if (field != null && field.getPlayerOn() != null) {
                        correctMove = true;
                    }
                } else if (cX > dX && cY < dY) {
                    field = mediator.getField(new Coordinates(dX+1, dY-1));
                    if (field != null && field.getPlayerOn() != null) {
                        correctMove = true;
                    }
                } else if (cX > dX && cY > dY) {
                    field = mediator.getField(new Coordinates(dX+1, dY+1));
                    if (field != null && field.getPlayerOn() != null) {
                        correctMove = true;
                    }
                }
            } else if (Math.abs(cX - dX) == 0 && Math.abs(cY - dY) == 4 && emptyDestination) {
                if (cY < dY) {
                    field = mediator.getField(new Coordinates(dX, dY-2));
                    if (field != null && field.getPlayerOn() != null) {
                        correctMove = true;
                    }
                } else {
                    field = mediator.getField(new Coordinates(dX, dY+2));
                    if (field != null && field.getPlayerOn() != null) {
                        correctMove = true;
                    }
                }
            }
            return correctMove;
        }
    }

    @Override
    public PlayerTemplate checkWinner(PlayerTemplate player) {
        for(Coordinates c : player.currentCoordinates) {
            if(!(player.destinationCoordinates.contains(c))) {
                return null;
            }
        }
        return player;
    }

    @Override
    public PlayerTemplate checkLooser(PlayerTemplate winner) {

        return null;
    }

    public void setMediator(LobbyMediator mediator) {
        this.mediator = mediator;
    }
}
