package client.logic;

import client.core.ClientGUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class ClientGUIController {
	ClientGUI client;

	//TEST---------------------------------------
	@FXML
	private StackPane test;
	@FXML
	private Button submitButton;
	@FXML
	private Label outputLabel;
	@FXML
	private TextField inputField;

	//MENU----------------------------------------
	@FXML
	private StackPane menu;
	@FXML
	private TextField nickNameField;
	@FXML
	private Button newGameButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button joinGameButton;
	//CREATE LOBBY--------------------------------
	@FXML
	private StackPane createLobby;
	@FXML
	private TextField lobbyNameField;
	@FXML
	private Button player2Button;
	@FXML
	private Button player4Button;
	@FXML
	private Button player6Button;
	@FXML
	private Spinner boardSizeSpinner;
	@FXML
	private Button createLobbyButton;
	@FXML
	private Button cancelCreateLobbyButton;
	//JOIN LOBBY----------------------------------
	@FXML
	private StackPane joinLobby;
	@FXML
	private Button cancelJoinLobbyButton;
	@FXML
	private Button joinLobbyButton;	
	//LOBBY---------------------------------------
	@FXML
	private StackPane lobby;
	@FXML
	private TextField invitePlayerField;
	@FXML
	private Button addBotButton;
	@FXML
	private Button removePlayerButton;
	@FXML
	private Button easyBotButton;
	@FXML
	private Button mediumBotButton;
	@FXML
	private Button hardBotButton;
	@FXML
	private Button readyButton;
	@FXML
	private Button exitLobbyButton;
	//GAME----------------------------------------
	@FXML
	private StackPane game;
	@FXML
	private ProgressBar turnTimeBar;
	@FXML
	private Button moveButton;
	@FXML
	private Button passButton;
	@FXML
	private Button surrenderButton;
	@FXML
	private Button sendMsgButton;
	
	
	public ClientGUIController(ClientGUI client) {
		this.client = client;
	}
	
	@FXML
	void initialize() {
	}
	
	//MENU
	public void newGameButtonOnClick(ActionEvent exent) {
		this.menu.setVisible(false);
		this.menu.setDisable(true);

		createPlayer();
		
		this.createLobby.setVisible(true);
		this.createLobby.setDisable(false);
	}
	
	public void exitButtonOnClick(ActionEvent event) {
		Platform.exit();
	}
	
	public void joinGameButtonOnClick(ActionEvent event) {
		this.menu.setVisible(false);
		this.menu.setDisable(true);

		createPlayer();
		
		this.joinLobby.setVisible(true);
		this.joinLobby.setDisable(false);
		
	}
	//CREATE LOBBY
	public void createLobbyButtonOnClick(ActionEvent event) {		
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);

		client.lobbyName = lobbyNameField.getText();
		client.rowForPlayerPawn = (int)boardSizeSpinner.getValue();
		System.out.println(client.lobbyName);
		System.out.println(client.rowForPlayerPawn);
		client.connection.invokeCreateLobbyMethod(client.factory, "createLobby", client.playerInLobby , client.rowForPlayerPawn, client.lobbyName, client.pid);

		this.lobby.setVisible(true);
		this.lobby.setDisable(false);
	}
	
	public void cancelCreateLobbyButtonOnClick(ActionEvent event) {
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//JOIN LOBBY
	public void joinLobbyButtonOnClick(ActionEvent event) {
		this.joinLobby.setVisible(false);
		this.joinLobby.setDisable(true);
		
		this.lobby.setVisible(true);
		this.lobby.setDisable(false);
	}
	
	public void cancelJoinLobbyButtonOnClick(ActionEvent event) {
		this.joinLobby.setVisible(false);
		this.joinLobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//LOBBY
	public void readyButtonOnClick(ActionEvent event) {
		this.lobby.setVisible(false);
		this.lobby.setDisable(true);
		
		this.game.setVisible(true);
		this.game.setDisable(false);
	}
	
	public void exitLobbyButtonOnClick(ActionEvent event) {
		this.lobby.setVisible(false);
		this.lobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//GAME
	public void surrenderButtonOnClick(ActionEvent event) {
		this.game.setVisible(false);
		this.game.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}

	private void createPlayer() {
		if (client.player == null) {
			client.connection.invokeCreatePlayerMethod(client.factory, "createPlayer", client.pid, nickNameField.getText());
			try {
				client.player = new ObjectName(client.domain+ client.pid +":type=jmx.Player,name=player" + client.pid);
			} catch (MalformedObjectNameException e) {
				e.printStackTrace();
			}
		}
	}
	
    public void fieldsHandleTest(MouseEvent event) {
		System.out.println("mouse click detected! "+ event.getSource());
    }

    public void boardClick(MouseEvent event) {
		System.out.println("Row: " + GridPane.getRowIndex((Node)event.getTarget())
				+ "\nColumn: " + GridPane.getColumnIndex((Node)event.getTarget()));
	}

	public void setPlayerNumberOn2ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 2;
	}

	public void setPlayerNumberOn4ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 4;
	}

	public void setPlayerNumberOn6ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 6;
	}

	//TEST
	public void submitButtonOnClick(ActionEvent event) {
//		try {
//			client.p.printStr(this.sendString());
//			this.outputLabel.setText(client.p.getReply());
//		} catch (RemoteException e1) {
//			e1.printStackTrace();
//		}
	}
//
//	private String sendString() {
//		String s = inputField.getText();
//		return s;
//	}
	
}
