

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer
{
	// Initializes an integer value to receive form the client to use in switch
	// statement.
	int value;

	// Initializes a string value of the screenname that is used.
	String name = "";

	// Initializes a final server port of 32150.
	static final int SERVER_PORT = 32150;

	// Initializes a ServerSocket object used in the server to work with client.
	ServerSocket sSock = null;

	// Initializes an InetSocketAddress object used in the server to work with
	// client.
	InetSocketAddress sentInt = null;

	// Initializes an InetSocketAddress object to be used in adding to
	// hashtable.
	InetSocketAddress clientInfo = null;

	// Initializes a byte array called clientIP to be used in receiving IP.
	byte[] cIP = new byte[4];

	// Initializes an integer value of the clientPort to 0, will change given
	// clientport.
	int clientPort = 0;

	// Initializes DataOut/InputStreams and sets them to null.
	DataOutputStream out = null;
	DataInputStream in = null;

	// Initializes a ConcurrentHashMap to use to keep track of clients.
	ConcurrentHashMap<String, InetSocketAddress> clientMap = null;

	public static void main(String[] args) {

		// Runs a new instance of ChatServer.
		new ChatServer();

	}

	public ChatServer() {

		// Initializes the ConcurrentHashMap from null to using
		// InetSocketAddress and Username
		clientMap = new ConcurrentHashMap<String, InetSocketAddress>();

		try {

			// Calls serverSock to create a new ServerSocket object.
			serverSock();

			// Calls method to display Contact Information.
			displayContactInfo();

		} catch (IOException e) {
			// Prints in the case that these previous things could not be
			// created.
			System.err.println("ERROR: Could not create server socket!");
			e.printStackTrace();
		}

		// Calls the method to accept and start the process.
		clientListen();
	}

	/**
	 * Reads in the IP from clients.
	 * 
	 */
	protected void readNameIPS() {

		// Reads name.
		readName();

		// Reads in the IP from chatClient.
		readIP();

		// Reads in clientPort.
		readClientPort();

	}

	/**
	 * Reads in the Port.
	 * 
	 */
	protected void readClientPort() {
		try {
			// Reads in the clientPort number from chatClient.
			clientPort = in.readInt();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	/**
	 * Reads in the IP from clients.
	 * 
	 */
	protected void readIP() {

		try {
			in.read(cIP);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Reads in the name from clients.
	 * 
	 */
	protected void readName() {

		try {
			// Reads in the clientname from chatClient.
			name = in.readUTF();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Creates the new ServerSocket: sSock.
	 * 
	 */
	protected void serverSock() throws IOException {
		// Creates a new ServerSocket: sSock with the given SERVER_PORT.
		// sSock = new ServerSocket(ChatClient.SERVER_PORT);
		sSock = new ServerSocket(SERVER_PORT);

	}

	/**
	 * Displays all contact information.
	 * 
	 */
	protected void displayContactInfo() {
		try {
			System.out.println("ChatServer standing by to accept Clients:"
					+ "\nIP : " + InetAddress.getLocalHost() + "\nPort: "
					+ sSock.getLocalPort() + "\n\n");
		} catch (UnknownHostException e) {
			System.err.println("ERROR: Couldn't get contact info!");
			e.printStackTrace();
		}
	}

	/**
	 * Accepts the serverSocket and starts the process.
	 * 
	 */
	protected void clientListen() {
		do {
			try {
				// Accepts the serverSocket and starts.
				new clientRequest(sSock.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Does this all the time.
		} while (true);
	}

	/**
	 * Follows client requests and extends the Thread class by inheriting all of
	 * the methods of Thread.
	 * 
	 */
	class clientRequest extends Thread {

		// Initializes a Socket to be used in clientRequest
		Socket cSock = null;

		/**
		 * Sets the cSock to the cSock that has been passed into it.
		 * 
		 */
		public clientRequest(Socket cSock) {

			// The cSock Socket is equal to the one sent in.
			this.cSock = cSock;
			System.out.println("Socket received!");
		}

		/**
		 * Allows for the class to be multi-threaded and creates streams and
		 * reads in a value to determine what the client wants to do.
		 * 
		 * @override the run class of Thread
		 * 
		 */
		public void run() {
			try {

				// Creates the streams by calling the method.
				createStreams();

				// The value sent by the client determines
				// what is done here using switch-statements.

				value = in.readInt();

				// 10 will remove the client from hashtable.

				if (value == 10) {
					// Reads in the client username.
					name = in.readUTF();

					// Removes this username from the clientMap hash.
					clientMap.remove(name);
				}

				// 20 will get information
				if (value == 20) {
					try {

						// Reads in the name.
						name = in.readUTF();

						if (!clientMap.containsKey(name)) {

							out.writeBoolean(false);

						}

						else {

							sentInt = clientMap.get(name);
							cIP = sentInt.getAddress().getAddress();
							clientPort = sentInt.getPort();

							out.writeBoolean(true);
							out.write(cIP);
							// out.write(ture);
							out.writeInt(clientPort);

						}

					} catch (IOException e) {
						System.err
								.println("ERROR: Could not find info properly!");
						e.printStackTrace();
					}
				}

				if (value == 30) {
					readNameIPS();

					clientInfo = new InetSocketAddress(cSock.getInetAddress(),
							clientPort);

					clientMap.put(name, clientInfo);
				}

			} catch (IOException e) {
				System.err.println("ERROR!!!!! :(");
			}

			closeStreams();
		}

		/**
		 * Closes all streams such as the sockets and DataInput/OutputStreams.
		 * 
		 */
		protected void closeStreams() {

			try {
				// Closes the clientSocket.
				cSock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// Closes the DataOutputStream
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// Closes the DataInputStream
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Creates all streams that are used (DataInput/OutputStreams).
		 * 
		 */
		protected void createStreams() throws IOException {

			// Sets the stream out to the cSock outputstream.
			out = new DataOutputStream(cSock.getOutputStream());

			// Sets the stream in to the cSock inputstream.
			in = new DataInputStream(cSock.getInputStream());
		}

	}

}
