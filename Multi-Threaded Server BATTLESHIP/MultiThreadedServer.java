
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadedServer implements Runnable {
	public ServerSocket ss;
	public Socket server;
	public boolean online = true;
	ProgramProtocol program;

	public MultiThreadedServer(int port, ProgramProtocol running) throws IOException {
		this.program = running;
		ss = new ServerSocket(port);

	}

	public void run() {
		
		while (online) {

			try {
				server = ss.accept();
			} catch (IOException e) {

				e.printStackTrace();
			}
			Connected connected = new Connected(server, program);
			program.setConnection(connected);
	

		}
	}

}