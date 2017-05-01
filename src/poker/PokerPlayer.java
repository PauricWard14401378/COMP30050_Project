package poker;


public abstract class PokerPlayer {

	//Declaration of variables that are accessed by other classes or child classes 
	protected DeckOfCards Deck;
	protected HandOfCards Hand;
	protected Bank Bank;
	public boolean CarryOn = false;
	public boolean CanOpen;
	public boolean opened=false;
	public boolean called=false;
	public String Name;
	public int stake=0; 
	public boolean AllIn=false;
	
	//The PokerPlayer constructor takes as input a deck of cards and deals a hand to the poker player. 
	//It also takes Bank as reference to keep track of player stacks.
	public PokerPlayer(DeckOfCards deck, Bank bank){
		Deck=deck;
		Bank=bank;
	}
	
	//Deals a hand of 5 cards for the player
	public void dealHand(DeckOfCards Deck){
		Hand = new HandOfCards(Deck);
	}
	
	//This method Discards cards back to the deck and receives cards from the deck to replace them
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
	//Checks if player is out of chips
	public boolean isBankrupt(){
		if(Bank.getPlayerStack(Name)<=0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Folds the hand for the poker player
	public void fold(){
		stake=0;
		for(int x=0;x<HandOfCards.HANDSIZE;x++){
			Deck.returnCard(Hand.removeCard(0));
		}
	}
	
	//Prints out the player's hand
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
