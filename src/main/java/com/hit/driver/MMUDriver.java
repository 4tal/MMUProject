package com.hit.driver;

import java.util.List;

import com.hit.algorithms.IAlgoCache;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;

public class MMUDriver {

	public static void main(String[] args){
		CLI cli = new CLI(System.in, System.out);
		new Thread(cli).start();
		}
	
	
	
		public static void start(String[] command){
		IAlgoCache<Long, Long> algo = null;
		int capacity = 0;
		
		//How to init that one to the right algo?
		//algo=ne
		
		/**
		* Initialize capacity and algo
		*/
		/**
		
		 * Build MMU and initial RAM (content)
		 */
		
		
		/*MemoryManagementUnit mmu = new MemoryManagementUnit(capacity, algo);
		 RunConfiguration runConfig = readConfigurationFile();
		 List<ProcessCycles> processCycles = runConfig.getProcessesCycles();
		 List<Process> processes = createProcesses(processCycles, mmu);
		 runProcesses(processes);
		*/
		}


}
