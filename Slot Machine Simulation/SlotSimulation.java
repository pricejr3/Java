import java.util.Random;

public class SlotSimulation {

	public static int[] reelOne = new int[22];
	public static int[] reelTwo = new int[22];
	public static int[] reelThree = new int[22];

	static Random rnd = new Random();

	public static void main(String[] args) {

		double totalWinnings = 0;
		// Populate the arrays which hold the values.
		populateArrays();

		for (int i = 0; i < 1000; i++) {

			// Spin the slot,
			spinSlot();

			// Acquire total winnings from slot machine.
			totalWinnings = totalWinnings + checkWinning();
		}

		double endResults = 0;
		double gainOrLoss = 0;
		
		// 1000 spins, so $1000 spent.
		endResults = totalWinnings / 1000;
		gainOrLoss = totalWinnings - 1000;
		double formattedLoss = gainOrLoss * -1;
		
		if(endResults < 1.0)
			System.out.println(gainOrLoss + " -> Net loss of: $" + formattedLoss);
		if(endResults > 1.0)
			System.out.println("Net gain of: $" + gainOrLoss );
		
	}

	// Shuffle the arrays to simulate pulling
	// the lever.
	public static void spinSlot() {

		int idx;
		int value;

		for (int j = 0; j < 100; j++) {
			for (int i = reelOne.length - 1; i > 0; i--) {
				idx = rnd.nextInt(i + 1);
				value = reelOne[idx];
				reelOne[idx] = reelOne[i];
				reelOne[i] = value;
			}
		}

		for (int j = 0; j < 100; j++) {
			for (int i = reelTwo.length - 1; i > 0; i--) {
				idx = rnd.nextInt(i + 1);
				value = reelTwo[idx];
				reelTwo[idx] = reelTwo[i];
				reelTwo[i] = value;
			}
		}

		for (int j = 0; j < 100; j++) {
			for (int i = reelThree.length - 1; i > 0; i--) {
				idx = rnd.nextInt(i + 1);
				value = reelThree[idx];
				reelThree[idx] = reelThree[i];
				reelThree[i] = value;
			}
		}

		// for (int i = 0; i < reelOne.length; i++)
		// System.out.print(reelOne[i] + " ");

		// System.out.println();
		// for (int i = 0; i < reelTwo.length; i++)
		// System.out.print(reelTwo[i] + " ");

		// System.out.println();
		// for (int i = 0; i < reelThree.length; i++)
		// System.out.print(reelThree[i] + " ");
	}

	// This method checks to see what the user has won
	// per each spin.
	// The value at index 0 for each of the arrays
	// will represent the symbol that was spun
	// from the wheel.
	public static double checkWinning() {

		double returnWinnings = 0;

		// Check to see if left has cherry (winnings = 2)
		if (reelOne[0] == 1)
			returnWinnings = 2.0;

		// Check to see if right has a cherry (winnings = 2)
		if (reelThree[0] == 1)
			returnWinnings = 2.0;

		// Check to see if two cherries pop up (winnings = 5)
		if ((reelOne[0] == 1 && reelTwo[0] == 1)
				|| (reelOne[0] == 1 && reelThree[0] == 1)
				|| (reelTwo[0] == 1 && reelThree[0] == 1))
			returnWinnings = 5.0;

		// Check to see if three cherries pop up (winnings = 20)
		if (reelOne[0] == 1 && reelTwo[0] == 1 && reelThree[0] == 1)
			returnWinnings = 20.0;

		// Check to see if three oranges pop up (winnings = 20)
		if (reelOne[0] == 2 && reelTwo[0] == 2 && reelThree[0] == 2)
			returnWinnings = 20.0;

		// Orange Orange Bar (winnings = 10)
		if (reelOne[0] == 2 && reelTwo[0] == 2 && reelThree[0] == 5)
			returnWinnings = 10.0;

		// Bar Orange Orange (winnings = 10)
		if (reelOne[0] == 5 && reelTwo[0] == 2 && reelThree[0] == 2)
			returnWinnings = 10.0;

		// Three Plums (winnings = 20)
		if (reelOne[0] == 3 && reelTwo[0] == 3 && reelThree[0] == 3)
			returnWinnings = 20.0;

		// Plum Plum Bar (winnings = 14)
		if (reelOne[0] == 3 && reelTwo[0] == 3 && reelThree[0] == 5)
			returnWinnings = 14.0;

		// Bar Plum Plum (winnings = 14)
		if (reelOne[0] == 5 && reelTwo[0] == 3 && reelThree[0] == 3)
			returnWinnings = 14.0;

		// Three Bells (winnings = 20)
		if (reelOne[0] == 4 && reelTwo[0] == 4 && reelThree[0] == 4)
			returnWinnings = 20.0;

		// Bell Bell Bar (winnings = 18)
		if (reelOne[0] == 4 && reelTwo[0] == 4 && reelThree[0] == 5)
			returnWinnings = 18.0;

		// Bar Bell Bell (winnings = 18)
		if (reelOne[0] == 5 && reelTwo[0] == 4 && reelThree[0] == 4)
			returnWinnings = 18.0;

		// Bar Bar Bar (winnings = 50)
		if (reelOne[0] == 5 && reelTwo[0] == 5 && reelThree[0] == 5)
			returnWinnings = 50.0;

		// 7 7 7 (winnings = 100)
		if (reelOne[0] == 6 && reelTwo[0] == 6 && reelThree[0] == 6)
			returnWinnings = 100.0;

		return returnWinnings;
	}

	// This method populates the int arrays that will
	// represent each reel of the slot machine.
	public static void populateArrays() {

		/*
		 * The int arrays will hold int values that are used to represent the
		 * symbols.
		 * 
		 * 
		 * KEY:
		 * 
		 * Cherries = 1 Oranges = 2 Plums = 3 Bells = 4 Bars = 5 7's = 6
		 */

		// 2 cherries, 2 oranges, 5 plums, 10 bells, 2 bars, 1 seven
		reelOne = new int[] { 1, 1, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4,
				4, 4, 4, 5, 5, 6 };

		// 5 cherries, 3 oranges, 1 plum, 2 bells, 10 bars, 1 seven
		reelTwo = new int[] { 1, 1, 1, 1, 1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 5,
				5, 5, 5, 5, 5, 6 };

		// 2 cherries, 7 oranges, 10 plums, 1 bell, 1 bar, 1 seven
		reelThree = new int[] { 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 4, 5, 6 };

	}
}
