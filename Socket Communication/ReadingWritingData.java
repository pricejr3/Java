import java.io.*;
import java.net.*;

/**
 * @author Jarred Price
 * 
 *         Number server for CSE 283 Lab1. Creates a ServerSocket to
 *         communicate via TCP connection from a client. Instantiates a socket
 *         for each client to communicate with the TCP Listener.
 * 
 *         Created on August 30, 2013
 */

public class ReadingWritingData {

	// Instantiates the ints, floats, doubles, long, and string used for
	// filename.
	protected String filename;
	protected int int1, int2;
	protected float float1, float2;
	protected double double1, double2;
	protected long long1, long2;

	/*
	 * Constructor for the ReadingWritingData class. It creates a ServerSocket
	 * to use for listening for clients. Displays the port number and IP address
	 * for the ServerSocket to the console and then enters an infinite loop to
	 * listen for clients. Spawns a new TCPListenThread object to handle each
	 * client.
	 */
	public ReadingWritingData(String filename) throws IOException {
		this.filename = filename;
		// Call reading method
		readFile(filename);
		// Call writing methods
		writeNumericValues();
		writeStringValues();
		// Call socket creating method
		writeToSocket();
	} // end constructor

	/*
	 * Creates a method that uses a FileInputStream and a DataInputStream to
	 * create a file named "differentTypes.dat" and reads each primitive into
	 * its own respective data member type.
	 * 
	 * @param filename The filename that is used within the method that is
	 * called by the main.
	 */
	public void readFile(String filename) throws IOException {
		// Creates a FileInputStream
		FileInputStream fis = new FileInputStream(new File(filename));
		// Creates a DataInputStream
		DataInputStream dis = new DataInputStream(fis);
		// Reads the primitives from the DataInputStream
		int1 = dis.readInt();
		int2 = dis.readInt();
		float1 = dis.readFloat();
		float2 = dis.readFloat();
		double1 = dis.readDouble();
		double2 = dis.readDouble();
		long1 = dis.readLong();
		long2 = dis.readLong();
		// Closes the DataInputSTream
		dis.close();

		// Prints the contents of the file to the console
		System.out.println("Values read from differentTypes.dat");
		System.out.println(int1 + " " + int2 + " " + float1 + " " + float2
				+ " " + double1 + " " + double2 + " " + long1 + " " + long2);

	} // end readFile

	/*
	 * Uses a FileOutputStream and a DataOutputStream to open a new file called
	 * numericTypes.dat.
	 */
	public void writeNumericValues() throws IOException {
		// Creates a FileOutputStream
		FileOutputStream fos = new FileOutputStream("numericTypes.dat");
		// Creates a DataOutputStream
		DataOutputStream dos = new DataOutputStream(fos);
		// Writes all of the values to the DataOutputStream
		dos.writeInt(int1);
		dos.writeInt(int2);
		dos.writeFloat(float1);
		dos.writeFloat(float2);
		dos.writeDouble(double1);
		dos.writeDouble(double2);
		dos.writeLong(long1);
		dos.writeLong(long2);
		// Closes the DataOutputStream
		dos.close();

	}

	/*
	 * Writes all of the primitive values as a string to the new filed called
	 * textTypes.dat
	 */
	public void writeStringValues() throws IOException {

		String stringToUse = int1 + " " + int2 + " " + float1 + " " + float2
				+ " " + double1 + " " + double2 + " " + long1 + " " + long2;
		FileWriter fw = new FileWriter("textTypes.dat");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringToUse);

		bw.close();
		fw.close();

	} // end writeStringValues

	/*
	 * Creates a socket to communicate with the TCPListen class in order to send
	 * the primitive data over and to the console.
	 */
	public void writeToSocket() throws IOException {
		// Creates a DataOutputStream
		DataOutputStream dos;
		// Creates a socket object
		Socket socket;

		socket = new Socket(InetAddress.getByName("192.168.1.102"), 32100);

		// Creates a FileOutputStream
		FileOutputStream fos = new FileOutputStream("numericTypes.dat");

		dos = new DataOutputStream(socket.getOutputStream());
		try {
			dos.writeInt(int1);
			dos.writeInt(int2);
			dos.writeFloat(float1);
			dos.writeFloat(float2);
			dos.writeDouble(double1);
			dos.writeDouble(double2);
			dos.writeLong(long1);
			dos.writeLong(long2);
			dos.close();

		} catch (FileNotFoundException e) {
			System.err.println("numericTypes.dat is not read");

		} catch (IOException e) {
			System.err.println("IOException!");
		}

		// Closes the socket
		socket.close();

	}

	// end writeToSocket

	/*
	 * Creates a main method that reads the file "differentTypes.dat" and passes
	 * it through the entire program
	 */
	public static void main(String[] args) throws IOException {
		new ReadingWritingData("differentTypes.dat");
	} // end main
} // end ReadingWritingData class