package poker;

import twitter4j.TwitterException;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws TwitterException{
		System.out.println("start");
		//In order to make the program work through Twitter set i = 1
		//In order to make the program work through the console set i != 1
		int i=1;
		DeckOfCards Deck=new DeckOfCards();
		
		if(i==1){
			IO IO=new IO(1);
			IO.startStatusListener();
			System.out.println(IO.foundPlayer());
			if(IO.foundPlayer()){
				GameOfPoker game=new GameOfPoker(IO.noBots(), Deck, IO);
			}
		}
		else{
			IO IO=new IO(0);
			GameOfPoker game=new GameOfPoker(3,Deck,IO);
		}
	}
}