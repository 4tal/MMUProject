package com.hit.processes;

import java.util.List;

public class RunConfiguration {
	private List<ProcessCycle> processCtcles;
	
	public RunConfiguration(List<ProcessCycles> processesCycles) {
		setProcessCtcles(processCtcles);
	}

	public List<ProcessCycle> getProcessCtcles() {
		return processCtcles;
	}

	public void setProcessCtcles(List<ProcessCycle> processCtcles) {
		this.processCtcles = processCtcles;
	}

	
	//We need to override it.
	public List<ProcessCycles> getProcessesCycles() {
		// TODO Auto-generated method stub
		return null;
	}
}
