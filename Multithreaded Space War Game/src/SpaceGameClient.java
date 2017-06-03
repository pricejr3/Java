
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import spaceWar.*;

/**
 * @author bachmaer
 * 
 *         Driver class for a simple networked space game. Opponents try to
 *         destroy each other by ramming. Head on collisions destroy both ships.
 *         Ships move and turn through GUI mouse clicks. All friendly and alien
 *         ships are displayed on a 2D interface.
 */
public class SpaceGameClient implements SpaceGUIInterface 
{

    /**
     * Main method to start the space game.
     * 
     */
    public static void main(String[] args) 
    {
        new SpaceGameClient();

    } // end main

    // Either keeps loops going (as in run the game) or stops
    // them (when they are set to false).
    boolean runGame = true;

    // Keeps track of the game state.
    Sector sector;

    // User interface.
    SpaceGameGUI gui;

    // IP address and port to identify ownship and the
    // DatagramSocket being used for game play messages.
    InetSocketAddress ownShipID;

    // For the x and y-coordinates used in sending, receiving
    // making obstacles, etc.
    public int x;
    public int y;

    // The port number for torpedoes and ships.
    int port;

    // DatagramPacket used in Client.
    DatagramPacket dgPack;

    // Byte array used for ships.
    byte ip[] = new byte[4];

    // Used to keep track of the id of the user.
    InetSocketAddress id = null;

    // Used for sending in codes.
    int readIN;

    // Direction of the way the ship is going.
    int direction;

    // The DatagramPacket used.
    DatagramPacket dgp;

    // Socket for sending and receiving
    // game play messages.
    DatagramSocket gamePlaySocket;

    // Input and Output streams used in sending to server
    // and receiving information from server.
    DataOutputStream out;
    DataInputStream in;

    // Socket used to register and to receive remove information
    // for ships
    Socket reliableSocket;

    // Temporary socket used later in program.
    Socket tempSocket;

    // Byte Array Streams
    ByteArrayOutputStream baos;
    ByteArrayInputStream bais;

    // DataInputStream
    DataInputStream dis;
    
    // DataOutputStream
    DataOutputStream torpDos;

    // Socket used to shut down
    Socket exitSock;

    // Debug will be false.
    static final boolean DEBUG = false;

    /**
     * Creates all components needed to start a space game. Creates Sector
     * canvas, GUI interface, a Sender object for sending update messages, a
     * Receiver object for receiving messages.
     * 
     * @throws UnknownHostException
     */
    public SpaceGameClient() 
    {
        // Create UDP Datagram Socket for sending and receiving
        // game play messages.
        try {
            gamePlaySocket = new DatagramSocket();

        } catch (SocketException e) {
            System.err.println("Error creating game play datagram socket.");
            System.exit(0);
        }

        // Instantiate ownShipID using the DatagramSocket port
        // and the local IP address.
        try {
            ownShipID = new InetSocketAddress(InetAddress.getLocalHost(),
                    gamePlaySocket.getLocalPort());
        } catch (UnknownHostException e) {

            System.err.println("Error creating ownship ID. Exiting.");
            System.exit(0);
        }

        // Create display, ownPort is used to uniquely identify the
        // controlled entity.
        sector = new Sector(ownShipID);

        // gui will call SpaceGame methods to handle user events
        gui = new SpaceGameGUI(this, sector);

        // Call a method that uses TCP/IP to register with the server
        // and receive obstacles from the server.
        obstacleRegistration();

        // Infinite loop or separate thread to receive update and join
        // messages from the server and use the messages to
        // update the sector display

        // clientUpdater();
        // clientUpdater(port);
        // Not working...

        // Works
        (new clientUpdater()).start();

        // Infinite loop or separate thread to receive remove
        // messages from the server and use the messages to
        // update the sector display
        (new eraseMethod()).start();

    } // end SpaceGame constructor

    /**
     * Creates a TCP connection with the server as well as receiving the
     * obstacles from the server to the client.
     * 
     */
    public void obstacleRegistration()
    {

        // Make Socket
        makeObstacleSocket();

        // Create both DataOutputStream and DataInputStreams.
        createObstacleDataStreams();

        writeToServer();

        coords();

    } // end obstacleRegistration method

    /**
     * Makes a reliableSockt for receiving the obstacles.
     * 
     */
    public void makeObstacleSocket() 
    {
        // Use the reliableSocket here since it's the
        // socket used for TCP.
        try {
            reliableSocket = new Socket(Constants.SERVER_IP,
                    Constants.SERVER_PORT);
        } catch (IOException e) {
            System.err.print("Couldn't do this!");
            e.printStackTrace();
        }
        
    } // end makeObstacleSocket method

    /**
     * Writes the registration code to the server and sends the UDP port to it
     * as well.
     * 
     */
    public void writeToServer() 
    {
    	// Sends the registration code out to the SpaceGameServer.
        try {
            out.writeInt(Constants.REGISTER);

            // Sends the port number of the socket that is used in UDP.
            out.writeInt(gamePlaySocket.getLocalPort());
            
        } catch (IOException e) {
            System.err.print("problem!");
            e.printStackTrace();
        }
        
    } // end writeToServer method

    /**
     * Creates the datastreams used in creating the obstacles from the server.
     * 
     */
    public void createObstacleDataStreams() 
    {

        try {
            in = new DataInputStream(reliableSocket.getInputStream());
            out = new DataOutputStream(reliableSocket.getOutputStream());
        } catch (IOException e) {
            System.err.print("NOOOOO, there is a  PROBLEM!!");
            e.printStackTrace();
        }
        
    } // end createObstacleDataStreams method

    /**
     * Reads in the x and y coord's from the server and then puts them into the
     * sector of new obstacles.
     * 
     */
    public void coords() 
    {
        try {
            // Read in x value.
            x = in.readInt();

            do {
                // Read in the y value.
                y = in.readInt();

                // Add each obstacle to the display
                sector.addObstacle(new Obstacle(x, y));

                // Read in x value again.
                x = in.readInt();

            } while (x >= 0);

        } catch (IOException e) {
            System.err.print("NO can do!");
            e.printStackTrace();
        }
        
    } // end coords method

    /**
     * Class that updates the clients screen and other necessary functions for
     * SpaceWar3.
     * 
     */
    public class clientUpdater extends Thread 
    {

        /**
         * Determines if a torpedo needs to be updated or if a spacecraft needs
         * to be updated.
         */
        protected void displayUpdater() {

            if (readIN == Constants.UPDATE_TORPEDO) {

                // Create a torpedo used to send to SpaceGameServer.
                Torpedo torp = new Torpedo(id, x, y, direction);

                // Update the sector display.
                sector.updateOrAddTorpedo(torp);

            } else if (readIN == Constants.UPDATE_SHIP) {

                // Spacecraft used to send to SpaceGameServer.
                // AlienSpaceCraft alien = new AlienSpaceCraft(x, y);
                AlienSpaceCraft alienCraft = new AlienSpaceCraft(id, x, y,
                        direction);

                // Update the sector display.
                sector.updateOrAddSpaceCraft(alienCraft);
            }
            else if (readIN == Constants.JOIN) {

                AlienSpaceCraft alienCraft = new AlienSpaceCraft(id, x, y,
                        direction);

                // Update the sector display.
                sector.updateOrAddSpaceCraft(alienCraft);
            }
            
        } // end displayerUpdater method

        /**
         * Obtains the datagram packet by calling short methods.
         * 
         */
        protected void getDGP()
        {

            createPacket();
            receivePacket();

            // Create Streams and red in information.
            streamProcesses();

        } // end getDGP method

        /**
         * Receives the dgPack from the Server.
         * 
         */
        private void receivePacket() 
        {
            try {
                gamePlaySocket.receive(dgPack);
            } catch (IOException e) {
                System.err.print("Coudn't receive DatagramPacket! :(");
                e.printStackTrace();
            }

        } // end receiveDatagramPacket method

        /**
         * Creates the dgPack with a byte array of 30 and length as well.
         * 
         */
        public void createPacket() 
        {
            dgPack = new DatagramPacket(new byte[30], 30);

        } // end createPacket method

        /**
         * Initializes ByteArrays for reading in bytes and ip.
         * 
         */
        public void streamProcesses() 
        {
            initializeBais();
            readStreamsIn();

            // Now write the id.
            try {
                id = new InetSocketAddress(InetAddress.getByAddress(ip), port);
            } catch (UnknownHostException e) {
                System.err.print("Couldn't create InetSocketAddress.");
                e.printStackTrace();
            }

        } // end streamProcesses method

        /**
         * Read the streams in.
         * 
         */
        void readStreamsIn() 
        {
            
            try {
                dis.read(ip);
            } catch (IOException e) {
                System.err.print("Couldn't read IP.");
                e.printStackTrace();
            }
            try {
                port = dis.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read Port.");
                e.printStackTrace();
            }

            try {
                readIN = dis.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read code.");
                e.printStackTrace();
            }

            try {
                x = dis.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read x-Coord.");
                e.printStackTrace();
            }
            try {
                y = dis.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read y-Coord.");
                e.printStackTrace();
            }
            try {
                direction = dis.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read the direction.");
                e.printStackTrace();
            }
            
        }

        /**
         * Initializes ByteArrays for reading in bytes and IP.
         * 
         */
        void initializeBais() 
        {
            bais = new ByteArrayInputStream(dgPack.getData());
            dis = new DataInputStream(bais);

        }

        /**
         * Runs the updater while the game is currently running.
         * 
         * @override run
         * 
         */
        public void run() 
        {
            while (runGame) {
                getDGP();
                displayUpdater();
            }
            
        } // end run method

    } // end clientUpdater class

    /**
     * This class will erase spacecraft and torpedos that are no longer needed.
     * 
     */
    public class eraseMethod extends Thread 
    {

        /**
         * Runs while the game is working, receives tcp connection and erases
         * what is dictated.
         * 
         * @override run
         * 
         */
        public void run() 
        {
            while (runGame) {
                tcpReceive();
                erase();
            }
            
        } // end run method

        /**
         * Erases either the torpedo or spacecraft depending on the readIN code
         * value that was sent.
         * 
         */
        protected void erase() 
        {

            if (readIN == Constants.REMOVE_TORPEDO) {

                eraseTorp();

            } else if (readIN == Constants.REMOVE_SHIP) {

                eraseSpaceCraft();
            }
            
        } // end erase method

        /**
         * Runs every short method that will allow for tcp connection to
         * receive.
         * 
         */
        protected void tcpReceive() 
        {
            createInputStream();
            readInput();
            makeID();

        } // end tcpReceive method

        /**
         * Erases the torpedo by creating a dummy torpedo and then removing it.
         * 
         */
        public void eraseTorp() 
        {
            // Below does not work.
            // Torpedo torp = new Torpedo(id, x, y, header);

            // Torpedo has id of the user.
            Torpedo torp = new Torpedo(id, 0, 0, 0);

            // Erases this Torpedo at this set location in the sector.
            sector.removeTorpedo(torp);

        } // end eraseTorp method

        /**
         * Erases the space craft by creating a dummy space craft and then
         * removing it.
         * 
         */
        public void eraseSpaceCraft() 
        {
            // Works much like how erase torpedos do.
            SpaceCraft spaceC = new SpaceCraft(id, 0, 0, 0);

            // Remove the spacecraft
            sector.removeSpaceCraft(spaceC);

        } // end eraseSpaceCraft method

        /**
         * Makes ID that will be sent.
         * 
         */
        public void makeID() 
        {
            try {
                id = new InetSocketAddress(InetAddress.getByAddress(ip), port);
            } catch (UnknownHostException e) {
                System.err.print("Couldn't read in InetSocket");
                e.printStackTrace();
            }
        } // end makeID method

        /**
         * Creates the input streams that are used with the reliableSocket.
         * 
         */
        public void createInputStream() 
        {
            try {
                in = new DataInputStream(reliableSocket.getInputStream());
            } catch (IOException e) {
                System.err.print("Couldn't create input stream!");
                e.printStackTrace();
            }
        } // end createInputStream method

        /**
         * Reads in the ip and port.
         * 
         */
        public void readInput() 
        {
            try {
                in.read(ip);
                port = in.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read IP or port");
                e.printStackTrace();
            }

            // Read in the code that was sent in.
            try {
                readIN = in.readInt();
            } catch (IOException e) {
                System.err.print("Couldn't read in CODE!");
                e.printStackTrace();
            }
        }
    } // end readInput method

    /**
     * Sends the packet to the SpaceGameServer. Sends the datagrampacket and
     * then closes streams.
     * 
     */
    public void sendPacketToServer() 
    {
        try {
            gamePlaySocket.send(dgp);
        } catch (IOException e) {
            System.err.print("Couldn't send packet.");
            e.printStackTrace();
        }
        closeSendStream();
        
    } // end sendPacketToServer method

    /**
     * Communicates with SpaceGameServer by using the code that is readIN.
     * 
     */
    protected void serverCommunication(int readIN) 
    {
        // Create stream.
        createSendStream();

        // Send and write.
        sendWrite();

        // Create the packet.
        createPacket();

        // Send it to server.
        sendPacketToServer();

    } // end serverCommunication method

    // Below is the turning and other operations...

    /**
     * Allows for a new ship to join the game by sending a message to the
     * SpaceGameServer.
     * 
     */
    public void join() 
    {
        if (sector.ownShip == null) {

            if (DEBUG)
                System.out.println("Join");

            // Add a new ownShip to the sector display.
            sector.createOwnSpaceCraft();

            // Send message to server to let them know you have joined the game.
            serverCommunication(Constants.JOIN);
        }

    } // end join method

    /**
     * Allows for the ship to turn left and this is sent to the SpaceGameServer
     * so it knows what is going on.
     * 
     */
    public void turnLeft()
    {
        // See if the player has a ship in play
        if (sector.ownShip != null) {

            if (DEBUG)
                System.out.println(" Left Turn ");

            // Update the display
            sector.ownShip.leftTurn();

            // Send update message to other server with new direction.
            serverCommunication(Constants.UPDATE_SHIP);
        }
    } // end turnLeft method

    /**
     * Allows for the ship to turn right and this is sent to the SpaceGameServer
     * so it knows what is going on.
     * 
     */
    public void turnRight()
    {
        if (sector.ownShip != null) {

            if (DEBUG)
                System.out.println(" Right Turn ");

            // Update the display
            sector.ownShip.rightTurn();

            // Send update message to server with new direction.
            serverCommunication(Constants.UPDATE_SHIP);

        }
    } // end turnRight method

    /**
     * Allows for the ship to move forward and this is sent to the
     * SpaceGameServer so it is aware of what is going on in the client.
     * 
     */
    public void moveFoward()
    {
        // Check if the player has and unblocked ship in the game
        if (sector.ownShip != null && sector.clearInfront()) {

            if (DEBUG)
                System.out.println(" Move Forward");

            // Update the displayed position of the ship
            sector.ownShip.moveForward();

            // Send a message with the updated position to server
            serverCommunication(Constants.UPDATE_SHIP);
        }
    } // end moveForward method

    /**
     * Allows for the ship to move backward and this is sent to the
     * SpaceGameServer so it is aware of what is going on in the client.
     * 
     */
    public void moveBackward() 
    {
        // Check if the player has and unblocked ship in the game
        if (sector.ownShip != null && sector.clearBehind()) {

            if (DEBUG)
                System.out.println("Move Backward");

            // Update the displayed position of the ship
            sector.ownShip.moveBackward();

            // Send a message with the updated position to server
            serverCommunication(Constants.UPDATE_SHIP);
        }
    } // end moveBackward method

    /**
     * Allows for the ship to fire a torpedo upon the enemy and this is sent to
     * the SpaceGameServer so it is aware that the enemy has been fired upon.
     * 
     */
    public void fireTorpedo()
    {
    	// See if the player has a ship in play
            if (sector.ownShip != null) {

                if (DEBUG)
                    System.out.println("Informing server of new torpedo");

                // Make a TCP connection to the server and send torpedo information
                try {
                    
                	tempSocket = new Socket(Constants.SERVER_IP,
                            Constants.SERVER_PORT);
                    torpDos = new DataOutputStream(tempSocket.getOutputStream());

                    // Inform server of torpedo request
                    torpDos.writeInt(Constants.FIRED_TORPEDO);
                    torpDos.writeInt(gamePlaySocket.getLocalPort());
                    torpDos.writeInt(sector.ownShip.getXPosition());
                    torpDos.writeInt(sector.ownShip.getYPosition());
                    torpDos.writeInt(sector.ownShip.getHeading());

                    // Close Output Stream and TCP connection
                    closeTorp();

                } catch (IOException e) {
                    System.err.print("PROBLEM!");
                }

            }
    } // end fireTorpedo method

    public void closeTorp() {
    	try {
			torpDos.close();
			  tempSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end closeTorp method

	/**
     * Closes the Torpedo streams.
     * 
     */
    public void closeTorpStreams()
    {
        try {
            out.close();
            tempSocket.close();
        } catch (IOException e) {
            System.err.print("Error");
            e.printStackTrace();
        }
    } // end closeTorpStreams method

    /**
     * Makes the sockets used for torpedos.
     * 
     */
    public void makeSocketTorp() 
    {
        try {
            tempSocket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            out = new DataOutputStream(tempSocket.getOutputStream());
        } catch (IOException e) {
            System.err.print("Error!");
            e.printStackTrace();
        }
    } // end makeSocketTorp method

    /**
     * Writes the torpedo to the server.
     * 
     */
    public void writeTorp() 
    {
        try {

            // Send server message that torpedo was fired
            // and send the port.
            out.writeInt(Constants.FIRED_TORPEDO);
            out.writeInt(gamePlaySocket.getLocalPort());

            // Send server user's ship location so as to create appropriate
            // torpedo with current location and direction
            out.writeInt(sector.ownShip.getXPosition());
            out.writeInt(sector.ownShip.getYPosition());
            out.writeInt(sector.ownShip.getHeading());

        } catch (IOException e) {
            System.err.print("Problem writing to server!");
            e.printStackTrace();
        }
    } // end writeTorp method

    /**
     * Closes all streams associated with the torpedo.
     * 
     */
    public void closeSendStream() 
    {
        try {
            out.close();
            baos.close();
        } catch (IOException e) {
            System.err.print("Couldn't close.");
            e.printStackTrace();
        }

    } // end closeSendStream method

    /**
     * Creates the datagram packet used.
     * 
     */
    public void createPacket() 
    {
        dgp = new DatagramPacket(baos.toByteArray(), baos.size());
        dgp.setAddress(Constants.SERVER_IP);
        dgp.setPort(Constants.SERVER_PORT);

    } // end createPacket method

    /**
     * Writes and sends to Server.
     * 
     */
    public void sendWrite() 
    {
        try {
            out.write(InetAddress.getLocalHost().getAddress());

            out.writeInt(gamePlaySocket.getLocalPort());

            out.writeInt(readIN);

            // GetX, GetY, getHeading are used here...
            out.writeInt(sector.ownShip.getXPosition());

            out.writeInt(sector.ownShip.getYPosition());

            out.writeInt(sector.ownShip.getHeading());
            
        } catch (IOException e) {
            System.err.print("Couldn't write Integer values! :(");
            e.printStackTrace();
        }

    } // end sendWrite method

    /**
     * Creates the streams.
     * 
     */
    public void createSendStream() 
    {
        baos = new ByteArrayOutputStream();
        out = new DataOutputStream(baos);
    }

    /**
     * Perform clean-up for application shut down. Shuts it down!
     * 
     */
    public void stop() 
    {
        if (DEBUG)
            System.out.println("stop");

        // Stop all thread and close all streams and sockets
        runGame = false;

        // Inform the server that the client is leaving the game
        try {

            // Use a socket to shut down.
            exitSock = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            out = new DataOutputStream(exitSock.getOutputStream());

            // Write the integer values out.
            writeStopInts();

            // Close Output Stream and TCP connection
            closeStopStreams();

        } catch (IOException e) {
            System.err
                    .print("There was a problem in server-client communication! :( ");
        }

    } // end stop method

    /**
     * Writes the INTS associated with exiting.
     * 
     */
    public void writeStopInts()
    {
        // Tell SpaceGameServer to exit using code.
        try {
            out.writeInt(Constants.EXIT);

            // Send the UDP socket out to the SpaceGameServer.
            out.writeInt(gamePlaySocket.getLocalPort());
            
        } catch (IOException e) {
            System.err.print("Problem!");
            e.printStackTrace();
        }

    } // end writeStopInts method

    /**
     * Closes streams associated with Stop.
     * 
     */
    public void closeStopStreams() 
    {
        try {
            out.close();
        } catch (IOException e1) {
            System.err.print("Can't close output stream! :( ");
            e1.printStackTrace();
        }
        try {
            exitSock.close();
        } catch (IOException e) {
            System.err.print("Can't close socket! :( ");
            e.printStackTrace();
        }
    } // end closeStopStreams method

} // end SpaceGameClient class