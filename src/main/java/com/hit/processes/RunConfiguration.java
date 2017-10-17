package com.hit.processes;

import java.util.List;

public class RunConfiguration {
	private List<ProcessCycles> processesCycles;

	/**
	 * 
	 * @param processCycles The List of the ProcessCycles
	 */
	public RunConfiguration(List<ProcessCycles> processCycles) {
		super();
		this.processesCycles = processCycles;
	}

	/**
	 * 
	 * @return getter for the ProcessCycles that store in RunConfiguration
	 */
	public List<ProcessCycles> getProcessCycles() {
		return processesCycles;
	}

	/**
	 * 
	 * @param processCycles set the ProcessCycles List in RunConfiguration
	 */
	public void setProcessCycles(List<ProcessCycles> processCycles) {
		this.processesCycles = processCycles;
	}

	@Override
	public String toString() {
		return processesCycles.toString();
	}
}
