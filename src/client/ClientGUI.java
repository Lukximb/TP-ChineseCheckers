package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import rmi.ITest;

public class ClientGUI extends Application {
	ITest p;
	
	public ClientGUI() {
	}
	
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		setConnection();
		
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
	
	public void setConnection() {
		try { 
			//int pid = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);   
			p = (ITest)Naming.lookup("print"); 
		}
		catch( NotBoundException ex )
		{ ex.printStackTrace(); }
		catch( RemoteException e )
		{ e.printStackTrace(); }
		catch( MalformedURLException e )
		{ e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


