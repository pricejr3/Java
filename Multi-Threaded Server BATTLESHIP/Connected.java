
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Connected {
	public ObjectOutputStream outputStream;

	public GetMessage message;

	public Connected(Socket sock, ProgramProtocol runner) {

		try {
			outputStream = new ObjectOutputStream(sock.getOutputStream());
			message = new GetMessage(sock, runner);
			new Thread(message).start();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void pushBoat(OceanShips arsenal) {
		try {
			outputStream.writeInt(1);
			outputStream.writeObject(arsenal);
			outputStream.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void pushHit(TargetCoordinates target) {
		try {
			outputStream.writeInt(2);
			outputStream.writeObject(target);
			outputStream.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void connectGame(String word) {
		try {
			outputStream.writeInt(3);
			outputStream.writeObject(word);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void quitGame() {
		try {
			outputStream.writeInt(4);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newGame() {
		try {
			outputStream.writeInt(5);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gameJoined() {
		try {
			outputStream.writeInt(6);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}