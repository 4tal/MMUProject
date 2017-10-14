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
		this.logger = MMULogger.getInstance();
	}
	
	@Override
	public Boolean call() throws Exception {
		try {
			for (ProcessCycle cycle : processCycles.getProcessCycles()) {
				Object pagesObject[] = cycle.getPages().toArray();
				Long[] pagesIds = Arrays.copyOf(pagesObject, pagesObject.length, Long[].class);
				Page<byte[]>[] pages = this.mmu.getPages(pagesIds);
				
				//logger.write("Suppose to write somthing elese - In process\r\n", Level.INFO);
				//Maybe need to remove this 2 logger.write (up and down)
				for (int i = 0; i < pages.length; i++) {
					//logger.write("GP:P"+this.id+" "+pages[i].getPageId() +" "+ Arrays.toString(processCycle.getData().toArray()) + "\r\n", Level.INFO);
					pages[i].setContent(cycle.getData().get(i));
					MMULogger.getInstance().write("GP:p"+this.getId()+" "+pages[i].getPageId()+" "+Arrays.toString(pages[i].getContent()) +"\n", Level.INFO);
				}
				Thread.sleep(cycle.getSleepMs());
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
