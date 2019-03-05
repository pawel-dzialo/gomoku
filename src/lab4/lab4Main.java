package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class lab4Main {
	public static void main(int[] args) {
		if (args.length == 1) {
			if (args[0] > 0) {
				GomokuClient gc = new GomokuClient(args[0]);
				GomokuGameState test = new GomokuGameState(gc);
				GomokuGUI test2 = new GomokuGUI(test, gc);
			}

		} else {
			GomokuClient gc = new GomokuClient(4001);
			GomokuGameState gameState = new GomokuGameState(gc);
			GomokuGUI gui = new GomokuGUI(gameState, gc);
		}
	}
}
