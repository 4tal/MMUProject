package com.hit.driver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class CLI extends Object implements Runnable {
	
	static String LFU="LFU";
	static String LRU="LRU";
	static String RANDOM="RANDOM";
	static String START="START";
	static String STOP="STOP";
	
	
	
	private InputStream inputFromUser;
	private OutputStream outputToUser;
	private OutputStreamWriter outputToUserWR;
	
	
	public CLI(InputStream in,
	           OutputStream out)
	{
		inputFromUser=in;
		outputToUser=out;
	}
	
	
	@Override
	public void run() {
		boolean programRunning=true;
		Scanner reader=new Scanner(System.in);
		
		String input =reader.nextLine();
		while(!(input.matches(START)))
		{
			//Case input is not valid.
			System.out.println("Not a valid command");
			input =reader.nextLine();
		}
		
		System.out.println("Please enter required algorithm and RAM capacity");
		input =reader.nextLine();
		
		while(programRunning){
			while(!((input.matches("(LRU|NFU|RANDOM)\\s\\d+")) || (input.matches(STOP))))
			{
				//Case input is not valid.
				System.out.println("Not a valid command");
				input =reader.nextLine();
			}
			
			if(input.matches("STOP")){
				//Case user want to quit
				//Need to add exit logic
				System.out.println("Stop");
				programRunning=false;
			}
			
			//Logic for 
			System.out.println("Not stop");
			input =reader.nextLine();
		}
		
		
		// TODO Auto-generated method stub
		
	}
	
	public void write(java.lang.String string)
	{
		
	}
	
}
