/*
 * Jarred Price
 * PRICEJR3
 * 
 * Interpreter
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class Main {

	public static TreeMap<String, String> mapping = new TreeMap<String, String>();
	public static String[] toks;
	public static FileReader filereading = null;
	public static int lineCounter = 0;
	public static String currentLine = null;

	public static String LHS = null;
	public static String RHS = null;
	public static String MIDDLE = null;

	public static void main(String[] args) {
                
		readFile(args[0]);
		

	}

	private static void readFile(String fileName) {

		try {
			filereading = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader wrapper = new BufferedReader(filereading);
		try {
			while ((currentLine = wrapper.readLine()) != null) {
				lineCounter++;
				createNewTokens(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createNewTokens(String currentLine) {

		// Reset the values to null in the String array.
		toks = null;

		// Strip out the whitespace
		toks = currentLine.trim().split("\\s++");

		usetoks(currentLine);

	}

	private static void usetoks(String currentLine) {

		if (toks[0].equals("PRINT")) {
			print(toks);
		} else if (Arrays.asList(toks).contains("FOR")) {
			loops(currentLine);

		} else {
			assignment(toks);
		}

	}

	public static void print(String[] toks) {

		if (!mapping.containsKey(toks[1])) {
			runtimeError();
		}
		System.out.println(toks[1] + "=" + mapping.get(toks[1]));

	}

	public static void runtimeError() {
		System.out.println("Runtime ERROR! on line: " + lineCounter);

		System.exit(0);
	}

	private static boolean isParsableString(String input) {
		boolean parsable = true;
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException e) {
			parsable = false;
		}
		return parsable;
	}

	public static void loops(String currentLine ) {

		ArrayList<String> forToks = new ArrayList<String>();

		String[] forTokens;
		forTokens = currentLine.trim().split("\\s++");
		StringBuilder newString = new StringBuilder();

		for (int i = 1; i < forTokens.length - 1; i++) {
			newString.append(forTokens[i]);
			newString.append(" ");
		}

		currentLine = newString.toString();

		int looping = Integer.parseInt(forTokens[1]);

		StringBuilder newString2 = new StringBuilder();
		for (int i = 2; i < forTokens.length - 1; i++) {
			newString2.append(forTokens[i]);
			newString2.append(" ");
		}

		currentLine = newString2.toString();

		for (int i = 0; i < looping; i++) {

			String[] tokensLoop;
			tokensLoop = currentLine.trim().split("\\s++");

			while (currentLine.length()!=0) {

				if (!currentLine.startsWith("FOR")) {

					String[] item = currentLine.split("\\; ");
					forToks.add(item[0] + ";");

					StringBuilder newString3 = new StringBuilder();
					for (int j = 1; j < item.length; j++) {
						newString3.append(item[j]);
						newString3.append("; ");
					}

					currentLine = newString3.toString();

				} else {

					String[] endForToks;
					endForToks = currentLine.trim().split("\\s++");
					StringBuilder endForString = new StringBuilder();

					int counter = 0;
					for (int p = endForToks.length - 1; p >= 0; p--) {

						if (!endForToks[p].equals("ENDFOR")) {
							counter++;
						}

						else if (endForToks[p].equals("ENDFOR")) {
							break;
						}

					}

					for (int q = 0; q <= endForToks.length - counter - 1; q++) {
						endForString.append(endForToks[q]);
						endForString.append(" ");
					}

					forToks.add(endForString.toString());

					// the input is now used up.
					currentLine = "";

				}
			}

			useForToks(forToks);
		}
	}

	public static void assignment(String[] toks) {

		boolean runtimeError = true;
		LHS = toks[0];
		MIDDLE = toks[1];
		RHS = toks[2];

		boolean rhsInteger = isParsableString(RHS);
		boolean rhsString = !isParsableString(RHS);
		boolean rhsIntegerVariable = isParsableString(mapping.get(RHS));

		// LHS items
		boolean lhsIntegerVariable = isParsableString(mapping.get(LHS));
		boolean lhsStringVariable = !isParsableString(mapping.get(LHS));

		if (rhsInteger == true || rhsIntegerVariable == true) {

			if (MIDDLE.equals("=")) {

				if (rhsInteger == true) {
					mapping.put(LHS, RHS);
					runtimeError = false;
				}

				if (rhsIntegerVariable == true) {
					mapping.put(LHS, mapping.get(RHS));
					runtimeError = false;
				}

			}

			if (lhsIntegerVariable == true) {

				if (rhsInteger == true) {

					if (MIDDLE.equals("+=")) {
						int addVal = Integer.parseInt(mapping.get(LHS));
						addVal += Integer.parseInt(RHS);
						mapping.put(LHS, Integer.toString(addVal));
						runtimeError = false;

					}
					if (MIDDLE.equals("*=")) {
						int multVal = Integer.parseInt(mapping.get(LHS));
						multVal *= Integer.parseInt(RHS);
						mapping.put(LHS, Integer.toString(multVal));
						runtimeError = false;
					}

					if (MIDDLE.equals("-=")) {
						int subVal = Integer.parseInt(mapping.get(LHS));
						subVal -= Integer.parseInt(RHS);
						mapping.put(LHS, Integer.toString(subVal));
						runtimeError = false;
					}
				}

				if (rhsIntegerVariable == true) {
					// INTEGER ARITHMETIC
					if (MIDDLE.equals("+=")) {
						int addVal = Integer.parseInt(mapping.get(LHS));
						addVal += Integer.parseInt(mapping.get(RHS));
						mapping.put(LHS, Integer.toString(addVal));
						runtimeError = false;

					}
					if (MIDDLE.equals("*=")) {
						int multVal = Integer.parseInt(mapping.get(LHS));
						multVal *= Integer.parseInt(mapping.get(RHS));
						mapping.put(LHS, Integer.toString(multVal));
						runtimeError = false;
					}

					if (MIDDLE.equals("-=")) {
						int subVal = Integer.parseInt(mapping.get(LHS));
						subVal -= Integer.parseInt(mapping.get(RHS));
						mapping.put(LHS, Integer.toString(subVal));
						runtimeError = false;
					}
				}

			}

		}

		if (rhsString == true && RHS.contains("\"")) {

			if (MIDDLE.equals("=")) {
				mapping.put(LHS, RHS.replace("\"", ""));
				runtimeError = false;
			}

			if (lhsStringVariable == true) {

				if (MIDDLE.equals("+=")) {
					String concatVal = mapping.get(LHS);
					concatVal += RHS;
					mapping.put(LHS, concatVal.replace("\"", ""));
					runtimeError = false;

				}

			}

		} // end rhsString is a string and not variable.

		// RHS with a variable
		if (rhsString == true && !RHS.contains("\"")
				&& mapping.containsKey(RHS)) {

			if (MIDDLE.equals("=")) {
				mapping.put(LHS, mapping.get(RHS).replace("\"", ""));
				runtimeError = false;
			}

			if (lhsStringVariable == true) {

				if (MIDDLE.equals("+=")) {

					String concatVal = mapping.get(LHS);
					concatVal += mapping.get(RHS);
					mapping.put(LHS, concatVal.replace("\"", ""));
					runtimeError = false;

				}

			}

		}

		if (runtimeError == true) {
			runtimeError();
		}
	}

	private static void useForToks(ArrayList<String> forToks) {
		for (String stringTokens : forToks) {
			createNewTokens(stringTokens);
		}
	}

}
