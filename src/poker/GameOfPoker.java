package poker;

import java.util.ArrayList;
import java.util.Scanner;

import twitter4j.TwitterException;

public class GameOfPoker {
	//Need both a bots arraylist and a humanpokerplayer because you need to distinguish between both human and bot
	private ArrayList<AutomatedPokerPlayer> bots=new ArrayList<AutomatedPokerPlayer>();
	private Scanner input=new Scanner(System.in);
	private HumanPokerPlayer human;
	private DeckOfCards Deck;
	private String[] BotsNames={"Tom","Dick","Harry","Sally"};
	private ArrayList<PokerPlayer> Players=new ArrayList<PokerPlayer>();
	private Boolean GameOver=false;
	public IO IO=new IO(0);
	public Bank Bank=new Bank();
	
	
	GameOfPoker(int numbots, DeckOfCards deck) throws TwitterException{
		
		Deck=deck;
		welcome();
		initializebots(numbots);
		initializePlayers(human,bots);
		Bank.initializePlayerStacks(Players);
		playGame();
	}
	

	private void initializePlayers(HumanPokerPlayer human2, ArrayList<AutomatedPokerPlayer> bots2) {
		Players.clear();
		Players.add(human2);
		for(int i=0;i<bots.size();i++){
			Players.add(bots2.get(i));
		}
	}

	private void welcome() {
		IO.Ouput("Welcome to the Automated Poker Machine!\nWhat is your name?");
		String humanName=input.nextLine();
		human= new HumanPokerPlayer(Deck,humanName,Bank);
		System.out.println("Let's play POKER!");
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
			System.out.println("New Deal:");
			//Shows how many chips each player has
			for(int x=0;x<Players.size();x++){
				System.out.println(Players.get(x).Name+" has "+Bank.getPlayerStack(Players.get(x).Name)+" chip(s) in the bank");
			}
			//Deals the cards
			round.dealCards();
			//If no one can open then re-deal
			if(!round.opening()){
				System.out.println("No one can open the betting! Re-deal");
				continue;
			}
			System.out.println("You have been dealt the following hand: \n"+human.showHand());
			//Handles Discarding
			System.out.println("Which cards would you like to discard? Enter 0 if you don't want to discard.");
			String cards=input.nextLine();
			if(! cards.contains("0")){
				Integer[] discardcards = new Integer[cards.length()];
				for(int x=0;x<cards.length();x++){
					discardcards[x]=Character.getNumericValue(cards.charAt(x));
				}
				round.discardCards(discardcards);
			}
			System.out.println("Your hand now looks like: \n"+human.showHand());
			System.out.println("Would you like to fold?");
			String fold=input.nextLine();
			round.folding(fold);
			if(human.CanOpen&&!fold.equals("yes")){
				System.out.println("Would you like to open the betting?");
				String open=input.nextLine();
				if(open.equals("yes")){
					System.out.println("How much would you like to bet?");
					String betting =input.nextLine();
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
	public static void main(String[] args) throws TwitterException{
		DeckOfCards Deck=new DeckOfCards();
		TwitterAPI twitter=new TwitterAPI();
		long ID=twitter.searchForTweets("#DealMeInBurnNTurn");
		System.out.println(ID);
		twitter.updateStatus("Done", ID);
		if(twitter.FoundPlayer){
			GameOfPoker game=new GameOfPoker(twitter.NoBots,Deck);
		}
		
		
	}
}
