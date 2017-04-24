package poker;

import twitter4j.TwitterException;

public class Main {
	public static void main(String[] args) throws TwitterException{
		System.out.println("start");
		int i=1;
		DeckOfCards Deck=new DeckOfCards();
		
		if(i==1){
			IO IO=new IO(1);
			IO.Twitter.searchForTweets("#DealMeInBurnNTurn");
			if(IO.Twitter.FoundPlayer){
				GameOfPoker game=new GameOfPoker(IO.Twitter.NoBots,Deck,IO);
			}
		}
		else{
			IO IO=new IO(0);
			GameOfPoker game=new GameOfPoker(3,Deck,IO);
		}
		
	
	}
}