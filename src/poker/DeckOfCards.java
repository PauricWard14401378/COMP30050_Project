//Pauric Ward 14401378
//Assignment 4
package poker;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

//This class is a programming representation of a deck of playing cards.
//It contains 52 PlayingCard objects in an ArrayList data structure.
public class DeckOfCards {
	private ArrayList<PlayingCard> Deck=new ArrayList<PlayingCard>();
	static private final int DECKSIZE=52; 
	private int numCards=52;
	
	public DeckOfCards(){
		reset();
	}
	
	//If the deck is full then this method clears the deck and
	//Initialises it with 52 new cards. Finally it shuffles the deck
	//using the shuffle() method.
	public void reset(){
		if(!Deck.isEmpty()){
			Deck.clear();
		}
		char suit=0;
		for(int x=0;x<4;x++){
			switch(x){
			case 0:
				suit =PlayingCard.CLUBS;
				break;
			case 1:
				suit =PlayingCard.SPADES;
				break;
			case 2:
				suit =PlayingCard.HEARTS;
				break;
			default:
				suit =PlayingCard.DIAMONDS;
				break;
			}
			
			Deck.add(new PlayingCard("2",suit,2,2));
			Deck.add(new PlayingCard("3",suit,3,3));
			Deck.add(new PlayingCard("4",suit,4,4));
			Deck.add(new PlayingCard("5",suit,5,5));
			Deck.add(new PlayingCard("6",suit,6,6));
			Deck.add(new PlayingCard("7",suit,7,7));
			Deck.add(new PlayingCard("8",suit,8,8));
			Deck.add(new PlayingCard("9",suit,9,9));
			Deck.add(new PlayingCard("10",suit,10,10));
			Deck.add(new PlayingCard("J",suit,11,11));
			Deck.add(new PlayingCard("Q",suit,12,12));
			Deck.add(new PlayingCard("K",suit,13,13));
			Deck.add(new PlayingCard("A",suit,1,14));
		}
		numCards=52;
		shuffle();
		
	}
	
	//This method gets two random numbers between 0 and the size of the deck and swaps 
	//The elements at those indexes in the Deck using the Collections.swap method.
	public void shuffle(){
		Random rand=new Random();
		for(int x=0;x<Math.pow(DECKSIZE,2);x++){
			int rand1=rand.nextInt(DECKSIZE);
			int rand2=rand.nextInt(DECKSIZE);
			Collections.swap(Deck, rand1, rand2);
		}
	}
	
	
	//This method returns null if there are no available cards to deal. If
	//There are cards to deal then it returns the card from the top of the Deck.
	public synchronized PlayingCard dealNext(){
		if(numCards==0){
			return null;
		}
		numCards--;
		return Deck.remove(numCards);
	}
	
	//This method takes a PlayingCard and adds it to end of the Deck.
	public synchronized void returnCard(PlayingCard discarded){
		Deck.add(discarded);
	}
}
