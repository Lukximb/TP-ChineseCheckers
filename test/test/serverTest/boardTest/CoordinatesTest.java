package test.serverTest.boardTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.board.Coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinatesTest {
    Coordinates coordinate;

    @BeforeEach
    void setUp() {
        coordinate = new Coordinates(3,5);
    }

    @Test
    void getXTest() {
        assertEquals(3, coordinate.getX());
    }

    @Test
    void getYTest() {
        assertEquals(5, coordinate.getY());
    }

    @Test
    void setXTest() {
        coordinate.setX(10);
        assertEquals(10, coordinate.getX());
    }

    @Test
    void setYTest() {
        coordinate.setY(10);
        assertEquals(10, coordinate.getY());
    }

}