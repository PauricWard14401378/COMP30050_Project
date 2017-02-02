//Pauric Ward 14401378
package poker;

import java.util.LinkedList;

public class DeckOfCards {
	LinkedList<PlayingCard> Deck=new LinkedList<PlayingCard>();
	int numCards=52;
	
	public DeckOfCards(){
		reset();
	}
	
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
	
	private void shuffle(){
		PlayingCard temp;
		for(int x=0;x<numCards;x++){
			int rand1=(int)(Math.random()*numCards);
			int rand2=(int)(Math.random()*numCards);
			temp=Deck.get(rand1);
			Deck.add(rand1, Deck.get(rand2));
			Deck.add(rand2,temp);
		}
	}
	
	public PlayingCard dealNext(){
		if(numCards==0){
			return null;
		}
		numCards--;
		return Deck.removeFirst();
	}
	
	public void returnCard(PlayingCard discarded){
		Deck.addLast(discarded);
	}
	
	public static void main(String[] args){
		DeckOfCards deckOfCards=new DeckOfCards();
		System.out.println(deckOfCards);
		System.out.println(deckOfCards.dealNext());
	}
}
