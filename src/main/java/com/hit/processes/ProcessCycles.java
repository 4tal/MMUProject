package com.hit.processes;

import java.util.List;

import com.hit.memoryunits.MemoryManagementUnit;

public class ProcessCycles {
	private List<ProcessCycle> processCycles;

	public ProcessCycles(List<ProcessCycle> processCycles) {
		setProcessCycles(processCycles);
	}

	//we have to override this method, have to fix implementation
	@Override
	public String toString() {
		return processCycles.toString();
	}

	public List<ProcessCycle> getProcessCycles() {
		return processCycles;
	}

	public void setProcessCycles(List<ProcessCycle> processCycles) {
		this.processCycles = processCycles;
	}
}
