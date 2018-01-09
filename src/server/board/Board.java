package server.board;

import server.player.PlayerTemplate;

public class Board {
    private volatile int n;
    private volatile int m;
    public volatile Field[][] fieldList = null;

    public Board(int n, int m) {
        this.n = n;
        this.m = m;
        fieldList = new Field[n][(2*m)-1];

        fillFieldList();
    }

    /**
     * Initialize board with fields.
     */
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

    /**
     * Moves player's pawn.
     * @param player - moving player.
     * @param currentCoordinates - current coordinates of pawn.
     * @param newCoordinates - destination coordinates of pawn.
     * @return
     */
    public synchronized boolean executeMove(PlayerTemplate player, Coordinates currentCoordinates, Coordinates newCoordinates) {
        boolean isPlayerOnCurrent = getField(currentCoordinates).getPlayerOn().equals(player);
        boolean isPlayerOnDest = getField(newCoordinates).getPlayerOn() == null;
        if (isPlayerOnCurrent && isPlayerOnDest) {
            getField(currentCoordinates).setPlayerOn(null);
            getField(newCoordinates).setPlayerOn(player);
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns field with specified coordinates.
     * @param coordinates - coordinates of returned field.
     * @return field
     */
    public synchronized Field getField(Coordinates coordinates) {
        return fieldList[coordinates.getX()][coordinates.getY()];
    }

    /**
     * Returns n-size of board.
     * @return n
     */
    public synchronized int getN() {
        return n;
    }

    /**
     * Returns m-size of board.
     * @return m
     */
    public synchronized int getM() {
        return m;
    }
}
