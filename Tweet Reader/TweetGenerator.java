

package twitter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.List;


public class TweetGenerator {
	private static final int NUM_PAGES = 10;

	public TweetGenerator() {
	}

	public static List<Status> generateTweets(String queryString)
			throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("CwSUNwscmLp2Yfbby4ebuaIE0",
				"Ihqd37z0OJ8hFf5BIG3JxgwH1cThWFUBTjhc2wUoV77hkTFqea");
		twitter.setOAuthAccessToken(new AccessToken(
				"24010090-jquwh3r9vcz0RIfQfvxbMXFvR0A1bUj4XmaAXEBiG",
				"uoDg3FzDXcwfYbY5DWjv7ZKXdJxXnBDlvFd754EyyAWOj"));
		List<Status> tweets = new ArrayList<>();
		try {
			Query query = new Query(queryString);
			query.setCount(100);
			QueryResult queryResult = twitter.search(query);
			for (int i = 0; i < NUM_PAGES; i++) {
				List<Status> currTweets = queryResult.getTweets();
				tweets.addAll(currTweets);
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			throw new TwitterException("Failed to search: " + te.getMessage());
		}
		return tweets;
	}
}
