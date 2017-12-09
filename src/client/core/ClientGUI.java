package client.core;

import client.logic.ClientGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.management.ObjectName;

import client.logic.*;
import org.junit.jupiter.api.Test;

@SuppressWarnings("restriction")
public class ClientGUI extends Application {

    String domain = null;

    public ClientGUI() {
    }


    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientConnection connection = new ClientConnection();
        domain = connection.getDomain();
        ObjectName mBeanName=
                new ObjectName(domain+"1" +":type=jmx.Hello,name=h2");
        connection.createNewMBean(mBeanName);
        connection.invokeMethod(mBeanName, "sayHello");
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
