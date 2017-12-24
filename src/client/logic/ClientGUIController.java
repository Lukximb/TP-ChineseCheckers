package client.logic;

import client.core.ClientGUI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class ClientGUIController {

//	ClientGUI client;

	public ClientGUI client;
	public BoardUpdate boardUpdate;
	private PlayerLogic playerLogic;

	public Node currentPosition;
	public Node destinationPosition;
	public Node previousNode;
	public Node waitingNode;
	public ArrayList<Node> jumpPositions;
	public boolean destinationSelected;

	private ObservableList<String> playersList;
	private ObservableList<String> lobbyList;
	public ClientListener clientListener;
	public GridPane board;

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
	private Button player3Button;
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
    private TableView<String> lobbyListTable;
    @FXML
    private TableColumn<String, String> lobbyListColumn;
	@FXML
	private StackPane joinLobby;
	@FXML
	private Button cancelJoinLobbyButton;
	@FXML
	private Button joinLobbyButton;
	@FXML
    private Button refreshLobbyListButton;
	//LOBBY---------------------------------------
	@FXML
	public StackPane lobby;
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
	@FXML
    private Button refreshPlayersListButton;
	//GAME----------------------------------------
	@FXML
	public StackPane game;
	@FXML
    public GridPane board4;
    @FXML
    public GridPane board3;
    @FXML
    public GridPane board2;
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
	//POPUP-INVITE--------------------------------------
	@FXML
	private StackPane invitePopUp;
	@FXML
	private Button invitePopButtonAccept;
	@FXML
	private Button invitePopButtonDecline;
	@FXML
	private Label popUpPlayerNick;

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

        lobbyList = FXCollections.observableArrayList();
        lobbyListColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        lobbyListTable.setItems(lobbyList);
        board = board4;
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

        client.connection.invokeSendWaitingLobbyList(client.manager, "sendWaitingLobbyList", client.playerName);

		this.joinLobby.setVisible(true);
		this.joinLobby.setDisable(false);
		
	}
	//CREATE LOBBY
	public void createLobbyButtonOnClick(ActionEvent event) {		
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);

		client.lobbyName = lobbyNameField.getText();
		client.rowOfPawn = (int)boardSizeSpinner.getValue();
		client.connection.invokeCreateLobbyMethod(client.factory, "createLobby", client.playerInLobby ,
				client.rowOfPawn, client.lobbyName, client.pid);
		client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
		client.addNotificationListenerToLobby();
		boardUpdate.setCorner(0);

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

        String lobbyName;
        lobbyName = lobbyListTable.getSelectionModel().getSelectedItem();
        client.connection.invokeAddPlayerToLobbyMethod(client.manager, "addPlayerToLobby", lobbyName, client.playerName);
        System.out.println("Player: " + client.playerName + "joined to lobby: " + lobbyName);
        client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
        client.lobbyName = lobbyName;
		
		this.lobby.setVisible(true);
		this.lobby.setDisable(false);
	}

	public void refreshLobbyListOnClick(ActionEvent event) {
        client.connection.invokeSendWaitingLobbyList(client.manager, "sendWaitingLobbyList", client.playerName);
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

		//client.connection.invokeStartGameMethod(client.player, "startGame");
		client.connection.invokeStartGameMethod(client.manager, "startGame", client.lobbyName);
		
		this.game.setVisible(true);
		this.game.setDisable(false);
	}
	
	public void exitLobbyButtonOnClick(ActionEvent event) {
		this.lobby.setVisible(false);
		this.lobby.setDisable(true);
		
		this.menu.setVisible(true);
		this.menu.setDisable(false);
	}

	public void refreshPlayersListButtonOnClick(ActionEvent event) {
        client.connection.invokeSendPlayersInLobbyList(client.manager, "sendPlayersInLobbyList", client.playerName);
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

	public void acceptInviteOnClick(ActionEvent event) {
        client.addNotificationListenerToLobby();
		playerLogic.addPlayerToLobby();
		this.invitePopUp.setVisible(false);
		this.invitePopUp.setDisable(true);
		this.menu.setVisible(false);
		this.menu.setDisable(true);
		this.joinLobby.setVisible(false);
		this.joinLobby.setDisable(true);
		this.createLobby.setVisible(false);
		this.createLobby.setDisable(true);

//        client.connection.invokeAddPlayerToLobbyMethod(client.manager, "addPlayerToLobby", lobbyName, client.playerName);

		this.lobby.setVisible(true);
		this.lobby.setDisable(false);
	}

	public void declineInviteOnClick(ActionEvent event) {
		client.lobbyName = "";
		this.invitePopUp.setVisible(false);
		this.invitePopUp.setDisable(true);
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
		//playersList.clear();
//		for(String s: list) {
//			playersList.add(s);
//		}
		playersList.setAll(list);
		//playersList.addAll(list);
		playersInLobbyList.setItems(playersList);
	}

	public void updateLobbyList(String[] list) {
        //lobbyList.clear();
//		for(String s: list) {
//			lobbyList.add(s);
//		}
		//lobbyList.addAll(list);
        lobbyList.setAll(list);
        lobbyListTable.setItems(lobbyList);
	}

	public void showInvitation(String[] PlayerAndLobbyName) {
		invitePopUp.setStyle("-fx-background-image: url('/client/popupBackground.png');");
		this.popUpPlayerNick.setText(PlayerAndLobbyName[0]);
		this.invitePopUp.setVisible(true);
		this.invitePopUp.setDisable(false);
		client.lobbyName = PlayerAndLobbyName[1];
	}


	//TEST
	public void submitButtonOnClick(ActionEvent event) {
	}

	public void fieldsHandleTest(MouseEvent event) {
		//System.out.println("mouse click detected! "+ event.getSource());
	}

    public void boardClick(MouseEvent event) {
		System.out.println("Row: " + GridPane.getRowIndex((Node)event.getTarget())
				+ "\nColumn: " + GridPane.getColumnIndex((Node)event.getTarget()));
	}
}
