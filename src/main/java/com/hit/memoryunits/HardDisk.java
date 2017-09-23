package com.hit.memoryunits;

import streams.HardDiskWriter;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import com.hit.util.MMULogger;

public final class HardDisk {

	private static final int SIZE = 1000;
	private static final String DEAFAULT_FILE_NAME = "src/main/resources/com/hit/config/harddisk";
	private static final HardDisk instance = new HardDisk();
	private Map<Long,Page<byte[]>> pagesOnHD;
	private MMULogger logger;


	private HardDisk() {
		this.pagesOnHD = new HashMap<>();
		this.logger = MMULogger.getInstance();
		
		for(Long i = 0L; i < SIZE; i++) {
			pagesOnHD.put(i, new Page<byte[]>(i, i.toString().getBytes()));
		}
		
		try {
			writeToHD();
		} catch (IOException e) {
			e.printStackTrace();
			String nextLine = System.getProperty("line.seperator");
			logger.write(e.getMessage() + nextLine, Level.SEVERE);
		}
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
		readFromHD();
		pagesOnHD.put(moveToHdPage.getPageId(), moveToHdPage);
		Page<byte[]> pageReturn = pagesOnHD.get(moveToRamId);
		writeToHD();
		
		return pageReturn;
	}

	private void writeToHD() throws FileNotFoundException, IOException {
		//try with resources automatically do "finally=> close"
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DEAFAULT_FILE_NAME))) {
			outputStream.writeObject(pagesOnHD);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			String nextLine = System.getProperty("line.seperator");
			logger.write(e.getMessage() + nextLine, Level.SEVERE);
		}
	}

	@SuppressWarnings("unchecked")
	private void readFromHD()  throws FileNotFoundException, IOException{
		
		//try with resources automatically do "finally=> close"
		try {
			pagesOnHD = readAllPages();
		} catch (IOException e) {
			e.printStackTrace();
			String nextLine = System.getProperty("line.seperator");
			logger.write(e.getMessage() + nextLine, Level.SEVERE);
		}
	}

	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	@SuppressWarnings("unchecked")
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
			String nextLine = System.getProperty("line.seperator");
			logger.write(e.getMessage() + nextLine, Level.SEVERE);
		}
		
		return pages;
	}
}
