/**
 * Jarred Price (pricejr3)
 *
 */


import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Allows for the website to be accessed with the Web Server and for the user to
 * be able to display the local website: "index.html".
 * 
 */
public class HttpRequest extends Thread 
{

	// The hard coded CRLF to return.
	final static String CRLF = "\r\n";

	// The socket that is used.
	Socket requestSocket = null;

	// Keeps track of # clients.
	static int clientCount = 1;

	// Instantiates the InputStream.
	InputStream dis = null;

	// Instantiates the DataOutputStream object.
	DataOutputStream dos = null;

	// Instantiates the BufferedReader object.
	BufferedReader br = null;

	// Instantiates the FileInputStream object.
	FileInputStream fileInputStream = null;

	// Instantiates the String variables used.
	String statusLine = "";
	String contentTypeLine = "";
	String entityBody = "";
	String fileName = "";
	String requestLine = "";
	String headerLine = "";

	// Instantiates the StringTokenizer tokens.
	StringTokenizer tokens = null;

	// Instantiates the boolean value to false.
	boolean fileExists = false;

	/**
	 * Constructor for the HttpRequest class. It creates a RequestSocket to use
	 * in conjunction with the WebServer in order to work with http requests and
	 * web pages.
	 * 
	 * @param requestSocket		The socket that has been requested.
	 * 
	 */
	public HttpRequest(Socket requestSocket) throws Exception {

		// Set this instance of the socket to that fed in.
		this.requestSocket = requestSocket;

	} // END of HttpRequest constructor.

	/**
	 * This overrides the run method that is in the Thread class. It calls
	 * runProgram to run everything that is needed.
	 * 
	 */
	public void run() {
	
		try {
			runProgram();
		} catch (Exception e) {
			System.out.println("Problem occured!");
		}

	} // END OF RUN method

	/**
	 * Runs the program.
	 * 
	 */
	private void runProgram() throws Exception {

		createStreams();
		requestLinePrint();
		extractTokens();
		openFile();
		constructMessage();
		sendLineBody();
	}

	/**
	 * Creates the streams used in the HttpRequest class.
	 * 
	 */
	protected void createStreams() {

		try {
			dis = requestSocket.getInputStream();
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			dos = new DataOutputStream(requestSocket.getOutputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}

		br = new BufferedReader(new InputStreamReader(dis));
	}

	/**
	 * Receives the requestLine and prints to the screen as well as the
	 * headerLine.
	 * 
	 */
	protected void requestLinePrint() {

		try {
			requestLine = br.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println();
		System.out.println(requestLine);

		headerLine = null;
		try {
			while ((headerLine = br.readLine()).length() != 0) {
				System.out.println(headerLine);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Extracts the fileName from the requestLine and stores into the value
	 * tokens.
	 * 
	 */
	protected void extractTokens() {

		// Extract the filename from the request line.
		tokens = new StringTokenizer(requestLine);
		tokens.nextToken(); // skip over the method, which should be "GET"
		fileName = tokens.nextToken();

	}

	/**
	 * Opens the file that has been requested.
	 * 
	 */
	protected void openFile() {

		// Prepend a "." so that file request is within the current directory.
		fileName = "." + fileName;

		// Open the requested file.
		fileExists = true;
		try {
			fileInputStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		}
	}

	/**
	 * Constructs the message to print to the screen about the content of the
	 * users webpage.
	 * 
	 */
	protected void constructMessage() {

		// Construct the response message.
		statusLine = null;
		contentTypeLine = null;
		entityBody = null;
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK";
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
		} else {
			statusLine = "HTTP/1.0 404 Not Found";
			contentTypeLine = "Content-Type: text/html" + CRLF;
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>"
					+ "<BODY>Not Found</BODY></HTML>";
		}
	}

	/**
	 * Sends the lineBody out and the type of the content.
	 * 
	 */
	protected void sendLineBody() {

		// Send the status line.
		try {
			dos.writeBytes(statusLine);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Send the content type line.
		try {
			dos.writeBytes(contentTypeLine);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Tells the client the connection will be closing
		try {
			dos.writeBytes("Connection: close" + CRLF);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Send a blank line to indicate the end of the header lines.
		try {
			dos.writeBytes(CRLF);
		} catch (IOException e) {

			e.printStackTrace();
		}

		// Send the entity body.
		if (fileExists) {
			try {
				sendBytes(fileInputStream, dos);
			} catch (Exception e) {

				e.printStackTrace();
			}
			try {
				fileInputStream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			try {
				dos.writeBytes(entityBody);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Creates a buffer and sends the bytes that are read in and read out.
	 * 
	 *  @param fis				The FileInputStream, fis.
	 *  @param dos				The OutputStream, dos.
	 *  @param requestSocket	The socket that has been requested.
	 *  
	 */
	private static void sendBytes(FileInputStream fis, OutputStream dos)
			throws Exception {

		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			dos.write(buffer, 0, bytes);
		}
	}

	/**
	 * Receives the fileNames of the objects on the webpage and then returns
	 * what type of object that it has determined to be located on the
	 * respective webpage.
	 * 
	 * @param fileName		the fileName that is used.
	 * @return				the proper string depending on the string.	
	 * 
	 */
	private static String contentType(String fileName) {

		if (fileName.endsWith(".htm") || fileName.endsWith("html")) {
			return "text/html";
		}
		if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
			return "image/jpeg";
		}
		if (fileName.endsWith(".gif")) {
			return "image/gif";
		}
		if (fileName.endsWith(".png")) {
			return "image/png";
		}
		return "application/octet-stream";
	}
}// END of HttpRequest class.
