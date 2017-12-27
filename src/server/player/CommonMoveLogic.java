package server.player;

import client.logic.MoveType;
import server.board.Coordinates;

import java.util.ArrayList;

public class CommonMoveLogic {
    private MoveType moveType;
    private MoveType prevMoveType;

    public boolean checkMoveType (MoveType mType, MoveType prevMType,
                                Coordinates cCoordinates, Coordinates dCoordinates) {
        moveType = mType;
        prevMoveType = prevMType;

        int cX = cCoordinates.getX();
        int cY = cCoordinates.getY();
        int dX = dCoordinates.getX();
        int dY = dCoordinates.getY();

        if (Math.abs(cX - dX) == 0 && Math.abs(cY - dY) == 2 && moveType == MoveType.EMPTY) {
            prevMoveType = moveType;
            moveType = MoveType.SINGLE;
            return true;
        } else if (Math.abs(cX - dX) == 1 && Math.abs(cY - dY) == 1 && moveType == MoveType.EMPTY && prevMoveType != MoveType.JUMP) {
            prevMoveType = moveType;
            moveType = MoveType.SINGLE;
            return true;
        } else if (Math.abs(cX - dX) == 2 && Math.abs(cY - dY) == 2 && moveType != MoveType.SINGLE) {
            prevMoveType = moveType;
            moveType = MoveType.JUMP;
            return true;
        } else if (Math.abs(cX - dX) == 0 && Math.abs(cY - dY) == 4 && moveType != MoveType.SINGLE) {
            prevMoveType = moveType;
            moveType = MoveType.JUMP;
            return true;
        } else {
            return false;
        }
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public MoveType getPrevMoveType() {
        return prevMoveType;
    }

    public boolean checkStartPosition(ArrayList<Coordinates> pawns, int x, int y) {
        boolean correctCoordinates = false;
        for (Coordinates c : pawns) {
            if (c.getX() == x && c.getY() == y) {
                correctCoordinates = true;
                break;
            }
        }
        return correctCoordinates;
    }

    public ArrayList<Coordinates> fillPawnList(int rows, int corn) {
        ArrayList<Coordinates> pawn = new ArrayList<>();
        int n = 0, m = 0;
        if(corn == 0) {
            n = 3 * rows + 1;
            m = 2 * rows + 1;
        } else if(corn == 1) {
            n = 3 * rows;
            m = 0;
        } else if(corn == 2) {
            n = rows;
            m = 0;
        } else if(corn == 3) {
            n = rows - 1;
            m = 2 * rows + 1;
        } else if(corn == 4) {
            n = rows;
            m = 4 * rows + 2;
        } else if(corn == 5) {
            n = 3 * rows;
            m = 4 * rows + 2;
        }
        if(corn == 0 || corn == 2 || corn == 4) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    if(i%2 == 0) {
                        pawn.add(new Coordinates(n+i, m+2*j+i));
                    }
                    else {
                        pawn.add(new Coordinates(n+i, m+2*j+i));
                    }
                }
            }
        }
        else {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    if(i%2 == 0) {
                        pawn.add(new Coordinates(n-i, m+2*j+i));
                    }
                    else {
                        pawn.add(new Coordinates(n-i, m+2*j+i));
                    }
                }
            }
        }
        return pawn;
    }
}
