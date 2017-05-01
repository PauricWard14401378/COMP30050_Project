package poker;

import java.util.Random;

public class AutomatedPokerPlayer extends PokerPlayer{
	//Declaration of variables and constants used to represent an automated poker player
	private int[] personality;
	//The base personality values where the first value is the probability of the player to call
	//And the second value is the probability of the player to raise.
	private static final int[] NERVOUS={40,30};
	private static final int[] AGGRESSIVE={60,50};
	private static final int[] BLUFFER={40,60};
	private static final int[] TAME={50,40};
	Random rand=new Random();
	
	//The constructor takes an extra input than its parent class. It also calls the decideProbability
	//Method which sets the base values for the player.
	public AutomatedPokerPlayer(DeckOfCards Deck,String name, Bank bank) {
		super(Deck, bank);
		Name=name;
		decidePersonality();
	}
	
	//Sets the base personality values for the player.
	private void decidePersonality() {
		if(Name.equals("Tom")){
			personality=NERVOUS;
		}
		else if(Name.equals("Dick")){
			personality=AGGRESSIVE;
		}
		else if(Name.equals("Harry")){
			personality=BLUFFER;
		}
		else if(Name.equals("Sally")){
			personality=TAME;
		}
	}
	
	//This method updates the probability to call and raise based on what class of hand the player has
	public void updatePercentages(){
		if(Hand.getGamevalue()>HandOfCards.ONEPAIR&&Hand.getGamevalue()<HandOfCards.TWOPAIR){
			personality[0]+=10;
			personality[1]+=10;
		}
		else if(Hand.getGamevalue()>HandOfCards.TWOPAIR&&Hand.getGamevalue()<HandOfCards.THREEOFAKIND){
			personality[0]+=20;
			personality[1]+=20;
			
		}
		else if(Hand.getGamevalue()>HandOfCards.THREEOFAKIND&&Hand.getGamevalue()<HandOfCards.STRAIGHT){
			personality[0]+=30;
			personality[1]+=30;
		}
		else if(Hand.getGamevalue()>HandOfCards.STRAIGHT&&Hand.getGamevalue()<HandOfCards.FLUSH){
			personality[0]+=40;
			personality[1]+=40;
		}
		else if(Hand.getGamevalue()>HandOfCards.FLUSH&&Hand.getGamevalue()<HandOfCards.FULLHOUSE){
			personality[0]+=50;
			personality[1]+=50;
		}
		else if(Hand.getGamevalue()>HandOfCards.FOUROFAKIND&&Hand.getGamevalue()<HandOfCards.STRAIGHTFLUSH){
			personality[0]+=60;
			personality[1]+=60;
		}
		else {
			personality[0]+=70;
			personality[1]+=70;
		}
		if(personality[0]>100){
			personality[0]=100;
		}
		if(personality[1]>100){
			personality[1]=100;
		}
	}
	
	//Handles the discarding of cards based on their discard probability
	public void discard(){
		int rand1, cardsDiscarded=0;
		//Cycles through the hand and decides whether to discard the card or not
		for(int x=HandOfCards.HANDSIZE-1;x<0;x--){
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
		updatePercentages();
	}
	
	//This method handles how the automated poker player calls bets based on their personality, class of hand and 
	//The size of the amount compared to their stack
	public boolean call(int amount){
		int amountSt = Math.abs(amount-stake);
		int rand1 = rand.nextInt(100);
		int scalePercentage = Math.abs(amount-stake)*20;
		int stackPercentage=0;
		CarryOn=false;
		//Handling division by zero in stackPercentage
		if(!(((Bank.getPlayerStack(Name)+stake)*100)==0)){
			stackPercentage=stake/(Bank.getPlayerStack(Name)+stake)*100;
		}
		//Handles the boolean which is used in raise
		if(stake==amount && amount!=0){
			called=true;
			return true;
		}
		//If the player wants to open, can open and another player has not opened
		if((personality[0]-scalePercentage-stackPercentage) >= rand1 && amount==0 && CanOpen==true){
			int openBet =Math.abs(personality[1]-rand1)/20;
			if(openBet==0){
				return false;
			}
			if(Bank.getPlayerStack(Name)-openBet <= 0){
				GameOfPoker.IO.Output(Name+" says: I open with "+ openBet+" chip(s)");
				Bank.removeFromBank(Name, Bank.getPlayerStack(Name));
				//Updates the stake that the player has in the hand
				stake+=openBet;
				opened=true;
				AllIn=true;
				return true;
			}
			else{
				GameOfPoker.IO.Output(Name+" says: I open with "+ openBet+" chip(s)");
				Bank.removeFromBank(Name, openBet);
				//Updates the stake that the player has in the hand
				stake+=openBet;
				opened=true;
				return true;
			}
		}
		//If the player wants to see the bet
		else if(personality[0]-scalePercentage-stackPercentage>=rand1 && amount!=0){
			if(Bank.getPlayerStack(Name)-amountSt <= 0){
				GameOfPoker.IO.Output(Name+" says: I see that "+Math.abs(amount-stake)+" chip(s)!");
				stake=amount;
				Bank.removeFromBank(Name, Bank.getPlayerStack(Name));
				AllIn=true;
				return true;
			}
			else{
				GameOfPoker.IO.Output(Name+" says: I see that "+Math.abs(amount-stake)+" chip(s)!");
				stake=amount;
				Bank.removeFromBank(Name, amountSt);
				return true;
			}
		}
		//If the player wants to stay in the hand but cannot open
		else if((personality[0]-scalePercentage-stackPercentage) >= rand1 && amount==0){
			CarryOn=true;
			return true;
		}
		//Otherwise the player indicates they want to fold
		else{
			return false;
		}

	}
	
	//This method handles how the automated poker player raises in the game
	public boolean raise(){
		int rand1=rand.nextInt(100);
		//If the player opened then carry on
		if(opened==true){
			opened=false;
			return false;
		}
		//If the player called then return false
		if(called==true){
			return false;
		}
		//If the player wants to raise based on their personality and their class of hand
		if(personality[1] >= rand1){
			GameOfPoker.IO.Output(Name+" says: and I raise you 1 chip(s)!");
			stake+=1;
			Bank.removeFromBank(Name,1);
			return true;
		}
		return false;
		
	}
	

}
