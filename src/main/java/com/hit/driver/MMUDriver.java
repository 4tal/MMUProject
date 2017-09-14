package com.hit.driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hit.algorithms.IAlgoCache;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.*;
import com.hit.processes.Process;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class MMUDriver {
	private static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration.json";
	private static final String ExecutorService = null;

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
		
		public static List<Process> createProcesses(List<ProcessCycles> appliocationsScenarios, MemoryManagementUnit mmu) {
			List<Process> processList = new ArrayList<>();
			int id = 1;
			
			for(ProcessCycles processCycles : appliocationsScenarios) {
				id++;
				processList.add(new Process(id, mmu, processCycles));
			}
			
			return processList;
		}

		
		public static RunConfiguration readConfigurationFile() {
			RunConfiguration runConfiguration = null;
			FileReader fileReader = null;
			
			try {
				fileReader = new FileReader(CONFIG_FILE);
				Gson gson = new Gson();
				runConfiguration = gson.fromJson(new JsonReader(fileReader), RunConfiguration.class);
			} catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
				System.out.println(e.getMessage());
			}
			
			return runConfiguration;
		}
		
		public static void runProcesses(List<Process> applications) throws InterruptedException, ExecutionException {
			ExecutorService executor = Executors.newCachedThreadPool();
			
			@SuppressWarnings("unchecked")
			Future<Boolean> futures[] = new Future[applications.size()];
			for (int i=0; i<applications.size(); i++){
				futures[i] = executor.submit(applications.get(i));
			}
			executor.shutdown();
			for (int i=0; i<applications.size(); i++){
				System.out.printf("process %d: %s",applications.get(i).getId(),futures[i].get());
			}
		}
}
