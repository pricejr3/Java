/*
 Jarred Price

 */

package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;


public class FilterTest {

	private static Instant d1;
	private static Instant d2;
	private static Instant d3;
	private static Instant d4;
	private static Instant d5;
	private static Instant d6;
	private static Instant d7;

	private static Instant trolltimeEarly;
	private static Instant trolltimeLater;

	private static Tweet tweet1;
	private static Tweet tweet2;
	private static Tweet tweet3;
	private static Tweet tweet4;
	private static Tweet tweet5;
	private static Tweet tweet6;
	private static Tweet tweet7;

	@BeforeClass
	public static void setUpBeforeClass() {

		// D1 and D2 are the base times.
		d1 = Instant.parse("2014-09-14T10:00:00Z");
		d2 = Instant.parse("2014-09-14T11:00:00Z");

		d3 = Instant.parse("2015-10-12T10:00:00Z");
		d4 = Instant.parse("2015-10-13T11:00:00Z");
		d5 = Instant.parse("2015-05-10T10:00:00Z");
		d6 = Instant.parse("2013-05-10T10:00:00Z");
		d7 = Instant.parse("2013-03-10T10:00:00Z");

		trolltimeEarly = Instant.parse("9999-03-10T10:00:00Z");
		trolltimeLater = Instant.parse("9999-09-10T10:00:00Z");

		// tweet1 and tweet2 are the base tweets.
		tweet1 = new Tweet(0, "alyssa",
				"is it reasonable to talk about rivest so much?", d1);
		tweet2 = new Tweet(1, "bbitdiddle", "rivest talk in 30 minutes #hype",
				d2);

		tweet3 = new Tweet(2, "CRAZYTWEETER",
				"@herbert, I love your sandwich Tacos", d3);
		tweet4 = new Tweet(3, "HERBERT101", "I eat Pizza @WOLFFFYY", d4);
		tweet5 = new Tweet(4, "BillGatez44",
				"@Billgates I like when Apples proFits FaLtER", d5);
		tweet6 = new Tweet(5, "heyY", "I", d6);
		tweet7 = new Tweet(6, "Heyy", "hi", d7);

	}

	@Test
	public void testWrittenByMultipleTweetsSingleResult() {
		List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2),
				"alyssa");

		assertFalse(writtenBy.isEmpty());
		assertEquals(1, writtenBy.size());
		assertTrue(writtenBy.contains(tweet1));
	}

	@Test
	public void testInTimespanMultipleTweetsMultipleResults() {
		Instant testDateStart = Instant.parse("2014-09-14T09:00:00Z");
		Instant testDateEnd = Instant.parse("2014-09-14T12:00:00Z");

		List<Tweet> inTimespan = Filter.inTimespan(Arrays
				.asList(tweet1, tweet2), new Timespan(testDateStart,
				testDateEnd));

		assertFalse(inTimespan.isEmpty());
		assertTrue(inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
	}

	// // START OF MY TESTS BELOW ///
	/**
	 * Test to see if one of two tweets contains the word talk.
	 */
	@Test
	public void testContaining() {
		List<Tweet> containing = Filter.containing(
				Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

		assertFalse(containing.isEmpty());
		assertTrue(containing.containsAll(Arrays.asList(tweet1, tweet2)));
	}

	

	/**
	 * Test to see if two a user, with different formatting of their name heyY
	 * and Heyy are the same.
	 */
	@Test
	public void testWrittenByDifferentPunctuation() {
		List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet6, tweet7),
				"heyy");

		assertFalse(writtenBy.isEmpty());

		// They wrote 2 tweets
		assertEquals(2, writtenBy.size());

		// Should contain tweet6 and tweet7
		assertTrue(writtenBy.contains(tweet6));
		assertTrue(writtenBy.contains(tweet7));
	}

	/**
	 * Test to see if it contains the specified word
	 */
	@Test
	public void testContainingOneWord() {
		List<Tweet> has = Filter.containing(Arrays.asList(tweet3, tweet4),
				Arrays.asList("Tacos"));
		List<Tweet> value = new ArrayList<Tweet>();
		value.add(tweet3);

		assertEquals(has, value);
	}

	/**
	 * Look for blank spaces.
	 */
	@Test
	public void testContainingEmptyWordFilter() {
		List<Tweet> containing = Filter.containing(
				Arrays.asList(tweet4, tweet2), Arrays.asList(""));
		List<Tweet> test = new ArrayList<Tweet>();

		assertEquals(containing, test);
	}

	/**
	 * Search to see if any of these tweets contain any of these words. They
	 * should NOT and the test should pass.
	 */
	@Test
	public void testContainingNoWord() {
		List<Tweet> containing = Filter.containing(Arrays.asList(tweet1,
				tweet2, tweet3), Arrays.asList("charmander", "kleenex",
				"bojangles", "redhawk", "Green Biscuit Day"));
		List<Tweet> test = new ArrayList<Tweet>();

		assertEquals(containing, test);
	}

	/**
	 * Look for blank spaces.
	 */
	@Test
	public void testContainingEmptyWordFilter2() {
		List<Tweet> containing = Filter.containing(
				Arrays.asList(tweet4, tweet2), Arrays.asList(" "));
		List<Tweet> test = new ArrayList<Tweet>();

		assertEquals(containing, test);
	}

	/**
	 * Test to see if it does not contain the specified word
	 */
	@Test
	public void testNotContainingOneWord() {
		List<Tweet> has = Filter.containing(Arrays.asList(tweet3, tweet4),
				Arrays.asList("Cars"));
		List<Tweet> value = new ArrayList<Tweet>();
		value.add(tweet3);

		assertNotEquals(has, value);
	}

	/**
	 * Test timespan on the tweets.
	 */
	@Test
	public void testTimespanOne() {

		List<Tweet> timeSpan = Filter.inTimespan(Arrays.asList(tweet1, tweet2),
				new Timespan(d1, d2));
		List<Tweet> value = new ArrayList<Tweet>();
		value.add(tweet1);
		value.add(tweet2);

		assertEquals(timeSpan, value);
	}

	/**
	 * Test timespan on the tweets again. This time, with a later tweet first
	 * and earlier tweet listed second.
	 */
	@Test
	public void testInTimespanTwo() {

		List<Tweet> timeSpan = Filter.inTimespan(Arrays.asList(tweet7, tweet4),
				new Timespan(d7, d4));
		List<Tweet> value = new ArrayList<Tweet>();
		value.add(tweet7);
		value.add(tweet4);

		assertEquals(timeSpan, value);
	}

	/**
	 * Test timespan on the tweets again. This time, pass the test when none of
	 * the tweets are in the time span. Timespan here is in the 9999's, hence
	 */
	@Test
	public void noneInTimeSpan() {

		List<Tweet> timeSpan = Filter.inTimespan(
				Arrays.asList(tweet1, tweet2, tweet3, tweet5, tweet7, tweet4),
				new Timespan(trolltimeEarly, trolltimeLater));
		List<Tweet> value = new ArrayList<Tweet>();

		assertEquals(timeSpan, value);
	}

}
