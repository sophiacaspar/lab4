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
		int xPosition = x/UNIT_SIZE;
		int yPosition = y/UNIT_SIZE;
		int position [] = new int [2];
		
		position[0] = xPosition;
		position[1] = yPosition;
		
		return position;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int size = grid.getSize();
		
		for (int i_y = 0; i_y < size; i_y++) {
			for (int i_x = 0; i_x < size; i_x++) {
				g.drawRect(i_x*UNIT_SIZE, i_y*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				
			}
			
		}
		
	}
	
}