package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HardDisk {

	private static final int SIZE = 1000;
	private static final String DEAFAULT_FILE_NAME = "<thefilename.txt>"; //TODO the file name where the pages will be saved

	private static HardDisk instance = null;
	private Map<Long,Page<byte[]>> pages;
	
	//Need to add try and catch;


	private HardDisk() {
		this.pages = new LinkedHashMap<>();
	}

	/**
	 * This method is called when a page is not in fast memory (RAM)
	 * @param pageId given Page id
	 * @return the page with the given pageId
	 * @throws FileNotFoundException indicates problem while getting the txt file where the pages saved
	 * @throws IOException
	 */
	public Page<byte[]> pageFault(Long pageId) throws FileNotFoundException, IOException{

		if(pages.containsKey(pageId)) {
			//TODO add missing part, I thing we should write to HD here before return statement- need to check
			return pages.get(pageId);
		}

		return null;
	}

	public static HardDisk getInstance() {
		if(instance == null) {
			instance = new HardDisk();
		}

		return instance;
	}

	/**
	 * This method is called when a page is not in fast memory (RAM) and RAM is also with full capacity

	 * @param moveToHdPage page which should be moved to HD
	 * @param moveToRamId page id of the pages which should be moved to RAM
	 * @return the page with the given pageId
	 * @throws FileNotFoundException indicates problem while getting the txt file where the pages saved
	 * @throws IOException
	 */
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId) throws FileNotFoundException, IOException{
		//TODO Complete implementation
		pages.put(moveToHdPage.getPageId(), moveToHdPage);

		if(pageFault(moveToRamId) != null) {
			return pageFault(moveToRamId);
		}

		//TODO I think we should write to HD before return statement- need to check
		return null;
	}


	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
