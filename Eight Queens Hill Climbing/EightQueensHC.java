/*
 * Jarred Price
 * 
 * Eight Queens Hill Climbing - Steepest Ascent
 */
public class EightQueensHC {

	public static int[][] board = new int[8][8];
	public static int[] queenLoc = new int[8];
	public static int optimalSolutions = 0;
	public static int nonOptimalSolutions = 0;

	public static void main(String[] args) {

		// Run the HC 10000 times
		for (int i = 0; i < 10000; i++) {
			start();
		}

		System.out.println("Optimal solutions: " + optimalSolutions);
		System.out.println("Non-Optimal solutions: " + nonOptimalSolutions);

	}

	/**
	 * Starts running of the program.
	 */
	public static void start() {

		boolean optimalSolutionFound = false;

		populateBoard();
		while (!optimalSolutionFound) {

		
			int[][] next = steepestAscentHC(board);
			int cost = findCost(board);

			if (cost <= findCost(next)) {

				break;
			}
			board = next;
		}

		if (findCost(board) == 0)
			optimalSolutionFound = true;
		if (findCost(board) > 0 || findCost(board) > 0)
			optimalSolutionFound = false;

		if (!optimalSolutionFound) {

			//System.out.println("A non-optimal solution: ");
		    //display(board);
			nonOptimalSolutions++;

		}

		if (optimalSolutionFound) {

			//System.out.println("An optimal solution: ");
			//display(board);
			optimalSolutions++;

		}


	}

	/**
	 *
	 * Populates the board with 8 queens and blank spaces randomly.
	 *
	 * 
	 * @return board the newly created board.
	 */
	public static int[][] populateBoard() {
		for (int[] queensBoard1 : board) {
			for (int i = 0; i < queensBoard1.length; i++) {
				queensBoard1[i] = ' ';
			}
		}
		for (int[] queensBoard1 : board) {
			int randCol = (int) (8 * Math.random());
			queensBoard1[randCol] = 'Q';
		}

		return board;
	}

	/**
	 * Displays the board for testing.
	 */
	public static void display(int[][] board) {

		for (int[] queensBoard : board) {
			for (int j = 0; j < board.length; j++) {
				System.out.print("|");
				System.out.print(Character.toString((char) queensBoard[j]));

			}
			System.out.println("|");
		}
		System.out.println();
	}

	/*
	 * Checks to see if the actual item at hand is held within the confines of
	 * the N-Queens game space.
	 */
	public static boolean inBounds(int rows, int cols) {
		return ((cols >= 0 && cols < 8) && (rows >= 0 && rows < 8));
	}

	/**
	 * Determines the cost
	 * 
	 * @param board
	 *            The board
	 * @return h The cost
	 */
	public static int findCost(int[][] board) {
		int h = 0;

		for (int k = 0; k < board.length; k++) {
			for (int l = 0; l < board.length; l++) {

				//
				/*
				 * if (board[k][l] == ' ') { h += distance(k, l, board);
				 * baseSize[k][l] = ' '; }
				 */
				if (board[k][l] == 'Q') {
					h += queenAttacking(k, l, board);

				}
			}
		}

		return h;
	}

	/**
	 * Checks to see if the Queen can attack another one.
	 * 
	 * @param rows
	 *            the rows
	 * @param cols
	 *            the columns to check
	 * @return canAttack The number of Queens in attacking distance
	 */
	public static int queenAttacking(int rows, int cols, int[][] board) {

		int[][] compareTo = new int[8][8];
		int canAttack = 0;
		for (int i = 1; i < board.length; i++) {

			int rowBounds = rows - i;
			int rowBounds2 = rows + i;
			int colBounds = cols + i;
			int colBounds2 = cols - i;
			int valueToAdd = 1;

			if (inBounds(rowBounds, colBounds) && 'Q' != compareTo[rowBounds][colBounds]) {

				if (board[rowBounds][colBounds] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rowBounds][colBounds] != 'Q') {
					valueToAdd = 0;
				}
				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rows, colBounds) && 'Q' != compareTo[rows][colBounds]) {
				if (board[rows][colBounds] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rows][colBounds] != 'Q') {
					valueToAdd = 0;
				}

				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rows, colBounds2) && 'Q' != compareTo[rows][colBounds2]) {

				if (board[rows][colBounds2] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rows][colBounds2] != 'Q') {
					valueToAdd = 0;
				}
				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rowBounds2, colBounds2) && 'Q' != compareTo[rowBounds2][colBounds2]) {

				if (board[rowBounds2][colBounds2] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rowBounds2][colBounds2] != 'Q') {
					valueToAdd = 0;
				}
				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rowBounds2, colBounds) && 'Q' != compareTo[rowBounds2][colBounds]) {

				if (board[rowBounds2][colBounds] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rowBounds2][colBounds] != 'Q') {
					valueToAdd = 0;
				}
				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rowBounds2, cols) && 'Q' != compareTo[rowBounds2][cols]) {

				if (board[rowBounds2][cols] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rowBounds2][cols] != 'Q') {
					valueToAdd = 0;
				}
				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rowBounds, colBounds2) && 'Q' != compareTo[rowBounds][colBounds2]) {

				if (board[rowBounds][colBounds2] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rowBounds][colBounds2] != 'Q') {
					valueToAdd = 0;
				}

				canAttack = canAttack + valueToAdd;
			}

			if (inBounds(rowBounds, cols) && 'Q' != compareTo[rowBounds][cols]) {

				if (board[rowBounds][cols] == 'Q') {
					valueToAdd = 1;
				}
				if (board[rowBounds][cols] != 'Q') {
					valueToAdd = 0;
				}
				canAttack = canAttack + valueToAdd;
			}
		}
		return canAttack;
	}

	/**
	 * Steepest Ascent Hill Climbing implementation.
	 * 
	 * @param board
	 *            The board.
	 * @return optimalBoard
	 */
	public static int[][] steepestAscentHC(int[][] board) {
		int[][] optimalBoard = board;
		int cost = findCost(board);

		for (int i = 0; (i < board.length); i++) {

			for (int k = 0; (k < board[i].length); k++) {

				if (k < queenLoc[i]) {

					board = new int[optimalBoard.length][optimalBoard.length];
					for (int m = 0; m < optimalBoard.length; m++) {
						System.arraycopy(optimalBoard[m], 0, board[m], 0, optimalBoard[m].length);
					}

					int[] loc = new int[board.length];
					for (int a = 0; a < board.length; a++) {
						for (int j = 0; j < board.length; j++) {
							if (board[a][j] == 'Q')
								loc[a] = j;
						}
					}

					queenLoc = loc;

					board[i][queenLoc[i]] = ' ';
					board[i][k] = 'Q';
					int qCost = findCost(board);

					if (qCost < cost) {

						optimalBoard = new int[board.length][board.length];
						for (int m = 0; m < board.length; m++) {
							System.arraycopy(board[m], 0, optimalBoard[m], 0, board[m].length);
						}

						cost = qCost;
					}
				}

				if (k == queenLoc[i]) {

				}

				if (k > queenLoc[i]) {

					board = new int[optimalBoard.length][optimalBoard.length];
					for (int m = 0; m < optimalBoard.length; m++) {
						System.arraycopy(optimalBoard[m], 0, board[m], 0, optimalBoard[m].length);
					}

					int[] loc = new int[board.length];
					for (int x = 0; x < board.length; x++) {
						for (int y = 0; y < board.length; y++) {
							if (board[x][y] == 'Q')
								loc[x] = y;
						}
					}

					queenLoc = loc;

					board[i][queenLoc[i]] = ' ';
					board[i][k] = 'Q';
					int qCost = findCost(board);
					if (qCost > cost) {
						// Do nothing

					}
					if (qCost < cost) {

						optimalBoard = new int[board.length][board.length];
						for (int m = 0; m < board.length; m++) {
							System.arraycopy(board[m], 0, optimalBoard[m], 0, board[m].length);
						}

						cost = qCost;
					}
				}
			}
		}
		return optimalBoard;
	}

}