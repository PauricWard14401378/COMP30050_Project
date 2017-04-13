package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class HumanPokerPlayer extends PokerPlayer{
	

	private Scanner input=new Scanner(System.in);
	
	public HumanPokerPlayer(DeckOfCards deck, String name) {
		super(deck);
		Name=name;
	}
	public void discard(int[] cards){
		//Need to come up with a way to have cards discard in the right order no matter what input i.e 34 & 43 works at the minute with 34
		if(cards.length>3){
			throw new ArithmeticException("Unable to discard more than 3 cards");
		}
		System.out.println(cards[0]+" "+cards[1]+" "+cards.length);
		for(int x=cards.length-1;x>=0;x--){
			Deck.returnCard(Hand.removeCard(cards[x]));
		}
		//Replace the cards that were discarded
		Hand.replaceDiscarded();
		//Resort the Hand
		Hand.sort();	
	}

	public int bet(int bet){
		if(bet==0){
			return 0;
		}
		else{
			removeFromBank(bet);
		}
		return bet;
	}
	public boolean call(int amount){
		System.out.println("Do you want to see the bet of "+amount+" chip(s)?");
		String call=input.nextLine();
		if(call.equals("yes")){
			removeFromBank(amount);
			return true;
		}
		else{
			return false;
		}
	}
	public boolean raise(){
		System.out.println("Do you want to raise the bet by 1 chip?");
		String raise=input.nextLine();
		if(raise.equals("yes")){
			this.removeFromBank(1);
			return true;
		}
		else{
			return false;
		}
	}
}
