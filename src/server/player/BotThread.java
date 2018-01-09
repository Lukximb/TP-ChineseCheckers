package server.player;

import client.logic.MoveType;
import server.board.Coordinates;
import server.board.Field;
import server.lobby.Lobby;
import server.neuralNetwork.NNManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BotThread implements Runnable{
    public volatile String name;
    public volatile Lobby lobby;
    public volatile int corner;

    private volatile Difficult difficultLevel;
    private volatile ArrayList<Coordinates> currentCoordinates;
    private volatile ArrayList<Coordinates> destinationCoordinates;
    private NNManager nnManager;
    private int failMoveNumber;

    private Bot bot;

    public BotThread(Bot bot) {
        nnManager = new NNManager();
        this.bot = bot;
        this.name = bot.name;
        this.lobby = bot.lobby;
        this.corner = bot.corner;
        this.currentCoordinates = bot.currentCoordinates;
        this.destinationCoordinates = bot.destinationCoordinates;
        sortDestinationCoordinates();
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (bot.run) {
            try {
                if (bot.myTurn) {
                    synchronized (bot) {
                        if (bot.myTurn) {
                            if (bot.difficultLevel == Difficult.EASY) {
                                if (checkPass()) {
                                    moveEASY();
                                }
                            } else if (bot.difficultLevel == Difficult.MEDIUM) {
                                if (checkPass()) {
                                    moveMEDIUM();
                                }
                            } else if (bot.difficultLevel == Difficult.HARD) {
                                if (checkPass()) {
                                    moveHARD();
                                }
                            }
                            bot.myTurn = false;
                            Thread.sleep(1000);
                            lobby.nextRound();
                        }
                    }
                }
                int r = rnd.nextInt(3) + 1;
                Thread.sleep(1000 * r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveHARD() {
        failMoveNumber = 0;
        ArrayList<Coordinates> coordinatesList =  new ArrayList<>();
        double bestWayScore = -10;
        for (Coordinates c : currentCoordinates) {
            if (bot.myTurn) {
                ArrayList<Coordinates> coordList;
                coordList = find(c);
                for (int k = 0; k < coordList.size() && bot.myTurn; k++) {
                    if (coordList.get(k) != null) {
                        if (k == 0 || k == 1 || k == 4 || k == 7 || k == 10 || k == 11) {
                            ArrayList<ArrayList<Coordinates>> jumpCoordList = findJump(c, 0);
                            for (ArrayList<Coordinates> array : jumpCoordList) {
                                if (bot.myTurn) {
                                    Coordinates destCoor = null;
                                    for (int q = 0; q < destinationCoordinates.size() && bot.myTurn; q++) {
                                        if (destinationCoordinates.get(q).getX() == c.getX() &&
                                                destinationCoordinates.get(q).getY() == c.getY()) {
                                            for (int w = 0; w < q; w++) {
                                                if (lobby.mediator.getField(destinationCoordinates.get(w)).getPlayerOn() == null) {
                                                    destCoor = destinationCoordinates.get(w);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (destCoor == null) {
                                        for (int m = 0; m < destinationCoordinates.size(); m++) {
                                            Coordinates newC = new Coordinates(destinationCoordinates.get(m).getX(), destinationCoordinates.get(m).getY());
                                            if (lobby.mediator.getField(newC).getPlayerOn() != bot) {
                                                destCoor = destinationCoordinates.get(m);
                                                break;
                                            }
                                        }
                                    }
                                    double startDistance = Math.sqrt(Math.pow(destCoor.getX() - c.getX(), 2)
                                            + Math.pow(destCoor.getY() - c.getY(), 2));
                                    double destinationDistance = Math.sqrt(Math.pow(destCoor.getX() - array.get(array.size() - 1).getX(), 2)
                                            + Math.pow(destCoor.getY() - array.get(array.size() - 1).getY(), 2));
                                    int minusForDestination = 0;
                                    int x = destinationCoordinates.size();
                                    for (Coordinates dest : destinationCoordinates) {
                                        if (dest.getX() == destCoor.getX() &&
                                                dest.getY() == destCoor.getY()) {
                                            minusForDestination = x;
                                            break;
                                        }
                                        x--;
                                    }
                                    if (startDistance - destinationDistance - minusForDestination >= bestWayScore) {
                                        bestWayScore = startDistance - destinationDistance;
                                        coordinatesList.clear();
                                        coordinatesList.add(c);
                                        coordinatesList.add(array.get(array.size() - 1));
                                        //System.out.println(name + " jump " + c.getX() + " " + c.getY() + " to "
                                        //        + array.get(array.size() - 1).getX() + " " + array.get(array.size() - 1).getY());
                                    }
                                }
                            }
                        } else {
                            if (bot.myTurn) {
                                Coordinates destCoor = null;
                                for (int q = 0; q < destinationCoordinates.size() && bot.myTurn; q++) {
                                    if (destinationCoordinates.get(q).getX() == c.getX() &&
                                            destinationCoordinates.get(q).getY() == c.getY()) {
                                        for (int w = 0; w < q; w++) {
                                            if (lobby.mediator.getField(destinationCoordinates.get(w)).getPlayerOn() == null) {
                                                destCoor = destinationCoordinates.get(w);
                                                break;
                                            }
                                        }

                                    }
                                }
                                if (destCoor == null) {
                                    for (int m = 0; m < destinationCoordinates.size(); m++) {
                                        Coordinates newC = new Coordinates(destinationCoordinates.get(m).getX(), destinationCoordinates.get(m).getY());
                                        if (lobby.mediator.getField(newC).getPlayerOn() != bot) {
                                            destCoor = destinationCoordinates.get(m);
                                            break;
                                        }
                                    }
                                }
                                double startDistance = Math.sqrt(Math.pow(destCoor.getX() - c.getX(), 2)
                                        + Math.pow(destCoor.getY() - c.getY(), 2));
                                double destinationDistance = Math.sqrt(Math.pow(destCoor.getX() - coordList.get(k).getX(), 2)
                                        + Math.pow(destCoor.getY() - coordList.get(k).getY(), 2));
                                int minusForDestination = 0;
                                int x = destinationCoordinates.size();
                                for (Coordinates dest : destinationCoordinates) {
                                    if (dest.getX() == destCoor.getX() &&
                                            dest.getY() == destCoor.getY()) {
                                        minusForDestination = x;
                                        break;
                                    }
                                    x--;
                                }
                                if (startDistance - destinationDistance - minusForDestination >= bestWayScore) {
                                    bestWayScore = startDistance - destinationDistance;
                                    coordinatesList.clear();
                                    coordinatesList.add(c);
                                    coordinatesList.add(coordList.get(k));
                                    //System.out.println(name + " single " + c.getX() + " " + c.getY() + " to "
                                    //       + coordList.get(k).getX() + " " + coordList.get(k).getY());
                                }
                            }
                        }
                    }
                }
            }
        }
        if (bot.myTurn) {
            try {
                Thread.sleep(500);
                executeMove(coordinatesList);
                bot.myTurn = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveMEDIUM() {
        failMoveNumber = 0;
        ArrayList<Coordinates> coordinatesList =  new ArrayList<>();
        double bestWayScore = -10;
        for (Coordinates c : currentCoordinates) {
            if (bot.myTurn) {
                ArrayList<Coordinates> coordList;
                coordList = find(c);
                for (int k = 0; k < coordList.size() && bot.myTurn; k++) {
                    if (coordList.get(k) != null) {
                        if (k == 0 || k == 1 || k == 4 || k == 7 || k == 10 || k == 11){
                            if (bot.myTurn) {
                                Coordinates destCoor = null;
                                for (int q = 0; q < destinationCoordinates.size() && bot.myTurn; q++) {
                                    if (destinationCoordinates.get(q).getX() == c.getX() &&
                                            destinationCoordinates.get(q).getY() == c.getY()) {
                                        for (int w = 0; w < q; w++) {
                                            if (lobby.mediator.getField(destinationCoordinates.get(w)).getPlayerOn() == null) {
                                                destCoor = destinationCoordinates.get(w);
                                                break;
                                            }
                                        }

                                    }
                                }
                                if (destCoor == null) {
                                    for (int m = 0; m < destinationCoordinates.size(); m++) {
                                        Coordinates newC = new Coordinates(destinationCoordinates.get(m).getX(), destinationCoordinates.get(m).getY());
                                        if (lobby.mediator.getField(newC).getPlayerOn() != bot) {
                                            destCoor = destinationCoordinates.get(m);
                                            break;
                                        }
                                    }
                                }
                                double startDistance = Math.sqrt(Math.pow(destCoor.getX() - c.getX(), 2)
                                        + Math.pow(destCoor.getY() - c.getY(), 2));
                                double destinationDistance = Math.sqrt(Math.pow(destCoor.getX() - coordList.get(k).getX(), 2)
                                        + Math.pow(destCoor.getY() - coordList.get(k).getY(), 2));
                                if (startDistance - destinationDistance >= bestWayScore) {
                                    bestWayScore = startDistance - destinationDistance;
                                    coordinatesList.clear();
                                    coordinatesList.add(c);
                                    coordinatesList.add(coordList.get(k));
                                }
                            }
                        } else {
                            if (bot.myTurn) {
                                Coordinates destCoor = null;
                                for (int q = 0; q < destinationCoordinates.size() && bot.myTurn; q++) {
                                    if (destinationCoordinates.get(q).getX() == c.getX() &&
                                            destinationCoordinates.get(q).getY() == c.getY()) {
                                        for (int w = 0; w < q; w++) {
                                            if (lobby.mediator.getField(destinationCoordinates.get(w)).getPlayerOn() == null) {
                                                destCoor = destinationCoordinates.get(w);
                                                break;
                                            }
                                        }

                                    }
                                }
                                if (destCoor == null) {
                                    for (int m = 0; m < destinationCoordinates.size(); m++) {
                                        Coordinates newC = new Coordinates(destinationCoordinates.get(m).getX(), destinationCoordinates.get(m).getY());
                                        if (lobby.mediator.getField(newC).getPlayerOn() != bot) {
                                            destCoor = destinationCoordinates.get(m);
                                            break;
                                        }
                                    }
                                }
                                double startDistance = Math.sqrt(Math.pow(destCoor.getX() - c.getX(), 2)
                                        + Math.pow(destCoor.getY() - c.getY(), 2));
                                double destinationDistance = Math.sqrt(Math.pow(destCoor.getX() - coordList.get(k).getX(), 2)
                                        + Math.pow(destCoor.getY() - coordList.get(k).getY(), 2));
                                if (startDistance - destinationDistance > bestWayScore) {
                                    bestWayScore = startDistance - destinationDistance;
                                    coordinatesList.clear();
                                    coordinatesList.add(c);
                                    coordinatesList.add(coordList.get(k));
                                }
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
        if (bot.myTurn) {
            try {
                Thread.sleep(500);
                executeMove(coordinatesList);
                bot.myTurn = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveEASY() {
        failMoveNumber = 0;
        ArrayList<Coordinates> coordinatesList =  new ArrayList<>();
        double bestWayScore = -10;
        for (Coordinates c : currentCoordinates) {
            if (bot.myTurn) {
                ArrayList<Coordinates> coordList;
                coordList = find(c);
                for (int k = 0; k < coordList.size() && bot.myTurn; k++) {
                    if (coordList.get(k) != null) {
                        if (k == 0 || k == 1 || k == 4 || k == 7  ||  k == 10 || k == 11){
                            continue;
                        } else {
                            if (bot.myTurn) {
                                Coordinates destCoor = null;
                                for (int q = 0; q < destinationCoordinates.size() && bot.myTurn; q++) {
                                    if (destinationCoordinates.get(q).getX() == c.getX() &&
                                            destinationCoordinates.get(q).getY() == c.getY()) {
                                        for (int w = 0; w < q; w++) {
                                            if (lobby.mediator.getField(destinationCoordinates.get(w)).getPlayerOn() == null) {
                                                destCoor = destinationCoordinates.get(w);
                                                break;
                                            }
                                        }

                                    }
                                }
                                if (destCoor == null) {
                                    for (int m = 0; m < destinationCoordinates.size(); m++) {
                                        Coordinates newC = new Coordinates(destinationCoordinates.get(m).getX(), destinationCoordinates.get(m).getY());
                                        if (lobby.mediator.getField(newC).getPlayerOn() != bot) {
                                            destCoor = destinationCoordinates.get(m);
                                            break;
                                        }
                                    }
                                }
                                double startDistance = Math.sqrt(Math.pow(destCoor.getX() - c.getX(), 2)
                                        + Math.pow(destCoor.getY() - c.getY(), 2));
                                double destinationDistance = Math.sqrt(Math.pow(destCoor.getX() - coordList.get(k).getX(), 2)
                                        + Math.pow(destCoor.getY() - coordList.get(k).getY(), 2));
                                if (startDistance - destinationDistance >= bestWayScore) {
                                    bestWayScore = startDistance - destinationDistance;
                                    coordinatesList.clear();
                                    coordinatesList.add(c);
                                    coordinatesList.add(coordList.get(k));
                                }
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
        if (bot.myTurn) {
            try {
                Thread.sleep(400);
                executeMove(coordinatesList);
                bot.myTurn = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeMove(ArrayList<Coordinates> coordinatesList) {
        if (coordinatesList.size() == 2) {
            if (coordinatesList.get(0) != null && coordinatesList.get(1) != null) {
                System.out.println("move from " +name);
                if (lobby.mediator.move(bot, coordinatesList.get(0), coordinatesList.get(1))) {
                    changePawns(coordinatesList.get(0), coordinatesList.get(1));
                    lobby.sendMoveNotification("E," + corner + ","
                            + coordinatesList.get(0).getX() + "," + coordinatesList.get(0).getY() + ","
                            + coordinatesList.get(coordinatesList.size() - 1).getX() + "," + coordinatesList.get(coordinatesList.size() - 1).getY());


                }
            }
        }
    }

    private void changePawns(Coordinates current, Coordinates destination) {
        int i = 0;
        for (Coordinates c : currentCoordinates) {
            if (c.getX() == current.getX() && c.getY() == current.getY()) {
                currentCoordinates.remove(i);
                currentCoordinates.add(destination);
                break;
            }
            i++;
        }
    }

    public ArrayList<Coordinates> find(Coordinates c) {
        int boardN = lobby.board.getN();
        int boardM = (2 * lobby.board.getM()) - 1;
        ArrayList<Coordinates> coordinatesList =  new ArrayList<>();
        ArrayList<Double> rawoutput;
        ArrayList<Double> data;
        data = getData(c);
        rawoutput = nnManager.getOutput(data);
        int i = 0;
        for (Double d : rawoutput) {
            if (!bot.myTurn) {
                for (; i < 12; i++) {
                    coordinatesList.add(null);
                }
                break;
            }
            if (d > 0.5) {
                Coordinates cD = getCoordinates(i, c);
                if (cD.getX() < 0 || cD.getY() < 0 || cD.getX() >= boardN || cD.getY() >= boardM) {
                    cD = null;
                }
                if (i == 0 || i == 1 || i == 4 || i == 7 || i == 10 || i == 11) {
                    if (cD != null && checkMove(c, cD, MoveType.JUMP)) {
                        coordinatesList.add(cD);
                    } else {
                        coordinatesList.add(null);
                        failMoveNumber++;
                    }
                } else {
                    if (cD != null && checkMove(c, cD, MoveType.SINGLE)) {
                        coordinatesList.add(cD);
                    } else {
                        coordinatesList.add(null);
                        failMoveNumber++;
                    }
                }
                //checkFailNumber();
            } else {
                coordinatesList.add(null);
            }
            i++;
        }
        return coordinatesList;
    }

    public ArrayList<ArrayList<Coordinates>> findJump(Coordinates c, int invocationNumber) {
        int boardN = lobby.board.getN();
        int boardM = (2 * lobby.board.getM()) - 1;
        ArrayList<ArrayList<Coordinates>> coordinatesList = new ArrayList<>();
        ArrayList<Double> rawoutput;
        ArrayList<Double> data;
        data = getData(c);
        rawoutput = nnManager.getOutput(data);
        int numberOfZero = 0;
        Coordinates[] index = new Coordinates[12];
        Arrays.fill(index, null);
        int i = 0;
        for (Double d : rawoutput) {
            if (d > 0.5) {
                Coordinates cD = getCoordinates(i, c);
                if (!bot.myTurn) {
                    break;
                }
                if (cD.getX() < 0 || cD.getY() < 0 || cD.getX() >= boardN || cD.getY() >= boardM) {
                    cD = null;
                }
                if (i == 0 || i == 1 || i == 4 || i == 7 || i == 10 || i == 11) {
                    if (cD != null && checkMove(c, cD, MoveType.JUMP)) {
                        ArrayList<Coordinates> tmpCoordList = new ArrayList<>();
                        tmpCoordList.add(c);
                        tmpCoordList.add(cD);
                        coordinatesList.add(tmpCoordList);
                        index[i] = cD;
                    } else {
                        numberOfZero++;
                        failMoveNumber++;
                    }
                }
                //checkFailNumber();
            } else {
                numberOfZero++;
            }
            i++;
        }
        if (numberOfZero == 12 || invocationNumber == 10 || !bot.myTurn) {
            return coordinatesList;
        } else {
            int l = 0;
            for (int k = 0;k < 12; k++) {
                if (index[k] != null) {
                    ArrayList<ArrayList<Coordinates>> tmpArray = findJump(index[k], invocationNumber + 1);
                    for (ArrayList<Coordinates> cList : tmpArray) {
                        coordinatesList.get(l).add(cList.get(1));
                    }
                    l++;
                }
            }
            return coordinatesList;
        }
    }

    private ArrayList<Double> getData(Coordinates c) {
        int n = lobby.board.getN();
        int m = lobby.board.getM();
        int x = c.getX();
        int y = c.getY();
        ArrayList<Double> data = new ArrayList<>();

        if (x - 2 < 0 || y - 2 < 0)
            data.add(1.0);
        else {
            checkField(data, x-2, y-2);
        }

        if (x - 2 < 0 || y + 2 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x-2, y+2);
        }

        if (x - 1 < 0 || y - 1 < 0)
            data.add(1.0);
        else {
            checkField(data, x-1, y-1);
        }

        if (x - 1 < 0 || y + 1 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x-1, y+1);
        }

        if (y - 4 < 0)
            data.add(1.0);
        else {
            checkField(data, x, y-4);
        }

        if (y - 2 < 0)
            data.add(1.0);
        else {
            checkField(data, x, y-2);
        }

        if (y + 2 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x, y+2);
        }

        if (y + 4 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x, y+4);
        }

        if (x + 1 >= n || y - 1 < 0)
            data.add(1.0);
        else {
            checkField(data, x+1, y-1);
        }

        if (x + 1 >= n || y + 1 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x+1, y+1);
        }

        if (x + 2 >= n || y - 2 < 0)
            data.add(1.0);
        else {
            checkField(data, x+2, y-2);
        }

        if (x + 2 >= n || y + 2 >= (2*m)-1)
            data.add(1.0);
        else {
            checkField(data, x+2, y+2);
        }

        return data;
    }

    private Coordinates getCoordinates(int i, Coordinates c) {
        int x = c.getX();
        int y = c.getY();
        if (i == 0) {
            return new Coordinates(x - 2, y - 2);
        } else if (i == 1) {
            return new Coordinates(x - 2,y + 2);
        } else if (i == 2) {
            return new Coordinates(x - 1,y - 1);
        } else if (i == 3) {
            return new Coordinates(x - 1,y + 1);
        } else if (i == 4) {
            return new Coordinates(x, y - 4);
        } else if (i == 5) {
            return new Coordinates(x, y - 2);
        } else if (i == 6) {
            return new Coordinates(x, y + 2);
        } else if (i == 7) {
            return new Coordinates(x, y + 4);
        } else if (i == 8) {
            return new Coordinates(x + 1, y - 1);
        } else if (i == 9) {
            return new Coordinates(x + 1, y + 1);
        } else if (i == 10) {
            return new Coordinates(x + 2, y - 2);
        } else {
            return new Coordinates(x + 2, y + 2);
        }
    }

    private ArrayList<Double> checkField(ArrayList<Double> data, int x, int y) {
        Coordinates newC = new Coordinates(x, y);
        Field field = lobby.mediator.getField(newC);
        if (field == null) {
            data.add(1.0);
        } else if (lobby.mediator.getField(newC).getPlayerOn() == null)
            data.add(0.0);
        else
            data.add(1.0);
        return data;
    }

    private boolean checkPass() {
        Random rnd = new Random();
        if (bot.difficultLevel == Difficult.EASY) {
            return (rnd.nextDouble() > 0.2);
        } else if (bot.difficultLevel == Difficult.MEDIUM) {
            return (rnd.nextDouble() > 0.1);
        } else if (bot.difficultLevel == Difficult.HARD) {
            return (rnd.nextDouble() > 0.001);
        }
        return true;
    }

//    private void checkFailNumber() {
//        if (difficultLevel == Difficult.EASY && failMoveNumber == 5) {
//            //myTurn = false;
//            System.out.println("bot " + name + " has max fail number, pass");
//            //lobby.nextRound();
//        } else if (difficultLevel == Difficult.MEDIUM && failMoveNumber == 10) {
//            //myTurn = false;
//            System.out.println("bot " + name + " has max fail number, pass");
//            //lobby.nextRound();
//        } else if (difficultLevel == Difficult.HARD && failMoveNumber == 15) {
//            //myTurn = false;
//            System.out.println("bot " + name + " has max fail number, pass");
//            //lobby.nextRound();
//        }
//    }

    //@Override
    public void sortDestinationCoordinates() {
        ArrayList<Coordinates> newDestCoordinates = new ArrayList<>();
        while (!destinationCoordinates.isEmpty()) {
            double maxDistance = 0;
            Coordinates toRemove = null;
            for (Coordinates c : destinationCoordinates) {
                double destinationDistance = Math.sqrt(Math.pow(c.getX() - currentCoordinates.get(currentCoordinates.size() - 1).getX(), 2)
                        + Math.pow(c.getY() - currentCoordinates.get(currentCoordinates.size() - 1).getY(), 2));
                if (destinationDistance > maxDistance) {
                    maxDistance = destinationDistance;
                    toRemove = c;
                }
            }
            destinationCoordinates.remove(toRemove);
            newDestCoordinates.add(toRemove);
        }
        destinationCoordinates = newDestCoordinates;
        bot.destinationCoordinates = destinationCoordinates;
    }

    public synchronized boolean checkMove(Coordinates currentCoordinates, Coordinates destinationCoordinates, MoveType moveType) {
        return lobby.mediator.checkMove(currentCoordinates, destinationCoordinates, moveType);
    }
}
