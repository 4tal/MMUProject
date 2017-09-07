package com.hit.memoryunits;

import com.hit.memoryunits.streams.HardDiskReader;
import com.hit.memoryunits.streams.HardDiskWriter;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HardDisk {

	private static final int SIZE = 1000;
	private static final String DEAFAULT_FILE_NAME = "lib/tempHD.txt"; //TODO should be in path "src/main/resources/<filename>.txt"
	private boolean firstRead;
	private static final HardDisk instance = new HardDisk();
	public Map<Long,Page<byte[]>> pagesOnHD;


	private HardDisk() {
		//TODO we need to write some dummy elements to the HD
		this.pagesOnHD = new LinkedHashMap<>(SIZE);
		firstRead = true;
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
		if(pagesOnHD.containsKey(pageId)){
			writeToHD();
			return pagesOnHD.get(pageId);
		}

		return null;
	}

	/**
	 * This method is called when a page is not in fast memory (RAM) and RAM is also with full capacity

	 * @param moveToHdPage page which should be moved to HD
	 * @param moveToRamId page id of the pages which should be moved to RAM
	 * @return the page with the given pageId
	 * @throws FileNotFoundException indicates problem while getting the txt file where the pages saved
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
		HardDiskWriter hardDiskWriter = new HardDiskWriter(new ObjectOutputStream(new FileOutputStream(DEAFAULT_FILE_NAME)));
		hardDiskWriter.writeAll(pagesOnHD);
	}

	private void readFromHD()  throws FileNotFoundException, IOException{
		if (firstRead) {
			HardDiskReader hardDiskReader = new HardDiskReader(new ObjectInputStream(new FileInputStream(DEAFAULT_FILE_NAME)));
			pagesOnHD = hardDiskReader.readAll();
			firstRead = false;
		}
	}

	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
