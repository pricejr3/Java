/**
 * Jarred Price (pricejr3)
 */

// Import declarations.
import java.io.*;
import java.net.*;

/**
 * Runs and creates a multi-threaded Web Server to use with user
 * created website as well as handling HTTP requests.
 * 
 */
public class WebServer
{
	// Hard coded SERVER_PORT set to 6789.
	final static int SERVER_PORT = 6789;

	// Sets the boolean value so server is always listening.
	static boolean listening = true;

	/**
	 * Main method that will run the Web Server.
	 */
	public static void main(String[] args) throws Exception {

		// Creates the serverSocket bound to the SERVER_PORT
		ServerSocket socket = new ServerSocket(SERVER_PORT);

		// Sets the connection socket (cSocket) to null.
		Socket cSocket = null;

		// Sets the httpRequest object to null.
		HttpRequest httpReq = null;

		// Sets the thread object to null.
		Thread threader = null;

		// Displays the Web Server information.
		try {
			System.out.println("Web Server standing by to accept Clients:"
					+ "\nIP Address: " + InetAddress.getLocalHost()
					+ "\nPort: " + socket.getLocalPort() + "\n\n");

		} catch (UnknownHostException e) {
			// NS lookup for host IP failed?
			// This should only happen if the host machine does
			// not have an IP address.
			e.printStackTrace();
		}

		// Always listening for clients.
		while (listening) {
			try {
				// Accept for the connection socket.
				cSocket = socket.accept();
			} catch (IOException e) {

				// Port not available.
				System.err.println("Could not accept the connection socket.");
				System.exit(-1);
			}

			// Constructs an object to process the HTTP request message
			try {
				// New HttpRequest.
				httpReq = new HttpRequest(cSocket);
			} catch (IOException e) {

				// Port not available.
				System.err.println("Could not create an object to process "
						+ "the HTTP request message");
				System.exit(-1);
			}

			// Creates a new HttpRequest

			try {
				// New HttpRequest.
				threader = new Thread(httpReq);
			} catch (Exception e) {

				// Port not available.
				System.err.println("Could not start new thread request!");
				System.exit(-1);
			}

			try {
				// Start the threading.
				threader.start();
			} catch (Exception e) {

				// Port not available.
				System.err.println("Could not start the thread.");
				System.exit(-1);
			}

		}

		// Closes socket if listening ever became true.
		socket.close();

	}
} // END of WebServer class.
