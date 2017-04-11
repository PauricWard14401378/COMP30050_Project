package poker;

import java.util.Random;

public class AutomatedPokerPlayer extends PokerPlayer{
	
	public AutomatedPokerPlayer(DeckOfCards deck) {
		super(deck);
	}
	public void discard(){
		Random rand=new Random();
		int rand1, cardsDiscarded=0;
		//Cycles through the hand and decides whether to discard the card or not
		for(int x=0;x<HandOfCards.HANDSIZE;x++){
			//The max number of cards that can be returned is 3
			if(cardsDiscarded==3){
				continue;
			}
			
			rand1=rand.nextInt(100);
			if(Hand.getDiscardProbability(x)==0){
				continue;
			}
			//If the discard probability is greater than a random number between 0-100 then discard the card
			else if(Hand.getDiscardProbability(x)>rand1){
				Deck.returnCard(Hand.removeCard(x));
				if(cardsDiscarded<3){
					cardsDiscarded++;
				}
				//Replace the cards that were discarded
				Hand.replaceDiscarded();
			}
			
		}
		//Resort the Hand
		Hand.sort();	
	}
	public String call(){
		return "";
	}

}
