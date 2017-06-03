import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hidato {

	public static int patcher = 0;

	// The Scanner.
	public static Scanner input = new Scanner(System.in);

	public static int zeroX;
	public static int zeroY;

	// Board dimensions. For rows and cols.
	public static int boardDimension;

	// The board that is made up of HidatoCell objects.
	public static HidatoCell[][] board;

	// The final board which is used to create stuff.
	public static int[][] finalBoard;

	// The ArrayList to hold the values of the domain.
	public static ArrayList<Integer> domainHolder = new ArrayList<Integer>();

	// The ArrayList to hold the remaining Values of the domain. aka, the pieces
	// to place.
	public static ArrayList<Integer> remainingDomain = new ArrayList<Integer>();

	// The highest possible value for the hidatoBoard.
	public static int hidatoCellCeiling;

	public static String hidatoBoard;

	public static void main(String[] args) throws FileNotFoundException {

		hidatoBoard = args[0];

		// Reads in the board from the specified file.
		readInBoard();

		// Populate the board with HidatoCell objects.
		populateBoard();

		// Acquire the domain for the Hidato board.
		acquireDomain();

		// Acquire the remaining domain that is used to fill in the HidatoCells.
		acquireRemainingDomain();

		// Create the possible domain of numbers
		makeDomain();

		// Reduce the domains
		reduceDomains();

		// We have all possible combinations
		possibleCombinationsFinder();

	}

	public static void reduceDomains() {

		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {

				// Top Left Corner of board
				if (i == 0 && j == 0) {

					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}

					///
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}

					///
					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}

					// board[i + 1][j].getNumber();
					// board[i][j + 1].getNumber();
					// board[i + 1][j + 1].getNumber();

				}

				// Top right Corner of board.
				if (i == 0 && j == boardDimension - 1) {

					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					//

					// board[i][j].setDomain(board[i][j - 1].getNumber());
					// board[i][j].setDomain(board[i + 1][j - 1].getNumber());
					// board[i][j].setDomain(board[i + 1][j].getNumber());

				}

				// Bottom right Corner of board.
				if (i == boardDimension - 1 && j == boardDimension - 1) {

					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
		
				}

				// Bottom left Corner of board.
				if (i == boardDimension - 1 && j == 0) {

					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
		

				}

				// Far left middle pieces, not in corners
				if (i != 0 && j == 0 && i != boardDimension - 1) {

					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}


				}

				// Far right middle pieces, not in corners
				if (i != 0 && j == boardDimension - 1 && i != boardDimension - 1) {

					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}


				}

				// TOP MOST MIDDLE UPPER PIECES
				if (i == 0 && j != boardDimension - 1 && j != 0) {

					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}


				}

				// Bottom most LOWER PIECES MIDDLE
				if (i == boardDimension - 1 && j != boardDimension - 1 && j != 0) {

					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}


				}

				// "MIDDLE" PIECES
				if (i != 0 && j != 0 && i != boardDimension - 1 && j != boardDimension - 1) {

					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j - 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i - 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i - 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i - 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j + 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j + 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j + 1].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j].getNumber());
					}
					//

					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() + 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
					if (board[i][j].domain.size() != 1 && board[i + 1][j - 1].domain.size() == 1
							&& board[i][j].domain.contains(board[i + 1][j - 1].getNumber() - 1)) {
						board[i][j].domain.remove(board[i + 1][j - 1].getNumber());
					}
		

				}
			}
		}

	}

	public static void makeDomain() {

		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {

				// If the Hidato boards numberer is not 0, it's domain is the
				// number
				if (board[i][j].getNumber() != 0) {
					board[i][j].domain.add(board[i][j].getNumber());
				}

				// If the hidato boards number is 0, then the domain could be
				// any remaining
				// value
				if (board[i][j].getNumber() == 0) {
					for (int k = 0; k < remainingDomain.size(); k++) {

						board[i][j].domain.add(remainingDomain.get(k));
					}

				}

			}
		}

	}

	@SuppressWarnings("unchecked")
	private static void possibleCombinationsFinder() {
		List lists = new ArrayList<List<Integer>>();

		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {

				List<Integer> l1 = new ArrayList<Integer>();
				for (int k = 0; k < 1; k++) {

					Integer[] strArr = new Integer[board[i][j].domain.size()];
					board[i][j].domain.toArray(strArr);

					for (int kk = 0; kk < strArr.length; kk++) {
						l1.add(strArr[kk]);
					}

				}
				lists.add(l1);
			}
		}

		HidatoPermutations<Integer> iterable = new HidatoPermutations<Integer>(lists);
		for (List<Integer> element : iterable) {

			finalBoard = new int[boardDimension][boardDimension];

			int x = 0;
			for (int i = 0; i < boardDimension; i++) {

				for (int j = 0; j < boardDimension; j++) {
					finalBoard[i][j] = element.get(x);
					// System.out.println(finalBoard[i][j]);
					x++;
				}

			}

			int currentValue;
			boolean justMoved = false;
			int xCompare = -1;
			int yCompare = -1;
			int startX = zeroX;
			int startY = zeroY;

			int printSolution = 1;

			for (int finalTest = 0; finalTest < hidatoCellCeiling; finalTest++) {

				if (printSolution == hidatoCellCeiling) {

					System.out.println(boardDimension + "");
					for (int jj = 0; jj < boardDimension; jj++) {
						for (int kk = 0; kk < boardDimension; kk++) {

							System.out.print(finalBoard[jj][kk] + " ");
							if (kk == boardDimension - 1) {
								System.out.println("");
							}

						}
					}
					System.out.println("");
				}

				if (xCompare == startX && yCompare == startY) {

					break;
				}

				currentValue = finalBoard[startX][startY];

				justMoved = false;

				xCompare = startX;
				yCompare = startY;

				if (startX - 1 >= 0 && startY + 1 <= boardDimension - 1 && justMoved == false) {

					if (finalBoard[startX - 1][startY + 1] == currentValue + 1) {
						startX = startX - 1;
						startY = startY + 1;
						justMoved = true;
						printSolution++;

					}
				} // END ASSERTIVE STATEMENT.

				if (startX - 1 >= 0 && startY - 1 >= 0 && justMoved == false) {

					if (finalBoard[startX - 1][startY - 1] == currentValue + 1) {
						startX = startX - 1;
						startY = startY - 1;
						justMoved = true;
						printSolution++;
					}
				} // END ASSERTIVE STATEMENT.

				if (startX + 1 <= boardDimension - 1 && startY >= 0 && justMoved == false) {

					if (finalBoard[startX + 1][startY] == currentValue + 1) {
						startX = startX + 1;
						startY = startY;
						justMoved = true;
						printSolution++;
					}
				} // END ASSERTIVE STATEMENT.

				if (startX >= 0 && startY - 1 >= 0 && justMoved == false) {

					if (finalBoard[startX][startY - 1] == currentValue + 1) {
						startX = startX;
						startY = startY - 1;
						justMoved = true;
						printSolution++;
					}
				} // END ASSERTIVE STATEMENT.

				if (startX >= 0 && startY + 1 <= boardDimension - 1 && justMoved == false) {

					if (finalBoard[startX][startY + 1] == currentValue + 1) {
						startX = startX;
						startY = startY + 1;
						justMoved = true;
						printSolution++;
					}
				} // END ASSERTIVE STATEMENT.

				if (startX - 1 >= 0 && startY >= 0 && justMoved == false) {

					if (finalBoard[startX - 1][startY] == printSolution + 1) {
						if (currentValue == 10) {

						}
						startX = startX - 1;
						startY = startY;
						justMoved = true;

						printSolution++;
					}
				} // END ASSERTIVE STATEMENT.

				if (startX + 1 <= boardDimension - 1 && startY + 1 <= boardDimension - 1 && justMoved == false) {

					if (finalBoard[startX + 1][startY + 1] == currentValue + 1) {
						startX = startX + 1;
						startY = startY + 1;
						justMoved = true;
						printSolution++;

					}
				} // END ASSERTIVE STATEMENT.

				if (startX + 1 <= boardDimension - 1 && startY - 1 >= 0 && justMoved == false) {

					if (finalBoard[startX + 1][startY - 1] == currentValue + 1) {
						startX = startX + 1;
						startY = startY - 1;
						justMoved = true;
						printSolution++;
					}
				} // END ASSERTIVE STATEMENT.

			}

		}
	}

	public static void acquireRemainingDomain() {

		// The maximum value that is to be placed.
		hidatoCellCeiling = boardDimension * boardDimension;

		for (int i = 1; i < hidatoCellCeiling + 1; i++) {

			if (!domainHolder.contains(i)) {
				remainingDomain.add(i);
			}
		}

	}

	public static void acquireDomain() {

		// Iterate through the board and find numbers...
		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {

				int domainNumber = board[i][j].getNumber();
				if (domainNumber != 0) {
					domainHolder.add(domainNumber);
				}

			}
		}

	}

	// Reads in the game board from the text file.
	public static void readInBoard() throws FileNotFoundException {

		// Read in the file
		String hidatoFile = hidatoBoard;
		input = new Scanner(new File(hidatoFile));

		// Acquire board size for rows and cols.
		boardDimension = input.nextInt();

		// Initialize board to the size.
		board = new HidatoCell[boardDimension][boardDimension];

	}

	private static void populateBoard() {

		// Iterate through the boardDimension and populate board.
		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {
				if (input.hasNextInt()) {
					int me = input.nextInt();

					if (me == 1) {
						zeroX = i;
						zeroY = j;
					}
					board[i][j] = new HidatoCell(me);
				}

			}
		}

	}
}
