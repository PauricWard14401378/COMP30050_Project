package poker;

import java.util.Arrays;
import java.util.Collections;


public class HumanPokerPlayer extends PokerPlayer{
	
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

	
	public boolean call(int amount){
		int amountSt = Math.abs(amount-stake);
		GameOfPoker.IO.Output(seeBetPromptToString(amountSt));
		String call=GameOfPoker.IO.input();
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
		GameOfPoker.IO.Output("Do you want to raise the bet by 1 chip?");
		String raise=GameOfPoker.IO.input();
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


