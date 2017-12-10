package client.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

import client.logic.*;

@SuppressWarnings("restriction")
public class ClientGUI extends Application {

    private String domain = null;
    private ObjectName factory = null;
    private int pid = 0;

    public ClientGUI() {
    }


    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        ClientConnection connection = new ClientConnection();
        domain = connection.getDomain();

        factory = new ObjectName(domain+"F" +":type=jmx.Factory,name=Factory");
        connection.invokeMethod(factory, "createPlayer", pid);

        connection.closeConnection();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/client/ClientFXML.fxml"));

        ClientGUIController controller = new ClientGUIController(this);
        loader.setController(controller);
        StackPane stackPane=loader.load();

        Scene scene = new Scene(stackPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chinese Checkers");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
