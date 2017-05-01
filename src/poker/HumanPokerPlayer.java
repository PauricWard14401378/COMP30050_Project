package poker;

import java.util.Arrays;
import java.util.Collections;


public class HumanPokerPlayer extends PokerPlayer{
	
	//For use of the program in the console and not on twitter
	private static final boolean console = true;
	
	//Keeps track of the input recieved from IO
	private String InputString;

	//The constructor takes an extra input than its parent class.
	public HumanPokerPlayer(DeckOfCards deck, String name, Bank bank) {
		super(deck, bank);
		Name=name;
	}
	
	//Deals with discarding cards back to the deck. The method takes as input
	//an array of the positions to replace. It then returns the cards to the deck,
	//gets replacement cards from the deck and shuffles the hand
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

	//Handles asking the user whether they want to call the bet or not
	public boolean call(int amount){
		int amountSt = Math.abs(amount-stake);
		if(amountSt==0){
			return true;
		}
		String call;
		do{
			GameOfPoker.IO.Output(seeBetPromptToString(amountSt));
			call=getInput();
		}while(!(call.equalsIgnoreCase("y")||call.equalsIgnoreCase("n")||call.equalsIgnoreCase("yes")||call.equalsIgnoreCase("no")));
		
		if(call.equalsIgnoreCase("yes")||call.equalsIgnoreCase("y")){
			//Handles when the player goes all in
			if(Bank.getPlayerStack(Name)-amountSt <= 0){
				Bank.removeFromBank(Name,Bank.getPlayerStack(Name));
				AllIn=true;
			}
			else{
				Bank.removeFromBank(Name,amountSt);
			}
			stake=amount;
			return true;
		}
		else{
			return false;
		}
	}
	
	//Handles asking the user whether they want to raise the bet by 1 chip or not
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
		
	//Allows for this thread to continue after an input is received
	public synchronized void notifyGame(){
		notify();
	}
	//Allows for this thread to wait on input
	@SuppressWarnings("unused")
	public synchronized String getInput(){
		if(console==false){//For testing on the console without Twitter
			InputString="";
			GameOfPoker.IO.tweetRemainingOutput();
			while(InputString.isEmpty()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				InputString=GameOfPoker.IO.input();
			}
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


