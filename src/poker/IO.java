package poker;

import java.util.HashMap;
import java.util.Scanner;

import twitter4j.StatusListener;
import twitter4j.TwitterException;

public class IO {
	private int i;
	private String Output="";
	Scanner input=new Scanner(System.in);
	TwitterAPI Twitter=new TwitterAPI();
	HashMap<String, String> UserOutput=new HashMap<String,String>();
	
	IO(int j) {
		i=j;
	}
	
	public String input() {
		if(i==0){
			return input.nextLine();
		}
		else{
			return Twitter.getinput();
				
		}
	}
	public void Output(String output){
		if(i==0){
			System.out.println(outputToString(output));
		}
		else{
			if((UserOutput.get(getUser()).length()+output.length()+Twitter.User.length())< TwitterAPI.MAXCHARSIZE-10){
				String add=UserOutput.get(getUser());
				add+="\n"+output;
				UserOutput.put(getUser(), add);
				
			}
			else{
				try {
					Twitter.updateStatus(UserOutput.get(getUser()));
					UserOutput.put(getUser(), "");
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void tweetRemainingOutput(){
		System.out.println(UserOutput.get(getUser()));
		if(!UserOutput.get(getUser()).isEmpty()){
			try {
				Twitter.updateStatus(UserOutput.get(getUser()));
				UserOutput.put(getUser(), "");
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

	public String outputToString(String output){
		return output;
	}
	public void startStatusListener(){
		Twitter.startListener();
	}
	public boolean foundPlayer(){
		return Twitter.FoundPlayer;
	}
	public int noBots(){
		return Twitter.NoBots;
	}
	public String getUser(){
		return Twitter.getUser();
	}
	public void addListener(StatusListener h){
		Twitter.twitterStream.addListener(h);
	}
	public void notifyListener(){
		Twitter.notifyListener();
	}
	public void setUser(String user){
		UserOutput.put(user, Output);
		Twitter.setUser(user);
	}
}
