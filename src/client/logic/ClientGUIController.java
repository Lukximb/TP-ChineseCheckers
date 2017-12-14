package client.logic;

import client.core.ClientGUI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class ClientGUIController {

//	ClientGUI client;

	private ClientGUI client;
	private BoardUpdate boardUpdate;
	private PlayerLogic playerLogic;

	public Node currentPosition;
	public Node destinationPosition;
	public Node previousNode;
	public Node waitingNode;
	public ArrayList<Node> jumpPositions;
	public boolean destinationSelected;

	private ObservableList<String> playersList;
	public ClientListener clientListener;

	//LOGIN---------------------------------------
	@FXML
	private StackPane playerNickNamePanel;
	@FXML
	private Button loginButton;
	@FXML
	public TextField nickNameField;

	//MENU----------------------------------------
	@FXML
	private StackPane menu;
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
	private TableView<String> playersInLobbyList;
	@FXML
	private TableColumn<String, String> playersInLobbyColumn;
	@FXML
	public TextField invitePlayerField;
	@FXML
	private Button addBotButton;
	@FXML
	private Button removePlayerButton;
	@FXML
	private Button addPlayerButton;
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
    public GridPane board;
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

	public void setListener(ClientListener clientListener) {
		this.clientListener = clientListener;
	}
	
	@FXML
	void initialize() {
		boardUpdate = new BoardUpdate(this, client);
		playerLogic = new PlayerLogic(this, client);

		game.setStyle("-fx-background-image: url('/client/wood.jpg');");
		jumpPositions = new ArrayList<>();
		destinationSelected = false;

		playersList = FXCollections.observableArrayList();
		playersInLobbyColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
		playersInLobbyList.setItems(playersList);
	}

	//LOGIN
	public void loginButtonOnClick(ActionEvent exent) {
		this.playerNickNamePanel.setVisible(false);
		this.playerNickNamePanel.setDisable(true);

		playerLogic.createPlayer();

		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}
	
	//MENU
	public void newGameButtonOnClick(ActionEvent exent) {
		this.menu.setVisible(false);
		this.menu.setDisable(true);

		this.createLobby.setVisible(true);
		this.createLobby.setDisable(false);
	}
	
	public void exitButtonOnClick(ActionEvent event) {
		Platform.exit();
	}
	
	public void joinGameButtonOnClick(ActionEvent event) {
		this.menu.setVisible(false);
		this.menu.setDisable(true);

		this.joinLobby.setVisible(true);
		this.joinLobby.setDisable(false);
		
	}
	//CREATE LOBBY
	public void createLobbyButtonOnClick(ActionEvent event) {		
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);

		client.lobbyName = lobbyNameField.getText();
		client.rowForPlayerPawn = (int)boardSizeSpinner.getValue();
		client.connection.invokeCreateLobbyMethod(client.factory, "createLobby", client.playerInLobby , client.rowForPlayerPawn, client.lobbyName, client.pid);

		playersList.clear();
		playersList.add(client.playerName);

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

	public void addPlayerButtonOnClick(ActionEvent event) {
		playerLogic.addPlayerButtonOnClick(event);
	}

	public void removePlayerButtonOnClick(ActionEvent event) {
		playerLogic.removePlayerButtonOnClick(event);
	}
	
	//GAME
	public void surrenderButtonOnClick(ActionEvent event) {
		this.game.setVisible(false);
		this.game.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}

	public void setPlayerNumberOn2ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 2;
	}

	public void setPlayerNumberOn3ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 3;
	}

	public void setPlayerNumberOn4ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 4;
	}

	public void setPlayerNumberOn6ButtonOnClick(ActionEvent event) {
		client.playerInLobby = 6;
	}


	public void doMoveOnClick(ActionEvent event) {
		boardUpdate.doMoveOnClick(event);
	}

	public void chooseCircleOnClick(MouseEvent event) {
		boardUpdate.chooseCircleOnClick(event);
	}

	//Set methods are invoked by notification listener after acceptation received
	public void setStartNode(Node node) {
		Circle circle = (Circle)node;
		circle.setStrokeWidth(4);
		circle.setStroke(Color.GREEN);
		currentPosition = node;
		previousNode = node;
	}

	public void setDestinationNode() {
		destinationSelected = true;
		Circle circle = (Circle)waitingNode;
		circle.setStrokeWidth(4);
		circle.setStroke(Color.RED);
		destinationPosition = waitingNode;
	}

	public void setJumpNode() {
		Circle circle = (Circle)waitingNode;
		circle.setStrokeWidth(4);
		circle.setStroke(Color.BLUE);
		jumpPositions.add(waitingNode);
		previousNode = waitingNode;
	}

	public void removeStartNode(Node node) {
		Circle circle = (Circle)node;
		circle.setStrokeWidth(1);
		circle.setStroke(Color.BLACK);
		currentPosition = null;
	}

	public void removeDestinationNode(Node node) {
		destinationSelected = false;
		Circle circle = (Circle)node;
		circle.setStrokeWidth(1);
		circle.setStroke(Color.BLACK);
		destinationPosition = null;
	}

	public void removeJumpNode(Node node) {
		Circle circle = (Circle)node;
		circle.setStrokeWidth(1);
		circle.setStroke(Color.BLACK);
		jumpPositions.remove(node);
		if (!jumpPositions.isEmpty()) {
			previousNode = jumpPositions.get(jumpPositions.size()-1);
		} else {
			previousNode = currentPosition;
		}
	}

	public void updatePlayersList(String[] list) {
		playersList.clear();
		for(String s: list) {
			playersList.add(s);
		}
		playersInLobbyList.setItems(playersList);
	}

	public void updateLobbyList(String[] list) {

	}


	//TEST
	public void submitButtonOnClick(ActionEvent event) {
	}

	public void fieldsHandleTest(MouseEvent event) {
		//System.out.println("mouse click detected! "+ event.getSource());
	}

    public void boardClick(MouseEvent event) {
		/*if (event.getButton() == MouseButton.SECONDARY) {
		}
		System.out.println("Row: " + GridPane.getRowIndex((Node)event.getTarget())
				+ "\nColumn: " + GridPane.getColumnIndex((Node)event.getTarget()));*/
	}
}
