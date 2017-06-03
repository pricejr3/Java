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
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 * 
 */
public class Extract {

	/**
	 * Determines if the character in the tweet is legit.
	 * 
	 * @param chars
	 * @return True or False depending on if it is valid.
	 */
	static boolean validTweetCharacter(char chars) {
		return (chars == '_' || Character.isLetter(chars) || Character
				.isDigit(chars));
	}

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets
	 *            list of tweets, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of
	 *         every tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {

		// Create a new list to hold all the tweets.
		List<Instant> tweetTimeList = new ArrayList<Instant>();

		// Iterate through all tweets and add the times
		// of the tweets to the tweetTimeList.
		for (int i = 0; i < tweets.size(); i++) {
			tweetTimeList.add(tweets.get(i).getTimestamp());
		}

		// Iterate through the list of times of tweets
		// and the the latest time.
		Instant latestTime = tweetTimeList.get(0);
		for (Instant currentDate : tweetTimeList) {
			if (latestTime.isBefore(currentDate)) {
				latestTime = currentDate;
			}
		}

		// Iterate through the list of times of tweets
		// and the the first (earliest) time.
		Instant earlistTime = tweetTimeList.get(0);
		for (Instant currentDate : tweetTimeList) {
			if (earlistTime.isAfter(currentDate)) {
				earlistTime = currentDate;
			}
		}

		// Return the new timespan with the earliest and latest time.
		return new Timespan(earlistTime, latestTime);
	}

	/**
	 * Get aliases mentioned in a list of tweets.
	 * 
	 * @param tweets
	 *            list of tweets, not modified by this method.
	 * @return the set of aliases who are mentioned in the text of the tweets. A
	 *         username-mention is "@" followed by a username. A username
	 *         consists of letters (A-Z or a-z), digits, and underscores ("_").
	 *         Twitter aliases are case-insensitive, so "rbmllr" and "RbMllr"
	 *         are equivalent. A username may occur at most once in the returned
	 *         set. The @ cannot be immediately preceded by an alphanumeric or
	 *         underscore character (A-Z, a-z, 0-9, _). For example, an email
	 *         address like bitdiddle@mit.edu does not contain a mention of mit.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {

		// Create a set of users with a new TreeSet.
		Set<String> treeSetUsers = new TreeSet<String>();

		// List to hold the mentioned users.
		List<String> users;

		// Iterate through the messages (tweets).
		for (Tweet message : tweets) {
			users = convert(message);
			for (String people : users) {
				treeSetUsers.add(people);
			}
		}
		// Return the tree set of the users.
		return treeSetUsers;
	}

	/**
	 * Extracts a list of usernames (lowercase) from a tweet
	 * 
	 * @param tweet
	 * @return list of usernames mentioned in lowercase (may be repeated)
	 */
	private static List<String> convert(Tweet tweet) {

		// The character that is found while iterating through the Tweet.
		char iteratedCharacter;

		// The string to represent the current alias through iteration.
		String presentAlias = "";

		// The list of aliases that are used.
		List<String> aliases = new ArrayList<String>();

		// The message of the text.
		String message = tweet.getText();

		// Is the tweet a validString?
		boolean validString = false;
		// boolean validString2 = false;

		// ID boolean
		boolean identifier = false;

		// Is tweet an underscore?
		// boolean under = false;

		// Is tweet a letter?
		// boolean letter = false;

		for (int i = 0; i < message.length(); i++) {

			// Acquire the character for the iteration.
			iteratedCharacter = message.charAt(i);

			// Check to see if the string is valid
			validString = validTweetCharacter(iteratedCharacter);

			if (validString) {
				if (identifier) {
					presentAlias = presentAlias + iteratedCharacter;
				}

			}
			if (!validString) {
				if (identifier) {
				}
				if (!presentAlias.equals("")) {
					aliases.add(presentAlias.toLowerCase());
				}
				presentAlias = "";
				identifier = false;
			}

			if (i != 0) {
				if (iteratedCharacter == '@'
						&& (!(validTweetCharacter(message.charAt(i - 1))))) {
					identifier = true;
				}
			}

			if (i == 0) {
				if (iteratedCharacter == '@') {
					identifier = true;
				}
			}

		}

		// If the length of the present string is > 0, then there
		// is actually an entry.
		if (presentAlias.length() > 0) {
			aliases.add(presentAlias.toLowerCase());
		}

		// Return the list of aliases
		return aliases;
	}

}