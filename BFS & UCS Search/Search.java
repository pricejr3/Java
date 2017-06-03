/**
* Jarred Price
*
* Implementation of BFS and UCS for various map states.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Search {
	

	// The Uniform Cost Search variable.
	public static double ucsCost;
	
	// The Scanner object.
	public static Scanner input = null;
	
	// The board to represent the environment.
	public static Node[][] board;
	
	// The starting Node.
	public static Node origin;
	
	// Number of rows.
	public static int boardRows;
	
	// Number of columns.
	public static int boardCols;
	
	// Holds the "pieces" of the board.
	public static char[][] boardPiece;
	
	// Other "stuff".
	public static int baseFactor;
	public static String baseString = "";
	public static int numberEnvelopes = 0;
	public static String directionString = "";
	public static String searchType = "BFS";
	public static int OriginXPos;
	public static int OriginYPos;
	
	

	public static void main(String[] args) throws FileNotFoundException {


		input = new Scanner(System.in);
		System.out.println("Please type in a filename: ");
		String filename = input.nextLine();
		readInFile(filename);
		ucsCost = 0.0;


		
		Comparator<Node> breadthComparator = new Node.BFSComparator();
		PriorityQueue<Node> breadthPQ = new PriorityQueue<Node>(50, breadthComparator);
		breadthPQ.add(new Node(OriginXPos, OriginYPos, 0.0));
		bfsUCSpath(breadthPQ);
		System.out.println(directionString);
		System.out.println("Number of movements for BFS: " + directionString.length());
		
	
       
		Comparator<Node> uniformCostComparator = new Node.UCSComparator();
		PriorityQueue<Node> uniformCostPQ = new PriorityQueue<Node>(5, uniformCostComparator);
		uniformCostPQ.add(new Node(OriginXPos, OriginYPos, 0.0));
		bfsUCSpath(uniformCostPQ);
		System.out.println(directionString);
		System.out.println("UCS COST: " + ucsCost);

	}

	public static void readInFile(String string) throws FileNotFoundException {

		// Read in the file.
		Scanner input;
		input = new Scanner(new File(string));
		
		// Read in the rows and columns.
		boardRows = input.nextInt();
		boardCols = input.nextInt();
		
		// Make the board with the rows and columns.
		board = new Node[boardRows][boardCols];

		// Acquire the origin position.
		OriginXPos = input.nextInt();
		OriginYPos = input.nextInt();
		
		// Make a new Node of the origin
		origin = new Node(OriginXPos, OriginYPos, 0);

		input.nextLine();

		// Read in the pieces of the board.
		boardPiece = new char[boardRows][boardCols];
		for (int i = boardRows - 1; i >= 0; i--) {
			String charString = input.nextLine();
			for (int j = 0; j < boardCols; j++) {
				boardPiece[i][j] = charString.charAt(j);

			}
		}

		
		// Read in the locations where envelopes lay
		// and mark the board accordingly.
		int envelope = 0;
		for (int i = boardRows - 1; i >= 0; i--) {

			for (int j = 0; j < boardCols; j++) {
				double doubleValue = input.nextDouble();
				board[i][j] = new Node(i, j, doubleValue);
				if (boardPiece[i][j] == 'X') {
					board[i][j].envelopes.add(envelope);
					origin.envelopes.add(envelope);
					envelope++;
				}
			}
		}

		// Close the scanner.
		input.close();

	}

	
	/**
	* Acquires children by comparing areas on the map and
	* determining where envelopes lay.
	* 
	* @param base  The base node.
	* @return nodeChild the children of the base node.
	*/
	public static Vector<Node> expand(Node base, Vector<Node> listOfNodes) {
		Vector<Node> nodeChild = new Vector<Node>();
		
		
		if (base.col > 0) {
			Vector<Integer> currEnvelopes = new Vector<Integer>();
			currEnvelopes.addAll(base.envelopes);
			for (int i = 0; i < board[base.row][base.col - 1].envelopes.size(); i++) {
				if (!currEnvelopes
						.contains(board[base.row][base.col - 1].envelopes
								.get(i))) {
					currEnvelopes.add(board[base.row][base.col - 1].envelopes
							.get(i));
				}
			}

			Node temp = new Node(base.row, base.col - 1, base.cost
					+ board[base.row][base.col - 1].cost, base.depth + 1,
					base.directions + "W", currEnvelopes);

			if (!listOfNodes.contains(temp)) {
				nodeChild.addElement(temp);
			}
		}

		if (base.row < boardRows - 1) {
			baseFactor = 1;
			baseString = "N";

			Vector<Integer> currEnvelopes = new Vector<Integer>();
			currEnvelopes.addAll(base.envelopes);
			for (int i = 0; i < board[base.row + baseFactor][base.col].envelopes
					.size(); i++) {
				if (!currEnvelopes
						.contains(board[base.row + baseFactor][base.col].envelopes
								.get(i))) {
					currEnvelopes
							.add(board[base.row + baseFactor][base.col].envelopes
									.get(i));
				}
			}

			Node temp = new Node(base.row + baseFactor, base.col, base.cost
					+ board[base.row + baseFactor][base.col].cost,
					base.depth + 1, base.directions + baseString, currEnvelopes);

			if (!listOfNodes.contains(temp)) {
				nodeChild.addElement(temp);
			}
		}

		if (base.row > 0) {
			Vector<Integer> currEnvelopes = new Vector<Integer>();
			currEnvelopes.addAll(base.envelopes);
			for (int i = 0; i < board[base.row - 1][base.col].envelopes.size(); i++) {
				if (!currEnvelopes
						.contains(board[base.row - 1][base.col].envelopes
								.get(i))) {
					currEnvelopes.add(board[base.row - 1][base.col].envelopes
							.get(i));
				}
			}

			Node temp = new Node(base.row - 1, base.col, base.cost
					+ board[base.row - 1][base.col].cost, base.depth + 1,
					base.directions + "S", currEnvelopes);

			if (!listOfNodes.contains(temp)) {
				nodeChild.addElement(temp);
			}
		}

		if (base.col < boardCols - 1) {
			Vector<Integer> currEnvelopes = new Vector<Integer>();
			currEnvelopes.addAll(base.envelopes);
			for (int i = 0; i < board[base.row][base.col + 1].envelopes.size(); i++) {
				if (!currEnvelopes
						.contains(board[base.row][base.col + 1].envelopes
								.get(i))) {
					currEnvelopes.add(board[base.row][base.col + 1].envelopes
							.get(i));
				}
			}

			Node temp = new Node(base.row, base.col + 1, base.cost
					+ board[base.row][base.col + 1].cost,
					base.depth + 1, base.directions + "E", currEnvelopes);

			if (!listOfNodes.contains(temp)) {
				nodeChild.addElement(temp);
			}
		}

	

		return nodeChild;
	}

	/**
	* Determines the cost of the BFS and UCS traversals.
	* 
	* @param pq The PriorityQueue value.
	* @return true if age is legal
	*/
	public static void bfsUCSpath(PriorityQueue<Node> pq) {
		Vector<Node> listOfNodes = new Vector<Node>();
		boolean collectedAllEnvelopes = false;

		do {

			Node thisNode = pq.poll();

			boolean same = true;
		
			
			if(searchType == "BFS"){
				
			
			
			if(thisNode.col != origin.col){
				same = false;
		
			}
			
			
			if(thisNode.row != origin.row){
				same = false;
			}
			
			if(thisNode.envelopes.size() != origin.envelopes.size()){
				same = false;
			}
			}
			
			
			if (!same) {
				
				same = true;
		
				

				if (thisNode.envelopes.size() == origin.envelopes.size()
						&& !collectedAllEnvelopes) {
					collectedAllEnvelopes = true;
					pq.clear();
				}
				listOfNodes.add(thisNode);

				pq.addAll(expand(thisNode, listOfNodes));

			} else {
				ucsCost = thisNode.cost;
				directionString = thisNode.directions;
				break;

			}

		} while (pq.size() != 0);
		{

		}
	}

	
}

