package poker;

public class GameOfPoker {
	private DeckOfCards Deck;
	private AutomatedPokerPlayer[] bot;
	private HumanPokerPlayer human;
	GameOfPoker(int numbots, DeckOfCards deck){
		//Poker bots with hands from a deck
		bot=new AutomatedPokerPlayer[numbots];
		Deck=deck;
	}
	
	public void playGame(){
		human=new HumanPokerPlayer(Deck);
		for(int i=0;i<bot.length;i++){
			bot[i]=new AutomatedPokerPlayer(Deck);
		}
		//while()
	}
}
