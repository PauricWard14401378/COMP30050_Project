package poker;

import java.io.IOException;
import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI{
	public TwitterFactory tf;
	public Twitter twitter;
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
	public void updateStatus(String string) throws TwitterException{
		twitter.updateStatus(string);
	}
	public void searchForPlayers(){
		Query query = new Query("#DealMeInBurnNTurn");
        int numberOfTweets = 1;
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();
        while (tweets.size () < numberOfTweets) {
          if (numberOfTweets - tweets.size() > 100)
            query.setCount(100);
          else 
            query.setCount(numberOfTweets - tweets.size());
          try {
            QueryResult result = twitter.search(query);
            tweets.addAll(result.getTweets());
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

            String user = t.getUser().getScreenName();
            String msg = t.getText();
            //String time = "";
            //if (loc!=null) {
              //Double lat = t.getGeoLocation().getLatitude();
              //Double lon = t.getGeoLocation().getLongitude();*/
             System.out. println(i + " USER: " + user + " wrote: " + msg + "\n");
            } 
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

