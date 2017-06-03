/**
 * Jarred Price
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Parser {

	// String value to store the given token.
	private String token;

	// Scanner for scanner functionality.
	static Scanner scanner;
	
	// Boolean value used for Junit testing.
	public static boolean passed = false;

	// DrawableTurtle titled turtle for drawing.
	static DrawableTurtle turtle;

	// An arrayList to hold strings for loops.
	private static ArrayList<String> list = new ArrayList<String>();

	// An arrayList to hold integers for loops.
	private static ArrayList<Integer> numList = new ArrayList<Integer>();

	// A set of tokens to compare tokens to to determine if valid.
	static Set<String> listOfTokens = new HashSet<String>(
			Arrays.asList(new String[] { "forward", "end", "programEnd",
					"turn", "begin" }));

	// Hashmap to hold the String values mapped to their respective Integer
	// value.
	private static HashMap<String, Integer> mapping = new HashMap<String, Integer>();

	// An int NUMBER to hold the int value for numbers.
	private static int NUMBER;

	// An integer to hold the number of times that it must loop.
	private static int LOOP;

	// A flag to determine if the loop needs to break.
	private static int loopFlag = 0;

	// An int value used in counting the line for errors.
	private static int lineNumber = 1;

	// Get the next word from the scanner.
	public static String getNextWord() {
		if (!scanner.hasNext())
			return "programEnd";
		else
			return scanner.next();
	}

	public void match(String str, int lineNumber) {
		if (listOfTokens.contains(str)) {

		} else {

			System.out
					.println("Error! Found: " + str
							+ " instead of an actual token on line "
							+ lineNumber + "!");
			System.out.println("PROGRAM TERMINATING");
			System.out.println("PROGRAM TERMINATED");
			System.exit(0);
		}
	}

	/**
	 * Determines if the token is an actual token from the list.
	 * 
	 * @param str
	 *            the string representation of the token.
	 */
	public static boolean match(String str) {
		boolean value = false;
		if (listOfTokens.contains(str)) {
			value = true;
		}
		return value;
	}

	/**
	 * Runs the program
	 * 
	 * Requires that getNextWord() has been implemented and that items match
	 * begin and programEnd to start and finish.
	 * 
	 */
	public void program() {

		token = getNextWord();
		match("begin");
		statementList();
		match("programEnd");
		passedProgram();

	}

	public static void passedProgram() {
		passed = true;
		
	}

	/**
	 * Determines if the end of the program has been found.
	 * 
	 */
	public void statementList() {
		statement();
		if (!token.contentEquals("programEnd")) {
			statementList();
		}
	}

	/**
	 * Engine for running the parser.
	 * 
	 */
	public void statement() {

		runParser();
	}

	/**
	 * Runs the parser and uses tokens, getNextWord() and many other functions.
	 * 
	 */
	private void runParser() {

		token = getNextWord();

		if (!token.contentEquals("begin") && !token.contentEquals("forward")
				&& !token.contentEquals("turn") && !token.contentEquals("loop")
				&& !token.contentEquals("end")
				&& !token.contentEquals("programEnd")
				&& token.contentEquals("^-?\\d+$")) {

			// Match token for failure
			match(token, lineNumber);
		}

		// Match for begin.
		if (token.contentEquals("begin")) {

			// Match begin.
			match("begin");
			lineNumber++;
		}

		// Check the tokens for variables.
		for (int i = 0; i < 500; i++) {
			checkStuff(token);
		}

		// Check to see if it equals forward.
		if (token.contentEquals("forward")) {

			String rep = token;

			// Match forward.
			match("forward");

			token = getNextWord();
			String variable = token;

			// If variable isn't made up of letters.
			if (!variable.matches("[a-zA-Z]+")) {
				NUMBER = Integer.parseInt(token);
				// match number
				turtle.forward(NUMBER);
			}

			// If variable is made up of letters.
			if (variable.matches("[a-zA-Z]+")) {
				NUMBER = mapping.get(variable);
				turtle.forward(NUMBER);
			}

			lineNumber++;

		}

		// Check to see if equals turn.
		if (token.contentEquals("turn")) {

			String rep = token;

			// Match forward.
			match("turn");

			token = getNextWord();
			String variable = token;

			// Checks to see if matches numbers.
			if (!variable.matches("[a-zA-Z]+")) {
				NUMBER = Integer.parseInt(token);
				// match number
				turtle.turn(NUMBER);
			}

			// Checks to see if matches letters.
			if (variable.matches("[a-zA-Z]+")) {
				NUMBER = mapping.get(variable);
				turtle.turn(NUMBER);
			}

			lineNumber++;

		}

		// Do loop functions.
		if (token.contentEquals("loop")) {

			lineNumber++;

			// Match loop.
			match("loop");

			token = getNextWord();

			if (!token.matches("[a-zA-Z]+")) {
				NUMBER = Integer.parseInt(token);
			}
			if (token.matches("[a-zA-Z]+")) {
				NUMBER = mapping.get(token);
			}

			// LOOP HOLDS THE NUMBER OF LOOPS THAT IT WILL LOOP.
			LOOP = NUMBER;

			// Check to see if begin is next
			token = getNextWord();

			do {
				token = getNextWord();

				if (token.contentEquals("forward")) {

					// Match forward.
					match("forward");

					token = getNextWord();

					if (!token.matches("[a-zA-Z]+")) {
						NUMBER = Integer.parseInt(token);
					}
					if (token.matches("[a-zA-Z]+")) {
						NUMBER = mapping.get(token);
					}

					// match number
					list.add("forward");
					numList.add(NUMBER);

				}

				if (token.contentEquals("turn")) {

					// Match turn.
					match("turn");

					token = getNextWord();

					if (!token.matches("[a-zA-Z]+")) {
						NUMBER = Integer.parseInt(token);
					}
					if (token.matches("[a-zA-Z]+")) {
						NUMBER = mapping.get(token);
					}

					// match number
					list.add("turn");
					numList.add(NUMBER);

				}
				if (token.contentEquals("end")) {

					loopFlag = 1;

				}
			} while (loopFlag != 1);

			int size = list.size();

			lineNumber = lineNumber + size;

			for (int i = 0; i < LOOP; i++) {

				int x = 0;

				do {

					if (list.get(x).equals("forward")) {
						turtle.forward(numList.get(x));
					}

					if (list.get(x).equals("turn")) {
						turtle.turn(numList.get(x));
					}

					x++;
				} while (x < size);
			}

		} // END OF BASIC LOOP

		if (token.contentEquals("end")) {

			// Match end.
			match("end");
			lineNumber++;

		}

	}

	private void checkStuff(String token3) {
		if (!token.contentEquals("^-?\\d+$") && !token.contentEquals("begin")
				&& !token.contentEquals("forward")
				&& !token.contentEquals("turn") && !token.contentEquals("loop")
				&& !token.contentEquals("end")
				&& !token.contentEquals("programEnd")) {

			String me = token;

			token = getNextWord();

			if (!token.contentEquals("=")) {
				System.out.println("ERROR!!! INVALID TOKEN ON LINE: "
						+ lineNumber);
				System.out.println("TERMINATING!");
				System.out.println("TERMINATED");
				System.exit(0);
			}

			token = getNextWord();

			int value = Integer.parseInt(token);
			listOfTokens.add(me);
			mapping.put(me, value);

			token = getNextWord();

			lineNumber++;
		}

	}

	/**
	 * Reads the file in and parses into a string.
	 * 
	 * @param fileName
	 *            the fileName of the file that is read in.
	 */
	public static String readFile(String fileName) throws IOException {
		BufferedReader bufferedRead = new BufferedReader(new FileReader(
				fileName));
		try {
			StringBuilder string = new StringBuilder();
			String line = bufferedRead.readLine();

			while (line != null) {
				string.append(line);
				string.append("\n");
				line = bufferedRead.readLine();
			}
			return string.toString();
		} finally {
			bufferedRead.close();
		}
	}

	public static void main(String[] args) throws Exception {

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String fileName = "";
		String again = "";

		do {

			System.out.println("Please enter a file name: ");
			fileName = input.next();

			String stringInput = readFile(fileName);

			turtle = new DrawableTurtle();
			scanner = new Scanner(stringInput);
			Parser myCalc = new Parser();
			myCalc.program();

			// Draw the turtle stuff to the screen.
			turtle.draw();
			scanner.close();

			System.out.println();
			System.out
					.println("Do you want to Parse again? Type 'N' or 'n' if You don't want to parse anymore.");
			System.out.println();
			System.out
					.println("Type in anything else if you want to parse another file.");
			System.out.println("Please type in your selection: ");
			again = input.next().toUpperCase();
		} while (!again.equals("N"));

	}

}
