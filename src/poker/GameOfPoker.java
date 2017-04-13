package poker;

import java.util.ArrayList;
import java.util.Scanner;

import twitter4j.TwitterException;

public class GameOfPoker {
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
		
		Players.add(human2);
		for(int i=0;i<bots.size();i++){
			Players.add(bots2.get(i));
		}
		System.out.println(Players.get(0).Name);
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
			HandOfPoker round = new HandOfPoker(human, bots, Deck);
			initializePlayers(human,bots);
			for(int i=0;i<bots.size();i++){
				if(bots.get(i).isBankrupt()){
					bots.remove(i);
				}
			}
			System.out.println("New Deal:");
			System.out.println(human.Name+" has "+human.getChipCount()+" chip(s) in the bank");
			for(int x=0;x<bots.size();x++){
				System.out.println(bots.get(x).Name+" has "+bots.get(x).getChipCount()+" chip(s) in the bank");
			}
			round.dealCards();
			round.opening();
			System.out.println("You have been dealt the following hand: \n"+human.showHand());
			System.out.println("Which cards would you like to discard?");
			String cards=input.nextLine();
			int[] discardcards = new int[cards.length()];
			for(int x=0;x<cards.length();x++){
				discardcards[x]=Character.getNumericValue(cards.charAt(x));
			}
			round.discardCards(discardcards);
			System.out.println("Your hand now looks like: \n"+human.showHand());
			System.out.println("Would you like to fold?");
			String fold=input.nextLine();
			round.folding(fold);
			if(human.canOpen()){
				System.out.println("Would you like to open the betting?");
				String open=input.nextLine();
				System.out.println(open);
				if(open.equals("yes")){
					System.out.println("How much would you like to bet?");
					String betting =input.nextLine();
					int openBet=Integer.parseInt(betting);
					round.betting(openBet); 
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
		GameOfPoker game=new GameOfPoker(2,Deck,twitter);
		
	}
}
