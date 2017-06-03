/*
 Jarred Price

*/

package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

	/**
	 * Find tweets written by a particular user.
	 * 
	 * @param tweets
	 *            a list of tweets, not modified by this method.
	 * @param username
	 *            Twitter username
	 * @return all tweets in the list whose author is username. Twitter
	 *         usernames are case-insensitive, so "rbmllr" and "RbMllr" are
	 *         equivalent.
	 */
	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {

		// The tweet to iterate with.
		Tweet iteratorTweet;

		// The name of the author
		String name = "";

		// The list of Tweets to add to.
		List<Tweet> tweetList = new ArrayList<Tweet>();

		// Iterate through the Tweets.
		for (int i = 0; i < tweets.size(); i++) {

			// Acquire name of the author at the ith tweet.
			name = tweets.get(i).getAuthor();

			// Convert both to uppercase for
			// boolean logic below.
			name = name.toLowerCase();
			username = username.toLowerCase();

			// Get the actual tweet at the ith tweet.
			iteratorTweet = tweets.get(i);

			// If the username is the same as name
			// then add the tweet to the authorlist.
			if (username.equals(name)) {
				tweetList.add(iteratorTweet);
			}
		}

		// Return the list of Tweets.
		return tweetList;
	}

	/**
	 * Find tweets that were sent during a particular timespan.
	 * 
	 * @param tweets
	 *            a list of tweets, not modified by this method.
	 * @param timespan
	 *            timespan
	 * @return all tweets in the list that were sent during the timespan.
	 */
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {

		// The tweet to iterate with.
		Tweet iteratorTweet;

		// The start of the timespan is at the start of the timespan.
		Instant earliestTime = timespan.getStart();

		// The end of the timespan is at the end of the timespan.
		Instant latestTime = timespan.getEnd();

		// Instant variable to hold the presentTime.
		Instant presentTime;

		// List of dates
		List<Tweet> times = new ArrayList<Tweet>();

		// Iterate through the tweets.
		for (int i = 0; i < tweets.size(); i++) {

			// Get the present time at tweet i.
			presentTime = tweets.get(i).getTimestamp();

			// Get the tweet at time i.
			iteratorTweet = tweets.get(i);

			// Check to see if the presentTime is before or after.
			if ((presentTime.isBefore(latestTime) || presentTime
					.equals(latestTime))
					&& (presentTime.equals(earliestTime) || presentTime
							.isAfter(earliestTime))) {
				
				// If true, add it in.
				times.add(iteratorTweet);
			}
		}

		// Return the times (dates).
		return times;
	}

	/**
	 * Search for tweets that contain certain words.
	 * 
	 * @param tweets
	 *            a list of tweets, not modified by this method.
	 * @param words
	 *            a list of words to search for in the tweets. Words must not
	 *            contain spaces.
	 * @return all tweets in the list such that the tweet text (when represented
	 *         as a sequence of words bounded by space characters and the ends
	 *         of the string) includes *all* the words found in the words list,
	 *         in any order. Word comparison is not case-sensitive, so "Obama"
	 *         is the same as "obama".
	 */
	public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {

		// The tweet to iterate with.
		Tweet iteratorTweet;

		// List of the words.
		List<Tweet> wordList = new ArrayList<Tweet>();

		// Acquire a set
		Set<String> set = new TreeSet<String>();

		// Iterate through the words
		// and modify them to uppercase.
		for (int i = 0; i < words.size(); i++) {
			set.add(words.get(i).toLowerCase());
		}

		// Iterate through the tweets.
		for (int i = 0; i < tweets.size(); i++) {

			// The current tweet.
			iteratorTweet = tweets.get(i);

			// Have a string array to split out the whitespace.
			String tweetMessage = iteratorTweet.getText();
			String[] array = tweetMessage.split("\\s+");

			// Acquire the other set.
			Set<String> set2 = new TreeSet<String>();
			for (int k = 0; k < array.length; k++) {
				set2.add(array[k].toLowerCase());
			}

			// System.out.println(set2.toString());
			// System.out.println(set.toString());

			// if (set2.contains(set)) {
			if (set2.containsAll(set)) {
				wordList.add(iteratorTweet);
			}
		}

		// Return the word list.
		return wordList;
	}

}
