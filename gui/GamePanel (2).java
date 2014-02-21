
/* Notes:
 * 		A GamePanel object observes a GameGrid object
 * 		GamePanel contains graphical representation of VIEW. (paintComponent, repaint)
 * 		(GomokuGUI sets up connections and interactivity and keeps track of GameState.)
 * 		GamePanel Observes GameGrid, and paints changes (movement).
 * 		Use UNIT_SIZE for the conversion between pixel coordinates and a square on the board.
 * 
 * 		
 * */


package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		//Sets up dimension of window in pixels
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		int[] gridPos = new int[2];
		gridPos[0] = x%20;
		gridPos[1] = y%20;
		return gridPos;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		//Paints all components. Should be a box with a grid.
		super.paintComponent(g);
		//Paint gameGrid grid.
		//Paint placed crosses and naughts
		//if winner?
		
		
	}
	
}
