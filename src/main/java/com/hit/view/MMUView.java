package com.hit.view;

import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JTable;

public class MMUView extends Observable implements View {
	JFrame frmMmuSimulator;
	private JTable table_1;
	boolean notInit = true;
	String[] commandsToExe=null;
	
	

	public MMUView(){
		System.out.println("View Ctor started");
		//System.out.println("View Started");
		//need to make this command to active:
		notInit=false;
	}
	
	private void createAndShowGUI(){
		System.out.println("Window is up");
	}
	
	public void initialized(String[] inputCommands){
		setCommandsToExe(inputCommands);
		openWindow();
	}
	
	public void setCommandsToExe(String[] commandsToExe) {
		this.commandsToExe = commandsToExe;
	}

	private void openWindow() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				createAndShowGUI();
			}

			
		});
	}
	
	@Override
	public void start() {
		openWindow();
		
	}

}
