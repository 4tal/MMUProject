package com.hit.processes;

import java.util.concurrent.Callable;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;

public class Process implements Callable<Boolean> {

	private int id;
	private MemoryManagementUnit mmu;
	private ProcessCycles processCycles;
	
	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles) {
		setId(id);
		this.mmu = mmu;
		this.processCycles = processCycles;
	}
	
	@Override
	public Boolean call() throws Exception {
		try {
			for(ProcessCycle processCycle : processCycles.getProcessCycles()) {
				Long[] ids = (Long[])processCycle.getPages().toArray();
				Page<byte[]>[] pages = mmu.getPages(ids);
				
				for (int i = 0; i < pages.length; i++) {
					pages[i].setContent(processCycle.getData().get(i));
				}
				
				Thread.sleep(processCycle.getSleepMs());
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
