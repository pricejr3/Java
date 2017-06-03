
 */
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayGame extends JPanel {
	
	public OceanShips user1Ship;
	public OceanShips user2Ship;
	public UserShips user1Items;
	public UserShips user2Items;
	public ServerBoard user1Board;
	public ServerBoard user2Board;
	public MoveShips directional;
	public Shoot shoot;
	public Boat typeOfBoat;
	public DisplayGui runner;
	public JFrame statistics;
	public JTextArea showStats;
	public int count;

	public DisplayGame(DisplayGui running) throws IOException {

		count = 0;
		this.runner = running;
		setSize(600, 400);
		setLayout(null);

		shoot = new Shoot(this);
		shoot.setBounds(321, 320, 10 * 25, 50);

		add(shoot);

		directional = new MoveShips(this);
		directional.setBounds(321, 63, 10 * 25, 10 * 25);
		add(directional);
		resetView();

		resetView();

	}

	public void resetView() throws IOException {
		if (user1Items != null) {
			this.remove(user1Items);
		}
		if (user2Items != null) {
			remove(user2Items);
		}
		user1Ship = new OceanShips();

		user1Items = new UserShips(user1Ship, true, "Player 1");
		shoot.setVisible(false);
		directional.setVisible(true);
		directional.setShips(user1Items);
		user1Items.setPosition(30, 63);
		user1Items.disableButtons();
		add(user1Items);
		repaint();
	}

	public void placeShipsView() {
		user1Items.enableButtons();
	}

	public void waitingView() {
		user1Items.disableButtons();
		repaint();
	}

	public void playView(OceanShips opponent) throws IOException {
		user1Items.commitShips(user1Ship);
		user2Ship = opponent;
		user2Items = new UserShips(user2Ship, false, "Player 2");
		user2Items.setPosition(321, 63);
		user2Items.setVisible(true);
		add(user2Items);
		directional.setVisible(false);
		shoot.setVisible(true);
		shoot.setEnabled(false);
		repaint();
	}

	public void chooseShotView() {
		shoot.enable();
	}

	public void waitingForShotView() {
		shoot.disable();
	}

	
	public void firing() throws IOException {
		TargetCoordinates target;

		target = user2Items.getSelectedCell();

		displayShot(target, user2Board, user2Items, user2Ship, "Player 2");
		runner.shootGui(target);

	}

	public void getShot(TargetCoordinates target) throws IOException {
		displayShot(target, user1Board, user1Items, user1Ship, "Player 1");
	}


	public void startGameMessage() throws IOException {

		runner.playGui(user1Ship);

	}

	public void displayShot(TargetCoordinates target, ServerBoard board,
			UserShips theShips, OceanShips arsenal, String playerName) throws IOException {

		statistics = new JFrame("Score Board");
		showStats = new JTextArea(6, 8);
		statistics.setSize(450, 80);
		statistics.setLocation(350, 600);
		statistics.setVisible(true);
		statistics.add(new JScrollPane(showStats));

		typeOfBoat = arsenal.isHit(target);

		theShips.totalShots++;
		String name = playerName;
		if (typeOfBoat != null) {

		
			theShips.numSpaces--;
			theShips.numberHit++;

			theShips.setHit(target);

			boolean won = false;
			if (theShips.numSpaces == 0) {
				won = true;
				ProgramProtocol.playAgainPrompt(won);
			}

		}

		if (typeOfBoat == null) {

			theShips.setMiss(target);
			theShips.numberMissed++;
		}

		if (count % 2 == 0) {

			playerName = "Player 1";
		}

		else {

			playerName = "Player 2";

		}
		count++;

		showStats.append(playerName + " Hits: " + theShips.numberHit + "\n");
		showStats.append(playerName + " Miss: " + theShips.numberMissed + "\n");

	}


}
