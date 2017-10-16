package com.hit.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JTable;

public class MMUView extends Observable implements View {
	private static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration.json";
	JFrame frmMmuSimulator;
	private JTable table_1;
	private List<String> rowsFromLog=null;
	private int pageFaults;
	private int pageReplacements;
	private int capacitySize;
	private int numberOfProcesses;
	private HashSet activeProcesses=null;
	private ArrayList freeColumns=null;
	
	

	public MMUView(){
		System.out.println("View Ctor started");

	}
	
	private void createAndShowGUI(){
		System.out.println("Window is up");
	}
	
	public void setParameters(Object arg1) {
		rowsFromLog=(List<String>) arg1;
		setPageFaults(0);
		setPageReplacements(0);
		setCapacitySize(Character.getNumericValue((rowsFromLog.get(0).charAt(3))));
		setNumberOfProcesses(Character.getNumericValue((rowsFromLog.get(1).charAt(3))));
		activeProcesses=new HashSet();
		freeColumns=new ArrayList();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public void initialized(){
		openWindow();
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

	public int getPageFaults() {
		return pageFaults;
	}

	public void setPageFaults(int pageFaults) {
		this.pageFaults = pageFaults;
	}

	public int getPageReplacements() {
		return pageReplacements;
	}

	public void setPageReplacements(int pageReplacements) {
		this.pageReplacements = pageReplacements;
	}

	public int getCapacitySize() {
		return capacitySize;
	}

	public void setCapacitySize(int capacitySize) {
		this.capacitySize = capacitySize;
	}

	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}

	public void setNumberOfProcesses(int numberOfProcesses) {
		this.numberOfProcesses = numberOfProcesses;
	}

	

	

}
