package poker;

import java.util.ArrayList;
import twitter4j.TwitterException;

public class GameOfPoker extends Thread{
	//For use of the program in the console and not on twitter! Set to true if you want to run through the console
	private static final boolean console = true;
	//Declaration of the variables and constants needed for GameOfPoker
	public static IO IO;
	private ArrayList<AutomatedPokerPlayer> bots=new ArrayList<AutomatedPokerPlayer>();
	private HumanPokerPlayer human;
	private DeckOfCards Deck;
	private String[] BotsNames={"Tom","Dick","Harry","Sally"};
	private ArrayList<PokerPlayer> Players=new ArrayList<PokerPlayer>();
	private Boolean GameOver=false;
	private Bank Bank=new Bank();
	private int NoBots;
	private String InputString = "";
	
	GameOfPoker(int numbots, DeckOfCards deck, IO io) {
		IO=io;
		Deck=deck;
		NoBots=numbots;
	}
	
	//The run method is an implementation of the Thread extension which allows the thread to start
	@Override
	public void run() {
		welcome();
		initializebots(NoBots);
		initializePlayers(human,bots);
		Bank.initializePlayerStacks(Players);
		try {
			playGame();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
	//Initialising the players playing the game of poker
	private void initializePlayers(HumanPokerPlayer human2, ArrayList<AutomatedPokerPlayer> bots2) {
		Players.clear();
		Players.add(human2);
		for(int i=0;i<bots.size();i++){
			Players.add(bots2.get(i));
		}
	}

	//Welcomes the user and asks for their name 
	private void welcome(){
		IO.Output("Welcome to the Automated Poker Machine!\nWhat is your name?");
		String humanName="";
		humanName=getInput();
		human= new HumanPokerPlayer(Deck,humanName,Bank);
		IO.Output(letsPlayToString());
	}

	
	private void initializebots(int numbots){
		if(numbots>4||numbots<1){
			numbots=4;
		}
		for(int i=0;i<numbots;i++){
			bots.add(new AutomatedPokerPlayer(Deck, BotsNames[i],Bank));
		}
		
	}
	
	//This method handles multiple hands of poker until one player wins or the User leaves the game
	private void playGame() throws TwitterException{
		while(!GameOver){
			//Checks if players are bankrupt
			for(int i=0;i<Players.size();i++){
				if(Players.get(i).isBankrupt()){
					PokerPlayer temp=Players.remove(i);
					for(int x=0; x<bots.size();x++){
						if(temp.Name.equals(bots.get(x).Name)){
							bots.remove(x);
						}
					}
				}
			}
			//If the human player is put out then end the game
			if(!Players.get(0).equals(human)){
				gameOver();
				GameOver=true;
				continue;
			}
			//If there is only one player left then they are declared the winner
			if(Players.size()==1){
				String winner=Players.get(0).Name;
				gameOver(winner);
				GameOver=true;
				continue;
			}
			//Resets the Deck
			Deck.reset();
			
			//Sets up a new round of poker
			HandOfPoker round = new HandOfPoker(human, bots, Deck, Bank);
			IO.Output(newDealToString1());
			
			//Shows how many chips each player has
			for(int x=0;x<Players.size();x++){
				String name = Players.get(x).Name;
				int bank = Bank.getPlayerStack(name);
				IO.Output(stackUpdateToString1(name,bank));
			}
			
			//If no one can open then re-deal
			if(!round.opening()){	
				IO.Output(badDealToString1());
				continue;
			}
			//Showing the user their hand
			String handSt = human.showHand();
			IO.Output(handToString1(handSt));
			//Handles Discarding
			String cards="";
			do{
				IO.Output(discardPromptToString1());
				cards=getInput();
			}while(!(cards.contains("0")||cards.contains("1")||cards.contains("2")||cards.contains("3")||cards.contains("4")||cards.contains("5")));
			
			if(!cards.contains("0")){
				cards=cards.replaceAll("[^\\d]", "");
				Integer[] discardcards = new Integer[cards.length()];
				for(int x=0;x<cards.length();x++){
					discardcards[x]=Character.getNumericValue(cards.charAt(x));
				}
				round.discardCards(discardcards);
			}
			//Showing the user their hand after discard
			String hand = human.showHand();
			IO.Output(handUpdateToString1(hand));
			//Handles folding
			String fold;
			do{
				IO.Output(foldPromptToString1());
				fold=getInput();
			}while(!(fold.equalsIgnoreCase("y")||fold.equalsIgnoreCase("n")||fold.equalsIgnoreCase("yes")||fold.equalsIgnoreCase("no")));
			round.folding(fold);
			//If the user can open and wants to open then ask for their open bet
			if(human.CanOpen&&(!fold.equalsIgnoreCase("yes")||!fold.equalsIgnoreCase("y"))){
				IO.Output(openBettingPromptToString1());
				//Asks if the player wants to open
				String open;
				do{
					open=getInput();
				}while(!(open.equalsIgnoreCase("y")||open.equalsIgnoreCase("n")||open.equalsIgnoreCase("yes")||open.equalsIgnoreCase("no")));
				if(open.equalsIgnoreCase("yes")||open.equalsIgnoreCase("y")){
					int humanStack=Bank.getPlayerStack(human.Name);
					//Asks the user how much they want to open with
					String betting;
					int openBet;
					do{
						IO.Output(betAmountPromptToString1(humanStack));
						betting =getInput();
						betting=betting.replaceAll("[^\\d]", "");
						openBet=Integer.parseInt(betting);
					}while(openBet>humanStack||openBet<1);
						human.stake+=openBet;
						round.betting(openBet); 
				}
				else{
					round.betting(0);
				}
			}
			else{
				round.betting(0);
			}
			//Compares the hands after betting and declares the winner of the hand
			round.compareHands();
		}
	}
	
	public void gameOver(String winner){
		IO.Output("GAME OVER!!\nThe winner is "+winner+". \nCONGRATULATIONS!!");
	}
	private void gameOver(){
		IO.Output("Sorry You have lost!\nPlease play the Automated Poker Game another time");
	}
	//Allows for this thread to continue after an input is received
	public synchronized void notifyGame(){
		notify();
	}
	//Allows for this thread to wait on input
	@SuppressWarnings("unused")
	public synchronized String getInput(){
		if(console==false){
			InputString="";
			IO.tweetRemainingOutput();
			while(InputString.isEmpty()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				InputString=IO.input();
			}
			System.out.println(InputString);
			return InputString;
		}
		else{
			return IO.input();
		}
	}	
	private String newDealToString1(){
		return "New Deal: ";
	}

	private String stackUpdateToString1(String name, int x){
		return name + " has " + x + " chip(s) in the bank";
	}
	
	private String badDealToString1(){
		return "No one can open the betting! Re-deal";
	}
	private String handToString1(String hand){
		return "You have been dealt the following hand: \n"+ hand;
	}
	
	private String discardPromptToString1(){
		return "Which cards would you like to discard? Enter 1-5 to select cards to discard. Enter 0 if you don't want to discard.";
	}
	
	private String handUpdateToString1(String hand){
		return "Your hand now looks like: \n"+ hand;
	}
	
	private String foldPromptToString1(){
		return "Would you like to fold?";
	}
	
	private String openBettingPromptToString1(){
		return "Would you like to open the betting?";
	}
	
	private String betAmountPromptToString1(int humanStack){
		return "How much would you like to bet? Enter a value greater than 0 and less than "+humanStack;
	}
	private String letsPlayToString(){
		return "Let's play POKER!";
	}
}
