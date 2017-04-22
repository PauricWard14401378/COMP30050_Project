package poker;

import java.io.IOException;
import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI{
	public TwitterFactory tf;
	public Twitter twitter;
	public String User;
	public boolean FoundPlayer=false;
	public int NoBots=0;
	TwitterAPI()throws TwitterException{
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("TZgUKWzY66XrRERknjJq9Uivf")
                .setOAuthConsumerSecret("ePbkDN4tlz9Bex1S5osV628kyxRY5AQrJoj6jhh2yVRm3grXV7")
                .setOAuthAccessToken("852154551444660224-ygDHEzVG6KutEUUo23jzvZnZaTQWnXz")
                .setOAuthAccessTokenSecret("kf3X5fu9Ms0Vwh0vP2SKZy0JfKB48P7PAKFNmII9B3RCo");
        tf = new TwitterFactory(cb.build());

        //access the twitter API using your twitter4j.properties file
        twitter = tf.getInstance();
        
        //send a tweet
	}
	public void updateStatus(String string, long MessageID) throws TwitterException{
		StatusUpdate statusUpdate = new StatusUpdate("@"+User+" "+string);
		statusUpdate.setInReplyToStatusId(MessageID);
		Status status = twitter.updateStatus(statusUpdate);
		//twitter.updateStatus("@"+User+" "+string);
	}
	public long searchForTweets(String keyword){
		Query query = new Query(keyword);
		long MessageID = 0;
        int numberOfTweets = 1;
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();
        while (tweets.size() < numberOfTweets) {
          if (numberOfTweets - tweets.size() > 100)
            query.setCount(100);
          else 
            query.setCount(numberOfTweets - tweets.size());
          try {
            QueryResult result = twitter.search(query);
            tweets.addAll(result.getTweets());
            if(tweets.size()>0){FoundPlayer=true;}
            System.out.println("Gathered " + tweets.size() + " tweets"+"\n");
            for (Status t: tweets) 
              if(t.getId() < lastID) 
                  lastID = t.getId();

          }

          catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
          }; 
          query.setMaxId(lastID-1);
        }
        for (int i = 0; i < tweets.size(); i++) {
            Status t = (Status) tweets.get(i);

           // GeoLocation loc = t.getGeoLocation();

            User = t.getUser().getScreenName();
            String msg = t.getText();
            if(msg.contains("1")){
            	NoBots=1;
            }
            else if(msg.contains("2")){
            	NoBots=2;
            }
            else if(msg.contains("3")){
            	NoBots=3;
            }
            else if(msg.contains("4")){
            	NoBots=4;
            }
            MessageID = t.getId();
            //String time = "";
            //if (loc!=null) {
              //Double lat = t.getGeoLocation().getLatitude();
              //Double lon = t.getGeoLocation().getLongitude();*/
             System.out. println(i + " USER: " + User + " wrote: " + msg + "\n");
            }
        return MessageID;
	}
	/*
	//if something goes wrong, we might see a TwitterException
    public static void main(String... args) throws TwitterException{

        System.out.println("Starting");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("TZgUKWzY66XrRERknjJq9Uivf")
                .setOAuthConsumerSecret("ePbkDN4tlz9Bex1S5osV628kyxRY5AQrJoj6jhh2yVRm3grXV7")
                .setOAuthAccessToken("852154551444660224-ygDHEzVG6KutEUUo23jzvZnZaTQWnXz")
                .setOAuthAccessTokenSecret("kf3X5fu9Ms0Vwh0vP2SKZy0JfKB48P7PAKFNmII9B3RCo");
        TwitterFactory tf = new TwitterFactory(cb.build());

        //access the twitter API using your twitter4j.properties file
        Twitter twitter = tf.getInstance();

        //send a tweet
        Status status = twitter.updateStatus("hup Glen");

        //print a message so we know when it finishes
        System.out.println("Done.");
}*/
}

