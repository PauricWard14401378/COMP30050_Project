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
	
	//The main makes a new instance of PokerPlayer and prints a hand from a DeckOfCards. The main
	//Displays the discard probability of the hand and then discards cards. The new hand and its 
	//corresponding game value is then displayed
	/*
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
	*/

}
