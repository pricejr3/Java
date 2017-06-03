
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class UserShips extends JPanel {

	public ImageIcon open = new ImageIcon(imageLoader("open.png"));
	public ImageIcon selected = new ImageIcon(imageLoader("selected.png"));
	public ImageIcon hit = new ImageIcon(imageLoader("hit.png"));
	public ImageIcon miss = new ImageIcon(imageLoader("miss.png"));

	public ImageIcon[] icon = new ImageIcon[10];

	public JRadioButton[] boats = new JRadioButton[5];

	public JRadioButton[][] array = new JRadioButton[10][10];

	public JRadioButton hidden = new JRadioButton();

	public ButtonGroup group = new ButtonGroup();
	public ButtonGroup ships = new ButtonGroup();

	public String name;
	public int numberHit;
	public int numberMissed;

	// HISTORY STUFF.
	public static int totalShots = 0;

	public OceanShips arsenal;

	public int numSpaces;

	public UserShips(OceanShips newarsenal, boolean positionShips,
			String userName) {

		numSpaces = 17;
		name = userName;
		numberHit = 0;
		numberMissed = 0;
		totalShots = 0;

		hidden.setBounds(250 * 10, 0, 0, 0);
		group.add(hidden);

		this.arsenal = newarsenal;

		setMaximumSize(new Dimension(250, 250));
		setLayout(null);
		setBounds(0, 0, 250, 250);
		this.makeGrid(positionShips);
		this.loadShips(positionShips);
		this.positionShips(arsenal);

		int z = -1;
		if (positionShips) {
			boats[0].setSelected(true);
			z = 0;
		}

		for (int i = 0; i < boats.length; i++) {
			add(boats[i], z);
		}

	}

	public BufferedImage imageLoader(String name) {
		name = "img/" + name;
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getClassLoader().getResource(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return image;
	}

	public void loadShips(boolean positionShips) {

		String[] paths = { "patrol.png", "destroyer.png", "sub.png",
				"bship.png", "carrier.png", "patrolRotated.png",
				"destroyerRotated.png", "subRotated.png", "carrierRotated.png",
				"bshipRotated.png" };

		for (int i = 0; i < 10; i++) {
			icon[i] = new ImageIcon(imageLoader(paths[i]));
		}

		JRadioButton btn;

		for (int p = 0; p < boats.length; p++) {

			btn = new JRadioButton(icon[p]);
			btn.setBorder(null);
			btn.setOpaque(false);
			btn.setSelectedIcon(icon[p + 5]);
			btn.setVisible(positionShips);
			boats[p] = btn;
			ships.add(btn);
		}

	}

	public void disableButtons() {
		for (int i = 0; i < boats.length; i++) {
			boats[i].setEnabled(false);
		}
	}

	public void enableButtons() {
		for (int i = 0; i < boats.length; i++) {
			boats[i].setEnabled(true);
		}
	}

	public void makeGrid(boolean positionShips) {
		// draw hit points
		JRadioButton btn;
		for (int i = 0; i < 10; i++) {
			for (int y = 0; y < 10; y++) {
				btn = new JRadioButton(open);
				btn.setMargin(null);
				btn.setBounds(i * 25, y * 25, 25, 25);
				btn.setSelectedIcon(selected);
				btn.setOpaque(false);
				btn.setDisabledIcon(open);
				btn.setEnabled(!positionShips);
				btn.setBorder(null);
				array[i][y] = btn;
				group.add(btn);
				add(btn);
			}
		}

	}

	/**
	 * positions the ships based on their positions in the arsenal object
	 * 
	 * @param newarsenal
	 *            arsenal
	 */
	public void positionShips(OceanShips newarsenal) {
		Boat ship;
		Boat.Rotation rot;
		int width;
		int height;
		int[] pos;

		for (int s = 0; s < 5; s++) {
			ship = newarsenal.getShip(s);
			pos = ship.getPosition();
			rot = ship.getRotation();
			if (rot == Boat.Rotation.HORIZ) {
				boats[s].setIcon(icon[s + 10]);
				boats[s].setSelectedIcon(icon[s + 15]);
				width = 25 * ship.getSize();
				height = 25;
			} else {
				width = 25;
				height = 25 * ship.getSize();
			}

			boats[s].setBounds(pos[0] * 25, pos[1] * 25, width, height);

		}

	}

	public void commitShips(OceanShips newarsenal) {
		this.removeAll();
		this.arsenal = newarsenal;
		this.makeGrid(true);
		this.loadShips(true);
		this.positionShips(newarsenal);

		int i = 0;
		commitShips(i);

	}

	public void commitShips(int i) {

		int z = -1;
		if (i < boats.length) {
			add(boats[i], z);
			commitShips(++i);
		}

	}

	public void setMiss(TargetCoordinates target) {
		int x = target.one;
		int y = target.two;
		array[x][y].setDisabledIcon(miss);
		array[x][y].setSelectedIcon(miss);
		array[x][y].setEnabled(false);
		hidden.setSelected(true);
		repaint();
	}

	public void setHit(TargetCoordinates target) {
		int x = target.one;
		int y = target.two;
		array[x][y].setDisabledIcon(hit);
		array[x][y].setSelectedIcon(hit);
		array[x][y].setEnabled(false);
		hidden.setSelected(true);
		repaint();
	}

	public void showShip(int index) {
		boats[index].setVisible(true);
		repaint();
	}

	public TargetCoordinates getSelectedCell() {
		int[] position = { 0, 0 };
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j].isSelected()) {
					position[0] = i;
					position[1] = j;
				}
			}
		}

		return new TargetCoordinates(position);
	}

	public int getSelectedShip() {
		for (int i = 0; i < boats.length; i++) {
			if (boats[i].isSelected()) {
				return i;
			}
		}
		return 6;

	}

	public void moveShip(int x, int y) {
		int ship = getSelectedShip();
		int length = 0;
		int width = 0;
		Boat boat = arsenal.getShip(ship);
		int[] positions = boat.getPosition();

		if (this.isInRange(boat, positions[0] + x, positions[1] + y)) {
			boat.setPosition(positions[0] + x, positions[1] + y);
			width = (int) boats[ship].getBounds().getWidth();
			length = (int) boats[ship].getBounds().getHeight();
			boats[ship].setBounds((positions[0] + x) * 25,
					(positions[1] + y) * 25, width, length);
		}

		repaint();

	}

	public void moveUp() {
		moveShip(0, -1);
	}

	public void moveDown() {
		moveShip(0, 1);
	}

	public void leftMove() {
		moveShip(-1, 0);
	}

	public void moveRight() {
		moveShip(1, 0);
	}

	public boolean isInRange(Boat ship, int x, int y) {
		// boolean ret = fales;
		int xVal = 9;
		int yVal = 9;

		int size = ship.getSize();

		xVal -= size + -1;

		if (x <= xVal && x >= 0 && y <= yVal && y >= 0) {
			return true;
		} else
			return false;

	}

	public void setPosition(int x, int y) {
		int width = getWidth();
		int height = getHeight();
		setBounds(x, y, width, height);
	}

}