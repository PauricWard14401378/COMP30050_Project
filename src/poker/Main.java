package poker;

import twitter4j.TwitterException;

public class Main {

	public static void main(String[] args) throws TwitterException{
		DeckOfCards Deck=new DeckOfCards();
		@SuppressWarnings("unused")
		GameOfPoker game=new GameOfPoker(3,Deck);
		
	}
}
