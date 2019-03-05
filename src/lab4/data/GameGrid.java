package lab4.data;

import java.util.Observable;

/**
 * @author Elsa Jonsson & Pawel Dzialo
 * 
 */

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable {
	int grid[][];
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = 2;
	public static final int INROW = 3;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            The width/height of the game grid
	 */
	public GameGrid(int size) {
		this.grid = new int[size][size];
		clearGrid();
	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y) {
		return this.grid[x][y];
	}

	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize() {
		return this.grid[0].length;
	}

	/**
	 * Enters a move in the game grid
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player) {
		if (this.grid[x][y] == EMPTY) {
			this.grid[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid() {
		for (int i = 0; i < this.getSize(); i++) {
			for (int j = 0; j < this.getSize(); j++) {
				this.grid[i][j] = EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player
	 *            the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player) {
		boolean inRow = false;

		for (int i = 0; i < this.grid[0].length - (INROW - 1); i++) {
			for (int j = 0; j < this.grid[0].length - (INROW - 1); j++) {
				if (this.grid[i][j] == player) {
					for (int k = 0; k < INROW; k++) {
						if (this.grid[i + k][j + k] == player) {
							inRow = true;
							continue;
						} else {
							inRow = false;
							break;
						}
					}
					if (inRow == true) {
						break;
					}
					for (int k = 0; k < INROW; k++) {
						if (this.grid[i + k][j] == player) {
							inRow = true;
							continue;
						} else {
							inRow = false;
							break;
						}
					}
					if (inRow == true) {
						break;
					}
					if (inRow == true) {
						break;
					}
					for (int k = 0; k < INROW; k++) {
						if (this.grid[i][j + k] == player) {
							inRow = true;
							continue;
						} else {
							inRow = false;
							break;

						}
					}
					if (inRow == true) {
						break;
					}
				}
			}
			if (inRow == true) {
				break;
			}
		}
		if (!inRow) {
			for (int i = 0 + (INROW - 1); i < getSize(); i++) {
				for (int j = 0; j < getSize() - (INROW - 1); j++) {
					if (this.grid[i][j] == player) {
						for (int k = 0; k < INROW; k++) {
							if (this.grid[i - k][j + k] == player) {
								inRow = true;
								continue;
							} else {
								inRow = false;
								break;

							}
						}
					}
				}
			}
		}
		return inRow;

	}
}