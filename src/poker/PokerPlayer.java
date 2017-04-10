package poker;

import java.util.Random;

public class PokerPlayer {

	private static final int HANDSIZE = 5;
	private DeckOfCards Deck;
	public HandOfCards Hand;
	
	//The PokerPlayer constructor takes as input a deck of cards and deals a hand to the poker player
	public PokerPlayer(DeckOfCards deck){
		Deck=deck;
		Hand=new HandOfCards(Deck);
	}
	
	//This method Discards cards back to the deck and receives card from the deck to replace them
	public int discard(){
		Random rand=new Random();
		int rand1, cardsDiscarded=0;
		//Cycles through the hand and decides whether to discard the card or not
		for(int x=0;x<HANDSIZE;x++){
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
		return cardsDiscarded;	
		}
	
	//The main makes a new instance of PokerPlayer and prints a hand from a DeckOfCards. The main
	//Displays the discard probability of the hand and then discards cards. The new hand and its 
	//corresponding game value is then displayed
	public static void main(String[] args) {
		DeckOfCards deck=new DeckOfCards();
		PokerPlayer player=new PokerPlayer(deck);
		System.out.println("Hand before discarding:\n"+player.Hand.toString());
		System.out.println("Discard Probability:\n"+player.Hand.getDiscardProbability(0)+" "+player.Hand.getDiscardProbability(1)+" "+player.Hand.getDiscardProbability(2)+" "+player.Hand.getDiscardProbability(3)+" "+player.Hand.getDiscardProbability(4));
		System.out.println("Game Value of hand: "+player.Hand.getGamevalue());
		System.out.println("\nNumber of cards discarded: "+player.discard());
		System.out.println("\nHand after Discarding:\n"+player.Hand.toString());
		System.out.println("Game Value of hand: "+player.Hand.getGamevalue());
		
	}

}
