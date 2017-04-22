package poker;

import twitter4j.TwitterException;

public class Main {
	public static void main(String[] args) throws TwitterException{
		DeckOfCards Deck=new DeckOfCards();
		TwitterAPI twitter=new TwitterAPI();
		long ID=twitter.searchForTweets("#DealMeInBurnNTurn");
		System.out.println(ID);
		twitter.updateStatus("Done", ID);
		if(twitter.FoundPlayer){
			GameOfPoker game=new GameOfPoker(twitter.NoBots,Deck);
		}
		
	
	}
}