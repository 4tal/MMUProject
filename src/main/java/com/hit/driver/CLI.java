package com.hit.driver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI extends Object implements Runnable {
	
	private static final String NFU = "NFU";
	private static final String LRU = "LRU";
	private static final String RANDOM = "RANDOM";
	private static final String START = "START";
	private static final String STOP = "STOP";
	
	
	
	private Scanner in;
	private PrintWriter out;	
	
	public CLI(InputStream in, OutputStream out) {
		this.in = new Scanner(in);
		this.out = new PrintWriter(out);
	}
	
	
	@Override
	public void run() {
		String algoType = null;
		String input = null;
		boolean goodInput = false;
		String[] command = new String[2];
		
		write("Please type 'start' to start");
		input = in.nextLine();
		
		while(!input.toUpperCase().matches(STOP)) {
			while(!input.toUpperCase().matches(START)) {
				write("Invalid commant, please type 'start' to start");
				input = in.nextLine();
			}
			
			do {
				write("Please enter required algorithm and RAM capacity");
				input = in.nextLine();
				algoType = input.split(" ")[0];
				if(algoType.toUpperCase().matches("(LRU|NFU|RANDOM)\\s\\d+") && tryParseInt(input.split(" ")[1])) {
					goodInput = true;
					command[0] = algoType;
					command[1] = input.split(" ")[1];
				}
			} while (!goodInput);
			
			MMUDriver.start(command);
		}
		
		write("Thank you");
		in.close();
		out.close();		
	}
	
	private boolean tryParseInt(String source) {
		try {
			Integer.parseInt(source);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public void write(String string)
	{
		out.println(string);
		out.flush();
	}
	
}
