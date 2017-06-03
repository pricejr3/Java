/**
 * Jarred Price (pricejr3)
 */

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeServer 
{
	// Socket for listening for clients
	ServerSocket serverSocket = null;

	// Counter for tracking number of clients
	int clientCount = 1;

	// Port number for server to listen on
	final int SERVER_PORT = 32100;

	// Flag for controlling listening loop
	// (Could be set to false to cause the server to stop
	// listening for clients
	boolean listening = true;

	/**
	 * Constructor for the TCPListen class. It creates a ServerSocket to use for
	 * listening for clients. Displays the port number and IP address for the
	 * ServerSocket to the console and then enters an infinite loop to listen
	 * for clients. Spawns a new TCPListenThread object to handle each client.
	 * 
	 */
	public TicTacToeServer() throws IOException {
		try {
			// Instantiate socket to listening.
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {

			// Port not available.
			System.err.println("Could not listen on port: " + SERVER_PORT);
			System.exit(-1);
		}

		// Display socket information.
		displayContactInfo();

		// Enter infinite loop to listen for clients
		TicTacToeListenForClients();

		// Close the socket (unreachable code)
		serverSocket.close();

	} // end TCPListen constructor

	/**
	 * Diplays IP address and port number that clients can use to contact the
	 * server to the console.
	 * 
	 */
	protected void displayContactInfo() {
		try {
			// Display contact information.
			System.out.println("Number Server standing by to accept Clients:"
					+ "\nIP Address: " + InetAddress.getLocalHost()
					+ "\nPort: " + serverSocket.getLocalPort() + "\n\n");

		} catch (UnknownHostException e) {
			// NS lookup for host IP failed?
			// This should only happen if the host machine does
			// not have an IP address.
			e.printStackTrace();
		}

	} // end displayContactInfo

	/*
	 * Infinite loop for listing for new clients. Spawns a new TCPListenThread
	 * object to handle each client.
	 */
	protected void TicTacToeListenForClients() throws IOException {
		while (listening) {

			// Create new thread to hand each client.
			// Pass the Socket object returned by the accept
			// method to the thread.
			new TTTListenThread(serverSocket.accept(), clientCount).start();

			clientCount++;
		}

	} // end listenForClients

	/*
	 * Instantiates a Server the listens for clients that are going to send a
	 * series of numbers.
	 */
	public static void main(String[] args) throws IOException
	{
		// Instantiate Server
		new TicTacToeServer();

	} // end main

} // end TCPListen class

/*
 * Class that defines a runnable object (extends Thread) that can handle a
 * single Tic-Tac-Toe client.
 */
class TTTListenThread extends Thread {

	// Handle for the Socket used for communicating with a
	// client.
	protected Socket socket = null;

	// Integer ID for the client.
	protected int clientNumber;

	// The string that holds the user inputted IP address
	public String ipString = "";

	// Creates an InetAddress ip object.
	public InetAddress ip = null;

	// Instantiates a new Scanner object.
	public Scanner input = new Scanner(System.in);

	// Instantiates a new DataOutputStream object.
	public DataOutputStream sendDataStream = null;

	// Instantiates a new DataInputStream object.
	public DataInputStream receiveDataStream = null;

	// Instantiates a new ServerSocket object.
	public ServerSocket ss = null;

	// Instantiates a new Socket object.
	Socket s = null;

	// Instantiates the Server Port as a final.
	public static final int SERVER_PORT = 32100;

	// Instantiates the status code to send.
	int statusCode = 90;

	// Instantiates the boolean value of clientWon to false.
	boolean clientWon = false;

	// Instantiates the boolean value of serverWon to false.
	boolean serverWon = false;

	// Instantiates the boolean value of isGameTie to false.
	boolean isGameTie = false;

	// Instantiates the rows of the board to 3.
	int rows = 3;

	// Instantiates the cols of the board to 3.
	int cols = 3;

	// Instantiates the move to 0.
	int move = 0;

	// Instantiates the final int values of CONTINUE_GAME to be used in status
	// code.
	public final int CONTINUE_GAME = 10;

	// Instantiates the final int values of TIE_GAME to be used in status
	// code.
	public final int TIE_GAME = 20;

	// Instantiates the final int values of SERVER_WON to be used in status
	// code.
	public final int SERVER_WON = 30;

	// Instantiates the final int values of CLIENT_WON to be used in status
	// code.
	public final int CLIENT_WON = 40;

	// Instantiates the final int values of ILLEGAL_MOVE to be used in status
	// code.
	public final int ILLEGAL_MOVE = 50;

	/*
	 * Sets up class data members. Displays the communication info.
	 */
	public TTTListenThread(Socket socket, int clientNumber) {
		// Call the super class (Thread) constructor.
		super("TTTListenThread_" + clientNumber);

		// Save a reference to the Socket connection
		// to the client.
		this.socket = socket;

		// Save the ID for the client
		this.clientNumber = clientNumber;

		displayClientInfo();

	} // end TCPListenThreadconstructor

	/*
	 * Displays IP address and port number information associated with a
	 * particular client.
	 */
	protected void displayClientInfo() {
		// Display IP address and port number client is using.
		System.out.println("Client " + clientNumber + " IP address: "
				+ socket.getInetAddress() + "\nClient Port number: "
				+ socket.getPort());

	} // end displayClientInfo

	void statusCode(int[][] board) throws IOException {

		if (isGameWonClient(board) == false && isGameWonServer(board) == false
				&& isGameTie(board) == false && isIllegalMove(move) == false) {
			statusCode = CONTINUE_GAME;
		} else if (isGameWonServer(board) == true) {
			statusCode = SERVER_WON;
		} else if (isGameTie(board) == true) {
			statusCode = TIE_GAME;
		} else if (isGameWonClient(board) == true) {
			statusCode = CLIENT_WON;
		} else if (isIllegalMove(move) == true) {
			statusCode = ILLEGAL_MOVE;
		}

		// Sends the statusCode to the client.
		sendDataStream.writeInt(statusCode);

	}

	/*
	 * Checks to see if an illegal move occurred.
	 * 
	 * @param move The move that is used in tic-tac-toe
	 * 
	 * @return boolean The boolean value that is returned
	 */
	public boolean isIllegalMove(int move) {
		if (move < 0)
			return true;
		else
			return false;

	}

	/**
	 * Checks to see if the game is tied.
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 * @return isGameTie Returns a boolean value if the game is tied
	 */
	public boolean isGameTie(int[][] board) throws IOException {

		if ((board[0][0] == -1 || board[0][0] == 1) && 
				
				(board[0][1] == -1 || board[0][1] == 1) && (board[0][2] == -1 || board[0][2] == 1)
				
				&& (board[1][0] == -1 || board[1][0] == 1) && (board[1][1] == -1 || board[1][1] == 1) 
				
				&& (board[1][2] == -1 || board[1][2] == 1) && (board[2][0] == -1 || board[2][0] == 1) 
				
				&& (board[2][1] == -1 || board[2][1] == 1) && (board[2][2] == -1 || board[2][2] == 1)) {

			// Returns true is the game is tied.
			isGameTie = true;
		}
		// Returns false if the game is not tied.
		return isGameTie;

	}

	/**
	 * Receives the users turn and puts it into its board
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 * @return statusCode The statusCode that is returned
	 */
	public void userPlay(int[][] board) throws IOException {

		// Receive the r and c values from the client.
		int r = receiveDataStream.readInt();
		
		int c = receiveDataStream.readInt();
		

		board[r][c] = -1;

	}

	/**
	 * Checks to see if the client won the game
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 * @return clientWon Returns a boolean value if the client won
	 */
	public boolean isGameWonClient(int[][] board) throws IOException {
		
		if ((board[0][0] == -1 && board[0][1] == -1 && board[0][2] == -1) ||

				(board[1][0] == -1 && board[1][1] == -1 && board[1][2] == -1) ||

				(board[2][0] == -1 && board[2][1] == -1 && board[2][2] == -1)||

				(board[0][0] == -1 && board[1][0] == -1 && board[2][0] == -1) ||

				(board[0][1] == -1 && board[1][1] == -1 && board[2][1] == -1) ||

				(board[0][2] == -1 && board[1][2] == -1 && board[2][2] == -1) ||

				(board[0][0] == -1 && board[1][1] == -1 && board[2][2] == -1) ||

				(board[2][0] == -1 && board[1][1] == -1 && board[0][2] == -1)) {
			
			clientWon = true;
		}

		return clientWon;
	}

	/**
	 * Checks to see if the server won the game
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 * @return serverWon Returns a boolean value if the server won
	 */
	public boolean isGameWonServer(int[][] board) throws IOException {

		if ((board[0][0] == 1 && board[0][1] == 1 && board[0][2] == 1) ||

				(board[1][0] == 1 && board[1][1] == 1 && board[1][2] == 1) ||

				(board[2][0] == 1 && board[2][1] == 1 && board[2][2] == 1) ||

				(board[0][0] == 1 && board[1][0] == 1 && board[2][0] == 1) ||

				(board[0][1] == 1 && board[1][1] == 1 && board[2][1] == 1) ||

				(board[0][2] == 1 && board[1][2] == 1 && board[2][2] == 1) ||

				(board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1) ||

				(board[2][0] == 1 && board[1][1] == 1 && board[0][2] == 1)) {
			
			serverWon = true;
		}
		// Returns the status of serverWon.
		return serverWon;
	}

	/**
	 * Randomly generates a move for the computer
	 * 
	 * @param board
	 *            The board that is used in tic-tac-toe
	 * 
	 * @return rows Writes the row to the client
	 * @return col Writes the col to the client
	 */
	public void compPlay(int[][] board) throws IOException {

		// Creates a random object, rand.
		Random rand = new Random();

		// Chooses a place for the computer to make a move.
		for (int rows = rand.nextInt(3); rows < board.length; rows++) {
			for (int col = rand.nextInt(3); col < board[0].length; col++) {
				if (board[rows][col] == ' ') { // empty cell
					board[rows][col] = 1;
					sendDataStream.writeInt(rows);
					sendDataStream.writeInt(col);
					return;
				}
			}
		}
	}

	/**
	 * Overrides run method of the Thread class. Create appropriate stream
	 * objects for receiving numeric data from a client. Closes out the Socket
	 * connection after all data has been received.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run()

	{

		try {

			// Changes the streams from null to using the sockets.
			receiveDataStream = new DataInputStream(socket.getInputStream());

			// Changes the streams from null to using the sockets.
			sendDataStream = new DataOutputStream(socket.getOutputStream());

			// Creates a new board for the server to keep track.
			int board[][] = new int[rows][cols];

			// Populates the board with empty spaces.
			for (int i = 0; i < board.length; i++)
				for (int j = 0; j < board[0].length; j++)
					board[i][j] = ' ';

			// Loop for the game to play and to communicate with client.
			do {
				userPlay(board);
				statusCode(board);
				if (statusCode == CONTINUE_GAME) {
					compPlay(board);
					statusCode(board);
				}

			} while (statusCode == CONTINUE_GAME);

		} catch (IOException e) {
			System.err.println("Error in communication with client "
					+ clientNumber);
			e.printStackTrace();
		}

		// Display closing message.
		System.out.println("All data received.\n"
				+ "Closing connection. Bye\n\n");
		// Close the Socket connection with the client.
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// end run

} // end TicTacToeServer class
