package poker;

import java.util.Scanner;

import twitter4j.TwitterException;

public class IO {
	private int i;
	Scanner input=new Scanner(System.in);
	TwitterAPI Twitter=new TwitterAPI();
	IO(int j)throws TwitterException {
		i=j;
	}
	
	public String input(){
		if(i==0){
			return input.nextLine();
		}
		else{
			
		}
		return "";
	}
	public void Ouput(String output) {
		if(i==0){
			System.out.println(outputToString(output));
		}
		else{
			try {
				Twitter.updateStatus(output);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String outputToString(String output){
		return output;
	}
}
