package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/** Main methods starts main-method
 * */

public class GomokuMain{
	
	/** main initiates:
	 *  	GomokuClient(port)
	 *  	GomokuGameState(client)
	 *  	GomokuGUI(gamestate, client)
	 * */
	
	public static void main(String[] args){
		
		GomokuClient client = new GomokuClient(0);
		GomokuGameState gamestate = new GomokuGameState(client);
		
		new GomokuGUI(gamestate, client);
		
	}
}