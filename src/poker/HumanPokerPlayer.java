package poker;

public class HumanPokerPlayer extends PokerPlayer{
	
	public String Name;
	
	public HumanPokerPlayer(DeckOfCards deck, String name) {
		super(deck);
		Name=name;
	}
	public void discard(int[] cards){
		if(cards.length>3){
			throw new ArithmeticException("Unable to discard more than 3 cards");
		}
		for(int x =0;x<cards.length-1;x++){
			Deck.returnCard(Hand.removeCard(cards[x]));
		}
		//Replace the cards that were discarded
		Hand.replaceDiscarded();
		//Resort the Hand
		Hand.sort();	
	}

	public int bet(int bet){
		if(bet==0){
			return 0;
		}
		else{
			removeFromBank(bet);
		}
		return bet;
	}

}
