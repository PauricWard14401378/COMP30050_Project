package poker;

import java.util.ArrayList;

public class HandOfPoker {
	//Declarations of variables used in the HandOfPoker class
	private HumanPokerPlayer Human;
	private ArrayList<AutomatedPokerPlayer> Bots=new ArrayList<AutomatedPokerPlayer>();
	private ArrayList<PokerPlayer> Players=new ArrayList<PokerPlayer>();
	private DeckOfCards Deck;
	private Bank Bank;
	private Boolean PlayerAllIn=false;
	
	//The constructor takes as input the players in the hand, a deck of cards and the bank with the player's chips
	//It deals each of the players a hand to start
	HandOfPoker(HumanPokerPlayer human, ArrayList<AutomatedPokerPlayer> bots, DeckOfCards deck, Bank bank){
		Human=human;
		Bots=bots;
		Deck=deck;
		Bank=bank;
		initializePlayers(human,bots);
		dealCards();
	}
	
	//Initialising the players that are in this hand of poker
	private void initializePlayers(HumanPokerPlayer human2, ArrayList<AutomatedPokerPlayer> bots2) {
			Players.add(human2);
			for(int i=0;i<bots2.size();i++){
				Players.add(bots2.get(i));
			}
	}

	//Deals a hand of cards to each of the players in the hand
	public void dealCards() {
		for(int i=0; i<Players.size();i++){
			Players.get(i).dealHand(Deck);
			if(i<Players.size()&&i!=0){
				Bots.get(i-1).updatePercentages();
			}
		}
	}

	//Checks which players can open the betting and outputs it
	public boolean opening() {
		boolean canOpen=false;
		for(int i=0;i<Players.size();i++){
			if(Players.get(i).canOpen()){
				GameOfPoker.IO.Output(Players.get(i).Name+" says: I can open");
				Players.get(i).CanOpen=true;
				canOpen=true;
			}
			else{
				GameOfPoker.IO.Output(Players.get(i).Name+" says: I cannot open");
			}
		}
		return canOpen;
	}
	
	//Handles the players discarding cards
	public void discardCards(Integer[] numcards) {
		Human.discard(numcards);
		for(int i=0; i<Bots.size();i++){
			Bots.get(i).discard();
		}
	}
	
	//This handles the rounds of betting in the hand
	public void betting(int amount){
		boolean raised=false;
		boolean firstSkipped=false;
		int currentStake=amount;
		
		do{
			//This do while loop keeps repeating round of betting until all players call or fold
			raised=false;
			//Goes through each player in the hand and asks then whether they want to call or raise
			for(int i=0; i < Players.size();i++){
				//Because we handle the user's opening of the bet in GameOfPoker then we skip 
				//The user for the first loop around
				if(i==0 && firstSkipped==false){
					firstSkipped=true;
					raised=true;
					continue;
				}
				if(Players.size()==1){
					break;
				}
				//Asks each player if they want to call the bet or not
				if(Players.get(i).call(currentStake)){
					if(PlayerAllIn){
						continue;
					}
					//If the player cannot open but wants to stay in the hand
					if(Players.get(i).CarryOn==true){
						GameOfPoker.IO.Output(Players.get(i).Name+" says: I cannot open, continue!");
						continue;
					}
					//Handles when a player goes all in
					if(Players.get(i).AllIn){
						PlayerAllIn=true;
						continue;
					}
					//Handles when a player opens the betting
					if(Players.get(i).opened){
						raised=true;
					}
					//Update the current stake of the hand
					currentStake=Players.get(i).stake;
					//Asks the player if they want to raise
					if(Players.get(i).raise()&&!PlayerAllIn){
						currentStake++;
						raised=true;
					}
				}
				//Otherwise fold
				else{
					GameOfPoker.IO.Output(Players.get(i).Name+" says: I fold!");
					Players.get(i).fold();
					Players.remove(i);
					i--;
				}
			}
		}while(raised==true);
	}
	
	//If the user indicates they want to fold then fold the user
	public void folding(String fold) {
		if(fold.equalsIgnoreCase("yes")||fold.equalsIgnoreCase("y")){
			Players.get(0).fold();
			Players.remove(0);
		}
	}
	
	//Compares the players' hands to see who won the hand
	public void compareHands(){
		int j=0;
		for(int i=0; i<Players.size()-1;i++){
			if(i==0){
				GameOfPoker.IO.Output(Players.get(i).Name+"'s hand: "+Players.get(i).Hand);
			}
			if(i==1||i==0){
				GameOfPoker.IO.Output(Players.get(i+1).Name+"'s hand: "+Players.get(i+1).Hand);
			}
			if(Players.get(j).Hand.getGamevalue()>Players.get(i+1).Hand.getGamevalue()){
				Players.get(i+1).fold();
			}
			else{
				Players.get(j).fold();
				j=i+1;
			}
		}
		//Adding the pot to the winner's bank
		Bank.addToBank(Players.get(j).Name, Bank.getPot());
		Bank.resetPot();
		Players.get(j).fold();
		GameOfPoker.IO.Output(Players.get(j).Name+" Wins!!");        
	}
}
