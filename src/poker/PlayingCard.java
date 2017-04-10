//Pauric Ward 14401378
//Assignment 4
package poker;


//The PlayingCard class is a programming representation of a playing card object 
//That is used for Draws Poker.
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
}
