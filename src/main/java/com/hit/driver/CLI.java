package com.hit.driver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI extends Object implements Runnable {
	
	static String LFU="LFU";
	static String LRU="LRU";
	static String RANDOM="RANDOM";
	static String START="START";
	static String STOP="STOP";
	
	
	
	private Scanner in;
	private PrintWriter out;
	private OutputStreamWriter outputToUserWR;
	
	
	public CLI(InputStream in, OutputStream out)
	{
		this.in = new Scanner(in);
		this.out = new PrintWriter(out);
	}
	
	
	@Override
	public void run() {
		boolean programRunning=true;
		
		
		String input = in.nextLine();
		while(!(input.toUpperCase().matches(START)))
		{
			//Case input is not valid.
			write("Not a valid command");
			input = in.nextLine();
		}
		
		write("Please enter required algorithm and RAM capacity");
		input = in.nextLine();
		
		while(programRunning){
			while(!((input.toUpperCase().matches("(LRU|NFU|RANDOM)\\s\\d+")) || (input.matches(STOP))))
			{
				//Case input is not valid.
				write("Not a valid command");
				input = in.nextLine();
			}
			
			if(input.matches("STOP")){
				//Case user want to quit
				//Need to add exit logic
				write("Stop");
				programRunning=false;
			}
			
			//Logic for 
			write("Not stop");
			input = in.nextLine();
		}
		
	}
	
	public void write(String string)
	{
		out.println(string);
		out.flush();
	}
	
}
