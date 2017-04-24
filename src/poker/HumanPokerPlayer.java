package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class HumanPokerPlayer extends PokerPlayer{
	

	private Scanner input=new Scanner(System.in);
	
	public HumanPokerPlayer(DeckOfCards deck, String name, Bank bank) {
		super(deck, bank);
		Name=name;
	}
	public void discard(Integer[] cards){
		Arrays.sort(cards,Collections.reverseOrder());
		if(cards.length>3){
			throw new ArithmeticException("Unable to discard more than 3 cards");
		}
		for(int x=0;x<cards.length;x++){
			Deck.returnCard(Hand.removeCard(cards[x]-1));
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
			Bank.removeFromBank(Name,bet);
		}
		return bet;
	}
	public boolean call(int amount){
		int amountSt = Math.abs(amount-stake);
		System.out.println(seeBetPromptToString(amountSt));
		String call=input.nextLine();
		if(call.equals("yes")){
			Bank.removeFromBank(Name,amount);
			stake=amount;
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
			stake+=1;
			this.Bank.removeFromBank(Name, 1);
			return true;
		}
		else{
			return false;
		}
	}
	
	public String seeBetPromptToString(int amountSt){
		return "Do you want to see the bet of "+ amountSt + " chip(s)?";
	}
}


