package poker;

import java.util.Random;

public class AutomatedPokerPlayer extends PokerPlayer{
	public int[] personality;
	//Will play with type of hand, bet size depending on hand, 
	//I added 30 to each value to make them more aggressive for testing
	
	private static final int[] NERVOUS={30,40};
	private static final int[] AGGRESSIVE={50,60};
	private static final int[] BLUFFER={40,60};
	private static final int[] TAME={40,50};
	private boolean opened=false;
	Random rand=new Random();
	
	public AutomatedPokerPlayer(DeckOfCards Deck,String name) {
		super(Deck);
		Name=name;
		decidePersonality();
	}
	private void decidePersonality() {
		int rand1=rand.nextInt(4);
		switch(rand1){
		case 0: personality=NERVOUS;
				break;
		case 1: personality=AGGRESSIVE;
				break;
		case 2: personality=BLUFFER;
				break;
		case 3: personality=TAME;
				break;
		}
	}
	public void updatePercentages(){
		if(Hand.getGamevalue()>HandOfCards.ONEPAIR&&Hand.getGamevalue()<HandOfCards.TWOPAIR){
			System.out.println(personality[1]);
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
	public int bet(int amount){
		int rand1=rand.nextInt(100);
		int scalePercentage=amount*10;
		System.out.println(scalePercentage);
		if(personality[1]-scalePercentage>=rand1){
			this.removeFromBank(amount);
		}
		if(personality[0]-scalePercentage>=rand1){
			this.removeFromBank(1);
			amount+=1;
		}
		return amount;
	}
	public void discard(){
		
		int rand1, cardsDiscarded=0;
		//Cycles through the hand and decides whether to discard the card or not
		for(int x=0;x<HandOfCards.HANDSIZE;x++){
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
		int scalePercentage=amount*10;
		System.out.println(personality[1]+" "+scalePercentage+" "+rand1);
		if(personality[1]-scalePercentage>=rand1 && amount==0){
			int openBet=Math.abs(personality[1]-rand1)/20;
			System.out.println(Name+" says: I open with "+ openBet+" chip(s)");
			this.removeFromBank(openBet);
			stake+=openBet;
			opened=true;
			return true;
		}
		else if(personality[1]-scalePercentage>=rand1){
			System.out.println(Name+" says: I see that "+Math.abs(amount-stake)+" chip(s)!");
			stake+=amount;
			this.removeFromBank(amount);
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
		int rand1=rand.nextInt(100);
		System.out.println(personality[0]+" "+rand1);
		if(personality[0]>=rand1){
			System.out.println(Name+" says: and I raise you 1 chip(s)!");
			stake+=1;
			this.removeFromBank(1);
			return true;
		}
		return false;
		
	}
	

}
