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

		Page<byte[]>[] pageResult = new Page[pageIds.length];
		for(int i = 0; i < pageIds.length; i++) {
			if(algo.getElement(pageIds[i]) != null) {
				pageResult[i] = ram.getPage(pageIds[i]);
			} else if(ram.getRAMSize() < ram.getInitialCapacity()) {
				if(hardDisk.pageFault(pageIds[i]) != null) {
					algo.putElement(pageIds[i], pageIds[i]);
					ram.addPage(hardDisk.pageFault(pageIds[i]));
					logger.write("PF:" + pageIds[i] + "\n", Level.INFO);
				}
			} else if(hardDisk.pageFault(pageIds[i]) != null) {
				Long id = algo.putElement(pageIds[i], pageIds[i]);
				Page<byte[]> pageToRemoved = ram.getPage(pageIds[i]);
				ram.removePage(pageToRemoved);
				ram.addPage(hardDisk.pageReplacement(pageToRemoved, pageIds[i]));
				pageResult[i] = ram.getPage(pageIds[i]);
				logger.write("PR:MTH " + pageToRemoved.getPageId() + " MTR" + pageIds[i] + "\n", Level.INFO);
			}
		}
		
		return pageResult;
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