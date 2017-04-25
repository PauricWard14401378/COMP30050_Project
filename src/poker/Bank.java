package poker;

import java.util.ArrayList;
import java.util.HashMap;

public class Bank {
	private static final int BANKTOTAL= 50;
	private ArrayList<PokerPlayer> Players;
	private int Pot=0;
	public HashMap<String,Integer> playerStacks=new HashMap<String,Integer>();
	Bank(){
		
	}
	public void initializePlayerStacks(ArrayList<PokerPlayer> players){
		Players=players;
		for(int i=0;i<Players.size();i++){
			playerStacks.put(Players.get(i).Name, (BANKTOTAL/Players.size()));
		}
	}
	public void addToBank(String name,int amount){
		amount+=playerStacks.get(name);
		playerStacks.put(name, amount);
	}
	public void removeFromBank(String name, int amount){
		Pot+=amount;
		amount=Math.abs(amount-playerStacks.get(name));
		playerStacks.put(name, amount);
	}
	public int getPlayerStack(String name){
		return playerStacks.get(name);
	}
	public int getPot(){
		return Pot;
	}
	public void resetPot(){
		Pot=0;
	}
}
