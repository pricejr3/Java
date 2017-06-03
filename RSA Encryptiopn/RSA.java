/** Implementation of RSA Homework
 *
 * CSE 270
 * 11/2/2014
 * 
 * @author Jarred Price
 * 
 */

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RSA {

	static long primeOne = 0;
	static long primeTwo = 0;
	static int flagPrint = 0;
	static long nPQ = 0;
	static int eValue = 0;
	static long coPrimeNum = 0;
	static String printCoPrime = "no";

	// Main method to run program.
	public static void main(String[] args) {

		// Instantiate scanner for use.
		Scanner scanner = new Scanner(System.in);

		// Print out to user.
		System.out.println("Part 1 - RSA KEY GENERATION");

		do {
			if (flagPrint == 2) {
				System.out.println("NOT PRIME!");
			}
			System.out.println("Enter prime p:");
			primeOne = scanner.nextLong();
			flagPrint = 2;
		} while (isPrime(primeOne) != true);

		do {
			if (flagPrint == 3) {
				System.out.println("NOT PRIME!");
			}
			System.out.println("Enter prime q:");
			primeTwo = scanner.nextLong();
			flagPrint = 3;
		} while (isPrime(primeTwo) != true);

		// Calculate nPQ and store into variable
		nPQ = getNPQ(primeOne, primeTwo);
		// Calculate the number of CoPrimes and store into variable
		coPrimeNum = coPrimeTotal(nPQ);

		// Print items out to user.
		System.out.println("Thank you. Keep these primes secret.");
		System.out.println("n = pq = " + nPQ + " (share this value)");
		System.out.println("There are " + coPrimeNum + " integers <= " + nPQ
				+ " that are coprime with " + nPQ + ".");
		System.out.println("Choose a value for e.");
		System.out.println("Make sure 1 < e < " + coPrimeNum
				+ " and e is coprime with " + coPrimeNum);

		// New Scanner because others aren't working right.
		Scanner scanner3 = new Scanner(System.in);

		// Ask if they would like to see the list of coPrimes.
		System.out.println("Would you like to see a list of " + coPrimeNum
				+ "'s coprimes (y/n)?");
		printCoPrime = scanner3.nextLine();

		// If they said 'y'. then print them out.
		if (printCoPrime.equals("y") || printCoPrime.equals("Y")) {
			System.out.println(printCoPrimesUsingArrayList(coPrimeNum));
		}

		// Ask them for an eValue. If it is not coPrime, then keep
		// asking them for valid input.
		do {
			if (flagPrint == 8) {
				System.out.println("Make sure 1 < e < " + coPrimeNum
						+ " and e is coprime with " + coPrimeNum);
			}
			System.out.println("Choose a value for e:");
			eValue = scanner.nextInt();
			flagPrint = 8;
		} while ((isCoPrime(coPrimeNum, eValue) != true));

		// Calculates the inverse and stores into a variable.
		int inverse2 = inverse(eValue, coPrimeNum);

		// Printing out to user.
		System.out.println("The inverse of " + eValue + ", mod " + coPrimeNum
				+ " is " + inverse2);
		System.out.println("PUBLIC KEY: e, n: " + eValue + ", " + nPQ);
		System.out.println("PRIVATE KEY: d, n: " + inverse2 + ", " + nPQ);

		// PART 2////////////////////////////////////////

		// Printing out to user.
		System.out.println("");
		System.out.println("");
		System.out.println("Part 2 - RSA ENCRYPTION USING PUBLIC KEY");
		System.out
				.println("KEYS HAVE BEEN GENERATED.  Let's encrypt a message.");
		System.out
				.println("Enter an uppercase word you would like to encrypt:");

		// New Scanner because others don't work properly.
		Scanner scanner2 = new Scanner(System.in);

		// New string variable to hold the persons word.
		String word = scanner2.nextLine();

		// Print out to user.
		System.out.println("Converted to numbers, your message is: "
				+ convertText(word));
		System.out.println("For each value, compute (value)^" + eValue
				+ " mod " + nPQ + ".");

		// This holds the encrypted text
		ArrayList<Integer> encrypted = getEncrypted(word);

		// Print out to user.
		System.out.println("Encrypted, your message is: " + encrypted);

		// PART 3////////////////////////////////////////

		// Print out to user.
		System.out.println("");
		System.out.println("");
		System.out.println("Part 3 - RSA DECRYPTION USING PRIVATE KEY");
		System.out.println("Now, for each value, compute (value)^" + inverse2
				+ " mod " + nPQ + ".");

		// Obtain an int[] array of the encrypted text.
		int[] encryptionArray = convertToIntArray(encrypted);

		// Obtain a string of the decrypted text.
		String decryptString = getDecrypted(encryptionArray);

		// Print out to user.
		System.out.println("Decrypted, your message is: " + decryptString);

		// String to hold the converted text.
		String convertedBack = convertText2(decryptString);
		System.out.println("Converted back to text, your message is: "
				+ convertedBack);

	}

	/**
	 * Converts the decrypted String to the actual word (not just the array in
	 * string representation).
	 * 
	 * @param decryptString
	 *            The string to convert.
	 * 
	 * @return doStuff The converted string.
	 */
	public static String convertText2(String decryptString) {

		// New string to return
		String doStuff = decryptString;

		// Convert it below:
		doStuff = doStuff.replaceAll("[\\[\\],]", "");
		doStuff = doStuff.replaceAll("10", "K");
		doStuff = doStuff.replaceAll("11", "L");
		doStuff = doStuff.replaceAll("12", "M");
		doStuff = doStuff.replaceAll("13", "N");
		doStuff = doStuff.replaceAll("14", "O");
		doStuff = doStuff.replaceAll("15", "P");
		doStuff = doStuff.replaceAll("16", "Q");
		doStuff = doStuff.replaceAll("17", "R");
		doStuff = doStuff.replaceAll("18", "S");
		doStuff = doStuff.replaceAll("19", "T");
		doStuff = doStuff.replaceAll("20", "U");
		doStuff = doStuff.replaceAll("21", "V");
		doStuff = doStuff.replaceAll("22", "W");
		doStuff = doStuff.replaceAll("23", "X");
		doStuff = doStuff.replaceAll("24", "Y");
		doStuff = doStuff.replaceAll("25", "Z");
		doStuff = doStuff.replaceAll("0", "A");
		doStuff = doStuff.replaceAll("1", "B");
		doStuff = doStuff.replaceAll("2", "C");
		doStuff = doStuff.replaceAll("3", "D");
		doStuff = doStuff.replaceAll("4", "E");
		doStuff = doStuff.replaceAll("5", "F");
		doStuff = doStuff.replaceAll("6", "G");
		doStuff = doStuff.replaceAll("7", "H");
		doStuff = doStuff.replaceAll("8", "I");
		doStuff = doStuff.replaceAll("9", "J");
		doStuff = doStuff.replaceAll(" ", "");

		// Return the string.
		return doStuff;
	}

	/**
	 * Converts the int array to a decrypted string.
	 * 
	 * @param encryptionArray
	 *            The inputed int array.
	 * 
	 * @return returnStr The decrypted string.
	 */
	public static String getDecrypted(int[] encryptionArray) {

		// New string to return.
		String returnStr = null;

		// Calculate the inverse.
		int inverse2 = inverse(eValue, coPrimeNum);

		// New arraylist List to do String conversion.
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < encryptionArray.length; i++) {
			int j = (int) modPow(encryptionArray[i], inverse2, nPQ);

			list.add(j);

		}

		// Return the string.
		returnStr = list.toString();
		return returnStr;

	}

	/**
	 * Converts the ArrayList into an int array
	 * 
	 * @param encrypted
	 *            The inputed arrayList
	 * 
	 * @return ret The int array.
	 */
	public static int[] convertToIntArray(ArrayList<Integer> encrypted) {

		// The int array to return.
		int[] ret = new int[encrypted.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = encrypted.get(i).intValue();
		}

		// Return the int array.
		return ret;
	}

	/**
	 * Converts the ArrayList into an int array
	 * 
	 * @param word
	 *            The inputed string
	 * 
	 * @return ret The arrayList
	 */
	public static ArrayList<Integer> getEncrypted(String word) {

		// The arrayList to return.
		ArrayList<Integer> list = new ArrayList<Integer>();
		String s = word;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			Character.getNumericValue(c);

			long value = Character.getNumericValue(c) - 10;

			list.add((int) modPow(value, eValue, nPQ));

		}

		// Return it.
		return list;

	}

	/**
	 * Returns the modPow.
	 * 
	 * @param a
	 *            Input value
	 * @param b
	 *            Input value
	 * @param m
	 *            Input value
	 * 
	 * @return returnVal The calculated value
	 */
	public static long modPow(long a, long b, long m) {
		long returnVal = 0;
		if (b == 0) {
			returnVal = 1;
		} else {
			long input = (b / 2);
			long modPow = modPow(a, input, m);
			if ((b % 2) != 0) {
				returnVal = (((modPow * modPow) % m) * a);
				returnVal = returnVal % m;

			} else {
				returnVal = (modPow * modPow);
				returnVal = returnVal % m;
			}

		}

		// Return the calculated value.
		return returnVal;

	}

	/**
	 * Converts the string to arrayList
	 * 
	 * @param word
	 *            The inputed string
	 * 
	 * 
	 * @return The converted arrayList
	 */
	public static ArrayList<Integer> convertText(String word) {

		ArrayList<Integer> list = new ArrayList<Integer>();
		String s = word;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			Character.getNumericValue(c);

			int value = Character.getNumericValue(c) - 10;
			list.add(value);

		}

		return list;

	}

	/**
	 * Returns the nPQ value.
	 * 
	 * @param p1
	 *            Input value
	 * @param p2
	 *            Input value
	 * 
	 * 
	 * @return nPQ
	 */
	public static long getNPQ(long p1, long p2) {

		return primeOne * primeTwo;
	}

	/**
	 * Returns the nPQ value.
	 * 
	 * @param a
	 *            Input value
	 * @param b
	 *            Input value
	 * 
	 * 
	 * @return a3 The calculated inverse
	 */
	public static int inverse(long a, long m) {

		long a1;
		long a2;
		long a4;
		long a5;
		long a6;

		a4 = m;
		a1 = 0;
		a2 = 1;

		do {
			a6 = a1 - ((a4 / a) * a2);
			a1 = a2;
			a2 = a6;
			a5 = (a4 % a);
			a4 = a;
			a = a5;
		} while (a != 1);

		long n = ((a2 + m));
		n = n % m;
		if (n == 0) {
			throw new IllegalStateException("no inverse in mod m");
		}
		if (n == 1) {
			throw new IllegalStateException("no inverse in mod m");
		}
		return ((int) n);

	}

	/**
	 * Returns if a number is CoPrime
	 * 
	 * @param coPrimeNum
	 *            Input value
	 * @param eValue
	 *            Input value
	 * 
	 * 
	 * @return ret True or false if it is coPrime.
	 */
	public static boolean isCoPrime(long coPrimeNum, int eValue) {

		boolean ret = false;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < coPrimeNum; i++) {
			if (gcd(coPrimeNum, i) == 1) {

				list.add(i);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(eValue)) {
				ret = true;
			}
		}
		return ret;

	}

	/**
	 * Returns if a number is prime
	 * 
	 * @param n
	 *            Input value
	 * 
	 * 
	 * @return True or false if it is Prime.
	 */
	public static Boolean isPrime(long n) {

		if (n == 1) {

			return false;
		}

		// 2 is the first prime number in this program.
		if (n == 2) {

			return true;
		}
		// Start with i = the first prime
		for (int i = 2; i <= (int) Math.sqrt(n) + 1; i++) {

			if (n % i == 0) {

				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the gcd
	 * 
	 * @param a
	 *            Input value
	 * @param b
	 *            Input value
	 * 
	 * 
	 * @return the gcd.
	 */
	public static int gcd(long a, long b) {

		if (b == 0)
			return (int) a;
		else
			return gcd(b, a % b);
	}

	/**
	 * Returns the number of coPrimes.
	 * 
	 * @param nPQ
	 *            Input value
	 * 
	 * 
	 * @return numOfCoPrimes the number of coPrimes.
	 */
	public static long coPrimeTotal(long nPQ) {
		int numOfCoPrimes = 0;
		for (int i = 1; i < nPQ; i++) {
			if (gcd(nPQ, i) == 1) {
				numOfCoPrimes++;
			}
		}
		return numOfCoPrimes;
	}

	/**
	 * Prints out the CoPrimes
	 * 
	 * @param nPQ
	 * 
	 * 
	 * @return String representation of the coPrimes.
	 */
	public static String printCoPrimes(long nPQ) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nPQ; i++) {
			if (gcd(nPQ, i) == 1) {
				sb.append(i);
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns a string of the coprimes using an arrayList
	 * 
	 * @param coPrimeNum
	 *            Input value
	 * 
	 * @return returnMe
	 */
	public static String printCoPrimesUsingArrayList(long coPrimeNum) {

		String returnMe = null;
		ArrayList<Long> list = new ArrayList<Long>();
		for (long i = 0; i < coPrimeNum; i++) {
			if (gcd(coPrimeNum, i) == 1) {

				list.add(i);
			}
		}
		returnMe = list.toString();
		returnMe = returnMe.replaceAll("[\\[\\],]", "");

		return returnMe;
	}

}