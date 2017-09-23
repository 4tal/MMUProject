package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.hit.algorithms.IAlgoCache;
import com.hit.util.MMULogger;

/**
 * This class represents the MMU - Hardware device that maps virtual to physical address by software
 */
public class MemoryManagementUnit{

	private RAM ram;
	private IAlgoCache<Long, Long> algo;
	private HardDisk hardDisk;
	private MMULogger logger;
	

	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long, Long> algo){
		setRam(new RAM(ramCapacity));
		setAlgo(algo);
		hardDisk = HardDisk.getInstance();
		logger = MMULogger.getInstance();
		logger.write("RC:" + ramCapacity + "\n", Level.INFO);
	}

	/**
	 * This method is the main method which returns array of pages that are requested from the user
	 * @param pageIds array of page ids
	 * @return returns array of pages that are requested from the user
	 * @throws FileNotFoundException indicates problems reading from HD(file)
	 * @throws IOException indicated problems reading from HD
	 */
	public synchronized Page<byte[]>[] getPages(Long[] pageIds) throws IOException{

		Page<byte[]>[] result = new Page[pageIds.length];

		for (int i = 0; i < pageIds.length; i++) {
			if (algo.getElement(pageIds[i]) == null) {
				if (ram.getRAMSize() < ram.getInitialCapacity()) {
					// adding the missing pageId to the ram
					algo.putElement(pageIds[i],pageIds[i]);
					// adding the missing page to the ram
					result[i] = hardDisk.pageFault(pageIds[i]);
					logger.write("PF " + pageIds[i]+ "\r\n", Level.INFO);
					ram.addPage(result[i]);
				} else {
					// adding the missing pageId to the ram algo
					// saving the id of the revomed page to save on the HD
					Long pageIdToHd = algo.putElement(pageIds[i],pageIds[i]);
					// getting the page
					Page<byte[]> pageToHd = ram.getPage(pageIdToHd);
					ram.removePage(pageToHd);
					result[i] = hardDisk.pageReplacement(pageToHd, pageIds[i]);
					logger.write("PR MTH " + pageIdToHd+" " + "MTR " + pageIds[i]+ "\r\n", Level.INFO);
					ram.addPage(result[i]);
				}
			} else {
				result[i] = ram.getPage(algo.getElement(pageIds[i]));
			}
		}
		return result;
	}

	private void setRam(RAM ram) {
		this.ram = ram;
	}

	private void setAlgo(IAlgoCache<Long, Long> algo) {
		this.algo = algo;
	}

	public void shutDown() throws FileNotFoundException, IOException
	{	
		Map<Long,Page<byte[]>> pages = ram.getPages();
		
		for(Map.Entry<Long,Page<byte[]>> page : pages.entrySet())
		{
			hardDisk.pageReplacement(page.getValue(), null);
		}
	}
}