
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	public int port;
	public ProgramProtocol running;
	public Socket client;
	public String sName;

	public Client(String name, int port, ProgramProtocol runner) {
		this.sName = name;
		this.port = port;
		this.running = runner;
	}

	@Override
	public void run() {
		try {
			this.client = new Socket(sName, port);
			running.setConnection(new Connected(client, running));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}