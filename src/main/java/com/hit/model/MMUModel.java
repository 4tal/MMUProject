package com.hit.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
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
import com.hit.algorithms.RandomAlgoCacheImpl;
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
	private static final String CONFIG_FILE = "src/main/resources/com/hit/config/Configuration.json";
	
	
	public MMUModel(){
		rowsFromLog=new ArrayList<>();
		
		
	}
	
	public void setCommands(String[] inputCommand){
		capacityForAlgo = Integer.parseInt(inputCommand[1]);
		algoToExe=createConcreteAlgo(inputCommand[0],capacityForAlgo);
	}

	private void readAllLog(){	
		try {
			rowsFromLog.addAll(Files.readAllLines(Paths.get(FILE_LOCATION)));
		} catch (IOException e) {
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
			algoToExe = new RandomAlgoCacheImpl<>(capacity);
			break;
		default:
			break;
		}
		
		return algoToExe;
	}

	/**
	 * @see Model#start()
	 */
	@Override
	public void start() {
		MemoryManagementUnit mmu = new MemoryManagementUnit(capacityForAlgo, algoToExe);
		RunConfiguration runConfiguration = readConfigurationFile();
		List<ProcessCycles> processCycles = runConfiguration.getProcessCycles();
		List<Process> processes = createProcesses(processCycles, mmu);
		
		logger.write("PN:" + processes.size() + "\n", Level.INFO);
		try {
			runProcesses(processes);
		} catch (InterruptedException | ExecutionException e) {
			logger.write(e.getMessage() + "\n", Level.SEVERE);
		}
		
		try {
			mmu.shutDown();
		} catch (IOException e) {
			logger.write(e.getMessage() + "\n", Level.SEVERE);
		}
		
		readAllLog();
		setChanged();
		
		
		notifyObservers(rowsFromLog);
			
	}

	/**
	 * read the json configurations file and return RunConfiguration file
	 * @return
	 */
	public static RunConfiguration readConfigurationFile() {
		RunConfiguration runConfiguration = null;
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(CONFIG_FILE);
			Gson gson = new Gson();
			runConfiguration = gson.fromJson(new JsonReader(fileReader), RunConfiguration.class);
			
		} catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
			logger.write(e.getMessage() + "\n", Level.SEVERE);
		}
		
		return runConfiguration;
	}
	
	/**
	 * 
	 * @param appliocationsScenarios List of ProcessCycles
	 * @param mmu the MMU 
	 * @return List of created Processes
	 */
	public static List<Process> createProcesses(List<ProcessCycles> appliocationsScenarios, MemoryManagementUnit mmu) {
		List<Process> processList = new ArrayList<>();
		int id = 0;
		for(ProcessCycles processCycles : appliocationsScenarios) {
			id++;
			processList.add(new Process(id, mmu, processCycles));
		}
			
		return processList;
	}
	
	/**
	 * 
	 * @param applications The List of Processes to run
	 * @throws InterruptedException indicates that another thread interrupt this operation
	 * @throws ExecutionException indicates problems to execute this operation in a Thread
	 */
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
}
