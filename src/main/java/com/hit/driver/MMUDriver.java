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
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hit.algorithms.IAlgoCache;
import com.hit.algorithms.LRUAlgoCacheImpl;
import com.hit.algorithms.NFUAlgoCacheImpl;
import com.hit.algorithms.Random;
import com.hit.controller.MMUController;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.model.MMUModel;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;
import com.hit.view.MMUView;

public class MMUDriver {
	
	
	private static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration.json";
	
	public static void main(String[] args){
		CLI cli = new CLI(System.in, System.out);
		MMUModel model=new MMUModel();
		MMUView view=new MMUView();
		MMUController controller=new MMUController(model, view);
		
		model.addObserver(controller);	
		cli.addObserver(controller);
		view.addObserver(controller);
			
		new Thread(cli).start();
		
	}
	
	public MMUDriver() {
		
	}

	public static List<Process> createProcesses(List<ProcessCycles> appliocationsScenarios, MemoryManagementUnit mmu) {
		List<Process> processList = new ArrayList<>();
		int id = 0;
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
		
		
		//Close the Executer.
		executer.shutdown();
		for(int i = 0; i < applications.size(); ++i) {
			System.out.println(String.format("process %d : %s", applications.get(i).getId(), futures[i].get()));
		}
	}
	

}

