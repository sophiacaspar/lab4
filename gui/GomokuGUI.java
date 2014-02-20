package lab4.gui;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.Box;
import java.awt.BorderLayout;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private GamePanel gamePanel;
	
	private JButton connectButton = new JButton("Connect");
	private JButton newGameButton = new JButton("New Game");
	private JButton disconnectButton = new JButton("Disconnect");
	private JLabel messageLabel;
	
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
		Listener lis = new Listener();
		
		JFrame frame = new JFrame("Gomoku");
		messageLabel = new JLabel(gamestate.getMessageString());
		
		frame.add(connectButton, BorderLayout.LINE_START);
		frame.add(newGameButton, BorderLayout.CENTER);
		frame.add(disconnectButton, BorderLayout.LINE_END);
		frame.add(messageLabel, BorderLayout.PAGE_END);
		
		//SETUP WINDOW
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
		
		//Add listeners
		gamePanel.addMouseListener(lis);
		connectButton.addActionListener(lis);
		disconnectButton.addActionListener(lis);
		newGameButton.addActionListener(lis);
	
	}
	
	
	public void update(Observable arg0, Object arg1) {
		gamePanel.repaint();
		
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
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
	}
}

private class Listener implements ActionListener, MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		int[] i = gamePanel.getGridPosition(e.getX(), e.getY());
		gamestate.move(i[0], i[1]);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == connectButton){
			 new ConnectionWindow(client);
		}
		if(e.getSource() == disconnectButton){
			gamestate.disconnect();
		}
		if(e.getSource() == newGameButton){
			gamestate.newGame();
		}
	}	
}