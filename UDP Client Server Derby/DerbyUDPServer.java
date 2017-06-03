import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class DerbyUDPServer {

	// Socket for UDP communication with clients
	DatagramSocket dgs = null;
	DatagramSocket dgs2 = null;

	// The DataGramPacket used
	DatagramPacket dgp = null;

	ByteArrayInputStream bis = null;

	DataInputStream dis = new DataInputStream(bis);

	int clientPort = 0;

	DatagramPacket dgp2 = null;

	// The string converted to an int is held in this variable
	int parsedString = 0;

	// Flag set to false when server should close
	boolean listening = true;

	// The String that is used to hold the extracted String
	String extracted = "";

	// Total number of clients server has sent data to
	int clientCount = 0;

	// Port number that server listens on
	static final int SERVER_PORT = 31250;

	ByteArrayOutputStream bos = null;
	DataOutputStream dos = null;

	// Array list containing a list of strings describing
	// Kentucky derby winners
	ArrayList<String> winners = new ArrayList<String>();

	public DerbyUDPServer() throws IOException {

		readInDataFile();
		handleClients();

	} // end DerbyUDPServer constructor

	protected void readInDataFile() throws IOException {
		int winIdx = 0;
		String singleWinner;

		BufferedReader in = new BufferedReader(new FileReader("derby.txt"));

		while ((singleWinner = in.readLine()) != null) {

			winners.add(singleWinner);
			winIdx++;
		}

		in.close();

	} // end readInDataFile

	/*
	 * Displays IP address and port number that clients can use to contact the
	 * server to the console.
	 */
	protected void displayContactInfo() {
		try {
			// Display contact information.
			System.out.println("Number Server standing by to accept Clients:"
					+ "\nIP : " + InetAddress.getLocalHost() + "\nPort: "
					+ dgs.getLocalPort() + "\n\n");

		} catch (UnknownHostException e) {
			// NS lookup for host IP failed?
			// This should only happen if the host machine does
			// not have an IP address.
			e.printStackTrace();
		}

	} // end displayContactInfo

	public void handleClients() throws IOException {

		try {

			dgs = new DatagramSocket(32150);

			dgp = new DatagramPacket(new byte[50], 50);

		} catch (SocketException e) {

			e.printStackTrace();

		}

		displayContactInfo();

		// Receive and then write

		// receive here

		dgs.receive(dgp);

		// Prints out the IP address and Port of the DataGramPacket (#5)
		System.out.println(dgp.getAddress());
		System.out.println(dgp.getPort());

		// Extract the string from the bytes (#6)

		ByteArrayInputStream bis = new ByteArrayInputStream(dgp.getData());

		DataInputStream dis = new DataInputStream(bis);
		// Read in the string from the DataInputString...
		try {
			extracted = dis.readUTF();
		} catch (IOException e) {

			e.printStackTrace();
		}

		// System.out.println("You entered the year: " + extracted);
		// Converts the String to an integer (#7)
		parsedString = Integer.parseInt(extracted);

		// System.out.println(getYearLine(parsedString));

		// Write below

		bos = new ByteArrayOutputStream();

		dos = new DataOutputStream(bos);

		dos.writeUTF(getYearLine(parsedString));

		dgp2 = new DatagramPacket(bos.toByteArray(), bos.size());

		dgp2.setAddress(dgp.getAddress());
		dgp2.setPort(dgp.getPort());

		dgs.send(dgp2);
		
		while(listening == true){
			handleClients();
		}

	}

	public String getYearLine(int year) {

		if (year < 2012 && year > 1874) {

			return winners.get(2011 - year);

		} else {

			return "no derby info for that year";
		}

	} // getYearLine

	public static void main(String[] args) throws IOException {

		new DerbyUDPServer();

	} // end main

} // end DerbyUDPServer
