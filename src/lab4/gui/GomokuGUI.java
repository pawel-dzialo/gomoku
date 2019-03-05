package lab4.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/**
 * The GUI class
 */

/**
 * @author Elsa Jonsson & Pawel Dzialo
 *
 */
public class GomokuGUI extends JFrame implements Observer, MouseListener {

	private GomokuClient client;
	private GomokuGameState gamestate;
	private JButton connectButton;
	private JButton disconnectButton;
	private JButton newGameButton;
	private JLabel messageLabel;
	private JPanel gameWindow;
	private GamePanel gamePanel;

	/**
	 * The constructor
	 * 
	 * @param g
	 *            The game state that the GUI will visualize
	 * @param c
	 *            The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c) {
		super("Gomoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.client = c;
		this.gamestate = g;
		this.gamePanel = new GamePanel(g.getGameGrid());
		this.gameWindow = new JPanel();
		client.addObserver(this);
		gamestate.addObserver(this);

		// **************************************************

		connectButton = new JButton("Connect");
		disconnectButton = new JButton("Disconnect");
		newGameButton = new JButton("New Game");
		messageLabel = new JLabel(g.getMessageString());

		// **************************************************

		gameWindow.add(gamePanel);
		gameWindow.add(connectButton);
		gameWindow.add(disconnectButton);
		gameWindow.add(newGameButton);
		gameWindow.add(messageLabel);

		// **************************************************

		gameWindow.setPreferredSize((new Dimension(350, 380)));
		this.setContentPane(gameWindow);

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow connectionWindow = new ConnectionWindow(c);
			}
		});

		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				g.disconnect();
			}
		});

		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				g.newGame();

			}
		});
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				int position[] = gamePanel.getGridPosition(x, y);
				g.move(position[0], position[1]);
			}
		});
		this.setVisible(true);
		this.pack();

	}

	public void update(Observable arg0, Object arg1) {

		// Update the buttons if the connection status has changed
		if (arg0 == client) {
			if (client.getConnectionStatus() == GomokuClient.UNCONNECTED) {
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			} else {
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}

		// Update the status text if the gamestate has changed
		if (arg0 == gamestate) {
			messageLabel.setText(gamestate.getMessageString());
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}