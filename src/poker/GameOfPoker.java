package poker;

import java.util.ArrayList;
import twitter4j.TwitterException;

public class GameOfPoker extends Thread{
	private static final boolean console = false;
	//Need both a bots arraylist and a humanpokerplayer because you need to distinguish between both human and bot
	private ArrayList<AutomatedPokerPlayer> bots=new ArrayList<AutomatedPokerPlayer>();
	private HumanPokerPlayer human;
	private DeckOfCards Deck;
	private String[] BotsNames={"Tom","Dick","Harry","Sally"};
	private ArrayList<PokerPlayer> Players=new ArrayList<PokerPlayer>();
	private Boolean GameOver=false;
	public static IO IO;
	private Bank Bank=new Bank();
	private int NoBots;
	private String InputString = "";
	
	GameOfPoker(int numbots, DeckOfCards deck, IO io) {
		IO=io;
		Deck=deck;
		NoBots=numbots;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		welcome();
		initializebots(NoBots);
		initializePlayers(human,bots);
		Bank.initializePlayerStacks(Players);
		try {
			playGame();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initializePlayers(HumanPokerPlayer human2, ArrayList<AutomatedPokerPlayer> bots2) {
		Players.clear();
		Players.add(human2);
		for(int i=0;i<bots.size();i++){
			Players.add(bots2.get(i));
		}
	}

	private void welcome(){
		IO.Output("Welcome to the Automated Poker Machine!\nWhat is your name?");
		String humanName="";
		humanName=getInput();
		//String humanName=IO.input();
		human= new HumanPokerPlayer(Deck,humanName,Bank);
		IO.Output(letsPlayToString());
	}
	
	public String letsPlayToString(){
		return "Let's play POKER!";
	}

	private void initializebots(int numbots){
		if(numbots>4||numbots<1){
			throw new ArithmeticException("There has to be at least one bot and at most four. Please enter another number of bots.");
		}
		for(int i=0;i<numbots;i++){
			bots.add(new AutomatedPokerPlayer(Deck, BotsNames[i],Bank));
		}
		
	}
	
	private void playGame() throws TwitterException{
		while(!GameOver){//Need to define when the game is over...i.e when players lose all their chips or human decides to quit
			//Checks if players are bankrupt
			for(int i=0;i<Players.size();i++){
				if(Players.get(i).isBankrupt()){
					Players.remove(i);
				}
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
			//Deals the cards
			round.dealCards();
			//If no one can open then re-deal
			if(!round.opening()){	
				IO.Output(badDealToString1());
				continue;
			}
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
			
			String hand = human.showHand();
			IO.Output(handUpdateToString1(hand));
			IO.Output(foldPromptToString1());
			String fold=getInput();
			round.folding(fold);
			if(human.CanOpen&&!fold.equals("yes")){
				IO.Output(openBettingPromptToString1());
				String open=getInput();
				if(open.equals("yes")){
					IO.Output(betAmountPromptToString1());
					String betting =getInput();
					int openBet=Integer.parseInt(betting);
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
			round.compareHands();
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
			IO.tweetRemainingOutput();
			while(InputString.isEmpty()){
				try {
					System.out.println("Before");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				InputString=IO.input();
			}
			System.out.println("After");
			
			System.out.println(InputString);
			return InputString;
		}
		else{
			return IO.input();
		}
		}
		
		
	public String newDealToString1(){
		return "New Deal: ";
	}

	public String stackUpdateToString1(String name, int x){
		return name + " has " + x + " chip(s) in the bank";
	}
	
	public String badDealToString1(){
		return "No one can open the betting! Re-deal";
	}
	
	
	public String newDealToString(){
		return "New Deal: ";
	}

	public String stackUpdateToString(String name, int x){
		return name + " has " + x + " chip(s) in the bank";
	}
	
	public String badDealToString(){
		return "No one can open the betting! Re-deal";
	}
	
	public String handToString1(String hand){
		return "You have been dealt the following hand: \n"+ hand;
	}
	
	public String discardPromptToString1(){
		return "Which cards would you like to discard? Enter 1-5 to select cards to discard. Enter 0 if you don't want to discard.";
	}
	
	public String handUpdateToString1(String hand){
		return "Your hand now looks like: \n"+ hand;
	}
	
	public String foldPromptToString1(){
		return "Would you like to fold?";
	}
	
	public String openBettingPromptToString1(){
		return "Would you like to open the betting?";
	}
	
	public String betAmountPromptToString1(){
		return "How much would you like to bet?";
	}
	
	
	
	public String handToString(String hand){
		return "You have been dealt the following hand: \n"+ hand;
	}
	
	public String discardPromptToString(){
		return "Which cards would you like to discard? Enter 0 if you don't want to discard.";
	}
	
	public String handUpdateToString(String hand){
		return "Your hand now looks like: \n"+ hand;
	}
	
	public String foldPromptToString(){
		return "Would you like to fold?";
	}
	
	public String openBettingPromptToString(){
		return "Would you like to open the betting?";
	}
	
	public String betAmountPromptToString(){
		return "How much would you like to bet?";
	}
	


	
	
	
}
