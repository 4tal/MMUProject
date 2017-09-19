package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hit.algorithms.IAlgoCache;

/**
 * This class represents the MMU - Hardware device that maps virtual to physical address by software
 */
public class MemoryManagementUnit{

	private RAM ram;
	private IAlgoCache<Long, Long> algo;
	private HardDisk hardDisk;

	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long, Long> algo){
		setRam(new RAM(ramCapacity));
		setAlgo(algo);
		hardDisk = HardDisk.getInstance();
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
		Page<byte[]> pageHdTarget = null;
		Long idPageReplace = null;
		
		for(int i=0; i<pageIds.length;i++)
		{
			if(algo.getElement(pageIds[i]) == null)
			{ 
				//if RAM is not full
				if(ram.getRAMSize() <= ram.getInitialCapacity()) {
					algo.putElement(pageIds[i],pageIds[i]);
					ram.addPage(hardDisk.pageFault(pageIds[i]));
				} else {
					idPageReplace = algo.putElement(pageIds[i],pageIds[i]);
					pageHdTarget = ram.getPage(idPageReplace);
					ram.addPage(hardDisk.pageReplacement(pageHdTarget, pageIds[i]));
				}
			}
			
			pageResult[i] = ram.getPage(pageIds[i]);
		}
		
		return pageResult;
	}

	private void setRam(RAM ram) {
		this.ram = ram;
	}

	private void setAlgo(IAlgoCache<Long, Long> algo) {
		this.algo = algo;
	}

	public void ShutDown() throws FileNotFoundException, IOException
	{	
		Map<Long,Page<byte[]>> pages = ram.getPages();
		
		for(Map.Entry<Long,Page<byte[]>> page : pages.entrySet())
		{
			hardDisk.pageReplacement(page.getValue(), null);
		}
	}
}