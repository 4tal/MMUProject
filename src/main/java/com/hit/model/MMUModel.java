package com.hit.model;

public class MMUModel extends java.util.Observable implements Model {
	public int numProcesses;
	public int ramCapacity;
	private String json;
	
	public MMUModel(String configuration){
		//Add the logic from MMUDriver
		//The CLI need to transfer the data from the view to the mode through the Controller
		setConfiguration(configuration);
	}
	
	public void setConfiguration(String configuration){
		json=configuration;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

}
