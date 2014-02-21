/*TODO:
 * 1.	 Four integer constants representing the different game states:
 * 		- NOT_STARTED when no game is going on,
 * 		- MY_TURN when the game is being played and it is "my" turn,{
 * 		- OTHERS_TURN when the game is being played and it is the "other" player's turn,
 * 		- FINISHED when a game has been played and one player has won.
 * 
 * Notes:
 * 		Observer and Observable
 * 
 * 		currentState will be equal to one of these four at any given moment in time.
 * 		It is only changed in this class's method.
 * 
 * 		String variable message refers to text displayed below board when some essential happens
 *		The class should notify any observers whenever the message is changed.
 *
 *		End of p.3 - p.4 for method info
 * */



/*
 * Created on 2007 feb 8
 */


package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHER_TURN = 2;
	private final int FINISHED = 3;
	
	private int currentState;
	
	private GomokuClient client;
	//Message changes to fit essential changes
	private String message = "Welcome to Gomoku!";
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	//used by GUI
	public String getMessageString(){
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		
		switch (currentState){
		case NOT_STARTED:
			message = "Game not started";
		
		case OTHER_TURN:
			message = "It's not your turn!";
			break;
		
		case MY_TURN:
			if(gameGrid.move(x, y, gameGrid.ME)){
				client.sendMoveMessage(x, y);
				currentState = gameGrid.isWinner(gameGrid.ME) ? FINISHED : OTHER_TURN;
				message = gameGrid.isWinner(gameGrid.ME) ? "You won!" : "Waiting for other player";
			}
			else{
				message = "Illegal move";
			}
			break;
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		gameGrid.clearGrid();
		client.sendNewGameMessage();
		currentState = OTHER_TURN;
		message = "Waiting for other player...";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "It's your turn";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		currentState = FINISHED;
		gameGrid.clearGrid();
		message = "Other guy left";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		currentState = FINISHED;
		gameGrid.clearGrid();
		client.disconnect();
		message = "Disconnected";
		setChanged();
		notifyObservers();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		//Other makes a move. If winning move, game is finished and other won.
		gameGrid.move(x, y, gameGrid.OTHER);
		if (gameGrid.isWinner(gameGrid.OTHER)){
			currentState = FINISHED;
			message = "You lost";
		}
		//else, it is your turn
		else{
			currentState = MY_TURN;
			message = "It's your turn";
		}
		
		setChanged();
		notifyObservers();
	}
	
	
	/* Observes client. Client stateChanged() updates currentState to who's turn it is.
	 * It then notifies its observer of this turn-change
	*/
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
		
	}
	
}
