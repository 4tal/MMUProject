package com.hit.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import com.hit.algorithms.IAlgoCache;
import com.hit.algorithms.LRUAlgoCacheImpl;
import com.hit.algorithms.NFUAlgoCacheImpl;
import com.hit.algorithms.Random;
import com.hit.driver.MMUDriver;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;


public class MMUModel extends Observable implements Model{
	private int capacityForAlgo;
	private IAlgoCache<Long, Long> algoToExe=null;
	private static MMULogger logger = MMULogger.getInstance();
	private List<String> rowsFromLog;
	private final String FILE_LOCATION= "logs/log.txt";
	private int currentLogPlace=0;
	
	
	public MMUModel(){
		//MMUDriver.start();
		rowsFromLog=new ArrayList<>();
		
	}
	
	public void setCommands(String[] inputCommand){
		capacityForAlgo = Integer.parseInt(inputCommand[1]);
		algoToExe=createConcreteAlgo(inputCommand[0],capacityForAlgo);
	}
	
	//For the start methods:
	
	//readNextLine()
	
	private void readAllLog(){
		
		try {
			rowsFromLog.addAll(Files.readAllLines(Paths.get(FILE_LOCATION)));
			for(int i=0;i<rowsFromLog.size();i++){
				/*
				if(dataFromLog.get(i).contains("RC:")){
					//Fix                                       ---------------------------------------
					//capacityForAlgo=Integer.parseInt((dataFromLog.get(i).charAt(3)));
				}
				//PageNumber;
				if(dataFromLog.get(i).contains("RC:")){
					//Fix                                       ---------------------------------------
					//capacityForAlgo=Integer.parseInt((dataFromLog.get(i).charAt(3)));
				}
				//Processes;
				if(dataFromLog.get(i).contains("RC:")){
					//Fix                                       ---------------------------------------
					//capacityForAlgo=Integer.parseInt((dataFromLog.get(i).charAt(3)));
				}
				
				
				
				*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void start() {
		MemoryManagementUnit mmu = new MemoryManagementUnit(capacityForAlgo, algoToExe);
		
		RunConfiguration runConfiguration = MMUDriver.readConfigurationFile();
		List<ProcessCycles> processCycles = runConfiguration.getProcessCycles();
		List<Process> processes = MMUDriver.createProcesses(processCycles, mmu);
		
		logger.write("PN:" + processes.size() + "\n", Level.INFO);
		try {
			MMUDriver.runProcesses(processes);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Write to HD
		try {
			mmu.shutDown();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
