/* Notes:
 * 		GomokuGUI sets up connections and interactivity and keeps track of GameState.
 * 
 * 		Creates the JFrame, components and layout of components
 * 		The components are a GamePanel gameGridPanel, a JLabel messageLabel, 
 * 		and three JButtons: connectButton, newGameButton, and disconnectButton.
 * 
 * TODO:
 * 	1.	Set up layout
 * 
 * 	2.	There should be one or more listeners connected to the game panel 
 * 		and the three buttons in order to have the program respond when 
 * 		the user clicks on these components.
 * */


package lab4.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * Graphical user interface for Gomoku game
 * */

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	private JButton connectButton, newGameButton, disconnectButton;
	private JLabel messageLabel;
	private GamePanel gameGridPanel;
	
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		
		JFrame frame = new JFrame("Gomoku");
		//Panel for all components
		JPanel pane = new JPanel();
		//Panel for GameGrid
		gameGridPanel = new GamePanel(g.getGameGrid());
		
		//Add MouseListener for gameGridPanel
		gameGridPanel.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent e){
				// maps pixels to grid-positions x and y and calls method g.move(x,y)
				int pixx = e.getX();
				int pixy = e.getY();
				int[] pos = gameGridPanel.getGridPosition(pixx, pixy);
				gamestate.move(pos[0], pos[1]);
			}
		});
		
		//Label for messages
		messageLabel = new JLabel(g.getMessageString());
		//Buttons
		connectButton = new JButton("CONNECT");
		newGameButton = new JButton("NEW GAME");
		disconnectButton = new JButton("DISCONNECT");
		
		//Add actionListeners to buttons
		connectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Connect
				new ConnectionWindow(client);
			}
		});
		newGameButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start new game
				gamestate.newGame();
			}
		});
		disconnectButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Disconnects
				gamestate.disconnect();
			}
		});
		
		//Add components to frame
		Box vbox = Box.createVerticalBox();
		vbox.add(gameGridPanel);
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(connectButton);
		buttonBox.add(newGameButton);
		buttonBox.add(disconnectButton);
		vbox.add(buttonBox);
		vbox.add(messageLabel);
		pane.add(vbox);
		
		//Setup frame
		frame.setContentPane(pane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocation(1000,700);
		frame.setVisible(true);
		
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the game-state has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
