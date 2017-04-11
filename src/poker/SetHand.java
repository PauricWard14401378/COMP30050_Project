//Pauric Ward 14401378
//Assignment 4
package poker;

import java.util.ArrayList;

/*This class takes a HandOfCards as argument and a number to specify which predefined hand to fill the HandOfCards with.
 * It then clears the Hand and fills it with the predefined hand using the setHand method.*/
public class SetHand {
	public ArrayList<PlayingCard> Hand;
	public String Class;
	
	public SetHand(HandOfCards hand,int handNo) {
		Hand=hand.Hand;
		Hand.clear();
		setHand(handNo);
	}
	
	//This method fills the HandOfCards with PlayingCards based on the handNo argument. It also sets the 
	//String 'Class' with a String describing the hand.
	private ArrayList<PlayingCard> setHand(int handNo){
		switch(handNo){
		case 1: Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Hand.add(new PlayingCard("10",'D',10,10));
				Class="Royal Flush";
				break;
		case 2: Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Hand.add(new PlayingCard("10",'D',10,10));
				Hand.add(new PlayingCard("9",'D',9,9));
				Class="King High Straight Flush";
				break;
				//
		case 3: Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("2",'D',2,2));
				Hand.add(new PlayingCard("3",'D',3,3));
				Hand.add(new PlayingCard("4",'D',4,4));
				Hand.add(new PlayingCard("5",'D',5,5));
				Class="5 High Straight Flush";
				break;
		case 4: Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("A",'H',1,14));
				Hand.add(new PlayingCard("A",'S',1,14));
				Hand.add(new PlayingCard("A",'C',1,14));
				Hand.add(new PlayingCard("2",'D',2,2));
				Class="Ace Four Of A Kind";
				break;
		case 5: Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("K",'H',13,13));
				Hand.add(new PlayingCard("K",'S',13,13));
				Hand.add(new PlayingCard("K",'C',13,13));
				Hand.add(new PlayingCard("A",'D',1,14));
				Class="King Four Of A Kind";
				break;
		case 6:	Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("A",'H',1,14));
				Hand.add(new PlayingCard("A",'S',1,14));
				Hand.add(new PlayingCard("2",'C',2,2));
				Hand.add(new PlayingCard("2",'D',2,2));
				Class="Aces over Twos Full House";
				break;
		case 7:	Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("K",'H',13,13));
				Hand.add(new PlayingCard("K",'S',13,13));
				Hand.add(new PlayingCard("Q",'C',13,13));
				Hand.add(new PlayingCard("Q",'D',13,13));
				Class="Kings over Queens Full House";
				break;
		case 8: Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Hand.add(new PlayingCard("9",'C',9,9));
				Class="Ace High Busted Flush with King, Queen, Jack and Nine";
				break;
		case 9: Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Hand.add(new PlayingCard("8",'D',8,8));
				Hand.add(new PlayingCard("7",'D',7,7));
				Class="Ace High Flush with King, Queen, Jack and Eight";
				break; 
		case 10:Hand.add(new PlayingCard("A",'S',1,14));
				Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Hand.add(new PlayingCard("10",'D',10,10));
				Class="Ace High Straight";
				break; 
		case 11:Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("k",'D',13,13));
				Hand.add(new PlayingCard("3",'D',3,3));
				Hand.add(new PlayingCard("3",'D',3,3));
				Hand.add(new PlayingCard("2",'S',2,2));	
				Class="Five High Straight";
				break;
		case 12:Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("Q",'H',12,12));
				Hand.add(new PlayingCard("J",'S',11,11));
				Hand.add(new PlayingCard("J",'D',11,11));
				Hand.add(new PlayingCard("3",'S',3,3));	
				Class="Set of Aces";
				break;
		case 13:Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("K",'H',13,13));
				Hand.add(new PlayingCard("K",'S',13,13));
				Hand.add(new PlayingCard("2",'C',2,2));
				Hand.add(new PlayingCard("A",'D',1,14));
				Class="Set of Kings";
				break;
		case 14:Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("A",'H',1,14));
				Hand.add(new PlayingCard("2",'C',2,2));
				Hand.add(new PlayingCard("2",'D',2,2));
				Hand.add(new PlayingCard("3",'C',3,3));
				Class="Two Pair, Aces over twos";
				break;
		case 15:Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("K",'H',13,13));
				Hand.add(new PlayingCard("Q",'D',12,12));
				Hand.add(new PlayingCard("Q",'S',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Class="Two Pair, Kings over Queens";
				break;
		case 16:Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("A",'H',1,14));
				Hand.add(new PlayingCard("2",'C',2,2));
				Hand.add(new PlayingCard("4",'D',4,4));
				Hand.add(new PlayingCard("3",'C',3,3));
				Class="One pair, Aces";
				break;
		case 17:Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("K",'H',13,13));
				Hand.add(new PlayingCard("10",'D',10,10));
				Hand.add(new PlayingCard("Q",'S',12,12));
				Hand.add(new PlayingCard("J",'D',11,11));
				Class="One Pair, Kings";
				break;
		case 18:Hand.add(new PlayingCard("A",'D',1,14));
				Hand.add(new PlayingCard("J",'H',11,11));
				Hand.add(new PlayingCard("2",'C',2,2));
				Hand.add(new PlayingCard("4",'D',4,4));
				Hand.add(new PlayingCard("3",'C',3,3));
				Class="Ace High";
				break;
		case 19:Hand.add(new PlayingCard("K",'D',13,13));
				Hand.add(new PlayingCard("J",'H',11,11));
				Hand.add(new PlayingCard("10",'C',10,10));
				Hand.add(new PlayingCard("9",'D',9,9));
				Hand.add(new PlayingCard("8",'C',8,8));
				Class="King High";
				break;
		}
		return Hand;
		
	}
	
	public String toString(){
		return Class;
	}

}
