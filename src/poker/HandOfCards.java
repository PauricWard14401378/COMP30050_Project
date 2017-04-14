//Pauric Ward 14401378
//Assignment 4
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//This class is a programming representation of a hand of playing cards.
//Each hand has to be classified to show its worth in the game of draw poker.

public class HandOfCards {
	
	public static final int HANDSIZE=5;
	public static final int ROYALFLUSH=9000000;
	public static final int STRAIGHTFLUSH=8000000;
	public static final int FOUROFAKIND=7000000;
	public static final int FULLHOUSE=6000000;
	public static final int FLUSH=5000000;
	public static final int STRAIGHT=4000000;
	public static final int THREEOFAKIND=3000000;
	public static final int TWOPAIR=2000000;
	public static final int ONEPAIR=1000000;
	
	public ArrayList<PlayingCard> Hand=new ArrayList<PlayingCard>();
	private DeckOfCards Deck;
	private int[] card=new int[HANDSIZE];
	private int[] card2=new int[HANDSIZE];
	private char[] card3=new char[HANDSIZE];
	
	//ints for keeping track of which cards are which in each hand
	private int leadPairCard = 5;
	private int bustedFlushCard = 5;
	private int bustedStraightCard = 5;
	private int highNonTrip = 5;
	private int lowNonTrip = 5;
	private int nonTwoPair = 5;
	private int highNonPair = 5;
	private int midNonPair = 5;
	private int lowNonPair = 5;
		
		
	
	//This constructor takes as input a deck of cards which is then referenced throughout the class by 
	//initialising 'Deck'. 5 cards are dealt from the deck and sorted by their game value.
	public HandOfCards(DeckOfCards deck){
		Deck=deck;
		for(int x=0;x<HANDSIZE;x++){
			Hand.add(deck.dealNext());
		}
		sort();
		getValues();
	}

	public void getValues(){ //stores the values in private ints/chars for easier usage in below methods
		for(int x=0;x<HANDSIZE;x++){
			card[x]=Hand.get(x).getGameValue();
			card2[x]=Hand.get(x).getFaceValue();
			card3[x]=Hand.get(x).getSuit();
		}
	}
	//This method sorts the hand of cards by the game value of the cards by overriding the comparator.
	public void sort(){
		 Collections.sort(Hand,new Comparator<PlayingCard>(){
		 public int compare(PlayingCard one,PlayingCard two){
				if(one.getGameValue()>=two.getGameValue()){
					return -1;
				}
				else{
					return 1;
				}
			}
		});
		
	}
	
	
	/*In each of the boolean methods I filled an array with the attribute that I wanted to compare from each card in the hand.
	 *I then compared these attributes and returned true or false depending on these comparisons. In this way we can classify 
	 *the hand depending on the game value.*/
	
	public boolean isRoyalFlush(){
		boolean straight=false,flush=false;
		
		//Checking for the Ace high straight
		if(card[0]==14&&card[1]==13&&card[2]==12&&card[3]==11&&card[4]==10){
			straight= true;
		}
		//Checking for a flush
		flush=this.isFlush();
		return (straight&&flush);
	}
	
	public boolean isStraightFlush(){
		boolean straight=false,flush=false;
		
		//checking for a straight
		straight=this.isStraight();
		
		//Checking for the flush
		flush=this.isFlush();
		return (flush&&straight);
	}
	
	public boolean isFourOfAKind(){
		boolean fourOfAKind=false;

		//Checking the two cases of four of a kind in a hand
		if((card[0]==card[1])&&(card[1]==card[2])&&(card[2]==card[3])){
			fourOfAKind=true;
		}
		else if((card[3]==card[4])&&(card[1]==card[2])&&(card[2]==card[3])){
			fourOfAKind=true;
		}
		return fourOfAKind;
	}
	
	public boolean isFullHouse(){
		boolean fullhouse=false;

		//Checking the two cases where a full house occurs
		if(((card[0]==card[1])&&(card[1]==card[2])) && (card[3]==card[4])){
			fullhouse= true;
		}
		else if(((card[2]==card[3])&&(card[3]==card[4]))&&(card[0]==card[1])){
			fullhouse= true;
		}
		return fullhouse;
	}
	
	
	public boolean isFlush(){
		//Checking the flush
		if((card3[0]==card3[1])&&(card3[1]==card3[2])&&(card3[2]==card3[3])&&(card3[3]==card3[4])){
			return true;
		}
		return false;
	}
	
	public boolean isStraight(){
		boolean straight=false;

		//Checking the straight
		if((card[0]==card[1]+1)&&(card[1]==card[2]+1)&&(card[2]==card[3]+1)&&(card[3]==card[4]+1)){
			straight=true;
			
		}
		
		//Checking the straight with an ace
		else if((card[0]==card[1]+9)&&(card[1]==card[2]+1)&&(card[2]==card[3]+1)&&(card[3]==card[4]+1)){
			straight=true;
		}
		return straight;
	}
	
	public boolean isThreeOfAKind(){
		boolean threeOfAKind=false;
		//Checking the three instances where a three of a kind occurs
		if((card[0]==card[1])&&(card[1]==card[2])){
			this.highNonTrip=3;
			this.lowNonTrip=4;
			threeOfAKind=true;
		}
		else if((card[1]==card[2])&&(card[2]==card[3])){
			this.highNonTrip=0;
			this.lowNonTrip=4;
			threeOfAKind=true;
		}
		else if((card[2]==card[3])&&(card[3]==card[4])){
			this.highNonTrip=0;
			this.lowNonTrip=1;
			threeOfAKind=true;
		}
		return threeOfAKind;
	}
	
	public boolean isTwoPair(){
		boolean twoPair=false;

		//Checking the instances where the a two pair can occur
		if(card2[0]==card2[1]&&card2[2]==card2[3]){
			twoPair=true;
			this.nonTwoPair=4;
		}
		else if(card2[0]==card2[1]&&card2[3]==card2[4]){
			twoPair=true;
			this.nonTwoPair=2;
		}
		else if(card2[1]==card2[2]&&card2[3]==card2[4]){
			twoPair=true;
			this.nonTwoPair=0;
		}
		return twoPair;
	}

	public boolean isOnePair(){
		//Checking the instances where a pair of cards occur
		if(card[0]==card[1]){
			leadPairCard = 0;
			this.highNonPair=2;
			this.midNonPair=3;
			this.lowNonPair=4;
			return true;
		}
		else if(card[1]==card[2]){
			leadPairCard = 1;
			this.highNonPair=0;
			this.midNonPair=3;
			this.lowNonPair=4;
			return true;
		}
		else if(card[2]==card[3]){
			leadPairCard = 2;
			this.highNonPair=0;
			this.midNonPair=1;
			this.lowNonPair=4;
			return true;
		}
		else if(card[3]==card[4]){
			leadPairCard = 3;
			this.highNonPair=0;
			this.midNonPair=1;
			this.lowNonPair=2;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isHighHand(){
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()||this.isFullHouse()||this.isFlush()||this.isStraight()||this.isThreeOfAKind()||this.isTwoPair()||this.isOnePair()){
			return false;
		}
		return true;
	}
	
	public DeckOfCards getDeck(){
		return Deck;
	}

	/*
	 * The getGameValue method gives the Hand a number value that shows its worth in the game.
	 * It scores each hand based on its overall classification based on the constants at the top
	 * of the class e.g flush=5000000, straight=4000000. It then adds to that score by weighting 
	 * the most important cards in the hand.
	 * The main mathematical idea behind this score is that in order to weight a card then we raise it 
	 * to the power of its significance in the hand. e.g DEFAULT_HAND_VALUE + (BestCard^5 + SecondBest^4 + ThirdBest^3 + 
	 * FourthBest^2 + Worst^1). If we have a hand with more than one of the same face value then we 
	 * treat it as one card e.g DEFAULT_HAND_VALUE + (PairCard^4 + SecondBest^3 + ThirdBest^2 + 
	 * Worst^1).  In this way it provides an exact classification of each hand.
	 */
	public int getGamevalue(){
		int gameValue=0;
		int[] card=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			card[x]=Hand.get(x).getGameValue();
		}
		if(this.isRoyalFlush()){
			gameValue=ROYALFLUSH;
		}
		else if(this.isStraightFlush()){
			gameValue=STRAIGHTFLUSH;
			//carding for the 5 high Straight Flush where Ace is low. In this case 5 will be the second card in the hand
			//Due to our sort() method sorting by gameValue
			if(card[1]==5){
				gameValue+=Math.pow(card[4], 5)+Math.pow(card[3], 4)+Math.pow(card[2], 3)+Math.pow(card[1], 2)+Hand.get(0).getFaceValue();
			}
			else{
				for(int x=0,j=5;x<HANDSIZE;x++,j--){
					gameValue+=Math.pow(card[x],j);
				}
			}
		}
		else if(this.isFourOfAKind()){
			gameValue=FOUROFAKIND;
			if(card[0]==card[1]){
				gameValue+=card[0];
			}
			else{
				gameValue+=card[1];
			}
		}
		else if(this.isFullHouse()){
			gameValue=FULLHOUSE+card[2];
		}
		
		else if(this.isFlush()){
			gameValue=FLUSH;
			for(int x=0,j=5;x<HANDSIZE;x++,j--){
				gameValue+=Math.pow(card[x],j);
			}
		}
		else if(this.isStraight()){
			gameValue=STRAIGHT;
			//carding for the 5 high straight where Ace is low. In this case 5 will be the second card in the hand
			//Due to our sort() method sorting by gameValue
			if(card[1]==5){
				gameValue+=Math.pow(card[1], 5)+Math.pow(card[2], 4)+Math.pow(card[3], 3)+Math.pow(card[4], 2)+Hand.get(0).getFaceValue();
			}
			else{
				for(int x=0,j=5;x<HANDSIZE;x++,j--){
					gameValue+=Math.pow(card[x],j);
				}
			}
		}
		
		else if(this.isThreeOfAKind()){
			gameValue=THREEOFAKIND;
			if(card[0]==card[1]&card[1]==card[2]){
				gameValue+=Math.pow(card[0], 3)+Math.pow(card[3], 2)+card[4];
			}
			else if(card[1]==card[2]&card[2]==card[3]){
				gameValue+=Math.pow(card[1], 3)+Math.pow(card[0], 2)+card[4];
			}
			else{
				gameValue+=Math.pow(card[2], 3)+Math.pow(card[0], 2)+card[1];
			}
		}
		else if(this.isTwoPair()){
			gameValue=TWOPAIR;
			//Finds the cards that are paired and weights them accordingly
			if(card[0]==card[1]&&card[2]==card[3]){
				gameValue+=Math.pow(card[0], 3)+Math.pow(card[2], 2)+card[4];
			}
			else if(card[0]==card[1]&&card[3]==card[4]){
				gameValue+=Math.pow(card[0], 3)+Math.pow(card[3], 2)+card[2];
			}
			else{
				gameValue+=Math.pow(card[1], 3)+Math.pow(card[3], 2)+card[0];
			}
		}
		else if(this.isOnePair()){
			gameValue=ONEPAIR;
			//Finds which cards are the pair and weights them accordingly
			if(card[0]==card[1]){
				gameValue+=Math.pow(14, 3)*card[0]+Math.pow(14,2)*card[2]+14*card[3]+card[4];
			}
			else if(card[1]==card[2]){
				gameValue+=Math.pow(14, 3)*card[1]+Math.pow(14,2)*card[0]+14*card[3]+card[4];
			}
			else if(card[2]==card[3]){
				gameValue+=Math.pow(14, 3)*card[2]+Math.pow(14,2)*card[0]+14*card[1]+card[4];
			}
			else if(card[3]==card[4]){
				gameValue+=Math.pow(14, 3)*card[3]+Math.pow(14,2)*card[0]+14*card[1]+card[2];
			}
		}
		else{
			//Best Card^5+Second Best^4+Third Best^3...etc
			for(int x=0,j=5;x<HANDSIZE;x++,j--){
				gameValue+=Math.pow(card[x],j);
			}
		}
		
		return gameValue;
	}
	
	public int getDiscardProbability(int cardPosition){
		int discardProbability = 0;
		//checks for invalid input
		if(cardPosition!=0&&cardPosition!=1&&cardPosition!=2&&cardPosition!=3&&cardPosition!=4){
			discardProbability =0;
		}
		//checks what type of hand the hand is
		if(isRoyalFlush()==true){
			discardProbability =0;
		}
		else if(isStraightFlush()==true){
			discardProbability = 0;
		}
		else if(isFourOfAKind()==true){
			discardProbability = 0;
		}
		else if(isFullHouse()==true){
			discardProbability=0;
		}
		else if(isFlush()==true){
			discardProbability=0;
		}
		else if(isStraight()==true){
			//not worth risking losing the straight to get a flush if it is a busted flush.
			discardProbability=0;
		}
		else if(isThreeOfAKind()==true){
			//discard low non-trip, to get full house
			if(cardPosition==this.lowNonTrip){
				discardProbability=100;
			}
			//discard both some-of the time, to get 4OK
			if(cardPosition==this.highNonTrip){
				discardProbability=20;
			}
		}
		
		else if(isTwoPair()==true){
			if(cardPosition==this.nonTwoPair){
				discardProbability = 70; //high probability as lessening the hand will only lessen the kicker
			}
			
		}
		
		else if(this.isOnePair()){
			//checks if it is a bustedFlush/bustedStraight
			if(this.isBustedFlush()&&this.isBustedStraight()){
				if((this.bustedFlushCard==this.bustedStraightCard)&&(this.bustedFlushCard==cardPosition)){
					discardProbability = 100;
				}
			}
			else if(this.isBustedFlush()){
				if(this.bustedFlushCard==cardPosition){
					discardProbability = 100;
				}
			}
			else if(this.isBustedStraight()){
				if(this.bustedStraightCard==cardPosition){
					discardProbability = 100;
				}
			}
			else{
				if(cardPosition==this.lowNonPair){
					discardProbability = 100;
				}
				else if(cardPosition==this.midNonPair){
					discardProbability = 50;
				}
				else if(cardPosition==this.highNonPair){
					discardProbability = 10;
				}
			}
		}
		
		else if(this.isHighHand()){
			//checks if it is a bustedFlush/bustedStraight
			if(this.isBustedFlush()&&this.isBustedStraight()){
				if((this.bustedFlushCard==this.bustedStraightCard)&&(this.bustedFlushCard==cardPosition)){
					discardProbability = 100;
				}
			}
			else if(this.isBustedFlush()){
				if(this.bustedFlushCard==cardPosition){
					discardProbability = 100;
				}
			}
			else if(this.isBustedStraight()){
				if(this.bustedStraightCard==cardPosition){
					discardProbability = 100;
				}
			}
			else{//discard worst card, and 2nd worst some of the time
				if(cardPosition==4){
					discardProbability = 100;
					return discardProbability;
				}
				else if(cardPosition==3){
					discardProbability = 50;
					return discardProbability;
				}
				else if(cardPosition==2){
					discardProbability = 10;
					return discardProbability;
				}
			}		
		}
		return discardProbability;
	}
	public boolean isBustedFlush(){
		if(card3[0]==card3[1]&&card3[0]==card3[2]&&card3[0]==card3[3]&&card3[0]!=card3[4]){
			bustedFlushCard = 4;
			return true;
		}
		else if(card3[0]==card3[1]&&card3[0]==card3[2]&&card3[0]!=card3[3]&&card3[0]==card3[4]){
			bustedFlushCard = 3;
			return true;
		}
		else if(card3[0]==card3[1]&&card3[0]!=card3[2]&&card3[0]==card3[3]&&card3[0]==card3[4]){
			bustedFlushCard = 2;
			return true;
		}
		else if(card3[0]!=card3[1]&&card3[0]==card3[2]&&card3[0]==card3[3]&&card3[0]==card3[4]){
			bustedFlushCard = 1;
			return true;
		}
		else if(card3[1]!=card3[0]&&card3[1]==card3[2]&&card3[1]==card3[3]&&card3[1]==card3[4]){
			bustedFlushCard = 0;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isBustedStraight(){
		//ACE LOW STRAUGHT
		if(card[0]==14&&card[1]!=6){
			//ACE LOW CONTAINING A PAIR
			if(isOnePair()==true){
				if(leadPairCard==1){
					if((card[2]==4&&card[3]==3&&card[4]==2)||(card[2]==5&&card[3]==3&&card[4]==2)||(card[2]==5&&card[3]==4&&card[4]==2)||(card[2]==5&&card[3]==4&&card[4]==3)){
						bustedStraightCard = 2;
						return true;
					}
				}
				else if(leadPairCard==2){
					if((card[1]==4&&card[3]==3&&card[4]==2)||(card[1]==5&&card[3]==3&&card[4]==2)||(card[1]==5&&card[3]==4&&card[4]==2)||(card[1]==5&&card[3]==4&&card[4]==3)){
						bustedStraightCard = 3;
						return true;
					}
				}
				else if(leadPairCard==3){
					if((card[1]==4&&card[2]==3&&card[3]==2)||(card[1]==5&&card[2]==3&&card[3]==2)||(card[1]==5&&card[2]==4&&card[3]==2)||(card[1]==5&&card[2]==4&&card[3]==3)){
						bustedStraightCard = 3;
						return true;
					}		
				}
				else{
					return false;
				}
			}
			//ACE LOW STRAIGHT THAT DOESN'T CONTAIN A PAIR
			else if(isHighHand()==true){
				if((card[2]==4&&card[3]==3&&card[4]==2)||(card[2]==5&&card[3]==3&&card[4]==2)||(card[2]==5&&card[3]==4&&card[4]==2)||(card[2]==5&&card[3]==4&&card[4]==3)){
					bustedStraightCard = 1;
					return true;
				}		
			}
		}
	//ALL OTHER CASES	
		if(isOnePair()==true){
			if(leadPairCard==0){
				if((card[0]-1==card[2]&&card[0]-2==card[3]&&card[0]-3==card[4])||(card[0]-1==card[2]&&card[0]-2==card[3]&&card[0]-4==card[4])||(card[0]-1==card[2]&&card[0]-3==card[3]&&card[0]-4==card[4])||(card[0]-2==card[2]&&card[0]-3==card[3]&&card[0]-4==card[4])){
					bustedStraightCard = 1;
					return true;
				}
			}
			else if(leadPairCard==1){
				if((card[1]+1==card[0]&&card[1]-1==card[3]&&card[1]-2==card[4])||(card[1]+2==card[0]&&card[1]-1==card[3]&&card[1]-2==card[4])||(card[1]+1==card[0]&&card[1]-2==card[3]&&card[1]-3==card[4])||(card[1]+2==card[0]&&card[1]-1==card[3]&&card[1]-3==card[4])){
					bustedStraightCard = 2;
					return true;
				}
			}
			else if(leadPairCard==2){
				if((card[2]+2==card[0]&&card[2]+1==card[1]&&card[2]-1==card[4])||(card[2]+3==card[0]&&card[2]+2==card[1]&&card[2]-1==card[4])||(card[2]+3==card[0]&&card[2]+1==card[1]&&card[2]-1==card[4])||(card[2]+2==card[0]&&card[2]+1==card[1]&&card[2]-2==card[4])){
					bustedStraightCard = 3;
					return true;
				}
			}
			else if(leadPairCard==3){
				if((card[3]+3==card[0]&&card[3]+2==card[1]&&card[3]+1==card[2])||(card[3]+4==card[0]&&card[3]+3==card[1]&&card[3]+2==card[2])||(card[3]+4==card[0]&&card[3]+2==card[1]&&card[3]+1==card[2])||(card[3]+4==card[0]&&card[3]+3==card[1]&&card[3]+1==card[2])){
					bustedStraightCard = 4;
					return true;
				}
			}
		}
		//NON PAIR CASE
		else{
			if((card[0]-1==card[1]&&card[0]-2==card[2]&&card[0]-3==card[3])||(card[0]-1==card[1]&&card[0]-2==card[2]&&card[0]-4==card[3])||(card[0]-1==card[1]&&card[0]-3==card[2]&&card[0]-4==card[3])||(card[0]-2==card[1]&&card[0]-3==card[2]&&card[0]-4==card[3])){
				bustedStraightCard = 0;
				return true;
			}
			else if((card[1]-1==card[2]&&card[1]-2==card[3]&&card[1]-3==card[4])||(card[1]-1==card[2]&&card[1]-2==card[3]&&card[1]-4==card[4])||(card[1]-1==card[2]&&card[1]-3==card[3]&&card[1]-4==card[4])||(card[1]-2==card[2]&&card[1]-3==card[3]&&card[1]-4==card[4])){
				bustedStraightCard = 4;
				return true;
			}
		}
	return false;
	}
	//Removes the PlayingCard at index cardNumber
	public PlayingCard removeCard(int cardNumber){
		return Hand.remove(cardNumber);
	}
	//Replaces the cards that were discarded
	public void replaceDiscarded(){
		while(Hand.size()<HANDSIZE){
			Hand.add(Deck.dealNext());
		}
		return;
	}
	
	public String toString(){
		return Hand.toString();
		
	}
	

	public static void main(String[] args){
		DeckOfCards Deck=new DeckOfCards();
		HandOfCards Hand=new HandOfCards(Deck);
		HandOfCards Hand2=new HandOfCards(Deck);
		SetHand set=new SetHand(Hand,11);
		SetHand set2=new SetHand(Hand2,12);
		Hand.getValues();
		Hand2.getValues();
		System.out.println(Hand+" "+Hand.isFourOfAKind()+" "+Hand.getDiscardProbability(0)+" "+Hand.getDiscardProbability(1)+" "+Hand.getDiscardProbability(2)+" "+Hand.getDiscardProbability(3)+" "+Hand.getDiscardProbability(4)+" ");
		System.out.println(Hand2+" "+Hand2.isStraight()+" "+Hand2.getGamevalue());
		
		
	}
}