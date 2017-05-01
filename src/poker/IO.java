package poker;

import java.util.HashMap;
import java.util.Scanner;

import twitter4j.StatusListener;
import twitter4j.TwitterException;

//The IO class allows us to either run the program through the conole or Twitter by defining input and output 
//Operations
public class IO {
	private int i;
	private String Output="";
	Scanner input=new Scanner(System.in);
	TwitterAPI Twitter=new TwitterAPI();
	//The hashmap stores all of the different users' output
	HashMap<String, String> UserOutput=new HashMap<String,String>();
	
	IO(int j) {
		i=j;
	}
	
	public String input() {
		//Console
		if(i==0){
			return input.nextLine();
		}
		//Twitter
		else{
			return Twitter.getinput();
		}
	}
	public void Output(String output){
		//Console
		if(i==0){
			System.out.println(outputToString(output));
		}
		//Twitter
		else{
			//Trying to get the character output as close to 140 characters as possible
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
	//Before we receive input we must Tweet remaining output 
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
