/**
 * Jarred Price (pricejr3)

 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient extends Thread 
{
	// Initializes a scanner object.
	Scanner input = new Scanner(System.in);

	// Initializes a Socket to be used.
	Socket cSock = null;

	// Initializes a Socket to be used with listening for the client.
	Socket listenClient = null;

	// Initializes ServerSockets.
	ServerSocket connection = null;
	ServerSocket clientListener = null;

	// Initializes byte arrays for IP address and related functions.
	byte[] ip = null;

	// Initializes an ArrayList of chatThreads to keep track of usernames.
	ArrayList<ChatThread> clientConnections = new ArrayList<ChatThread>();

	// Initializes integer values to process port functions and values.
	int clientPort = 0;
	int portTrue = 0;

	// Initializes String objects for given names and to find the names.
	String name = "";
	String findName = "";

	// Initializes a boolean values to determine if the thread still listens and
	// if the client has registered.
	boolean listening = true;
	boolean isRegistered = false;

	// Initializes the DataInput/OutputStreams to null.
	DataInputStream in = null;
	DataOutputStream out = null;

	public static void main(String[] args) throws IOException {

		// Starts the ChatClient up.
		new ChatClient();

	}

	/**
	 * Runs the program and starts the ChatClient process.
	 * 
	 */
	public ChatClient() throws IOException {

		// Runs the clientTimer method.
		clientTimer();

		// Runs the method to ask for username.
		askForUserName();

		// Registers username to server.
		register();

		// Starts the process.
		start();

		// Listens for clients.
		listen();

	}

	/**
	 * Asks the client for their username to be used with the ChatServer.
	 * 
	 */
	protected void askForUserName() {

		// Asks for username.
		System.out.println("Please enter your Screenname: ");
		name = input.nextLine();

	}

	/**
	 * Starts the connection with the chatServer and initializes all streams
	 * used in the process.
	 * 
	 */
	protected void startConnect() throws Exception {

		// Runs the method that creates the data streams.
		makeOutputInputStreams();
	}

	/**
	 * Closes all streams involved in the process. Closes the sockets,
	 * DataInputStream and DataOutputStream.
	 * 
	 */
	protected void closeStreams() {

		try {
			// Closes the client Socket.
			cSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// Closes the DataInputStream.
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// Closes the DataOutputStream.
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Registers new instances of ChatClients with the ChatServer by starting a
	 * connection, registering name, registering IP, registering port and then
	 * closes the streams that were opened for this process.
	 */
	protected void register() {
		try {

			// Starts the connection.
			startConnect();

			// Registers the name to the server.
			registerName();

			// Registers their IP to the server.
			registerIP();

			// Registers their port to the server.
			registerPort();

			System.out.println(name
					+ "; you are now registered with the Server...");

			// Closes all streams.
			closeStreams();

		} catch (Exception e) {
			System.err.println("ERROR: Can't connect nor register!!!");
			e.printStackTrace();
		}
	}

	/**
	 * Adds the names to the arrayList if they are existant.
	 * 
	 */
	protected void startChatIfExistant() throws IOException {

		in.read(ip);
		portTrue = in.readInt();

		addToArrayList();

	}

	/**
	 * Allows the ChatClients to register their ports to the port of the
	 * clientListener.
	 * 
	 */
	public void registerPort() {

		// Sets the clientPort to the port of the clientListener.
		clientPort = clientListener.getLocalPort();
		try {
			out.writeInt(clientPort);
		} catch (IOException f) {
			f.printStackTrace();
		}

	}

	/**
	 * Registers the clients IP with the ChatServer.
	 * 
	 */
	public void registerIP() {

		try {
			// Sets the IP address to the localhost address.
			ip = InetAddress.getLocalHost().getAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
			// Writes this ip out to the server.
			out.write(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds the new ChatThread instance created by passing in the name and
	 * InetSocketAddress to the arrayList of clientConnections. This is how the
	 * arrayList keeps track of the clients.
	 * 
	 */
	protected void addToArrayList() throws UnknownHostException, IOException {

		// Adds the new ChatThread with a given name and socket to
		// clientConnections.
		clientConnections.add(new ChatThread(name, new Socket(InetAddress
				.getByAddress(ip), portTrue)));

	}

	/**
	 * Registers the clients name to the Chatserver by sending in a CODE to the
	 * ChatServer to tell them what the client wants to be done.
	 * 
	 */
	protected void registerName() {

		try {
			/*
			 * If registering to client, send 30 to the ChatServer. This is like
			 * what happened in the TicTacToe programs. Sending a CODE to
			 * signify something that needs to happen.
			 */
			out.writeInt(30);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// Sends the name of the client to the server.
			out.writeUTF(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a code to the ChatServer to determine if the client wants to
	 * receive the information of a given username.
	 */
	protected void receiveInformation(String str) {
		try {

			// Starts connections
			startConnect();

			// Sends code and name to the server.
			out.writeInt(20);
			out.writeUTF(str);

			// Runs method to see if it is registered.
			isRegistered(str);

			// Closes all streams.
			closeStreams();

		} catch (Exception e) {
			System.err
					.println("Unable to connect and register with ChatServer.");
			e.printStackTrace();
		}
	}

	protected void isRegistered(String str) {

		// See if it was actually registered by reading it in.
		try {
			isRegistered = in.readBoolean();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (isRegistered != true) {
			System.out
					.println(str + " was never registered with the server!!!");

		}

		if (isRegistered == true) {
			System.out.println("Looking for: " + str);
			System.out.println(str + " found!");
			System.out.println("You may now chat with " + str);
			try {
				startChatIfExistant();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Prompts user for screenname to search for and then sets listening to
	 * false if user decided to quit, then the program stops listening and
	 * terminated. If not, the user searches for a name and receiveInformation is
	 * called to determine the info.
	 * 
	 * @override The run method of the thread class.
	 * 
	 */
	public void run() {

		try {
			do {
				System.out
						.println("Please enter a Screenname to search or quit to quit:  ");
				findName = input.nextLine();
				String findName2 = findName.toUpperCase();

				if (findName2.equals("QUIT")) {

					listening = false;

					exitProgram();
				} else {

					receiveInformation(findName);
				}

			} while (listening);

			// Terminate all threads when not needed.
			terminateChatThreads();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Terminates all the chat threads when they are not needed and then clears
	 * out the arrayList.
	 * 
	 */
	protected void terminateChatThreads() {

		// If the arrayList is not empty, then they will terminate.
		if (clientConnections.size() != 0) {

			automaticGuiClose();
			clientConnections.clear();

		}

	}

	/**
	 * Iterates through the arrayList and then shuts the guis down.
	 * 
	 */
	protected void automaticGuiClose() {

		// Iterates through the arrayList and shuts them down.
		for (int i = 0; i < clientConnections.size(); i++) {

			// Finishes the gui.
			clientConnections.get(i).finish();
		}

	}

	/*
	 * public void setTimer(){
	 * 
	 * 
	 * setTimeOutException(23); }
	 */

	/**
	 * Sets a timer to allow the program to work properly. Sets the time to 1000
	 * ms, so there can be a 1 second limit if there is a timeout.
	 * 
	 */
	protected void clientTimer() {
		try {

			clientListener = new ServerSocket(0);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			clientListener.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Exits the program and sends the appropriate code to the chatServer.
	 * 
	 */
	protected void exitProgram() {
		try {

			startConnect();

			out.writeInt(10);
			out.writeUTF(name);

			System.out.println("Exiting....");
			System.out.println("...........");
			System.out.println("Exited!");

			closeStreams();

		} catch (Exception e) {
			System.err.println("ERROR: Unable to connect and exit program!");
			e.printStackTrace();
		}
	}

	/**
	 * Creates the streams used in the ChatClient.
	 * 
	 */
	protected void makeOutputInputStreams() throws IOException {

		try {
			// Sets the chatSocket to the ip/port number via what InetAddress
			// dictates are inputed.
			cSock = new Socket(InetAddress.getByName("127.0.0.1"),
					ChatServer.SERVER_PORT);

		} catch (Exception e) {
			throw e;
		}

		out = new DataOutputStream(cSock.getOutputStream());

		in = new DataInputStream(cSock.getInputStream());
	}

	/**
	 * Listens for ChatClients by accepting thme and then adding them to the
	 * arraylist. It catches socketTimeoutExpetions.
	 * 
	 */
	protected void listen() {

		while (listening) {

			try {

				listenClient = clientListener.accept();

				clientConnections.add(new ChatThread(name, listenClient));

			} catch (SocketTimeoutException e) {

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}