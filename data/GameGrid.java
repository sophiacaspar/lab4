/*TODO: 
 * 1. 	Declare a 2-dimensional array of integers for representing boards. 
 * 		The actual size is given as a parameter to the constructor. 
 * 		If n is the size of a board, the board contains n^2 squares.
 * 
 * 2.	Declare three public static integer constants EMPTY, ME, and OTHER 
 * 		that stand for these square contents.
 * 
 * 3.	The constant INROW is the number of filled squares in a row needed to win (= 5).
 * 
*/



package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	private int[][] cells;
	public static int EMPTY = 0;
	public static int ME = 1;
	public static int OTHER = 2;
	//number needed in one row to win;
	private final int INROW = 5;
	private int size;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		//set 2D-array size, with all column elements as 0. 0 = empty.
		this.cells = new int[size][size];
		this.size = size;
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return cells[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	/*setChanged() and notifyObservers()*/
	public boolean move(int x, int y, int player){
		if(cells[x][y] == 0){
			cells[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Clears the grid of pieces
	 */
	/*setChanged() and notifyObservers()*/
	public void clearGrid(){
		cells = new int[size][size];
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		boolean win = false;
		//Go through every row
		Label: for(int r = 0; r < cells.length; r++){
			int c = 0;
			//Go row every column in row
			while(win == false && c < cells.length){
				if (cells[r][c] == player){
					win = check(r,c,player);
					if (win){break Label;}
				}
				c++;
			}	
		}
		setChanged();
		notifyObservers();
		return win;
	}
	
	//Checks how many of one player to right, downwards, diagonally right, diagonally left.
	//(UP, LEFT, dRIGHT, dLeft have already been checked in previous loops)
	private boolean check(int x, int y, int player){
		boolean win = false;
		while (win == false){
			win = toRight(x,y,player);
			win = toDown(x,y,player);
			win = toDownRight(x,y,player);
			win = toDownLeft(x,y,player);
			return false;
		}
		return true;
	}
	
	//Private methods used by check() :
	
	//counts same-player pieces to the right
	private boolean toRight(int x, int y, int player){
		int inrow = 1;
		int r = x;
		int c = y;
		while(  (cells[r][c+1] == player) && (c < cells.length) && (inrow < INROW)  ){
			inrow++;
			c++;
		}
		return inrow == INROW ? true : false;
	}
	//counts same-player pieces downwards
	private boolean toDown(int x, int y, int player){
		int inrow = 1;
		int r = x;
		int c = y;
		while (  (cells[r+1][c] == player) && (r < cells.length) && (inrow < INROW)  ){
			inrow++;
			r++;
		}
		return inrow == INROW ? true : false;
	}
	//counts same-player pieces diagonally down to the right
	private boolean toDownRight(int x, int y, int player){
		int inrow = 1;
		int r = x;
		int c = y;
		while ( (cells[r+1][c+1] == player) && (r < cells.length) && (inrow < INROW) ){
			inrow++;
			r++;
			c++;
		}
		return inrow == INROW ? true : false;	
	}
	//counts same-player pieces diagonally to the left
	private boolean toDownLeft(int x, int y, int player){
		int inrow = 1;
		int r = x;
		int c = y;
		while (  (cells[r][c] == player) && (r < cells.length) && (inrow < INROW) ){
			inrow++;
			r++;
			c--;
		}
		return inrow == INROW ? true : false;
	}
	
	
}
