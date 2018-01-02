package test.serverTest.boardTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Coordinates;
import server.board.Field;
import server.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldTest {
    Field field;
    Coordinates coordinate;

    @BeforeEach
    void setUp() {
        coordinate = new Coordinates(3,5);
        field = new Field(coordinate);
    }

    @Test
    void getCoordinatesTest() {
        Coordinates c = field.getCoordinates();
        assertTrue(c.getX() == coordinate.getX() && c.getY() == coordinate.getY());
    }

    @Test
    void setCoordinatesTest() {
        Coordinates c = new Coordinates(12, 15);
        field.setCoordinates(c);
        assertTrue(c.getX() == field.getCoordinates().getX() && c.getY() == field.getCoordinates().getY());
    }

    @Test
    void getPlayerOnTest() {
        assertTrue(null == field.getPlayerOn());
    }

    @Test
    void setPlayerOnTest() {
        Player player = new Player(123, "test");
        field.setPlayerOn(player);
        assertEquals(player, field.getPlayerOn());
    }

}