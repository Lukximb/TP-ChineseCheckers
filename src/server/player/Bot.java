package server.player;

import server.board.Coordinates;

public class Bot implements IBot{
    private Difficult difficultLevel = Difficult.MEDIUM;

    public Bot() {

    }

    @Override
    public void move(Coordinates coordinates) {

    }

    @Override
    public Coordinates getCurrentPosition() {
        return null;
    }

    @Override
    public void pass() {

    }

    @Override
    public void surrender() {

    }
}
