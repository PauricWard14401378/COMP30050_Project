package poker;

import java.util.HashMap;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;

public class Main {
	static int p=0;
	static HashMap<String, GameOfPoker> Games= new HashMap<String, GameOfPoker>();
	public static void main(String[] args) throws TwitterException{
		System.out.println("start");
		int option=0;
		IO IO;
		IO = new IO(0);
		DeckOfCards Deck=new DeckOfCards();
		if(option==0){
			GameOfPoker game=new GameOfPoker(1,Deck,IO);
			game.run();
		}
		else{
			StatusListener hashTagListener=new StatusListener(){
		        public void onStatus(Status status){
		        	if(status.getText().startsWith("#DealMeInBurnNTurn")){
		        		System.out.println(status.getUser().getScreenName());
		        		IO.setUser(status.getUser().getScreenName());
	        			System.out.println("Thread Number "+p);
	        			p++;
	        			String InputMessage=status.getText();
	        			int NoBots=Integer.parseInt(InputMessage.substring(19));
	        			GameOfPoker newGame= new GameOfPoker(NoBots, Deck, IO);
	        			Thread t=new Thread (newGame);
	        			t.start();
	        			Games.put(IO.getUser(),newGame);
	        			System.out.println("Made it this far");
		        	}
		        	if(status.getText().startsWith("#DealMeOutBurnNTurn")){
		        		Games.get(status.getUser().getScreenName()).interrupt();
			        	Games.remove(IO.getUser());
		        	}
		        	//Games.get(IO.getUser()).notifyGame();
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