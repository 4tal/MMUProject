package com.hit.processes;

import java.util.List;

import com.hit.memoryunits.MemoryManagementUnit;

public class ProcessCycles {
	private List<ProcessCycle> processCycles;

	public ProcessCycles(List<ProcessCycle> processCycles) {
		setProcessCycles(processCycles);
	}

	@Override
	public String toString() {
		return "ProcessCycles [processCycles=" + processCycles + "]";
	}

	public List<ProcessCycle> getProcessCycles() {
		return processCycles;
	}

	public void setProcessCycles(List<ProcessCycle> processCycles) {
		this.processCycles = processCycles;
	}
}
