package com.hit.view;

import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JTable;

public class MMUView extends Observable implements View {
	JFrame frmMmuSimulator;
	private JTable table_1;
	boolean notInit = true;
	
	public MMUView(){
		System.out.println("View Ctor started");
		//System.out.println("View Started");
		//need to make this command to active:
		notInit=false;
		start();
	}
	
	private void createAndShowGUI(){
		
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
		System.out.println("View start started");
		// TODO Auto-generated method stub
		
	}

}
