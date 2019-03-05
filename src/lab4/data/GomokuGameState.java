/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;
import lab4.client.GomokuClient;

/**
 * @author Elsa Jonsson & Pawel Dzialo
 * 
 */

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer {

	// Game variables
	private final int DEFAULT_SIZE = 10;
	private GameGrid gameGrid;

	// Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHERS_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;

	private GomokuClient client;

	private String message = "Welcome to Gomoku!";

	/**
	 * The constructor
	 * 
	 * @param gc
	 *            The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc) {
		this.client = gc;
		this.client.addObserver(this);
		gc.setGameState(this);
		this.currentState = NOT_STARTED;
		this.gameGrid = new GameGrid(DEFAULT_SIZE);
	}

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString() {
		return message;
	}

	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid() {
		return this.gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public void move(int x, int y) {
		if (currentState == MY_TURN) {
			if (gameGrid.move(x, y, MY_TURN)) {
				client.sendMoveMessage(x, y);
				if (gameGrid.isWinner(MY_TURN)) {
					currentState = FINISHED;
					message = "Game is over, you won.";
				} else {
					currentState = OTHERS_TURN;
				}
			} else {
				message = "Invalid move.";
			}
		} else if (currentState == OTHERS_TURN) {
			message = "It is not your turn";

		} else if (currentState == NOT_STARTED) {
			message = "There is no game active.";

		} else {
			message = "The game has already finished, no game active.";

		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Starts a new game with the current client
	 */
	public void newGame() {
		gameGrid.clearGrid();
		currentState = OTHERS_TURN;
		message = "You have started a new game, it is the other players turn.";
		client.sendNewGameMessage();
		setChanged();
		notifyObservers();

	}

	/**
	 * Other player has requested a new game, so the game state is changed
	 * accordingly
	 */
	public void receivedNewGame() {
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "You have recieved a new game, it is your turn.";
		setChanged();
		notifyObservers();
	}

	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft() {
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "Your opponent left.";
		setChanged();
		notifyObservers();
	}

	/**
	 * The player disconnects from the client
	 */
	public void disconnect() {
		gameGrid.clearGrid();
		currentState = NOT_STARTED;
		client.disconnect();
		message = "You left the game";
		setChanged();
		notifyObservers();
	}

	/**
	 * The player receives a move from the other player
	 * 
	 * @param x
	 *            The x coordinate of the move
	 * @param y
	 *            The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		gameGrid.move(x, y, OTHERS_TURN);
		if (gameGrid.isWinner(OTHERS_TURN)) {
			currentState = FINISHED;
			message = "Game is over, you lost.";
		} else {
			message="It is your turn.";
			currentState = MY_TURN;
		}
		setChanged();
		notifyObservers();
	}

	public void update(Observable o, Object arg) {

		switch (client.getConnectionStatus()) {
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHERS_TURN;
			break;
		}
		setChanged();
		notifyObservers();

	}

}