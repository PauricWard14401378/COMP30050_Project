package poker;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import twitter4j.TwitterException;

public class IO {
	private int i;
	Scanner input=new Scanner(System.in);
	TwitterAPI Twitter=new TwitterAPI();
	String Output="";
	//public long LastMessageID;
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
					Twitter.updateStatus(Output,Twitter.LastMessageID);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Output="";
			}
				System.out.println("Time Started");
				try {
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Made It Out");
				try {
					return Twitter.getInput(Twitter.LastMessageID);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return "failed";
	}
	public void Output(String output){
		if(i==0){
			System.out.println(outputToString(output));
		}
		else{
			if((Output.length()+output.length())<100){
				Output+="\n"+output;
			}
			else{
				try {
					Twitter.updateStatus(Output,Twitter.LastMessageID);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Output=output;
			}
		}
	}
	public void OutputConcatenation(){
		if(Output.length()>100&& Output.length()<120){
			
		try {
			Twitter.updateStatus(Output,Twitter.LastMessageID);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Output="";
		}
		else{
			return;
		}
	}
	
	public String outputToString(String output){
		return output;
	}
}
