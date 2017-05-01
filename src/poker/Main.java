package poker;

import java.util.HashMap;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;

public class Main {
	public static final boolean console = false;
	//This hash map will store all of the threads that are running at the same time
	static HashMap<String, GameOfPoker> Games= new HashMap<String, GameOfPoker>();
	//In the main we can either run the program through the console or on Twitter.
	//To run the main through the console: Set "option" to 0, Set "console" to true
	//To run the program through Twitter: Set "option" to 1, set "console" to false
	public static void main(String[] args) throws TwitterException{
		int option=1;
		IO IO;
		IO = new IO(option);
		DeckOfCards Deck=new DeckOfCards();
		//Run program through console
		if(option==0){
			GameOfPoker game=new GameOfPoker(5,Deck,IO);
			game.run();
		}
		//Run through Twitter with updates from console
		else{
			StatusListener hashTagListener=new StatusListener(){
		        public void onStatus(Status status){
		        	if(status.getText().startsWith("#DealMeInBurnNTurn")){
		        		IO.setUser(status.getUser().getScreenName());
	        			String InputMessage=status.getText();
	        			int NoBots=Integer.parseInt(InputMessage.substring(19));
	        			GameOfPoker newGame= new GameOfPoker(NoBots, Deck, IO);
	        			Thread t=new Thread (newGame);
	        			t.start();
	        			Games.put(IO.getUser(),newGame);
		        	}
		        	if(status.getText().startsWith("#DealMeOutBurnNTurn")){
		        		Games.get(status.getUser().getScreenName()).interrupt();
			        	Games.remove(IO.getUser());
		        	}
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

			IO.addListener(hashTagListener);
			IO.startStatusListener();
		}
	}
}