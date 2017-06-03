/*
 Jarred Price

 */

package twitter;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;


public class TweetConverter {

	public TweetConverter() {

	}

	public static List<Tweet> convertTweets(List<Status> tweets) {
		List<Tweet> newTweets = new ArrayList<>();
		for (Status tweet : tweets) {
			Tweet newTweet = new Tweet(tweet.getId(), tweet.getUser()
					.getScreenName(), tweet.getText(), tweet.getCreatedAt()
					.toInstant());
			newTweets.add(newTweet);
		}
		return newTweets;
	}
}
