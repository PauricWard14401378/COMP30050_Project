package poker;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI{
	private TwitterFactory tf;
	public Twitter twitter;
	private TwitterStream twitterStream;
	public String User;
	public boolean FoundPlayer=false;
	public int NoBots=0;
	private String InputMessage="";
	public static final int MAXCHARSIZE =140;
	private StatusListener listener = new StatusListener() {
        public void onStatus(Status status) {
            User=status.getUser().getScreenName();
            InputMessage=status.getText();
            if(NoBots==0){
            	NoBots=Integer.parseInt(InputMessage.substring(19));
            }
            FoundPlayer=true;
            notifyListener();
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        public void onScrubGeo(long userId, long upToStatusId) {
            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        public void onException(Exception ex) {
            ex.printStackTrace();
        }

		@Override
		public void onDeletionNotice(StatusDeletionNotice arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO Auto-generated method stub
			
		}
    };
	
	
	TwitterAPI(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("SAxG0CB9pUJOqUS0DRl96Y1gh")
                .setOAuthConsumerSecret("33oV5lmu2oIh4yLmQJMCQIA6Eah92qOkZetY2FgsCKfwdSVY3R")
                .setOAuthAccessToken("856603703188615168-9Ob6mcxFWSyAtTyZbigsx7iKhhDGRQX")
                .setOAuthAccessTokenSecret("nbBnbUfM7lK2Wc4Y1Cl2oDLhOelg3o1H3ASskt9w4xg6o");
        tf = new TwitterFactory(cb.build());
        ConfigurationBuilder cb2 = new ConfigurationBuilder();
        cb2.setDebugEnabled(true)
		        .setOAuthConsumerKey("SAxG0CB9pUJOqUS0DRl96Y1gh")
		        .setOAuthConsumerSecret("33oV5lmu2oIh4yLmQJMCQIA6Eah92qOkZetY2FgsCKfwdSVY3R")
		        .setOAuthAccessToken("856603703188615168-9Ob6mcxFWSyAtTyZbigsx7iKhhDGRQX")
		        .setOAuthAccessTokenSecret("nbBnbUfM7lK2Wc4Y1Cl2oDLhOelg3o1H3ASskt9w4xg6o");
        //access the twitter API using your twitter4j.properties file
        twitter = tf.getInstance();
        twitterStream= new TwitterStreamFactory(cb2.build()).getInstance();
	}
	
	public void updateStatus(String string) throws TwitterException{
		StatusUpdate statusUpdate = new StatusUpdate("@"+User+" "+string);
		twitter.updateStatus(statusUpdate);
	}
	
	private synchronized void notifyListener(){
		notifyAll();
	}
	
	public synchronized void startListener() {
		System.out.println("Starting Listener");
	    FilterQuery fq = new FilterQuery();
	    String keywords[] = {"#DealMeInBurnNTurn","@BurnAndTurn173"};
	    fq.track(keywords);
	    twitterStream.addListener(listener);
	    twitterStream.filter(fq);  
	    try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized String getinput(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return InputMessage.substring(16);
	}
}

