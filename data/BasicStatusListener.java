import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * Overrides StatusListener as on twitter4j demo code
 * https://github.com/yusuke/twitter4j/blob/master/twitter4j-examples/src/main/java/twitter4j/examples/stream/PrintFilterStream.java
 */
public class BasicStatusListener implements StatusListener {
	
	private BufferedWriter bw;
	
	@Override
    public void onStatus(Status status) {
        System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        try {
        	bw = new BufferedWriter(new FileWriter("StreamingTweets.txt", true));
			bw.write(status.getCreatedAt() + "\n" + "@" + status.getUser().getScreenName()
					+ " - " + status.getText() + "\r\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}
