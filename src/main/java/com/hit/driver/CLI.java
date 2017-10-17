package com.hit.driver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.hit.view.View;

public class CLI extends Observable implements Runnable,View {
	
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
	public void start() {
		String algoName = null;
		String input = null;
		boolean goodInput = false;
		boolean stop = false;
		String[] command = new String[3];
		command[2]="None";
		
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
					command[2]="exit";
					break;
				}
				
				if(isCorrectAlgoCommand(algoName) && tryParseInt(input.split(" ")[1])) {
					goodInput = true;
					command[0] = algoName.toUpperCase();
					command[1] = input.split(" ")[1].toUpperCase();
				}
			} while (!goodInput);
			
			if(!stop) {
				
				setChanged();
				notifyObservers(command);
			}
		}
		
		setChanged();
		notifyObservers(command);
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


	@Override
	public void run() {
		start();
		
	}
}
