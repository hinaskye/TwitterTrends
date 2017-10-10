import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter Rest API, uses search with user auth
 * Query Saves all query results to a file "SearchTweets.txt"
 * current API limit of search is 180 queries
 * with each query returning 100 tweets
 * 
 * application only auth code currently does not work
 * limit reached is still 180 depsite debug saying limit available of 450 (stops at 271 left)
 */
public class SearchTweets {

	private static final String CONSUMER_KEY = "UQkwQaR8e6adURg4MxaNyMVhv";
	private static final String CONSUMER_SECRET = "EZwba3MAq4gYgSxKmhGz5IdqOabTyqyqBEG1F9AtDYvuZuflDO";
	private static final String ACCESS_TOKEN = "300561939-KCtzUP3hfQcHc7z8fXRZ5lifZp4hlySP6GMl1HUf";
	private static final String ACCESS_SECRET = "XSAYw7zS06KyKXcdz3uj5v4LSOiVIwHvl7y4TNn9GzuVw";
	
	public static void main(String[] args) {
		// will need to make it so user can specify a file to get their interest from
		// removed twice and blackpink to get faster data
		String hashtags = "#iu OR #ailee OR #snsd OR #ioi OR #aoa OR #gfriend OR "
				+ "#redvelvet OR #exid OR #apink OR #wjsn OR #ohmygirl";
		
		try {
			// Config user auth
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
				.setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(ACCESS_SECRET);
			
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			
			// Config application only auth
			// Current error: despite debug saying limit is 450, once reach 271 error reached limit (180)
			/*
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setDebugEnabled(true);
			builder.setApplicationOnlyAuthEnabled(true);

			builder.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET);
			OAuth2Token token = new TwitterFactory(builder.build()).getInstance().getOAuth2Token();

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setApplicationOnlyAuthEnabled(true);

			Twitter twitter = new TwitterFactory(cb.build()).getInstance();

			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			twitter.setOAuth2Token(token);*/
			
			// Query details
			Query query = new Query(hashtags);
			query.count(100);
			query.setResultType(Query.RECENT);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date yesterday = calendar.getTime();
			//query.setSince(new SimpleDateFormat("yyyy-MM-dd").format(yesterday));
			//System.err.println("yesterday: " + new SimpleDateFormat("yyyy-MM-dd").format(yesterday));
			int sleepCounter = 10;
			
			QueryResult result;
			do
			{
				// Start querying for results
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				// For each tweet
				for(Status tweet : tweets)
				{
					// Print to console
					System.out.println(tweet.getCreatedAt() + "\n" + "@" + tweet.getUser().getScreenName()
							+ " - " + tweet.getText() + "\r\n");
					
					// Append to text file
					BufferedWriter bw;
					bw = new BufferedWriter(new FileWriter("SearchTweets.txt", true));
					bw.write(tweet.getCreatedAt() + "\n" + "@" + tweet.getUser().getScreenName()
							+ " - " + tweet.getText() + "\r\n");
					bw.close();
				}
				
				// API search limit
				Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");;
				RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
				
				// Get next page of queries
				query = result.nextQuery();
				
				// debug
				System.err.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
						  searchTweetsRateLimit.getRemaining(),
						  searchTweetsRateLimit.getLimit(),
						  searchTweetsRateLimit.getSecondsUntilReset());
				
				// sleep until limit is reset
				if(searchTweetsRateLimit.getRemaining() < 1)
				{
					System.err.println("Reached limit of API call!");
					System.err.println("This program will sleep until your API limit has reset");
					TimeUnit.SECONDS.sleep(searchTweetsRateLimit.getSecondsUntilReset()+sleepCounter);
					//sleepCounter+=10;
				}
				// while loop fix: https://stackoverflow.com/questions/28048130/twitter4j-result-nextquery-is-giving-results-from-the-first-page-again
			} while(result == null || result.hasNext());//while ((query = result.nextQuery())!= null);
			System.out.println("Exited");
			System.exit(0);
		}
		catch (TwitterException te)
		{
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
}
