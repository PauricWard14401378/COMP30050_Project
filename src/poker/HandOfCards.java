//Pauric Ward 14401378
//Assignment 4
package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//This class is a programming representation of a hand of playing cards.
//Each hand has to be classified to show its worth in the game of draw poker.

public class HandOfCards {
	
	private static final int HANDSIZE=5;
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
	
	//This constructor takes as input a deck of cards which is then referenced throughout the class by 
	//initialising 'Deck'. 5 cards are dealt from the deck and sorted by their game value.
	public HandOfCards(DeckOfCards deck){
		Deck=deck;
		for(int x=0;x<HANDSIZE;x++){
			Hand.add(deck.dealNext());
		}
		sort();
	}

	//This method sorts the hand of cards by the game value of the cards by overriding the comparator.
	public void sort(){
		Collections.sort(Hand,new Comparator<PlayingCard>(){
			@Override public int compare(PlayingCard one,PlayingCard two){
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
		int[] check=new int[HANDSIZE];
		char[] check2=new char[HANDSIZE];
		boolean straight=false,flush=false;
		
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
			check2[x]=Hand.get(x).getSuit();
		}
		//Checking for the Ace high straight
		if(check[0]==14&&check[1]==13&&check[2]==12&&check[3]==11&&check[4]==10){
			straight= true;
		}
		//Checking for a flush
		if(check2[0]==check2[1]&&check2[1]==check2[2]&&check2[2]==check2[3]&&check2[3]==check2[4]){
			flush=true;
		}
		return (straight&&flush);
	}
	
	public boolean isStraightFlush(){
		int[] check=new int[HANDSIZE];
		char[] check2 = new char[HANDSIZE];
		boolean straight=false,flush=false;
		if(this.isRoyalFlush()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
			check2[x]=Hand.get(x).getSuit();
		}
		//Checking for a straight
		if(check[0]==(check[1]+1)&&(check[1]==check[2]+1)&&(check[2]==check[3]+1)&&(check[3]==check[4]+1)){
			straight=true;
		}
		//Checking for a straight with an ace
		else if(check[0]==(check[1]+9)&&(check[1]==check[2]+1)&&(check[2]==check[3]+1)&&(check[3]==check[4]+1)){
			straight=true;
		}
		//Checking for the flush
		if((check2[0]==check2[1])&&(check2[1]==check2[2])&&(check2[2]==check2[3])&&(check2[3]==check2[4])){
			flush= true;
		}
		return (flush&&straight);
	}
	
	public boolean isFourOfAKind(){
		int[] check=new int[HANDSIZE];
		boolean fourOfAKind=false;
		if(this.isStraightFlush()||this.isRoyalFlush()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		//Checking the two cases of four of a kind in a hand
		if((check[0]==check[1])&&(check[1]==check[2])&&(check[2]==check[3])){
			fourOfAKind=true;
		}
		else if((check[3]==check[4])&&(check[1]==check[2])&&(check[2]==check[3])){
			fourOfAKind=true;
		}
		return fourOfAKind;
	}
	
	public boolean isFullHouse(){
		int[] check=new int[HANDSIZE];
		boolean fullhouse=false;
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		
		//Checking the two cases where a full house occurs
		if(((check[0]==check[1])&&(check[1]==check[2])) && (check[3]==check[4])){
			fullhouse= true;
		}
		else if(((check[2]==check[3])&&(check[3]==check[4]))&&(check[0]==check[1])){
			fullhouse= true;
		}
		return fullhouse;
	}
	
	
	
	public boolean isFlush(){
		char[] check = new char[HANDSIZE];
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()||this.isFullHouse()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getSuit();
		}
		//Checking the flush
		if((check[0]==check[1])&&(check[1]==check[2])&&(check[2]==check[3])&&(check[3]==check[4])){
			return true;
		}
		return false;
	}
	
	public boolean isStraight(){
		int[] check=new int[HANDSIZE];
		boolean straight=false;
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()||this.isFullHouse()||this.isFlush()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		//Checking the straight
		if(check[0]==(check[1]+1)&&(check[1]==check[2]+1)&&(check[2]==check[3]+1)&&(check[3]==check[4]+1)){
			straight=true;
		}
		//Checking the straight with an ace
		else if(check[0]==(check[1]+9)&&(check[1]==check[2]+1)&&(check[2]==check[3]+1)&&(check[3]==check[4]+1)){
			straight=true;
		}
		return straight;
	}
	
	public boolean isThreeOfAKind(){
		int[] check=new int[HANDSIZE];
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()||this.isFullHouse()||this.isFlush()||this.isStraight()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		//Checking the three instances where a three of a kind occurs
		if(((check[0]==check[1])&&(check[1]==check[2]))||((check[1]==check[2])&&(check[2]==check[3]))||((check[2]==check[3])&&(check[3]==check[4]))){
			return true;
		}
		return false;
	}
	
	public boolean isTwoPair(){
		int[] check=new int[HANDSIZE];
		boolean twoPair=false;
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()||this.isFullHouse()||this.isFlush()||this.isStraight()||this.isThreeOfAKind()){
			return false;
		}
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getFaceValue();
		}
		//Checking the instances where the a two pair can occur
		if(check[0]==check[1]&&check[2]==check[3]){
			twoPair=true;
		}
		else if(check[0]==check[1]&&check[3]==check[4]){
			twoPair=true;
		}
		else if(check[1]==check[2]&&check[3]==check[4]){
			twoPair=true;
		}
		return twoPair;
	}

	public boolean isOnePair(){
		int[] check=new int[HANDSIZE];
		if(this.isStraightFlush()||this.isRoyalFlush()||this.isFourOfAKind()||this.isFullHouse()||this.isFlush()||this.isStraight()||this.isThreeOfAKind()||this.isTwoPair()){
			return false;
		}
		
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getFaceValue();
		}
		
		//Checking the instances where a pair of cards occur
		if((check[0]==check[1])||(check[1]==check[2])||(check[2]==check[3])||(check[3]==check[4])){
			return true;
		}
		return false;
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
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		if(this.isHighHand()){
			//Best Card^5+Second Best^4+Third Best^3...etc
			for(int x=0,j=5;x<HANDSIZE;x++,j--){
				gameValue+=Math.pow(check[x],j);
			}
		}
		else if(this.isOnePair()){
			gameValue=ONEPAIR;
			//Finds which cards are the pair and weights them accordingly
			if(check[0]==check[1]){
				gameValue+=Math.pow(check[0], 4)+Math.pow(check[2],3)+Math.pow(check[3], 2)+check[4];
			}
			else if(check[1]==check[2]){
				gameValue+=Math.pow(check[1], 4)+Math.pow(check[0],3)+Math.pow(check[3], 2)+check[4];
			}
			else if(check[2]==check[3]){
				gameValue+=Math.pow(check[2], 4)+Math.pow(check[0],3)+Math.pow(check[1], 2)+check[4];
			}
			else if(check[3]==check[4]){
				gameValue+=Math.pow(check[3], 4)+Math.pow(check[0],3)+Math.pow(check[1], 2)+check[2];
			}
		}
		else if(this.isTwoPair()){
			gameValue=TWOPAIR;
			//Finds the cards that are paired and weights them accordingly
			if(check[0]==check[1]&&check[2]==check[3]){
				gameValue+=Math.pow(check[0], 3)+Math.pow(check[2], 2)+check[4];
			}
			else if(check[0]==check[1]&&check[3]==check[4]){
				gameValue+=Math.pow(check[0], 3)+Math.pow(check[3], 2)+check[2];
			}
			else{
				gameValue+=Math.pow(check[1], 3)+Math.pow(check[3], 2)+check[0];
			}
		}
		else if(this.isThreeOfAKind()){
			gameValue=THREEOFAKIND;
			if(check[0]==check[1]&check[1]==check[2]){
				gameValue+=Math.pow(check[0], 3)+Math.pow(check[3], 2)+check[4];
			}
			else if(check[1]==check[2]&check[2]==check[3]){
				gameValue+=Math.pow(check[1], 3)+Math.pow(check[0], 2)+check[4];
			}
			else{
				gameValue+=Math.pow(check[2], 3)+Math.pow(check[0], 2)+check[1];
			}
		}
		else if(this.isStraight()){
			gameValue=STRAIGHT;
			//Checking for the 5 high straight where Ace is low. In this case 5 will be the second card in the hand
			//Due to our sort() method sorting by gameValue
			if(check[1]==5){
				gameValue+=Math.pow(check[1], 5)+Math.pow(check[2], 4)+Math.pow(check[3], 3)+Math.pow(check[4], 2)+Hand.get(0).getFaceValue();
			}
			else{
				for(int x=0,j=5;x<HANDSIZE;x++,j--){
					gameValue+=Math.pow(check[x],j);
				}
			}
		}
		else if(this.isFlush()){
			gameValue=FLUSH;
			for(int x=0,j=5;x<HANDSIZE;x++,j--){
				gameValue+=Math.pow(check[x],j);
			}
		}
		else if(this.isFullHouse()){
			gameValue=FULLHOUSE;
			if(check[0]==check[1]&&check[1]==check[2]){
				gameValue+=Math.pow(check[0], 2)+check[3];
			}
			else{
				gameValue+=Math.pow(check[4],2)+check[0];
			}
		}
		else if(this.isFourOfAKind()){
			gameValue=FOUROFAKIND;
			if(check[0]==check[1]){
				gameValue+=Math.pow(check[0], 2)+check[4];
			}
			else{
				gameValue+=Math.pow(check[4], 2)+check[0];
			}
		}
		else if(this.isStraightFlush()){
			gameValue=STRAIGHTFLUSH;
			//Checking for the 5 high Straight Flush where Ace is low. In this case 5 will be the second card in the hand
			//Due to our sort() method sorting by gameValue
			if(check[1]==5){
				gameValue+=Math.pow(check[4], 5)+Math.pow(check[3], 4)+Math.pow(check[2], 3)+Math.pow(check[1], 2)+Hand.get(0).getFaceValue();
			}
			else{
				for(int x=0,j=5;x<HANDSIZE;x++,j--){
					gameValue+=Math.pow(check[x],j);
				}
			}
		}
		else{
			gameValue=ROYALFLUSH;
		}
		
		return gameValue;
	}
	
	/* The getDiscardProbability method takes a card from the hand and assigns a probability to it based on its worth to the hand and
	 * hand classification. I decided to assign a probability to each card based on what hand classification it belongs to and the 
	 * probability of removing that card having a positive impact on the hand. I went through each class of hand and decided, using my 
	 * own poker knowledge and probability, which cards I would keep and which I would remove. If I was assigning a value to a card 
	 * purely based its gamevalue I used this formula ((14-check[cardPosition])*7.7) which splits the range 0-100 into 14 pieces
	 * and assigned to the card. */
	public int getDiscardProbability(int cardPosition){
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		//If invalid position entered return 0
		if(cardPosition<0||cardPosition>4){
			return 0;
		}
		//If best hand in the game do not discard
		if(this.isRoyalFlush()){
			return 0;
		}
		//Returns the probability of improving straight flush
		if(this.isStraightFlush()){
			if(cardPosition==4){
				return 8;
			}
			else{
				return 0;
			}
		}
		
		if(this.isFourOfAKind()){
			//If the card position is one of the four of a kind then don't discard
			if(this.fourOfAKindPosition()==cardPosition||this.fourOfAKindPosition()+1==cardPosition||this.fourOfAKindPosition()+2==cardPosition||this.fourOfAKindPosition()+3==cardPosition){
				return 0;
			}
			//Else use the evaluation formula to get the probability of discard
			else{
				return (int) ((14-check[cardPosition])*7.7);
			}
		}
		if(this.isFullHouse()){
			//If the card position is one of the three of a kind then don't discard
			if(this.fullHousePosition()==cardPosition||this.fullHousePosition()+1==cardPosition||this.fullHousePosition()+2==cardPosition){
				return 0;
			}
			//Else return the probability of getting a four of a kind by discarding the pair
			else{
				return 9;
			}
		}
		
		if(this.isFlush()){
			//If the hand is a flush then return the probability of getting a higher flush
			if(cardPosition==4){
				return (14-check[0]);
			}
			else{
				return 0;
			}
			
		}
		//If the card position is the position that the flush is busted then discard
		if(this.isBustedFlush()==cardPosition){
			//if the busted flush contains a pair then return the probability of improving the hand
			if(this.isOnePair()){
				if(cardPosition==0||cardPosition==4){
					return 25;
				}
			}
			//If it is a straight then return the probability of improving the hand
			else if(this.isStraight()){
				return 11;
			}
			else{
				return 100;
			}
		}
		
		if(this.isStraight()){
			if(cardPosition==4){
				return 4;
			}
			return 0;
		}
		//If the card position is the position that the flush is busted then discard
		if(this.isBustedStraight()==cardPosition){
			if(this.isOnePair()){
				//Open ended with pair
				if(cardPosition==0||cardPosition==4){
					return 10;
				}
				else{
					return 12;
				}
			}
			else{
				return 100;
			}
		}
		if(this.isThreeOfAKind()){
			//Discard all but the three of a kind cards based on the evaluation function
			if(this.threeOfAKindPosition()==cardPosition||this.threeOfAKindPosition()+1==cardPosition||this.threeOfAKindPosition()+2==cardPosition){
				return 0;
			}
			else{
				return (int) ((14-check[cardPosition])*7.7);
			}
		}
		if(this.isTwoPair()){
			//Discard all except the two pairs based on the evaluation function
			if(this.twoPairPosition()[0]==cardPosition||this.twoPairPosition()[0]+1==cardPosition||this.twoPairPosition()[1]==cardPosition||this.twoPairPosition()[1]+1==cardPosition){
				return 0;
			}
			else{
				return (int) ((14-check[cardPosition])*7.7);
			}
		}
		//Discard all except one pair based on the evaluation function
		if(this.isOnePair()){
			if(this.onePairPosition()==cardPosition||this.onePairPosition()+1==cardPosition){
				return 0;
			}
			else{
				return (int) ((14-check[cardPosition])*7.7);
			}
		}
		if(this.isHighHand()){
			//Return the probability using the evaluation function
			return (int) ((14-check[cardPosition])*7.7);
		}
		return 0;
	}
	
	//This method goes through the hand to see if it is a busted straight and returns the position of the card that busts it
	private int isBustedStraight(){
		if(this.isStraight()){
			return -1;
			}
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		
		if(this.isOnePair()){
			//ppxxx
			if((check[0]==check[1]&&(check[1]==check[2]+2||check[1]==check[2]+1)&&check[2]==check[3]+1&&check[3]==check[4]+1)){
				return 0;
			}
			//xppxx
			else if((check[1]==check[2]&&check[0]==check[1]+1&&check[2]==check[3]+1&&check[3]==check[4]+1)){
				return 1;
			}
			//xxppx
			else if((check[0]==check[1]+1&&check[1]==check[2]+1&&check[2]==check[3]&&check[3]==check[4]+1)){
				return 2;
			}
			//xxxpp
			else if((check[0]==check[1]+1&&check[1]==check[2]+1&&(check[2]==check[3]+1||check[2]==check[3]+2)&&check[3]==check[4])){
				return 3;
			}
			//aaxxx
			else if(check[0]==14&&check[0]==check[1]&&(check[0]==check[2]+9||check[0]==check[2]+10)&&check[2]==check[3]+1&&check[3]==check[4]+1){
				return 0;
			}
		}
		//xxxxy
		if(check[0]==check[1]+1&&check[1]==check[2]+1&&check[2]==check[3]+1){
			return 4;
		}
		//yxxxx
		else if(check[3]==check[4]+1&&check[1]==check[2]+1&&check[2]==check[3]+1){
			return 0;
		}
		//xyxxx
		else if(check[0]==check[2]+2&&check[2]==check[3]+1&&check[3]==check[4]+1){
			return 1;
		}
		//xxyxx
		else if(check[0]==check[1]+1&&check[1]==check[3]+2&&check[3]==check[4]+1){
			return 2;
		}
		//xxxyx
		else if(check[0]==check[1]+1&&check[1]==check[2]+1&&check[2]==check[4]+2){
			return 3;
		}
		return -1;
	}
	
	//This method goes through the hand to see if it is a busted flush and returns the position of the card that busts it
	private int isBustedFlush(){
		if(this.isFlush()){
			return -1;
			}
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getSuit();
		}
		//xxxxy
		if(check[0]==check[1]&&check[1]==check[2]&&check[2]==check[3]){
			return 4;
		}
		//yxxxx
		else if(check[1]==check[2]&&check[2]==check[3]&&check[3]==check[4]){
			return 0;
		}
		//xyxxx
		else if(check[0]==check[2]&&check[2]==check[3]&&check[3]==check[4]){
			return 1;
		}
		//xxyxx
		else if(check[0]==check[1]&&check[1]==check[3]&&check[3]==check[4]){
			return 2;
		}
		//xxxyx
		else if(check[0]==check[1]&&check[1]==check[2]&&check[2]==check[4]){
			return 3;
		}
		return -1;
	}
	
	//Gets the position of the pair
	private int onePairPosition(){
		if(!this.isOnePair()){
			return -1;
		}
		int[] check=new int[HANDSIZE];
		int pairPosition = -1;
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		if(check[0]==check[1]){
			pairPosition=0;
		}
		else if(check[1]==check[2]){
			pairPosition=1;
		}
		else if(check[2]==check[3]){
			pairPosition=2;
		}
		else if(check[3]==check[4]){
			pairPosition=3;
		}
		return pairPosition;
	}
	
	//Gets the position of the two pair
	private int[] twoPairPosition(){
		int[] pairPosition=new int[2];
		if(!this.isTwoPair()){
			pairPosition[0]=-1;
		}
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		//xxyyp
		if(check[0]==check[1]&&check[2]==check[3]){
			pairPosition[0]=0;
			pairPosition[1]=2;
		}
		//xxpyy
		else if(check[0]==check[1]&&check[3]==check[4]){
			pairPosition[0]=0;
			pairPosition[1]=3;
		}
		//pxxyy
		else if(check[1]==check[2]&&check[3]==check[4]){
			pairPosition[0]=1;
			pairPosition[1]=3;
		}
		return pairPosition;
	}
	//Gets the position of the three of a kind
	private int threeOfAKindPosition(){
		int setPosition=-1;
		if(!this.isThreeOfAKind()){
			return -1;
		}
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		//xxxyy
		if(check[0]==check[1]&&check[1]==check[2]){
			setPosition=0;
		}
		//yxxxy
		else if(check[1]==check[2]&&check[2]==check[3]){
			setPosition=1;
		}
		//yyxxx
		else if(check[2]==check[3]&&check[3]==check[4]){
			setPosition=2;
		}
		return setPosition;
	}
	
	//Gets the position of the three of a kind in the full house
	private int fullHousePosition(){
		int setPosition=-1;
		if(!this.isFullHouse()){
			return -1;
		}
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		if(check[0]==check[1]&&check[1]==check[2]){
			setPosition=0;
		}
		else if(check[1]==check[2]&&check[2]==check[3]){
			setPosition=1;
		}
		else if(check[2]==check[3]&&check[3]==check[4]){
			setPosition=2;
		}
		return setPosition;
	}
	//Gets the position of the four of a kind
	private int fourOfAKindPosition(){
		if(!this.isFourOfAKind()){
			return -1;
		}
		int[] check=new int[HANDSIZE];
		for(int x=0;x<HANDSIZE;x++){
			check[x]=Hand.get(x).getGameValue();
		}
		if(check[0]==check[1]&&check[1]==check[2]&&check[2]==check[3]){
			return 0;
		}
		else{
			return 1;
		}
	}
	//Removes the PlayingCard at index cardNumber
	public PlayingCard removeCard(int cardNumber){
		return Hand.remove(cardNumber);
	}
	//Replaces the cards that were discarded
	public void replaceDiscarded(){
		if(Hand.size()==HANDSIZE){
			return;
		}
		else{
			Hand.add(Deck.dealNext());
		}
		return;
	}
	
	public String toString(){
		return Hand.toString();
		
	}
}