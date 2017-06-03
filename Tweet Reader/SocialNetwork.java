/*
 Jarred Price

 */

package twitter;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be undefined or map[A] may be the
 * empty set; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 * 
 */
public class SocialNetwork {

	/**
	 * Determines if the character in the tweet is legit.
	 * 
	 * @param chars
	 * @return True or False depending on if it is valid.
	 */
	private static boolean validTweetCharacter(char chars) {
		return (chars == '_' || Character.isLetter(chars) || Character
				.isDigit(chars));
	}

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets
	 *            a list of tweets providing the evidence, not modified by this
	 *            method.
	 * @return a social network (as defined above) in which Ernie follows Bert
	 *         if and only if there is evidence for it in the given list of
	 *         tweets. One kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of
	 *            evidence may be used at the implementor's discretion. All the
	 *            Twitter usernames in the returned social network must be
	 *            either authors or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {

		// New Map of followers.
		Map<String, Set<String>> followers = new HashMap<String, Set<String>>();

		// The name for the Tweeter.
		String tweeter = "";

		// A set to hold the users
		Set<String> users;

		// Used in the "make smarter" part
		Set<String> smartUsers;

		// Iterate through the tweets.
		for (Tweet iterationTweet : tweets) {

			// Get the name of the author.
			tweeter = iterationTweet.getAuthor();

			// Format it to Upper case.
			tweeter = tweeter.toUpperCase();

			// Extract the mentioned users as as a list.
			users = Extract.getMentionedUsers(Arrays.asList(iterationTweet));

			if (users.contains(tweeter)) {
				users.remove(tweeter);
			}

			if (!(followers.containsKey(tweeter))) {
				followers.put(tweeter, users);
			} else {
				followers.get(tweeter).addAll(users);
			}
		}

		Map<String, Set<String>> hashTags = new HashMap<String, Set<String>>();
		for (Tweet t : tweets) {
			String currentAuthor = t.getAuthor().toUpperCase();

			// The character that is found while iterating through the Tweet.
			char iteratedCharacter;

			// The string to represent the current alias through iteration.
			String presentAlias = "";

			// The list of aliases that are used.
			List<String> aliases = new ArrayList<String>();

			// The message of the text.
			String message = t.getText();

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
						aliases.add(presentAlias.toUpperCase());
					}
					presentAlias = "";
					identifier = false;
				}

				if (i != 0) {
					if (iteratedCharacter == '#'
							&& (!(validTweetCharacter(message.charAt(i - 1))))) {
						identifier = true;
					}
				}

				if (i == 0) {
					if (iteratedCharacter == '#') {
						identifier = true;
					}
				}

			}

			// If the length of the present string is > 0, then there
			// is actually an entry.
			if (presentAlias.length() > 0) {
				aliases.add(presentAlias.toUpperCase());
			}

			// return followers;

			// BEGIN make "SMARTER":

			List<String> currentHashtags = aliases;

			for (String hashtag : currentHashtags) {
				if (hashTags.containsKey(hashtag)) {
					hashTags.get(hashtag).add(currentAuthor);
				} else {
					Set<String> singleAuthor = new HashSet<String>();
					singleAuthor.add(currentAuthor);
					hashTags.put(hashtag, singleAuthor);
				}
			}
		}

		for (String hashIteration : hashTags.keySet()) {

			smartUsers = hashTags.get(hashIteration);

			for (String tweeter2 : smartUsers) {
				if (!(followers.containsKey(tweeter2))) {
					followers.put(tweeter2, smartUsers);
				} else {
					followers.get(tweeter2).addAll(smartUsers);
				}

				followers.get(tweeter2).remove(tweeter2);
			}
		}

		// Return the followers.
		return followers;
	}

	/**
	 * Find the people in a social network who have the greatest influence, in
	 * the sense that they have the most followers.
	 * 
	 * @param followsGraph
	 *            a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 * 
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {

		// users set of the followsGraph keyset
		Set<String> users = followsGraph.keySet();

		// The list to return
		List<String> finalFollowers;

		// List of twitterUsers who can influence
		List<String> twitterUsers = new ArrayList<String>();

		// New mapping for influence.
		Map<String, Integer> influence = new HashMap<String, Integer>();

		// Iterate through all the users
		for (String tweeterIterator : users) {
			for (String isFollow : followsGraph.get(tweeterIterator)) {

				// If it contains the key, then put it in the influence.
				if (influence.containsKey(isFollow)) {
					influence.put(isFollow, influence.get(isFollow) + 1);
				} else if (!(influence.containsKey(isFollow))) {
					influence.put(isFollow, 1);
				}
			}
		}

		// Iterate through the users again.
		for (String user : influence.keySet()) {
			if (twitterUsers.isEmpty()) {
				twitterUsers.add(user);
			} else if (influence.get(user) > influence.get(twitterUsers
					.get(twitterUsers.size() - 1))) {
				for (int i = 0; i < twitterUsers.size(); i++) {
					if (influence.get(user) > influence
							.get(twitterUsers.get(i))) {
						twitterUsers.add(i, user);

					}
				}
			} else {
				twitterUsers.add(user);
			}
		}

		finalFollowers = twitterUsers;

		// Add the twitter users to the list.
		for (String currentAuthor : users) {
			if (finalFollowers.contains(currentAuthor)) {
				// DO nothing.
			}
			if (!finalFollowers.contains(currentAuthor)) {
				finalFollowers.add(currentAuthor);
			}
		}
		return finalFollowers;
	}

}