package poker;

import java.util.Random;

public class AutomatedPokerPlayer extends PokerPlayer{
	public int[] personality;
	//Will play with type of hand, bet size depending on hand, 
	
	private static final int[] NERVOUS={30,40};
	private static final int[] AGGRESSIVE={50,60};
	private static final int[] BLUFFER={40,60};
	private static final int[] TAME={40,50};
	Random rand=new Random();
	
	public AutomatedPokerPlayer(DeckOfCards Deck,String name, Bank bank) {
		super(Deck, bank);
		Name=name;
		decidePersonality();
	}
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
	public boolean call(int amount){
		int rand1=rand.nextInt(100);
		int scalePercentage=Math.abs(amount-stake)*20;
		int stackPercentage=stake/(Bank.getPlayerStack(Name)+stake)*100;
		if(stake==amount && amount!=0){
			called=true;
			return true;
		}
		if(personality[1]-scalePercentage-stackPercentage>=rand1 && amount==0){
			int openBet=Math.abs(personality[1]-rand1)/20;
			if(openBet==0){
				return false;
			}
			GameOfPoker.IO.Output(Name+" says: I open with "+ openBet+" chip(s)");
			Bank.removeFromBank(Name, openBet);
			stake+=openBet;
			opened=true;
			return true;
		}
		else if(personality[1]-scalePercentage-stackPercentage>=rand1){
			GameOfPoker.IO.Output(Name+" says: I see that "+Math.abs(amount-stake)+" chip(s)!");
			stake=amount;
			Bank.removeFromBank(Name, amount);
			return true;
		}
		else{
			return false;
		}

	}
	public boolean raise(){
		if(opened==true){
			opened=false;
			return false;
		}
		if(called==true){
			return false;
		}
		int rand1=rand.nextInt(100);
		if(personality[0]>=rand1){
			GameOfPoker.IO.Output(Name+" says: and I raise you 1 chip(s)!");
			stake+=1;
			this.Bank.removeFromBank(Name,1);
			return true;
		}
		return false;
		
	}
	

}
