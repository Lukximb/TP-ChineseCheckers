package server.player;

import server.board.Coordinates;

public interface IBot {
    Difficult DIFFICULT_LEVEL = Difficult.MEDIUM;

    void move(Coordinates coordinates);

    Coordinates getCurrentPosition();

    void pass();

    void surrender();
}
