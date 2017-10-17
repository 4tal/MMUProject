package com.hit.processes;

import java.util.List;

public class ProcessCycles {
	private List<ProcessCycle> processCycles;

	/**
	 * 
	 * @param processCycles List of the ProcessCylce to be stored in ProcessCycles
	 */
	public ProcessCycles(List<ProcessCycle> processCycles) {
		setProcessCycles(processCycles);
	}

	@Override
	public String toString() {
		return processCycles.toString();
	}

	/**
	 * 
	 * @return the list of the ProcessCycle
	 */
	public List<ProcessCycle> getProcessCycles() {
		return processCycles;
	}

	/**
	 * 
	 * @param processCycles setter for the List of ProcessCycle
	 */
	public void setProcessCycles(List<ProcessCycle> processCycles) {
		this.processCycles = processCycles;
	}
}
