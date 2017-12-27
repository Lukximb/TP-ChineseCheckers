package botNN;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileWriter;
import java.util.ArrayList;

public class Controller {

    @FXML
    private StackPane dataBoard;
    @FXML
    private Button generateButton;
    @FXML
    private GridPane board;

    private ArrayList<Integer> data;

    public Controller() {
        data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(1);
        }
    }

    public void generateExampleOnClick(ActionEvent event) {
        writeToFile();
        clearBoard();
        System.out.println("Board clear");
        data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(1);
        }
    }

    public void fillCircle(MouseEvent event) {
        ObservableList<Node> childrens = board.getChildren();
        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == GridPane.getRowIndex((Node)event.getTarget()) &&
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex((Node)event.getTarget())) {
                if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 2) {
                    Circle circle = (Circle) node;
                    if (data.get(0) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(0, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(0, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 6) {
                    Circle circle = (Circle) node;
                    if (data.get(1) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(1, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(1, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 3) {
                    Circle circle = (Circle) node;
                    if (data.get(2) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(2, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(2, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 5) {
                    Circle circle = (Circle) node;
                    if (data.get(3) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(3, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(3, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 0) {
                    Circle circle = (Circle) node;
                    if (data.get(4) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(4, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(4, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 2) {
                    Circle circle = (Circle) node;
                    if (data.get(5) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(5, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(5, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 6) {
                    Circle circle = (Circle) node;
                    if (data.get(6) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(6, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(6, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 8) {
                    Circle circle = (Circle) node;
                    if (data.get(7) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(7, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(7, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 3) {
                    Circle circle = (Circle) node;
                    if (data.get(8) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(8, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(8, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 5) {
                    Circle circle = (Circle) node;
                    if (data.get(9) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(9, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(9, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 2) {
                    Circle circle = (Circle) node;
                    if (data.get(10) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(10, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(10, 1);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 6) {
                    Circle circle = (Circle) node;
                    if (data.get(11) == 1) {
                        circle.setFill(Color.YELLOW);
                        data.set(11, 0);
                    } else {
                        circle.setFill(Color.WHITE);
                        data.set(11, 1);
                    }
                    break;
                }
            }
        }
        for (int d : data) {
            System.out.print(d + " ");
        }
        System.out.print("\n");
    }

    private void clearBoard() {
        ObservableList<Node> childrens = board.getChildren();
        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 2) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 6) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 3) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 5) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 0) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 2) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 6) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 8) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 3) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 5) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 2) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            } else if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 6) {
                Circle circle = (Circle) node;
                circle.setFill(Color.WHITE);
            }
        }
    }

    private void writeToFile() {
        StringBuilder text = new StringBuilder();
        for (int d : data) {
            text.append(d);
            text.append(" ");
        }

        try {
            String filename= "dataExample.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.write(text + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
