
import java.io.Serializable;

public class Boat implements Serializable {

	int length = 0;
	String name;
	

	private int x = 0;
	private int y = 0;
	private Boat.Rotation rotation;

	public Boat(String shipName, int shipSize) {
		length = shipSize;
		name = shipName;
	}

	public String getName() {
		String name = new String(this.name);
		return name;
	}

	public int getSize() {
		return length;
	}

	public enum Rotation {
		HORIZ
	};

	public void setPosition(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	public void setRotation(Boat.Rotation rotation2) {
		this.rotation = rotation2;
	}

	public int[] getPosition() {
		int[] pos = { this.x, this.y };
		return pos;
	}

	public Boat.Rotation getRotation() {
		return this.rotation;
	}

	protected void setName(String newName) {
		this.name = newName;
	}

	protected void setSize(int newSize) {
		this.length = newSize;

	}

}
