package com.hit.processes;

import java.io.IOException;
import java.util.List;
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
		List<ProcessCycle> cycles = processCycles.getProcessCycles();
		
		for (ProcessCycle processCycle : cycles) {
			Long[] pagesIds = new Long[processCycle.getPages().size()];
			pagesIds = processCycle.getPages().toArray(pagesIds);
			
			try {
				Page<byte[]>[] returnedPages = mmu.getPages(pagesIds);
				
				for (int i = 0; i < returnedPages.length; i++) {
					//it's synchronized to avoid that other thread will interrupt during changing the content
					synchronized (returnedPages[i]) {
						for(int j = 0; j < processCycle.getData().get(i).length; ++j) {
							returnedPages[i].getContent()[j] = processCycle.getData().get(i)[j];
						}
					}
				}
				
				Thread.sleep(processCycle.getSleepMs());
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return false;
			}
		}
		
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
