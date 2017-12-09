package server.player;

import server.board.Coordinates;

public interface IBot {
    Dificult dificultLevel = Dificult.MEDIUM;

    void move(Coordinates coordinates);

    Coordinates getCurrentPosition();

    void pass();

    void surrender();
}
