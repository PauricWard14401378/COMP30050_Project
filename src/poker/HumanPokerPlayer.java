package poker;

import java.util.Arrays;
import java.util.Collections;


public class HumanPokerPlayer extends PokerPlayer{
	
	
	private static final boolean console = false;
	private String InputString;

	public HumanPokerPlayer(DeckOfCards deck, String name, Bank bank) {
		super(deck, bank);
		Name=name;
	}
	
	public void discard(Integer[] cards){
		Arrays.sort(cards,Collections.reverseOrder());
		for(int x=0;x<cards.length;x++){
			Deck.returnCard(Hand.removeCard(cards[x]-1));
		}
		//Replace the cards that were discarded
		Hand.replaceDiscarded();
		//Resort the Hand
		System.out.println(Hand.toString());
		Hand.sort();	
	}

	
	public boolean call(int amount){
		int amountSt = Math.abs(amount-stake);
		String call;
		do{
			GameOfPoker.IO.Output(seeBetPromptToString(amountSt));
			call=getInput();
		}while(!(call.equalsIgnoreCase("y")||call.equalsIgnoreCase("n")||call.equalsIgnoreCase("yes")||call.equalsIgnoreCase("no")));
		if(call.equalsIgnoreCase("yes")||call.equalsIgnoreCase("y")){
			if(Bank.getPlayerStack(Name)-amountSt<0){
				Bank.removeFromBank(Name,Bank.getPlayerStack(Name));
				AllIn=true;
			}
			Bank.removeFromBank(Name,amountSt);
			stake=amount;
			return true;
		}
		else{
			return false;
		}
	}
	public boolean raise(){
		
		String raise;
		do{
			GameOfPoker.IO.Output(raiseBetPromptToString());
			raise=getInput();
		}while(!(raise.equalsIgnoreCase("y")||raise.equalsIgnoreCase("n")||raise.equalsIgnoreCase("yes")||raise.equalsIgnoreCase("no")));
		if(raise.equalsIgnoreCase("yes")||raise.equalsIgnoreCase("y")){
			stake+=1;
			this.Bank.removeFromBank(Name, 1);
			return true;
		}
		else{
			return false;
		}
	}
		
	public synchronized void notifyGame(){
		System.out.println("NOTIFIED");
		notify();
	}
	@SuppressWarnings("unused")
	public synchronized String getInput(){
		if(console==true){
			InputString="";
			GameOfPoker.IO.tweetRemainingOutput();
			while(InputString.isEmpty()){
				try {
					System.out.println("Before");
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				InputString=GameOfPoker.IO.input();
			}
			System.out.println("After");
			
			System.out.println(InputString);
			return InputString;
		}
		else{
			return GameOfPoker.IO.input();
		}
		}
		
	public String raiseBetPromptToString(){
		return "Do you want to raise the bet by 1 chip? (Y/N) (yes/no)";
	}
	public String seeBetPromptToString(int amountSt){
		return "Do you want to see the bet of "+ amountSt + " chip(s)? (Y/N) (yes/no)";
	}
}


