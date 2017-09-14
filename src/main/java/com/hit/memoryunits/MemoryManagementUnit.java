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
	}

	/**
	 * This method is the main method which returns array of pages that are requested from the user
	 * @param pageIds array of page ids
	 * @return returns array of pages that are requested from the user
	 * @throws FileNotFoundException indicates problems reading from HD(file)
	 * @throws IOException indicated problems reading from HD
	 */
	public synchronized Page<byte[]>[] getPages(Long[] pageIds) throws IOException{

		Page<byte[]>[] pagesResult = new Page[pageIds.length];
		hardDisk = HardDisk.getInstance();

		for (int i = 0; i < pageIds.length; i++) {
			//if ram contains this page
			if (algo.getElement(pageIds[i]) != null) {
				pagesResult[i] = ram.getPage(pageIds[i]);
			} else if (ram.getRAMSize() < ram.getInitialCapacity()) { //have space for the page but the page isn't in the RAM

				if (hardDisk.pageFault(pageIds[i]) != null) {
					algo.putElement(pageIds[i], pageIds[i]);
					ram.addPage(hardDisk.pageFault(pageIds[i]));
					pagesResult[i] = ram.getPage(pageIds[i]);
				}
			} else if (hardDisk.pageFault(pageIds[i]) != null) { //ram is full then replace the page

				long id = algo.putElement(pageIds[i], pageIds[i]);
				Page<byte[]> removedPage;
				removedPage = ram.getPage(id);
				ram.removePage(removedPage);
				ram.addPage(hardDisk.pageReplacement(removedPage, pageIds[i]));
				pagesResult[i] = ram.getPage(pageIds[i]);
			}
		}

		return pagesResult;
	}

	private RAM getRam() {
		return ram;
	}

	private void setRam(RAM ram) {
		this.ram = ram;
	}

	private void setAlgo(IAlgoCache<Long, Long> algo) {
		this.algo = algo;
	}

	private HardDisk getHardDisk() {
		return hardDisk;
	}

	private void setHardDisk(HardDisk hardDisk) {
		this.hardDisk = hardDisk;
	}
	
	public void ShutDown() throws FileNotFoundException, IOException
	{	
		Map<Long,Page<byte[]>> pages = ram.getPages();
		
		for(Map.Entry<Long,Page<byte[]>> page : pages.entrySet())
		{
			HardDisk.getInstance().pageReplacement(page.getValue(), null);
		}
	}
}