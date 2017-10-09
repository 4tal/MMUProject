package com.hit.processes;



import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

public class Process implements Callable<Boolean> {

	private int id;
	private MemoryManagementUnit mmu;
	private ProcessCycles processCycles;
	private MMULogger logger = MMULogger.getInstance();
	
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
				
				logger.write("Suppose to write somthing elese - In process\r\n", Level.INFO);
				
				for (int i = 0; i < pages.length; i++) {
					logger.write("GP:P"+this.id+" "+pages[i].getPageId() +" "+ Arrays.toString(processCycle.getData().toArray()) + "\r\n", Level.INFO);
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
