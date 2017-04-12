package poker;

import java.util.ArrayList;
import java.util.Scanner;

public class GameOfPoker {
	private ArrayList<AutomatedPokerPlayer> bots=new ArrayList<AutomatedPokerPlayer>();
	private Scanner input=new Scanner(System.in);
	private HumanPokerPlayer human;
	private DeckOfCards Deck;
	private String[] BotsNames={"Tom","Dick","Harry","Sally"};
	
	GameOfPoker(int numbots, DeckOfCards deck){
		Deck=deck;
		welcome();
		initializebots(numbots);
		playGame();
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
	
	private void playGame(){
		while(!human.isBankrupt()&& bots.size()>0){
			HandOfPoker round = new HandOfPoker(human, bots, Deck);
			for(int i=0;i<bots.size();i++){
				if(bots.get(i).isBankrupt()){
					bots.remove(i);
				}
			}
			System.out.println("New Deal:");
			System.out.println(human.Name+" has "+human.getChipCount()+" chip(s) in the bank");
			for(int x=0;x<bots.size();x++){
				System.out.println(bots.get(x).Name+" has "+human.getChipCount()+" chip(s) in the bank");
			}
			round.dealCards();
			boolean[] names=round.opening();
			for(int i=0;i<bots.size()+1;i++){
				if(i==0){
					if(human.canOpen()){
						System.out.println(human.Name+" says: I can open");
					}
					else{
						System.out.println(human.Name+" says: I cannot open");
					}
				}
				else{
					if(bots.get(i-1).canOpen()){
						System.out.println(bots.get(i-1).Name+" says: I can open");
						
					}
					else{
						System.out.println(bots.get(i-1).Name+" says: I cannot open");
					}
				}
			}
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
			System.out.println("How much would you like to bet? (Enter 0 if you would not like to bet)");
			String betting=input.nextLine();
			int bet=Integer.parseInt(betting);
			round.betting(bet);
			round.compareHands();
		}
	}
	public static void main(String[] args){
		DeckOfCards Deck=new DeckOfCards();
		GameOfPoker game=new GameOfPoker(1,Deck);
		
	}
}
