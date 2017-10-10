package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import controller.BasicStatusListener;
import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * 
 */
public class StreamTweets {

	private static final String CONSUMER_KEY = "UQkwQaR8e6adURg4MxaNyMVhv";
	private static final String CONSUMER_SECRET = "EZwba3MAq4gYgSxKmhGz5IdqOabTyqyqBEG1F9AtDYvuZuflDO";
	private static final String ACCESS_TOKEN = "300561939-KCtzUP3hfQcHc7z8fXRZ5lifZp4hlySP6GMl1HUf";
	private static final String ACCESS_SECRET = "XSAYw7zS06KyKXcdz3uj5v4LSOiVIwHvl7y4TNn9GzuVw";
	
	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(CONSUMER_KEY)
			.setOAuthConsumerSecret(CONSUMER_SECRET)
			.setOAuthAccessToken(ACCESS_TOKEN)
			.setOAuthAccessTokenSecret(ACCESS_SECRET);
		
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener basicStatusListener = new BasicStatusListener();
		
		List<String> track = new ArrayList<String>();
		//solo
		track.add("#iu");
		track.add("#ailee");
		track.add("#kimsejeong");
		//girl group
		track.add("#snsd");
		track.add("#twice");
		track.add("#ioi");
		track.add("#blackpink");
		track.add("#aoa");
		track.add("#gfriend");
		track.add("#redvelvet");
		track.add("#exid");
		track.add("#apink");
		track.add("#wjsn");
		
		String[] trackArray = track.toArray(new String[track.size()]);
		
		FilterQuery filterQuery = new FilterQuery();
		
		filterQuery.track(trackArray);
		filterQuery.language(new String[]{"en"});
		
		twitterStream.addListener(basicStatusListener);
		twitterStream.filter(filterQuery);
		
		try {
			/* May change this to an input argument */
			TimeUnit.HOURS.sleep(1); // Call this to stream one hour worth of data
			//TimeUnit.DAYS.sleep(1); // Call this to stream one day worth of data
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally
		{
			twitterStream.cleanUp();
			twitterStream.shutdown();
		}
	}
}
