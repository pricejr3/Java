/*
 Jarred Price

 */

package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class ExtractTest {

	private static Instant d1;
	private static Instant d2;
	private static Instant d3;
	private static Instant d4;
	private static Instant d5;
	private static Instant d6;
	private static Instant d7;

	private static Tweet tweet1;
	private static Tweet tweet2;
	private static Tweet tweet3;
	private static Tweet tweet4;
	private static Tweet tweet5;
	private static Tweet tweet6;
	private static Tweet tweet7;
	private static Tweet tweet99;
	private static Tweet tweet100;

	@BeforeClass
	public static void setUpBeforeClass() {

		// D1 and D2 are the base times.
		d1 = Instant.parse("2014-09-14T10:00:00Z");
		d2 = Instant.parse("2014-09-14T11:00:00Z");

		d3 = Instant.parse("2015-10-12T10:00:00Z");
		d4 = Instant.parse("2015-10-13T11:00:00Z");
		d5 = Instant.parse("2015-05-10T10:00:00Z");
		d6 = Instant.parse("2015-05-10T10:00:00Z");
		d7 = Instant.parse("2015-05-10T10:00:00Z");

		// tweet1 and tweet2 are the base tweets.
		tweet1 = new Tweet(0, "alyssa",
				"is it reasonable to talk about rivest so much?", d1);
		tweet2 = new Tweet(1, "bbitdiddle", "rivest talk in 30 minutes #hype",
				d2);

		tweet3 = new Tweet(2, "pizza", "@pizza, I love your Tacos", d3);
		tweet4 = new Tweet(3, "HERBERT101", "I eat @pizza Pizza", d4);
		tweet5 = new Tweet(4, "BillGatez44",
				"@Billgates I like when Apples proFits @pizza", d5);
		tweet6 = new Tweet(5, "heyY", "I", d6);
		tweet7 = new Tweet(6, "pizza", "hi", d7);
		tweet99 = new Tweet(99, "@`~~``````", "hi", d7);
		tweet100 = new Tweet(100, "@@", "hevvv", d7);
	}

	@Test
	public void testGetTimespanTwoTweets() {

		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

		assertEquals(d1, timespan.getStart());
		assertEquals(d2, timespan.getEnd());
	}

	@Test
	public void testGetMentionedUsersNoMention() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays
				.asList(tweet1));

		assertTrue(mentionedUsers.isEmpty());
	}

	// // My TESTS below///
	/**
	 * Test to see if the timespan works on one tweet.
	 */
	@Test
	public void testGetTimespanOneTweet() {

		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4));
		assertEquals(d4, timespan.getStart());
		assertEquals(d4, timespan.getEnd());
	}

	/**
	 * Test to see if the timespan works on one tweet again, for good measure.
	 */
	@Test
	public void testGetTimespanOneTweetAgain() {

		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3));
		assertEquals(d3, timespan.getStart());
		assertEquals(d3, timespan.getEnd());
	}

	/**
	 * Test to see if the timespan works on two tweets, with the same times.
	 * Time of tweet5 is the start, time of tweet5 is the end.
	 */
	@Test
	public void multipleSameTimeSpan() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet5, tweet6));
		assertEquals(d5, timespan.getStart());
		assertEquals(d5, timespan.getEnd());
	}

	/**
	 * Same idea as last test, but with 3 tweets.
	 */
	@Test
	public void multipleSameTimeSpanThree() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet5, tweet6,
				tweet7));
		assertEquals(d5, timespan.getStart());
		assertEquals(d5, timespan.getEnd());
	}

	/**
	 * Tests timespan to see if it works when tweets are in order sorted by
	 * oldest to newest.
	 */
	@Test
	public void testProperOrder() {

		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2,
				tweet3));

		assertEquals(d1, timespan.getStart());
		assertEquals(d3, timespan.getEnd());
	}

	/**
	 * Tests timespan to see if it works when tweets are in order sorted by
	 * oldest to newest. Same as before, but with 4 tweets.
	 */
	@Test
	public void testProperOrderFour() {

		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2,
				tweet3, tweet4));

		assertEquals(d1, timespan.getStart());
		assertEquals(d4, timespan.getEnd());
	}

	/**
	 * Tests timespan to see if it works when tweets are in order sorted in
	 * reverse order.
	 */
	public void improperOrderTour() {

		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4, tweet3,
				tweet2, tweet1));

		assertEquals(d1, timespan.getStart());
		assertEquals(d4, timespan.getEnd());
	}

	/**
	 * Tests again to see if a tweet doesnt mention somebody.
	 */
	@Test
	public void testGetMentionedUsersNoMentionAgain() {
		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays
				.asList(tweet6));

		assertTrue(mentionedUsers.isEmpty());
	}

	/**
	 * Checks to see when an "@" mention is at the front, if it works.
	 */
	@Test
	public void mentionAtFront() {
		Set<String> front = Extract.getMentionedUsers(Arrays.asList(tweet3));
		Set<String> stuff = new HashSet<String>();
		 stuff.add("pizza");

		assertEquals(front, stuff);
	}
	
	 /**
     * See if case of the usernames matters.
     */
    @Test
    public void isUsercaseInsensitive(){
    	Set<String> insensitive = Extract.getMentionedUsers(Arrays.asList(tweet6));
    	Set<String> stuff = new HashSet<String>();
		assertEquals(insensitive, stuff);
    	
    }

	/**
	 * Checks "@" with invalid stuff. Should return true.
	 */
	@Test
	public void testInvalidCharacters() {
		Set<String> invalid = Extract.getMentionedUsers(Arrays.asList(tweet99));
		Set<String> stuff = new HashSet<String>();
		assertEquals(invalid, stuff);
	}

	/**
	 * Checks "@" with more invalid stuff. Should return true.
	 */
	@Test
	public void testInvalidCharacters2() {
		Set<String> invalid = Extract
				.getMentionedUsers(Arrays.asList(tweet100));
		Set<String> stuff = new HashSet<String>();
		assertEquals(invalid, stuff);
	}
	
	

	/**
	 * Tests to see if the validTweetCharacter works
	 */
	@Test
	public void testValidTweetCharacter() {
		boolean value = Extract.validTweetCharacter('e');
		assertEquals(value, true);
	}
	
	/**
	 * Tests to see if the validTweetCharacter works 2
	 */
	@Test
	public void testValidTweetCharacter2() {
		boolean value = Extract.validTweetCharacter(' ');
		assertEquals(value, false);
	}


}
