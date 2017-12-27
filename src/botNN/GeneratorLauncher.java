package botNN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Simple application to generating data sample for neural network
 * When app run, array is filling by 1, next we click on field which neural network should choose
 * 1 - player on field, we cannot choose this field
 * 0 - empty field, we can choose it
 */
public class GeneratorLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("dataExample.txt"));

        Controller controller = new Controller();
        loader.setController(controller);
        StackPane stackPane=loader.load();

        Scene scene = new Scene(stackPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Bot data generator");

        primaryStage.show();
    }

    @Override
    public void init() {
    }
}
