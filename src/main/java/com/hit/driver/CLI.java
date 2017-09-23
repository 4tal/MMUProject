package com.hit.driver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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
		String algoName = null;
		String input = null;
		boolean goodInput = false;
		boolean stop = false;
		String[] command = new String[2];
		
		write("Please type 'start' to start");
		input = in.nextLine();
		
		while(!input.toUpperCase().equals(STOP)) {
			while(!input.toUpperCase().equals(START)) {
				if(input.toUpperCase().equals(STOP)) {
					goodInput = true;
					break;
				}
				
				if(goodInput) {
					break;
				}
				
				write("Invalid commant, please type 'start' to start");
				input = in.nextLine();
			}
			
			do {
				write("Please enter required algorithm and RAM capacity");
				input = in.nextLine();
				algoName = input.split(" ")[0];
				if(input.toUpperCase().equals(STOP)) {
					stop = true;
					break;
				}
				
				if(isCorrectAlgoCommand(algoName) && tryParseInt(input.split(" ")[1])) {
					goodInput = true;
					command[0] = algoName.toUpperCase();
					command[1] = input.split(" ")[1].toUpperCase();
				}
			} while (!goodInput);
			
			if(!stop) {
				try {
					MMUDriver.start(command);
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
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
	
	private boolean isCorrectAlgoCommand(String algoName) {
		boolean success = false;
		algoName = algoName.toUpperCase();
		
		if(algoName.equals(NFU) || algoName.equals(LRU) || algoName.equals(RANDOM)) {
			success = true;
		}
		
		return success;
	}
}
