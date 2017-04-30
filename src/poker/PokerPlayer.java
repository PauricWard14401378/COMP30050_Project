package poker;


public abstract class PokerPlayer {

	protected DeckOfCards Deck;
	public HandOfCards Hand;
	protected Bank Bank;
	//private boolean Bankrupt=false;
	public boolean CanOpen;
	public boolean opened=false;
	public boolean called=false;
	public String Name;
	public int stake=0; 
	public boolean AllIn=false;
	
	//The PokerPlayer constructor takes as input a deck of cards and deals a hand to the poker player
	public PokerPlayer(DeckOfCards deck, Bank bank){
		Deck=deck;
		Bank=bank;
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
			CanOpen=false;
			return false;
		}
	}
	
	public void discard(){
		
	}
	
	public boolean isBankrupt(){
		if(Bank.getPlayerStack(Name)==0){
			return true;
		}
		else{
			return false;
		}
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
	public boolean call(int amount){
		return true;
		
	}
	public boolean raise(){
		return true;
		
	}

}
