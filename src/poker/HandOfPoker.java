package poker;

import java.util.ArrayList;
import java.util.Scanner;

public class HandOfPoker {
	private HumanPokerPlayer Human;
	private ArrayList<AutomatedPokerPlayer> Bots=new ArrayList<AutomatedPokerPlayer>();
	private ArrayList<PokerPlayer> Players=new ArrayList<PokerPlayer>();
	private DeckOfCards Deck;
	private Scanner input=new Scanner(System.in);
	
	HandOfPoker(HumanPokerPlayer human, ArrayList<AutomatedPokerPlayer> bots, DeckOfCards deck){
		Human=human;
		Bots=bots;
		Deck=deck;
		initializePlayers(human,bots);
		dealCards();
	}
	

	private void initializePlayers(HumanPokerPlayer human2, ArrayList<AutomatedPokerPlayer> bots2) {
			Players.add(human2);
			for(int i=0;i<bots2.size();i++){
				Players.add(bots2.get(i));
			}
	}


	public void dealCards() {
		for(int i=0; i<Players.size();i++){
			Players.get(i).dealHand(Deck);
			if(i<Players.size()&&i!=0){
				Bots.get(i-1).updatePercentages();
			}
		}
	}

	public boolean opening() {
		boolean canOpen=false;
		for(int i=0;i<Players.size();i++){
			if(Players.get(i).canOpen()){
				System.out.println(Players.get(i).Name+" says: I can open");
				canOpen=true;
			}
			else{
				System.out.println(Players.get(i).Name+" says: I cannot open");
			}
		}
		return canOpen;
	}
	public void discardCards(Integer[] numcards) {
		Human.discard(numcards);
		for(int i=0; i<Bots.size();i++){
			Bots.get(i).discard();
		}
	}
	public void betting(int amount){
		boolean raised=false;
		boolean firstSkipped=false;
		int currentStake=amount;
		do{
			raised=false;
			for(int i=0; i<Players.size();i++){
				if(i==0&&firstSkipped==false){
					firstSkipped=true;
					continue;
				}
				if(Players.get(i).call(currentStake)){
					currentStake=Players.get(i).stake;
					raised=true;
					if(Players.get(i).raise()){
						currentStake++;
						raised=true;
					}
				}
				else{
					System.out.println(Players.get(i).Name+" says: I fold!");
					Players.get(i).fold();
					Players.remove(i);
				}
			}
		}while(raised==true);
	}
	public void folding(String fold) {
		if(fold.equals("yes")){
			Human.fold();
		}
	}
	public void compareHands(){
		int j=0;
		for(int i=0; i<Players.size()-1 ;i++){
			if(Players.get(j).Hand.getGamevalue()>Players.get(i+1).Hand.getGamevalue()){
				Players.get(i+1).fold();
				j=i;
			}
			else{
				Players.get(i).fold();
				j=i+1;
			}
		}
		System.out.println(Players.get(j).Name+" Wins!!");        
	}
	
}
