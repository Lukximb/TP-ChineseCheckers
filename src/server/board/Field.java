package server.board;

import server.player.Player;
import server.player.PlayerTemplate;

public class Field {
    private Coordinates coordinates;
    private PlayerTemplate playerOn = null;

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

    public PlayerTemplate getPlayerOn() {
        return playerOn;
    }

    public void setPlayerOn(PlayerTemplate playerOn) {
        this.playerOn = playerOn;
    }

}
