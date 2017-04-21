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
	TwitterAPI Twitter;
	
	
	GameOfPoker(int numbots, DeckOfCards deck,TwitterAPI twitter) throws TwitterException{
		Deck=deck;
		Twitter=twitter;
		welcome();
		initializebots(numbots);
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
		System.out.println("Welcome to the Automated Poker Machine!\nWhat is your name?");
		String humanName=input.nextLine();
		human= new HumanPokerPlayer(Deck,humanName);
		System.out.println("Let's play POKER!");
	}

	private void initializebots(int numbots){
		if(numbots>4||numbots<1){
			throw new ArithmeticException("There has to be at least one bot and at most four. Please enter another number of bots.");
		}
		for(int i=0;i<numbots;i++){
			bots.add(new AutomatedPokerPlayer(Deck, BotsNames[i]));
		}
		
	}
	
	private void playGame() throws TwitterException{
		while(!human.isBankrupt()&& bots.size()>0){
			for(int i=0;i<bots.size();i++){
				if(bots.get(i).isBankrupt()){
					Players.remove(i+1);
				}
			}
			Deck.reset();
			HandOfPoker round = new HandOfPoker(human, bots, Deck);
			initializePlayers(human,bots);
			System.out.println("New Deal:");
			for(int x=0;x<Players.size();x++){
				System.out.println(Players.get(x).Name+" has "+Players.get(x).getChipCount()+" chip(s) in the bank");
			}
			round.dealCards();
			//If no one can open then re-deal
			if(!round.opening()){
				System.out.println("No one can open the betting! Re-deal");
				continue;
			}
			System.out.println("You have been dealt the following hand: \n"+human.showHand());
			System.out.println("Which cards would you like to discard? Enter 0 if you don't want to discard.");
			String cards=input.nextLine();
			if(!cards.contains("0")){
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
			if(human.canOpen()){
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
		TwitterAPI twitter=new TwitterAPI();
		DeckOfCards Deck=new DeckOfCards();
		@SuppressWarnings("unused")
		GameOfPoker game=new GameOfPoker(3,Deck,twitter);
		
	}
}
