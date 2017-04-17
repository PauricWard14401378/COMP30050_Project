package poker;


public abstract class PokerPlayer {
	private static final int DEFAULTCHIPCOUNT=10;
	protected DeckOfCards Deck;
	public HandOfCards Hand;
	private int Bank=DEFAULTCHIPCOUNT;
	private boolean Bankrupt=false;
	public boolean CanOpen;
	public boolean opened=false;
	public String Name;
	public int stake=0;
	
	//The PokerPlayer constructor takes as input a deck of cards and deals a hand to the poker player
	public PokerPlayer(DeckOfCards deck){
		Deck=deck;
	}
	
	public void dealHand(DeckOfCards Deck){
		Hand = new HandOfCards(Deck);
	}
	//This method Discards cards back to the deck and receives card from the deck to replace them
	public boolean canOpen(){
		if(Hand.getGamevalue()>=HandOfCards.ONEPAIR){
			CanOpen=true;
			return true;
		}
		else{
			CanOpen=true;
			return false;
		}
	}
	
	public void discard(){
	}
	
	public int getChipCount(){
		return Bank;
	}
	public int removeFromBank(int amount){
		if(amount>Bank){
			throw new ArithmeticException("Unable to remove that amount from your bank.");
		}
		Bank-=amount;
		if(Bank<=0){
			Bankrupt=true;
		}
		return amount;
	}
	public void addToBank(int amount){
		Bank+=amount;
	}
	public boolean isBankrupt(){
		return Bankrupt;
	}
	public void fold(){
		stake=0;
		for(int x=0;x<HandOfCards.HANDSIZE;x++){
			Deck.returnCard(Hand.removeCard(0));
		}
	}
	public String showHand(){
		return Hand.toString();
	}
	public int bet(int amount){
		return amount;
		
	}
	public boolean call(int amount){
		return true;
		
	}
	public boolean raise(){
		return true;
		
	}

}
