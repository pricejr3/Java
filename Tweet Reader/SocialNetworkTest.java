/*
 Jarred Price

 */

package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class SocialNetworkTest {

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

	@BeforeClass
	public static void setUpBeforeClass() {

		// D1 and D2 are the base times.
		d1 = Instant.parse("2014-09-14T10:00:00Z");
		d2 = Instant.parse("2014-09-14T11:00:00Z");

		d3 = Instant.parse("2015-10-12T10:00:00Z");
		d4 = Instant.parse("2015-10-13T11:00:00Z");
		d5 = Instant.parse("2015-05-10T10:02:10Z");
		d6 = Instant.parse("2013-05-10T10:55:00Z");
		d7 = Instant.parse("2013-03-10T10:00:00Z");

		// tweet1 and tweet2 are the base tweets.
		tweet1 = new Tweet(0, "alyssa", "@alyssa", d1);
		tweet2 = new Tweet(1, "bbitdiddle", "@alyssa rivest chap lad bro in 30 minutes #hype",
				d2);

		tweet3 = new Tweet(2, "CRAZYTWEETER",
				"@joe, I love your Tacos", d3);
		tweet4 = new Tweet(3, "joe", "I eat Pizza", d4);
		tweet5 = new Tweet(4, "BillGatez44",
				"@Billgates I like when Apples proFits FaLtER", d5);
		tweet6 = new Tweet(5, "heyY", "I", d6);
		tweet7 = new Tweet(6, "Heyy", "hi", d7);

	}

	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(new ArrayList<>());

		assertTrue(followsGraph.isEmpty());
	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertTrue(influencers.isEmpty());
	}
	
    /**
     * Tests to see if Alyssa only mentions herself, or whomever.
     */
    @Test
    public void mentionSelf() {
        Map<String, Set<String>> user = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));

        String me = user.toString();
        
        // The name in the box should only be alyssa, since she only mentions herself.
        String me2 = "{ALYSSA=[alyssa]}";
     
        assertEquals(me, me2);
    }
    /**
     * Tests to see if person x mentions person y.
     */
    @Test
    public void testGuessFollowsGraphOneWayMention() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3,tweet4));
        
        String me = followsGraph.toString();
        
        // Joe mentions nobody, CrazyTweeter mentions Joe
        String me2 = "{JOE=[], CRAZYTWEETER=[joe]}";
        assertEquals(me, me2);
    }
    
    /**
     * Tests to see if person mentions nobody.
     */
    @Test
    public void mentionNobody() {
        Map<String, Set<String>> user = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet7));

        String me = user.toString();
    
        // The name in the box should only be nothing.
        String me2 = "{HEYY=[]}";
     
        assertEquals(me, me2);
    }
    
    /**
     * Testing of influence.
     */
    @Test
    public void testInfluencershOneTweetUserMention() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        String me = influencers.toString();

        assertEquals("[alyssa, ALYSSA]", me);
    }
    
    

	
	   



}