package client.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.management.NotificationFilterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

import client.logic.*;

@SuppressWarnings("restriction")
public class ClientGUI extends Application {

    public String domain = null;
    public ObjectName factory = null;
    public ObjectName player = null;
    public int pid = 0;
    public int playerInLobby = 0;
    public int rowForPlayerPawn = 0;
    public String lobbyName = "";
    public ClientConnection connection;

    public ClientGUI() {
    }

    @Override
    public void init() {
    }

    @Override
    public void stop() {
        connection.invokeMethod(factory, "deletePlayer", pid);
        connection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        connection = new ClientConnection();
        domain = connection.getDomain();

        factory = new ObjectName(domain+"F" +":type=jmx.Factory,name=Factory");

        ClientListener clientListener = new ClientListener();
        NotificationFilterSupport myFilter = new NotificationFilterSupport();
        myFilter.disableAllTypes();
        myFilter.enableType(String.valueOf(pid));
        connection.mbsc.addNotificationListener(factory, clientListener, myFilter, null);

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

    /*public void handleNotification(Notification notification, Object handback) {
        System.out.println("Notification type: " + notification.getType());
        System.out.println("Notification source: " + notification.getSource());
    }*/

    public static void main(String[] args) {
        launch(args);
    }

}
