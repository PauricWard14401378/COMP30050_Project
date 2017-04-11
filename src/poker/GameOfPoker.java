package poker;

import java.util.ArrayList;

public class GameOfPoker {
	private DeckOfCards Deck;
	private ArrayList<AutomatedPokerPlayer> bot=new ArrayList<AutomatedPokerPlayer>();
	private HumanPokerPlayer human;
	
	GameOfPoker(int numbots, DeckOfCards deck){
		Deck=deck;
		while(numbots>0){
			bot.add(new AutomatedPokerPlayer(Deck));
			numbots--;
		}
		human=new HumanPokerPlayer(Deck);
	}
	
	public void playGame(){
		while(!human.isBankrupt()&& bot.size()!=0){
			HandOfPoker round = new HandOfPoker();
			for(int i=0;i<bot.size();i++){
				if(bot.get(i).isBankrupt()){
					bot.remove(i);
				}
			}
		}
	}
}
