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

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hit.algorithms.IAlgoCache;
import com.hit.algorithms.LRUAlgoCacheImpl;
import com.hit.algorithms.NFUAlgoCacheImpl;
import com.hit.algorithms.Random;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;

public class MMUDriver {
	private static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration.json";
	
	public static void main(String[] args){
		CLI cli = new CLI(System.in, System.out);
		new Thread(cli).start();
	}
	
	public MMUDriver() {
		
	}
	
	public static void start(String[] command) throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
		int capacity = Integer.parseInt(command[1]);
		IAlgoCache<Long, Long> algo = createConcreteAlgo(command[0], capacity);
		MemoryManagementUnit mmu = new MemoryManagementUnit(capacity, algo);
		RunConfiguration runConfiguration = readConfigurationFile();
		List<ProcessCycles> processCycles = runConfiguration.getProcessCycles();
		List<Process> processes = createProcesses(processCycles, mmu);
		runProcesses(processes);
		mmu.shutDown();
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
		ExecutorService executer = Executors.newCachedThreadPool();
		Future<Boolean> futures[] = new Future[applications.size()];
		
		for (int i = 0; i < futures.length; i++) {
			futures[i] = executer.submit(applications.get(i));
		}
		
		executer.shutdown();
		for(int i = 0; i < applications.size(); ++i) {
			System.out.println(String.format("process %d : %s", applications.get(i).getId(), futures[i].get()));
		}
	}
	
	private static IAlgoCache<Long, Long> createConcreteAlgo(String algoName, int capacity) {
		
		IAlgoCache<Long, Long> algo = null;
		
		switch (algoName) {
		case "NFU":
			algo = new NFUAlgoCacheImpl<>(capacity);
			break;
		case "LRU":
			algo = new LRUAlgoCacheImpl<>(capacity);
			break;
		case "RANDOM":
			algo = new Random<>(capacity);
			break;
		default:
			break;
		}
		
		return algo;
	}
}

