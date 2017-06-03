/*
 Jarred Price
 */

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

public class ParserTester {

	/*
	 * This tests to see if the readFile() method works.
	 */
	@Test
	public void readFileTest() throws IOException {

		String test = Parser.readFile("testJunit");
		test = test.replaceAll("\\s+", "");
		String compare = "begin";
		assertEquals("These should be equal.", compare, test);
	}

	/*
	 * This tests to see if the readFile() method works again.
	 */
	@Test
	public void readFileTest2() throws IOException {

		String test = Parser.readFile("JunitTest2");
		test = test.replaceAll("\\s+", "");
		String compare = "beginend";
		assertEquals("These should be equal.", compare, test);
	}

	/*
	 * This tests to see if the readFile() method works again.
	 */
	@Test
	public void readFileTest3() throws IOException {

		String test = Parser.readFile("blankTest");
		String compare = "";
		assertEquals("These should be equal.", compare, test);
	}

	/*
	 * This tests to see if testListOfTokens work.
	 */
	@Test
	public void checkListOfTokensTest() throws IOException {

		Set<String> listOfTokens2 = new HashSet<String>(
				Arrays.asList(new String[] { "forward", "end", "programEnd",
						"turn", "begin" }));
		assertEquals("These should be equal.", listOfTokens2,
				Parser.listOfTokens);
	}

	/*
	 * This tests to see if hashMap will work for this.
	 */
	@Test
	public void hashMapTest() throws IOException {

		HashMap<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("loop", 50);
		HashMap<String, Integer> mapping2 = new HashMap<String, Integer>();
		mapping2.put("loop", 50);
		assertEquals("These should be equal.", mapping, mapping2);
	}

	/*
	 * This tests to see if hashMap will work for this.
	 */
	@Test
	public void hashMapTest2() throws IOException {

		HashMap<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("forward", 5054);
		HashMap<String, Integer> mapping2 = new HashMap<String, Integer>();
		mapping2.put("forward", 5054);

		assertEquals("These should be equal.", mapping, mapping2);
	}

	/*
	 * This tests to see if regex will work with this.
	 */
	@Test
	public void regexTest() throws IOException {

		String one = "forward";
		boolean compare = false;
		boolean value = false;

		if (!one.matches("[a-zA-Z]+")) {
			value = true;
		}

		assertEquals("These should be equal.", value, compare);
	}

	/*
	 * This tests to see if regex will work with this.
	 */
	@Test
	public void regexTest2() throws IOException {

		String one = "12929292929";
		boolean compare = false;
		boolean value = false;

		if (!one.matches("[a-zA-Z]+")) {
			value = false;
		}

		assertEquals("These should be equal.", value, compare);
	}

	/*
	 * This tests to see if regex will work with this.
	 */
	@Test
	public void regexTest3() throws IOException {

		String one = "string";
		String two = "129344";
		boolean compare = false;
		boolean value = false;

		if (!one.matches("[a-zA-Z]+")) {
			value = false;
		}
		if (two.matches("[a-zA-Z]+")) {
			compare = true;
		}

		assertEquals("These should be equal.", value, compare);
	}

	/*
	 * This tests to see if regex will work with this.
	 */
	@Test
	public void regexTest4() throws IOException {

		String one = "stringfgff";
		String two = "5454";
		boolean compare = false;
		boolean value = false;

		if (!one.matches("[a-zA-Z]+")) {
			value = false;
		}
		if (two.matches("[a-zA-Z]+")) {
			compare = true;
		}

		assertEquals("These should be equal.", !value, !compare);
	}

	/*
	 * This tests to see if match() will work with this.
	 */
	@Test
	public void matchTest() throws IOException {

		boolean me = Parser.match("programEnd");

		assertEquals("These should be equal.", true, me);
	}

	/*
	 * This tests to see if match() will work with this.
	 */
	@Test
	public void matchTest2() throws IOException {

		boolean me = Parser.match("herbert");

		assertEquals("These should be equal.", false, me);
	}

	/*
	 * This tests to see if match() will work with this.
	 */
	@Test
	public void matchTest3() throws IOException {

		boolean me = Parser.match("jajajajaja");

		assertEquals("These should be equal.", false, me);
	}

	/*
	 * This tests to see if an correct grammar will pass It should. This is
	 * grammar #1.
	 */
	@Test
	public void correctTest() throws IOException {

		String fileName = "test.txt";

		String stringInput = Parser.readFile(fileName);
		Parser.turtle = new DrawableTurtle();
		Parser.scanner = new Scanner(stringInput);
		Parser myCalc = new Parser();
		myCalc.program();

		boolean me = Parser.passed;

		assertEquals("These should be equal.", true, me);
	}

	/*
	 * This tests to see if an correct grammar will pass It should. This is
	 * grammar #2.
	 */
	@Test
	public void correctTest2() throws IOException {

		String fileName = "test2.txt";

		String stringInput = Parser.readFile(fileName);
		Parser.turtle = new DrawableTurtle();
		Parser.scanner = new Scanner(stringInput);
		Parser myCalc = new Parser();
		myCalc.program();

		boolean me = Parser.passed;

		assertEquals("These should be equal.", true, me);
	}
}

