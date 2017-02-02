//Pauric Ward 14401378
//Assignment 1
package poker;

//The PlayingCard class is a programming representation of a playing card object 
//That is used for Texas hold-em Poker.
//It consists of the type, suit, face value and game value. There is also a main
//Method which tests the PlayingCard class.
public class PlayingCard {
	//Declaration of the constants that define the suits of playing card.
	static public final char HEARTS='H';
	static public final char DIAMONDS='D';
	static public final char SPADES='S';
	static public final char CLUBS='C';
	
	//Declaration of the instance variables needed for the class. 
	private String type;
	private char suit;
	private int gameValue;
	private int faceValue;
	
	//The PlayingCard constructor.
	public PlayingCard(String type,char suit,int faceValue,int gameValue){
		this.type=type;
		this.suit=suit;
		this.gameValue=gameValue;
		this.faceValue=faceValue;
		}
	
	public char getSuit(){
		return suit;
	}
	public int getFaceValue(){
		return faceValue;
	}
	public int getGameValue(){
		return gameValue;
	}
	public String toString(){
		return  type+suit;
	}
	
	//Main method which tests the PlayingCard class by filling an array of 52 PlayingCards.
	//It then prints the array using the PlayingCard's toString method.
	public static void main(String[] args) {
		PlayingCard[] Deck =new PlayingCard[52];
		char suit = 0;
		int counter=0;
		
		//Creates 52 instances of the PlayingCard class and stores it in the Deck array.
		for(int x=0;x<4;x++){
			switch(x){
			case 0:
				suit =PlayingCard.CLUBS;
				break;
			case 1:
				suit =PlayingCard.SPADES;
				break;
			case 2:
				suit =PlayingCard.HEARTS;
				break;
			default:
				suit =PlayingCard.DIAMONDS;
				break;
			}
			
			Deck[counter++]=new PlayingCard("2",suit,2,2);
			Deck[counter++]=new PlayingCard("3",suit,3,3);
			Deck[counter++]=new PlayingCard("4",suit,4,4);
			Deck[counter++]=new PlayingCard("5",suit,5,5);
			Deck[counter++]=new PlayingCard("6",suit,6,6);
			Deck[counter++]=new PlayingCard("7",suit,7,7);
			Deck[counter++]=new PlayingCard("8",suit,8,8);
			Deck[counter++]=new PlayingCard("9",suit,9,9);
			Deck[counter++]=new PlayingCard("10",suit,10,10);
			Deck[counter++]=new PlayingCard("J",suit,11,11);
			Deck[counter++]=new PlayingCard("Q",suit,12,12);
			Deck[counter++]=new PlayingCard("K",suit,13,13);
			Deck[counter++]=new PlayingCard("A",suit,1,14);
		}
		
		//Prints the Deck array using the PlayingCard's toString method.
		for(int x=0;x<52;x++){
			System.out.println(Deck[x].toString());	
		}
	}
}
