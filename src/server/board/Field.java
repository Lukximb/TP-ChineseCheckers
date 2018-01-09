package server.board;

import server.player.PlayerTemplate;

public class Field {
    private Coordinates coordinates;
    private PlayerTemplate playerOn = null;
    private PlayerTemplate owner = null;
    private int corner;

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

    public PlayerTemplate getOwner() {
        return owner;
    }

    public void setowner(PlayerTemplate owner) {
        this.owner = owner;
    }

    public void setCorner(int c) {
        this.corner = c;
    }

    public int getCorner() {
        return corner;
    }

}
