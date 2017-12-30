package botNN;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
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
    private ArrayList<Integer> target;

    public Controller() {
        data = new ArrayList<>();
        target = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(1);
            target.add(0);
        }
    }

    public void generateExampleOnClick(ActionEvent event) {
        writeExampleToFile();
        System.out.println("Board clear");
        data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(1);
        }
        writetargetToFile();
        System.out.println("Board clear");
        target = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            target.add(0);
        }
        clearBoard();
    }

    public void fillCircle(MouseEvent event) {
        ObservableList<Node> childrens = board.getChildren();
        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == GridPane.getRowIndex((Node)event.getTarget()) &&
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex((Node)event.getTarget())) {
                if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 2) {
                    Circle circle = (Circle) node;
                    if (data.get(0) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(0, 0);
                    } else if (data.get(0) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(0, 1);
                    } else if (target.get(0) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(0, 1);
                    } else if (target.get(0) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(0, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 0 && GridPane.getColumnIndex(node) == 6) {
                    Circle circle = (Circle) node;
                    if (data.get(1) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(1, 0);
                    } else if (data.get(1) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(1, 1);
                    } else if (target.get(1) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(1, 1);
                    } else if (target.get(1) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(1, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 3) {
                    Circle circle = (Circle) node;
                    if (data.get(2) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(2, 0);
                    } else if (data.get(2) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(2, 1);
                    } else if (target.get(2) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(2, 1);
                    } else if (target.get(2) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(2, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 5) {
                    Circle circle = (Circle) node;
                    if (data.get(3) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(3, 0);
                    } else if (data.get(3) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(3, 1);
                    } else if (target.get(3) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(3, 1);
                    } else if (target.get(3) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(3, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 0) {
                    Circle circle = (Circle) node;
                    if (data.get(4) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(4, 0);
                    } else if (data.get(4) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(4, 1);
                    } else if (target.get(4) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(4, 1);
                    } else if (target.get(4) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(4, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 2) {
                    Circle circle = (Circle) node;
                    if (data.get(5) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(5, 0);
                    } else if (data.get(5) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(5, 1);
                    } else if (target.get(5) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(5, 1);
                    } else if (target.get(5) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(5, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 6) {
                    Circle circle = (Circle) node;
                    if (data.get(6) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(6, 0);
                    } else if (data.get(6) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(6, 1);
                    } else if (target.get(6) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(6, 1);
                    } else if (target.get(6) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(6, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 8) {
                    Circle circle = (Circle) node;
                    if (data.get(7) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(7, 0);
                    } else if (data.get(7) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(7, 1);
                    } else if (target.get(7) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(7, 1);
                    } else if (target.get(7) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(7, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 3) {
                    Circle circle = (Circle) node;
                    if (data.get(8) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(8, 0);
                    } else if (data.get(8) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(8, 1);
                    } else if (target.get(8) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(8, 1);
                    } else if (target.get(8) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(8, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 5) {
                    Circle circle = (Circle) node;
                    if (data.get(9) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(9, 0);
                    } else if (data.get(9) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(9, 1);
                    } else if (target.get(9) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(9, 1);
                    } else if (target.get(9) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(9, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 2) {
                    Circle circle = (Circle) node;
                    if (data.get(10) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(10, 0);
                    } else if (data.get(10) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(10, 1);
                    } else if (target.get(10) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(10, 1);
                    } else if (target.get(10) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(10, 0);
                    }
                    break;
                } else if (GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 6) {
                    Circle circle = (Circle) node;
                    if (data.get(11) == 1 && event.getButton() == MouseButton.PRIMARY) {
                        circle.setFill(Color.YELLOW);
                        data.set(11, 0);
                    } else if (data.get(11) == 0 && event.getButton() == MouseButton.PRIMARY){
                        circle.setFill(Color.WHITE);
                        data.set(11, 1);
                    } else if (target.get(11) == 0 && event.getButton() == MouseButton.SECONDARY) {
                        circle.setFill(Color.BLUE);
                        target.set(11, 1);
                    } else if (target.get(11) == 1 && event.getButton() == MouseButton.SECONDARY){
                        circle.setFill(Color.WHITE);
                        target.set(11, 0);
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

    private void writeExampleToFile() {
        StringBuilder text = new StringBuilder();
        for (int d : data) {
            text.append(d);
            text.append(" ");
        }

        try {
            String filename= "dataEx.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.write(text + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writetargetToFile() {
        StringBuilder text = new StringBuilder();
        for (int d : target) {
            text.append(d);
            text.append(" ");
        }

        try {
            String filename= "targetEX.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.write(text + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
