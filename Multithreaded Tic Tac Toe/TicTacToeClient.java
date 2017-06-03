/**
 * Jarred Price (pricejr3)
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicTacToeClient {

	// Instantiates a scanner object to use.
	public Scanner input = new Scanner(System.in);

	// Initializes a String to obtain the IP from user.
	public String ipString = "";

	// Initializes an InetAddress object.
	public InetAddress ip = null;

	// Initializes a Socket object.
	public Socket s = null;

	// Initializes a DataOutputStream object to null.
	public DataOutputStream sendStream = null;

	// Initializes a DataInputStream object to null.
	public DataInputStream receiveStream = null;

	// Initializes the clientWon to false.
	public boolean clientWon = false;

	// Initializes the serverWon to false.
	public boolean serverWon = false;

	// Initializes a new 2D array board to null.
	public int board[][] = null;

	// Initializes the rows of the board to 3.
	public int rows = 3;

	// Initializes the cols of the board to 3.
	public int cols = 3;

	// Initializes the integer statusCode.
	public int statusCode;

	// Initializes the final integer SERVER_PORT to 32100.
	final int SERVER_PORT = 32100;

	/**
	 * The constructor for the TicTacToeClient Class.
	 * 
	 */
	public TicTacToeClient() throws IOException {
		welcomeMessage();
		createSocketandStreams();
		playGame();
		closeStreams();
	} // end constructor

	/**
	 * Runs the tic-tac-toe game and creates a board as well as the parts that
	 * will populate the board
	 * 
	 */
	public void playGame() throws IOException {
		// Create the board
		int board[][] = new int[rows][cols];
		// Populates the board with blank characters
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = ' ';
			}
		}

		do {

			printBoard(board);
			userPlay(board);
			printBoard(board);
			readAndPrintStatusCode(board);
			
			if (statusCode == 10) {
				compPlay(board);
				printBoard(board);
				readAndPrintStatusCode(board);
				
			}

		} while (statusCode == 10);
	}

	/**
	 * Receives the status code from the client
	 * 
	 * @param board
	 *            The board that is used.
	 * 
	 * @return statusCode The statusCode that is returned
	 */
	public void readAndPrintStatusCode(int[][] board) throws IOException {
		// Reads in the statusCode from the server.
		statusCode = receiveStream.readInt();

		// These if statements determine what will be printed to the user.
		if (statusCode == 10) {
			System.out
					.println("Still empty squares on the board. No winner. Game continues.");
		} else if (statusCode == 20) {
			System.out
					.println("No empty squares on the board. Draw. Game is over.");
		} else if (statusCode == 30) {
			System.out
					.println("'O' (the server) has won the game. Game is over.");
		} else if (statusCode == 40) {
			System.out
					.println("'X' (the client) has won the game. Game is over.");
		} else if (statusCode == 50) {
			System.out.println("Illegal move. Game is over.");
		} else {
			System.out.println("Error in statusCode");
		}
		
		//System.out.println("Status code = " + statusCode);

	}

	/**
	 * Closes the streams and DataOutput/DataInputStreams
	 * 
	 */
	public void closeStreams() throws IOException {
		s.close();
		sendStream.close();
		receiveStream.close();

	}

	/**
	 * Prints out the board so the client can see it
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 * 
	 */
	void printBoard(int[][] board) {

		// Creates the board by iterating through loop and printing
		// everything that is essential.
		System.out.println("");
		for (int r = 0; r < rows; r++) {
			System.out.print("|");
			for (int c = 0; c < cols; c++) {
				if (board[r][c] == -1) {
					System.out.print(" X |");
				} else if (board[r][c] == 1) {
					System.out.print(" O |");
				} else {
					System.out.print(" _ |");
				}
			}
			System.out.println();
		}

	}

	/**
	 * Allows the user to pick a spot for their X and sends it to server.
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe.
	 * @return r The row if it it legal.
	 * @return c The col if it is legal.
	 * 
	 */
	public void userPlay(int[][] board) throws IOException {
		// Displays these statements to the user and receives the values.
		System.out.println("Your Move (enter negative number to quit).");
		System.out.print("Enter row: ");
		int r = input.nextInt();
		System.out.println("Enter column: ");
		int c = input.nextInt();

		// Allows only spots to be inputed that have not been used.
		while (board[r][c] != ' ') {
			System.out.println("You can't choose the same spot: ");
			System.out.print("Enter row: ");
			r = input.nextInt();
			System.out.println("Enter column: ");
			c = input.nextInt();
		}

		// The board equals -1 at this location.
		board[r][c] = -1;

		// Sends both r and c to the server
		sendStream.writeInt(r);
		sendStream.writeInt(c);

	}

	/**
	 * Receives the move from the computer and places them into the
	 * TicTacToeClient 2D array board.
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 */
	public void compPlay(int[][] board) throws IOException {

		// Receives the values from the server.
		int r = receiveStream.readInt();
		int c = receiveStream.readInt();
		

		board[r][c] = 1;
		System.out.println("Computer chose row: " + r + " , column: " + c);
		System.out.println("");

	}

	/**
	 * Prints a welcome message and obtains IP address from client.
	 * 
	 */
	public void welcomeMessage() {
		// First, welcome message and display the board
		System.out.println("Welcome to TCP Tic-tac-toe! ");
		System.out.println("The human player always goes first! ");
		System.out.println("Enter your system's IP Address: ");
		ipString = input.nextLine();

		// If user just presses return, IP is set to this default value.
		if (ipString.length() == 0) {

			ipString = "127.0.0.1";
		}

		try {
			ip = InetAddress.getByName(ipString);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the sockets and streams that are used in the TicTacToeClient.
	 * 
	 */
	public void createSocketandStreams() throws IOException {
		// Creates a new socket from the user inputed IP and given server port.
		s = new Socket(InetAddress.getByName(ipString), SERVER_PORT);

		// Creates the streams.
		sendStream = new DataOutputStream(s.getOutputStream());
		receiveStream = new DataInputStream(s.getInputStream());

	}

	public static void main(String[] args) throws IOException
	{
		// Runs the TicTacToeClient every time the class is ran.
		new TicTacToeClient();
	} // end main
} // end TicTacToeClient class