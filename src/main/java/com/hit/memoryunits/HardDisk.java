package com.hit.memoryunits;

import streams.HardDiskReader;
import streams.HardDiskWriter;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HardDisk {

	private static final int SIZE = 1000;
	private static final String DEAFAULT_FILE_NAME = "src/main/resources/com/hit/config/harddisk";
	private boolean firstRead;
	private static final HardDisk instance = new HardDisk();
	public Map<Long,Page<byte[]>> pagesOnHD;


	private HardDisk() {
		this.pagesOnHD = new HashMap<>();
	}

	public static HardDisk getInstance() {
		return instance;
	}

	/**
	 * This method is called when a page is not in fast memory (RAM)
	 * @param pageId given pageId
	 * @return the page with the given pageId
	 * @throws FileNotFoundException indicates problems accessing the HD(the file)
	 * @throws IOException indicates problems accessing the HD
	 */
	public Page<byte[]> pageFault(Long pageId) throws FileNotFoundException, IOException {
		readFromHD();
		Page<byte[]> page = pagesOnHD.get(pageId);
		
		return page;
	}

	/**
	 * This method is called when a page is not in fast memory (RAM) and RAM is also with full capacity

	 * @param moveToHdPage page which should be moved to HD
	 * @param moveToRamId page id of the pages which should be moved to RAM
	 * @return the page with the given pageId
	 * @throws FileNotFoundException indicates problem while getting the .txt file where the pages saved
	 * @throws IOException
	 * @throws FileNotFoundException indicates problems read from files that behave like HD
	 */
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId) throws FileNotFoundException, IOException{
		pagesOnHD.put(moveToHdPage.getPageId(), moveToHdPage);

		if (pageFault(moveToRamId) != null) {
			return pageFault(moveToRamId);
		}

		writeToHD();

		return null;
	}

	private void writeToHD() throws FileNotFoundException, IOException {
		//try with resources automatically do "finally=> close"
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DEAFAULT_FILE_NAME))) {
			outputStream.writeObject(pagesOnHD);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void readFromHD()  throws FileNotFoundException, IOException{
		
		//try with resources automatically do "finally=> close"
		try {
			pagesOnHD = readAllPages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	private Map<Long, Page<byte[]>> readAllPages() throws FileNotFoundException, IOException 
	{
		boolean toContinue = true;
		Map<Long, Page<byte[]>> pages = new LinkedHashMap<Long, Page<byte[]>>();
		Page<byte[]> page = new Page<byte[]>();
		try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DEAFAULT_FILE_NAME))) {
			while(toContinue)
			{
				page = (Page<byte[]>)inputStream.readObject();
				pages.put(page.getPageId(), page);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return pages;
	}
}
