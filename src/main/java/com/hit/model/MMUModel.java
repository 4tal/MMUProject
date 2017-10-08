package com.hit.model;

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
import com.hit.driver.MMUDriver;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.processes.Process;

public class MMUModel extends java.util.Observable implements Model {
	public int numProcesses;
	public int ramCapacity;
	private String filePath;
	//private String[] command;
	private int capacityForAlgo;
	private IAlgoCache<Long, Long> algoToExe=null;
	private MemoryManagementUnit mmu=null;
	RunConfiguration runConfiguration=null;
	List<ProcessCycles> processCycles=null;
	List<Process> processes=null;
	List<Process> processList=null;
	
	
	public MMUModel(String inputFilePath){
		filePath=inputFilePath;
		configure();

	}
	private void configure(){
		try {
			runConfiguration = readConfigurationFile();
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setCommands(String[] inputCommand){
		capacityForAlgo=Integer.parseInt(inputCommand[1]);
		algoToExe=createConcreteAlgo(inputCommand[0],capacityForAlgo);
	}
	
	private IAlgoCache<Long, Long> createConcreteAlgo(String algoName, int capacity) {
		switch (algoName) {
		case "NFU":
			algoToExe = new NFUAlgoCacheImpl<>(capacity);
			break;
		case "LRU":
			algoToExe = new LRUAlgoCacheImpl<>(capacity);
			break;
		case "RANDOM":
			algoToExe = new Random<>(capacity);
			break;
		default:
			break;
		}
		
		return algoToExe;
	}
	
	
	//Changed the throws to different kind of Exceptions.
	public RunConfiguration readConfigurationFile() throws FileNotFoundException,JsonIOException,JsonSyntaxException {
		//System.out.println("111");
		RunConfiguration runConfiguration = null;
		FileReader fileReader = null;
	//	System.out.println("222");
		try {
		//	System.out.println("223");
			fileReader = new FileReader(filePath);
		//	System.out.println("224");
			Gson gson = new Gson();
			//System.out.println("225");
			runConfiguration = gson.fromJson(new JsonReader(fileReader), RunConfiguration.class);
			//System.out.println("226");
		} catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
			System.out.println(e.getMessage());
		}
		//System.out.println("333");
		return runConfiguration;
	}
	
	
	public List<Process> createProcesses(List<ProcessCycles> appliocationsScenarios, MemoryManagementUnit mmu) {
		List<Process> processList = new ArrayList<>();
		int id = 1;
		for(ProcessCycles processCycles : appliocationsScenarios) {
			id++;
			processList.add(new Process(id, mmu, processCycles));
		}
			
		return processList;
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
	
	
	@Override
	public void start(){
		//System.out.println("11");
		
		
			
		
	//	catch(FileNotFoundException | JsonIOException | JsonSyntaxException e){
	//		System.out.println("Problem in MMU model start");
	//	}
		//System.out.println("22");
		mmu = new MemoryManagementUnit(capacityForAlgo, algoToExe);
		//System.out.println("33");
		processCycles = runConfiguration.getProcessCycles();
		processes = createProcesses(processCycles, mmu);
		
		try {
			runProcesses(processes);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mmu.shutDown();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("33");
		
	}
	
	

}

