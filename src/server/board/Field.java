package server.board;

import jmx.Player;

public class Field {
    private Coordinates coordinates;
    private Player playerOn = null;

    public Field(Coordinates coordinates) {
        this.coordinates = coordinates;
        playerOn = null;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Player getPlayerOn() {
        return playerOn;
    }

    public void setPlayerOn(Player playerOn) {
        this.playerOn = playerOn;
    }
}
