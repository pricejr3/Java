public class attribute {

	public static void main(String[] args) {
		boolean[][] data1 = { { true, true, true, true, true },
				{ true, true, false, true, true },
				{ true, true, true, false, true },
				{ true, false, true, true, true },
				{ false, true, false, false, false },
				{ false, true, true, false, false },
				{ false, true, false, true, false },
				{ false, true, false, false, false }, };
		boolean[][] data2 = { { false, false, true, true, true, true },
				{ false, false, true, true, false, true },
				{ true, false, true, false, true, true },
				{ true, true, true, false, false, true },
				{ false, false, true, true, false, false },
				{ true, false, true, true, true, false },
				{ true, false, false, false, true, false },
				{ false, false, false, false, true, false }, };

		boolean[][] data3 = { { true, false, true, true },
				{ true, false, false, true }, { true, false, true, true },
				{ false, true, false, false }, { false, true, true, false },
				{ false, true, false, false }, };

		boolean[][] data4 = { { true, false, true, true },
				{ true, false, true, true }, { true, false, true, true },
				{ false, true, false, false }, { false, true, true, false },
				{ false, true, false, false }, };

		boolean[][] data5 = { { true, true, true, true },
				{ true, true, true, true }, { true, true, true, true },
				{ false, true, true, true }, { false, false, false, true },
				{ false, true, false, true }, };

		System.out.println(GetAttribute(data1)); // should output 1
		System.out.println(GetAttribute(data2)); // should output 3
		System.out.println(GetAttribute(data3)); // should output 1
		System.out.println(GetAttribute(data4)); // should output 3
		System.out.println(GetAttribute(data5)); // should output 2 checking to
													// see if displaying the 2nd
													// col works when tied with
													// 3

	}

	// Takes in a 2D boolean array and returns the attribute to choose
	private static int GetAttribute(boolean[][] data1) {

		int rows = 0;
		int cols = 0;

		int columnLookingAt = 0;

		boolean done = false;

		// Rows
		for (int i = 0; i < data1.length; i++) {
			// Get number of rows
			rows++;

			// Cols
			for (int j = 0; j < data1[i].length; j++) {
				cols++;
			}
		}

		// Get number of columns
		cols = cols / rows;

		int[] colCounter = new int[cols];
		int arrayIndex = 0;

		// END get row/col info
		// ////////////////////////////////////////

		// Iterate through each row and column

		do {
			for (int k = 0; k < rows; k++) {

				for (int j = 0; j < cols; j++) {
					if (j != arrayIndex) {

						if (data1[k][arrayIndex] == true && data1[k][j] == true) {
							colCounter[arrayIndex]++;

						}

					}
				}

			}
			// Increment colNumber by one
			arrayIndex++;

			if (arrayIndex == cols) {
				done = true;
			}

		} while (!done);

		int returnValue = 0;
		int indexToReturn = 0;

		for (int i = 0; i < colCounter.length - 1; i++) {

			if (returnValue < colCounter[i]) {
				returnValue = colCounter[i];
				indexToReturn = i;
			}

		}

		return indexToReturn + 1;
	}

}