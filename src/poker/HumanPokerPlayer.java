package poker;

import java.util.Random;

public class HumanPokerPlayer extends PokerPlayer{
	
	public HumanPokerPlayer(DeckOfCards deck) {
		super(deck);
	}
	
	public void discard(int[] cards){
		if(cards.length>=3){
			throw new ArithmeticException("Unable to discard more than 3 cards");
		}
		for(int x =0;x<cards.length;x++){
			Deck.returnCard(Hand.removeCard(cards[x]));
		}
		//Replace the cards that were discarded
		Hand.replaceDiscarded();
		//Resort the Hand
		Hand.sort();	
	}
	public String call(){
		return "";
	}

	
}
