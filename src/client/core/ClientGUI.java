package client.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    public ObjectName manager = null;
    public int pid = 0;
    public int playerInLobby = 0;
    public int rowForPlayerPawn = 0;
    public String lobbyName = "";
    public String playerName = "";
    public ClientConnection connection;
    public ClientListener clientListener;

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

        //Get factory and manager from registry
        factory = new ObjectName(domain+"F" +":type=jmx.Factory,name=Factory");
        manager = new ObjectName(domain+"M" +":type=manager.Manager,name=Manager");

        //Load GUI
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/client/ClientFXML.fxml"));

        ClientGUIController controller = new ClientGUIController(this);
        loader.setController(controller);
        StackPane stackPane=loader.load();

        Scene scene = new Scene(stackPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chinese Checkers");

        //Create notification listener and notification filter
        clientListener = new ClientListener(controller);
        NotificationFilterSupport myFilter = new NotificationFilterSupport();
        myFilter.disableAllTypes();
        myFilter.enableType(String.valueOf(pid));

        //Add notification listener
        connection.mbsc.addNotificationListener(factory, clientListener, myFilter, null);
        connection.mbsc.addNotificationListener(manager, clientListener, myFilter, null);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
