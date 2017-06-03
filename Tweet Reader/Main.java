/*
 Jarred Price

 */

package twitter;

import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the main program.
 * 
 * You may change this class if you wish, but you don't have to.
 */
public class Main {

	/**
	 * The query string denoted by the Twitter Search API
	 */
	private static final String QUERY_STRING = "from:MiamiUniversity";

	/**
	 * Main method of the program. Fetches a sample of tweets from a 6.005 and
	 * prints some facts about it.
	 * 
	 * @param args
	 *            command-line arguments (not used)
	 */
	public static void main(String[] args) {

		final List<Tweet> tweets;

		try {
			System.err.println("fetching tweets for query: " + QUERY_STRING);
			tweets = TweetConverter.convertTweets(TweetGenerator
					.generateTweets(QUERY_STRING));
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}

		// display some characteristics about the tweets
		System.err.println("fetched " + tweets.size() + " tweets");

		final Timespan span = Extract.getTimespan(tweets);
		System.err.println("ranging from " + span.getStart() + " to "
				+ span.getEnd());

		final Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
		System.err
				.println("covers " + mentionedUsers.size() + " Twitter users");

		// infer the follows graph
		final Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(tweets);
		System.err.println("follows graph has " + followsGraph.size()
				+ " nodes");

		// print the top-N influencers
		final int count = 10;
		final List<String> influencers = SocialNetwork
				.influencers(followsGraph);
		for (String username : influencers.subList(0,
				Math.min(count, influencers.size()))) {
			System.out.println(username);
		}

	}

}
