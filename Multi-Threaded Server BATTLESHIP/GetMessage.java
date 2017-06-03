
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class GetMessage implements Runnable {
		public ObjectInputStream ois;
		public ProgramProtocol runner;
		public boolean working = true;
		public Socket socket;

		public GetMessage(Socket socket, ProgramProtocol main) throws IOException {
			this.socket = socket;
			this.runner = main;
			ois = new ObjectInputStream(new BufferedInputStream(
					socket.getInputStream()));
		}

		@Override
		public void run() {
			int tracker;
			while (working) {

				try {
					tracker = ois.available();
					if (tracker == 0)
						continue;
					int code = ois.readInt();

					if (code == 4) {
						working = false;
						runner.exitGameMessage();
					} else if (code == 2) {
						TargetCoordinates target = (TargetCoordinates) ois.readObject();
						runner.recieveShotMessage(target);

					} else if (code == 6) {
						runner.gameJoinedCallback();

					} else if (code == 3) {

						String name = (String) ois.readObject();
						runner.joinGameMsg(name);
					} else if (code == 1) {
						OceanShips arsenal = (OceanShips) ois.readObject();
						runner.recievearsenalCallback(arsenal);
					} else if (code == 5) {
						runner.playAgainMessage();
					}

				} catch (IOException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}

		}

	}

