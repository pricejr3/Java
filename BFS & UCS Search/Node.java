/**
 * Jarred Price
 * Implementation of a basic Node class to use with the Search
 * techniques pertaining to the maps.
 */
import java.util.Vector;
import java.util.Comparator;

public class Node {

	// The components of the Node.
	double cost;
	int row;
	int col;
	Vector<Integer> envelopes;
	int depth;
	String directions;

	/**
	 * Constructor for Node with only 3 parameters.
	 * 
	 * @param row
	 *            The row of the node pos.
	 * @param col
	 *            The col of the node pos.
	 * @param cost
	 *            The cost of the node.
	 */
	public Node(int row, int col, double cost) {
		this.row = row;
		this.col = col;
		this.cost = cost;
		depth = 0;
		directions = "";
		envelopes = new Vector<Integer>();
	}

	/**
	 * Constructor for Node with 5 parameters.
	 * 
	 * @param row
	 *            The row of the node pos.
	 * @param col
	 *            The col of the node pos.
	 * @param cost
	 *            The cost of the node.
	 * @param depth
	 *            The depth of the node.
	 * @param direction
	 *            The direction of the node.
	 * @param envelopes
	 *            The envelope that may lay in the node.
	 */
	public Node(int row, int col, double cost, int depth, String actions,
			Vector<Integer> currEnvelopes) {
		this.row = row;
		this.col = col;
		this.cost = cost;
		this.depth = depth;
		this.directions = actions;
		this.envelopes = currEnvelopes;
	}

	/**
	 * Determines if a node is equal to another node.
	 * 
	 */
	public boolean equals(Object object) {
		Node nde = (Node) object;
		return (nde.row == row && (envelopes.size() == nde.envelopes.size() && nde.col == col));
	}

	/**
	 * Compares the cost of 2 nodes. Used in UCS search.
	 * 
	 */
	public static class UCSComparator implements Comparator<Node> {
		public int compare(Node a, Node b) {
			return (int) (a.cost - b.cost);
		}
	}

	/**
	 * Compares the depth of 2 nodes. Used in BFS search.
	 * 
	 */
	public static class BFSComparator implements Comparator<Node> {
		public int compare(Node a, Node b) {
			return a.depth - b.depth;
		}
	}

}
