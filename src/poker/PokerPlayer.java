package poker;


public abstract class PokerPlayer {
	private static final int DEFAULTCHIPCOUNT=5000;
	protected DeckOfCards Deck;
	public HandOfCards Hand;
	private int Bank=DEFAULTCHIPCOUNT;
	
	//The PokerPlayer constructor takes as input a deck of cards and deals a hand to the poker player
	public PokerPlayer(DeckOfCards deck){
		Deck=deck;
		Hand=new HandOfCards(Deck);
	}
	
	//This method Discards cards back to the deck and receives card from the deck to replace them
	public boolean canOpen(){
		if(Hand.getGamevalue()>=HandOfCards.ONEPAIR){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void discard(){
	}
	
	public String call(){
		return null;
	}
	public int getChipCount(){
		return Bank;
	}
	public int removeFromBank(int amount){
		if(amount>Bank){
			throw new ArithmeticException("Unable to remove that amount from your bank.");
		}
		Bank-=amount;
		return amount;
	}
	public void addToBank(int amount){
		Bank+=amount;
	}

}
