package poker;

import java.util.Scanner;
import twitter4j.TwitterException;

public class IO {
	private int i;
	private String Output="";
	Scanner input=new Scanner(System.in);
	TwitterAPI Twitter=new TwitterAPI();
	
	
	IO(int j)throws TwitterException {
		i=j;
	}
	
	public String input() {
		if(i==0){
			return input.nextLine();
		}
		else{
			if(!Output.isEmpty()){
				try {
					Twitter.updateStatus(Output);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				Output="";
			}
			
			return Twitter.getinput();
				
		}
	}
	public void Output(String output){
		if(i==0){
			System.out.println(outputToString(output));
		}
		else{
			if((Output.length()+output.length()+Twitter.User.length())< TwitterAPI.MAXCHARSIZE-5){
				Output+="\n"+output;
			}
			else{
				try {
					Twitter.updateStatus(Output);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				Output=output;
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
}
