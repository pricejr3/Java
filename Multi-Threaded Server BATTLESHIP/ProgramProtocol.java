

import java.io.IOException;

import javax.swing.JOptionPane;

public class ProgramProtocol {
	public static Connected frequency;
	public static boolean starting = false;
	public static boolean client = false;
	public static OceanShips person1;
	public static OceanShips person2;
	public static boolean replay1 = false;
	public static boolean replay2 = false;
	public static DisplayGui view;
	public static MultiThreadedServer server = null;
	public static int port;
	
	public ProgramProtocol() throws IOException {
		view = new DisplayGui(this);
		
		String serverPort = JOptionPane.showInputDialog("Enter port number: ");
			port = Integer.parseInt(serverPort);
		
	}

	public boolean setConnection(Connected connect) {
		if (this.frequency == null) {
			this.frequency = connect;
			if (client) {
				connect.connectGame("HERBERT");
			}
			return true;
		} else {
			return false;
		}
	}

	public void startServer() throws IOException {
		
			server = new MultiThreadedServer(port, this);
			new Thread(server).start();
			client = false;
		
	}
	

	public void startClient(String host) {
	
		client = true;
		new Thread(new Client(host, port, this)).start();
	}


	public void setShips(OceanShips arsenal) throws IOException {
		person1 = arsenal;
		startGameIfHaveBotharsenals();
		frequency.pushBoat(arsenal);
	}

	public OceanShips getEnemyarsenal() {
		return person2;
	}

	public void startGameIfHaveBotharsenals() throws IOException {
		
		if (person1 != null && person2 != null) {
		
			view.botharsenalsRecievedView();
			if (client) {
				view.waitingForShotView();
			} else {
				view.chooseShotView();
			}
		}
	}

	public static void newGame() throws IOException {
		person1 = person2 = null;
		if (replay1 && replay2) {
			view.startupView();
			replay1 = replay2 = false;
		}
		
		starting = true;
		view.positionarsenalView();
	}

	public static void endGame() throws IOException {
		client = false;
	
		if (starting) {
			frequency.quitGame();
			starting = false;
			view.startupView();
		}
		
	}

	public void fireShot(TargetCoordinates target) {
		frequency.pushHit(target);
		
	}

	public void recievearsenalCallback(OceanShips arsenal) throws IOException {
		
		person2 = arsenal;
		startGameIfHaveBotharsenals();
	}

	public void recieveShotMessage(TargetCoordinates target) throws IOException {
		view.recieveShot(target);
		

	}

	public static void playAgainPrompt(boolean won) throws IOException {
		String status = won ? "WON!" : "LOST.";
		boolean result = view.confirmDialog("YOU " + status + " Play again?");

		if (result) {
			frequency.newGame();
			replay1 = true;
			view.startupView();
			playAgain();
		} else {
			frequency.quitGame();
			endGame();
		}
	}

	public static void playAgain() throws IOException {
		if (replay1 && replay2) {
			newGame();
		}
	}

	public void joinGameMsg(String name) throws IOException {
		
		frequency.gameJoined();
			newGame();
	}

	public void exitGameMessage() throws IOException {
		if (starting) {
			System.out.println("THEY LEFT THE GAME)");
			endGame();
		}
	}

	public void playAgainMessage() throws IOException {
		replay2 = true;
		playAgain();
	}

	public void gameJoinedCallback() throws IOException {
		newGame();
	}

	

}
