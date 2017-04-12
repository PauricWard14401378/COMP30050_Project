package poker;

import java.util.ArrayList;

public class HandOfPoker {
	private HumanPokerPlayer Human;
	private ArrayList<AutomatedPokerPlayer> Bots=new ArrayList<AutomatedPokerPlayer>();
	private ArrayList<Object> Players=new ArrayList<Object>();
	private DeckOfCards Deck;
	
	HandOfPoker(HumanPokerPlayer human, ArrayList<AutomatedPokerPlayer> bots, DeckOfCards deck){
		Human=human;
		Bots=bots;
		Deck=deck;
		dealCards();
		opening();
	}
	
	
	public void dealCards() {
		Human.dealHand(Deck);
		for(int i=0; i<Bots.size() ;i++){
			Bots.get(i).dealHand(Deck);
		}
	}

	public boolean[] opening() {
		boolean[] canOpen=new boolean[Bots.size()+1];
		canOpen[0]=Human.canOpen();
		for(int i=0; i<Bots.size() ;i++){
			canOpen[i+1]=Bots.get(i).canOpen();
		}
		return canOpen;
	}
	public void discardCards(int[] numcards) {
		Human.discard(numcards);
		for(int i=0; i<Bots.size() ;i++){
			Bots.get(i).discard();
		}
	}
	public void betting(int amount){
		if(Human.canOpen()&&!Human.Folded){
			Human.bet(amount);
		}
		for(int i=0; i<Bots.size() ;i++){
			if(Bots.get(i).canOpen()&&!Bots.get(i).Folded){
				//Some code to handle the bots betting
			}
		}
	}
	public void folding(String fold) {
		if(fold=="yes"){
			Human.fold();
		}
		for(int i=0; i<Bots.size() ;i++){
			if(false/*Insert some way of deciding when bot should fold*/){
				Bots.get(i).fold();
			}
		}
	}
	public void compareHands(){
		//int winner;
		for(int i=0; i<Bots.size() ;i++){
			if(Bots.get(i).Hand.getGamevalue()>Bots.get(i+1).Hand.getGamevalue()){
				
			}
		}
	}
	
}
