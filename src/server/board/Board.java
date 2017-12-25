package server.board;

import jmx.Player;

public class Board implements IBoard {
    private int n;
    private int m;
    public Field[][] fieldList = null;

    public Board(int n, int m) {
        this.n = n;
        this.m = m;
        fieldList = new Field[n][(2*m)-1];

        fillFieldList();
    }

    public void fillFieldList() {
        int x=n-m;
        for(int i=x; i<n; i++) {
            for(int j=0; j<2*m-1; j++) {
                if(j>=i-x && j<2*m-i+x) {
                    if(i % 2 == 0) {
                        if(j%2 == 0) {
                            fieldList[i][j] = new Field(new Coordinates(i, j));
                        }
                    }
                    else {
                        if(j%2 == 1) {
                            fieldList[i][j] = new Field(new Coordinates(i, j));
                        }
                    }
                }
            }
        }
        for(int i=n-x-1; i>=0; i--) {
            for(int j=0; j<2*m-1; j++) {
                if(j>=n-x-i-1 && j<2*m-n+x+i) {
                    if(i%2 == 0) {
                        if(j%2 == 0) {
                            if(fieldList[i][j] == null) {
                                fieldList[i][j] = new Field(new Coordinates(i, j));
                            }
                        }
                    }
                    else {
                        if(j%2 == 1) {
                            if(fieldList[i][j] == null) {
                                fieldList[i][j] = new Field(new Coordinates(i, j));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean executeMove(Player player, Coordinates currentCoordinates, Coordinates newCoordinates) {
        boolean isPlayerOnCurrent = getField(currentCoordinates).getPlayerOn().equals(player);
        boolean isPlayerOnDest = getField(newCoordinates).getPlayerOn() == null;
        System.out.print("");
        if (isPlayerOnCurrent && isPlayerOnDest) {
            getField(currentCoordinates).setPlayerOn(null);
            getField(newCoordinates).setPlayerOn(player);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Field getField(Coordinates coordinates) {
        return fieldList[coordinates.getX()][coordinates.getY()];
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }
}
