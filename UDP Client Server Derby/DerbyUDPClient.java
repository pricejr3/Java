import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DerbyUDPClient {

	public InetAddress ip = null;

	// To print the string at the end
	String finalPrint = "";
	// Socket for UDP communication with server
	DatagramSocket dgs = null;
	DatagramSocket dgs2 = null;

	// The DataGramPacket used with server
	DatagramPacket dgp = null;
	DatagramPacket dgp2 = null;

	ByteArrayOutputStream bos = null;
	DataOutputStream dos = null;

	String year = "";

	// The String to hold the user's IP Address
	String ipString = "";
	Scanner input = new Scanner(System.in);

	public DerbyUDPClient() throws IOException {
		getIPAddressServerAndYear();

	}

	public void getIPAddressServerAndYear() throws IOException {

		// Ask user for IP address, or they can just press return (#1)
		System.out.println("Enter the IP Address of the server, please: ");
		ipString = input.nextLine();

		if (ipString.length() == 0) {

			ipString = "127.0.0.1";
		}

		try {
			ip = InetAddress.getByName(ipString);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

		// Ask user for IP address, or they can just press return (#1)
		System.out.println("Please enter a year between 1875 and 2011: ");

		year = input.nextLine();

		// Checks to see if the user has inputted a year (#3)
		if (isInteger(year) == true) {

			// write and then receive

			dgs = new DatagramSocket();

			bos = new ByteArrayOutputStream();

			dos = new DataOutputStream(bos);

			dos.writeUTF(year);

			
			dgp = new DatagramPacket(bos.toByteArray(), bos.size());

			dgp.setAddress(InetAddress.getLocalHost());
			dgp.setPort(32150);

			dgs.send(dgp);

			// receive
			

			dgs.receive(dgp);

			ByteArrayInputStream bis = new ByteArrayInputStream(dgp.getData());

			DataInputStream dis = new DataInputStream(bis);
			finalPrint = dis.readUTF();
			System.out.println(finalPrint);

			dgs.close();
		}

	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) throws IOException {

		new DerbyUDPClient();

	} // end main

} // end DerbyUDPClient
