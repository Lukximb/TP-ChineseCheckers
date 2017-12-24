package client.logic;

import client.core.ClientGUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import server.board.Coordinates;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardUpdate {
    private ClientGUIController controller;
    private ClientGUI client;
    private MoveType moveType;
    private MoveType prevMoveType;
    private boolean correctMove;
    private ArrayList<Coordinates> pawns;
    private ArrayList<Integer> enemyCorner;
    private int corner = 0;
    private int numberOfPlayers;
    private Image img;

    public BoardUpdate(ClientGUIController controller, ClientGUI client) {
        this.controller = controller;
        this.client = client;
        moveType = MoveType.EMPTY;
        prevMoveType = MoveType.EMPTY;
        correctMove = false;
        pawns = new ArrayList<>();
        enemyCorner = new ArrayList<>();
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    private void setEnemyCorner() {
        if(numberOfPlayers == 2) {
            enemyCorner.add(0);
            enemyCorner.add(3);
            int index = 0;
            for (int i: enemyCorner) {
                if (i == corner) {
                    enemyCorner.remove(index);
                    break;
                }
                index++;
            }
        } else if(numberOfPlayers == 3) {
            enemyCorner.add(0);
            enemyCorner.add(2);
            enemyCorner.add(4);
            int index = 0;
            for (int i: enemyCorner) {
                if (i == corner) {
                    enemyCorner.remove(index);
                    break;
                }
                index++;
            }
        } else if(numberOfPlayers == 4) {
            enemyCorner.add(0);
            enemyCorner.add(1);
            enemyCorner.add(3);
            enemyCorner.add(4);
            int index = 0;
            for (int i: enemyCorner) {
                if (i == corner) {
                    enemyCorner.remove(index);
                    break;
                }
                index++;
            }
        } else if(numberOfPlayers == 6) {
            for (int i = 0; i < 6; i++) {
                if ( i != corner) {
                    enemyCorner.add(i);
                }
            }
        }
    }

    public void createPawnList(int rows, int numberOfPlayers) {
        this.numberOfPlayers  = numberOfPlayers;
        int n = 0, m = 0;
        if(corner == 0) {
            n = 3 * rows + 1;
            m = 2 * rows + 1;
        } else if(corner == 1) {
            n = 3 * rows;
            m = 0;
        } else if(corner == 2) {
            n = rows;
            m = 0;
        } else if(corner == 3) {
            n = rows - 1;
            m = 2 * rows + 1;
        } else if(corner == 4) {
            n = rows;
            m = 4 * rows + 2;
        } else if(corner == 5) {
            n = 3 * rows;
            m = 4 * rows + 2;
        }
        if(corner == 0 || corner == 2 || corner == 4) {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    if(i%2 == 0) {
                        pawns.add(new Coordinates(n+i, m+2*j+i));
                    }
                    else {
                        pawns.add(new Coordinates(n+i, m+2*j+i));
                    }
                }
            }
        }
        else {
            for(int i=0; i<rows; i++) {
                for(int j=0; j<rows-i; j++) {
                    if(i%2 == 0) {
                        pawns.add(new Coordinates(n-i, m+2*j+i));
                    }
                    else {
                        pawns.add(new Coordinates(n-i, m+2*j+i));
                    }
                }
            }
        }

        setPawnColor();
        drawPawns();
        setEnemyCorner();
    }

    private void setPawnColor() {
        if (corner == 0) {
            img = new Image("/client/pawnPink.png");
        } else if (corner == 1) {
            img = new Image("/client/pawnYellow.png");
        } else if (corner == 2) {
            img = new Image("/client/pawnBlue.png");
        } else if (corner == 3) {
            img = new Image("/client/pawnGreen.png");
        } else if (corner == 4) {
            img = new Image("/client/pawnRed.png");
        } else if (corner == 5) {
            img = new Image("/client/pawnLightBlue.png");
        }
    }

    private void drawPawns() {
        ObservableList<Node> childrens = controller.board.getChildren();
        int i = 0;
        while (i < pawns.size()) {
            for (Node node : childrens) {
                if(GridPane.getRowIndex(node) == pawns.get(i).getX() &&
                        GridPane.getColumnIndex(node) == pawns.get(i).getY()) {
                    Circle circle = (Circle)node;
                    circle.setFill(Color.TRANSPARENT);
                    circle.setFill(new ImagePattern(img));
                    i++;
                }
            }
        }
    }

    public void doMoveOnClick(ActionEvent event) {
        if (controller.currentPosition != null && controller.destinationPosition != null) {
            if (controller.jumpPositions.isEmpty()) {
                Coordinates cCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.currentPosition),
                                GridPane.getColumnIndex(controller.currentPosition));
                Coordinates dCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.destinationPosition),
                                GridPane.getColumnIndex(controller.destinationPosition));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                int index = 0;
                for (Coordinates c : pawns) {
                    if (c.getX() == GridPane.getRowIndex(controller.currentPosition) &&
                            c.getY() == GridPane.getColumnIndex(controller.currentPosition)) {
                        pawns.remove(index);
                        break;
                    }
                    index++;
                }
                pawns.add(dCoordinates);

                Circle circleC = (Circle)controller.currentPosition;
                circleC.setStrokeWidth(1);
                circleC.setStroke(Color.BLACK);
                circleC.setFill(Color.WHITE);
                controller.currentPosition = null;

                Circle circleD = (Circle)controller.destinationPosition;
                circleD.setStrokeWidth(1);
                circleD.setFill(Color.TRANSPARENT);
                circleD.setFill(new ImagePattern(img));
                circleD.setStroke(Color.BLACK);
                controller.destinationPosition = null;
                controller.destinationSelected = false;
            } else {
                //Current --> 1st jump
                Coordinates cCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.currentPosition),
                                GridPane.getColumnIndex(controller.currentPosition));
                Coordinates dCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(0)),
                                GridPane.getColumnIndex(controller.jumpPositions.get(0)));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                int index = 0;
                for (Coordinates c : pawns) {
                    if (c.getX() == GridPane.getRowIndex(controller.currentPosition) &&
                            c.getY() == GridPane.getColumnIndex(controller.currentPosition)) {
                        pawns.remove(index);
                        break;
                    }
                    index++;
                }
                pawns.add(dCoordinates);

                Circle circleC = (Circle)controller.currentPosition;
                circleC.setStrokeWidth(1);
                circleC.setStroke(Color.BLACK);
                circleC.setFill(Color.WHITE);
                controller.currentPosition = null;

                Circle circleJ = (Circle)controller.jumpPositions.get(0);
                circleJ.setStrokeWidth(1);
                circleJ.setFill(Color.TRANSPARENT);
                circleJ.setFill(new ImagePattern(img));
                circleJ.setStroke(Color.BLACK);

                for (int i = 1; i < controller.jumpPositions.size(); i++) {
                    Circle circleJ1 = (Circle)controller.jumpPositions.get(i-1);
                    circleJ1.setStrokeWidth(1);
                    circleJ1.setFill(Color.WHITE);
                    circleJ1.setStroke(Color.BLACK);

                    cCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(i-1)),
                                    GridPane.getColumnIndex(controller.jumpPositions.get(i-1)));
                    dCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(i)),
                                    GridPane.getColumnIndex(controller.jumpPositions.get(i)));
                    client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                    Circle circleJ2 = (Circle)controller.jumpPositions.get(i);
                    circleJ2.setStrokeWidth(1);
                    circleJ2.setFill(Color.TRANSPARENT);
                    circleJ2.setFill(new ImagePattern(img));
                    circleJ2.setStroke(Color.BLACK);
                }

                Circle circleJL = (Circle)controller.jumpPositions.get(controller.jumpPositions.size()-1);
                circleJL.setStrokeWidth(1);
                circleJL.setFill(Color.WHITE);
                circleJL.setStroke(Color.BLACK);

                cCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)),
                                GridPane.getColumnIndex(controller.jumpPositions.get(controller.jumpPositions.size()-1)));
                dCoordinates =
                        new Coordinates(GridPane.getRowIndex(controller.destinationPosition),
                                GridPane.getColumnIndex(controller.destinationPosition));
                client.connection.invokeMovePlayerMethod(client.player, "move", cCoordinates, dCoordinates);

                controller.jumpPositions.clear();

                Circle circleD = (Circle)controller.destinationPosition;
                circleD.setStrokeWidth(1);
                circleD.setFill(Color.TRANSPARENT);
                circleD.setFill(new ImagePattern(img));
                circleD.setStroke(Color.BLACK);
                controller.destinationPosition = null;
                controller.destinationSelected = false;
            }
            moveType = MoveType.EMPTY;
            prevMoveType = MoveType.EMPTY;
            correctMove = false;
        }
    }

    public void chooseCircleOnClick(MouseEvent event) {
        ObservableList<Node> childrens = controller.board.getChildren();
        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == GridPane.getRowIndex((Node)event.getTarget()) &&
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex((Node)event.getTarget())) {
                if (controller.currentPosition == null
                        && node != controller.destinationPosition
                        && event.getButton() == MouseButton.PRIMARY) {

                    if (checkStartPosition(GridPane.getRowIndex(node), GridPane.getColumnIndex(node))) {
                        controller.setStartNode(node);
                    }
                } else if (node == controller.currentPosition
                        && event.getButton() == MouseButton.PRIMARY) {

                    controller.removeStartNode(node);

                } else if (controller.destinationPosition == null
                        && controller.currentPosition != null
                        && event.getButton() == MouseButton.SECONDARY
                        && !controller.jumpPositions.contains(node)) {

                    Coordinates cCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.previousNode),
                                    GridPane.getColumnIndex(controller.previousNode));
                    Coordinates dCoordinates =
                            new Coordinates(GridPane.getRowIndex(node),
                                    GridPane.getColumnIndex(node));
                    checkMoveType(cCoordinates,dCoordinates);

                    if (correctMove) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("end");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    }
                } else if (node == controller.destinationPosition
                        && event.getButton() == MouseButton.SECONDARY) {

                    controller.removeDestinationNode(node);
                    if (moveType == MoveType.SINGLE && correctMove) {
                        moveType = MoveType.EMPTY;
                    } else if (moveType == MoveType.SINGLE && prevMoveType == MoveType.EMPTY) {
                        moveType = MoveType.EMPTY;
                        prevMoveType = MoveType.EMPTY;
                        correctMove = true;
                    } else if (prevMoveType == MoveType.JUMP) {
                        moveType = MoveType.JUMP;
                    } else {
                        moveType = MoveType.EMPTY;
                    }


                } else if (!controller.destinationSelected
                        && node != controller.currentPosition
                        && node != controller.destinationPosition
                        && !controller.jumpPositions.contains(node)
                        && event.getButton() == MouseButton.PRIMARY) {

                    Coordinates cCoordinates =
                            new Coordinates(GridPane.getRowIndex(controller.previousNode),
                                    GridPane.getColumnIndex(controller.previousNode));
                    Coordinates dCoordinates =
                            new Coordinates(GridPane.getRowIndex(node),
                                    GridPane.getColumnIndex(node));
                    checkMoveType(cCoordinates,dCoordinates);

                    if (correctMove && moveType == MoveType.JUMP) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("jump");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    } else if (correctMove && moveType == MoveType.SINGLE) {
                        controller.waitingNode = node;
                        controller.clientListener.setMove("end");
                        client.connection.invokeCheckMoveMethod(client.player, "checkMove", cCoordinates, dCoordinates, moveType);
                    }
                } else if(node != controller.currentPosition
                        && node != controller.destinationPosition
                        && controller.jumpPositions.contains(node)
                        && event.getButton() == MouseButton.PRIMARY) {

                    controller.removeJumpNode(node);
                    if (moveType == MoveType.SINGLE && correctMove) {
                        moveType = MoveType.EMPTY;
                    } else if (moveType == MoveType.SINGLE && prevMoveType == MoveType.EMPTY) {
                        moveType = MoveType.EMPTY;
                        prevMoveType = MoveType.EMPTY;
                        correctMove = true;
                    } else if (prevMoveType == MoveType.JUMP) {
                        moveType = MoveType.JUMP;
                    } else {
                        moveType = MoveType.EMPTY;
                    }
                }
                break;
            }
        }
    }

    private void checkMoveType (Coordinates cCoordinates, Coordinates dCoordinates) {
        int cX = cCoordinates.getX();
        int cY = cCoordinates.getY();
        int dX = dCoordinates.getX();
        int dY = dCoordinates.getY();

        if (Math.abs(cX - dX) == 0 && Math.abs(cY - dY) == 2 && moveType == MoveType.EMPTY) {
            prevMoveType = moveType;
            moveType = MoveType.SINGLE;
            correctMove = true;
        } else if (Math.abs(cX - dX) == 1 && Math.abs(cY - dY) == 1 && moveType == MoveType.EMPTY) {
            prevMoveType = moveType;
            moveType = MoveType.SINGLE;
            correctMove = true;
        } else if (Math.abs(cX - dX) == 2 && Math.abs(cY - dY) == 2 && moveType != MoveType.SINGLE) {
            prevMoveType = moveType;
            moveType = MoveType.JUMP;
            correctMove = true;
        } else if (Math.abs(cX - dX) == 0 && Math.abs(cY - dY) == 4 && moveType != MoveType.SINGLE) {
            prevMoveType = moveType;
            moveType = MoveType.JUMP;
            correctMove = true;
        } else {
            correctMove = false;
        }
    }

    public void setMoveTypeAsEmpty() {
        this.moveType = MoveType.EMPTY;
    }

    private boolean checkStartPosition(int x, int y) {
        boolean correctCoordinates = false;
        for (Coordinates c : pawns) {
            if (c.getX() == x && c.getY() == y) {
                correctCoordinates = true;
                break;
            }
        }
        return correctCoordinates;
    }
}
