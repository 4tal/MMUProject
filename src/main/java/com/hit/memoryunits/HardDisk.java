package com.hit.memoryunits;

import com.hit.memoryunits.streams.HardDiskReader;
import com.hit.memoryunits.streams.HardDiskWriter;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HardDisk {

	private static final int SIZE = 1000;
	private static final String DEAFAULT_FILE_NAME = "src/main/resources.HardDisk.txt"; //TODO should be in path "src/main/resources/<filename>.txt"
	private boolean firstRead;
	private static final HardDisk instance = new HardDisk();
	public Map<Long,Page<byte[]>> pagesOnHD;


	private HardDisk() {
		//TODO we need to write some dummy elements to the HD
		this.pagesOnHD = new LinkedHashMap<>(SIZE);

		byte[] byte1 = "sometotest1".getBytes();
		byte[] byte2 = "sometotest2".getBytes();
		byte[] byte3 = "sometotest3".getBytes();
		byte[] byte4 = "sometotest4".getBytes();
		byte[] byte5 = "sometotest5".getBytes();

		Long[] ids = new Long[5];
		ids[0] = new Long(1);
		ids[1] = new Long(2);
		ids[2] = new Long(3);
		ids[3] = new Long(4);
		ids[4] = new Long(5);

		Page<byte[]> page1 = new Page<>(ids[0], byte1);
		Page<byte[]> page2 = new Page<>(ids[1], byte2);
		Page<byte[]> page3 = new Page<>(ids[2], byte3);
		Page<byte[]> page4 = new Page<>(ids[3], byte4);
		Page<byte[]> page5 = new Page<>(ids[4], byte5);

		pagesOnHD.put(ids[0], page1);
		pagesOnHD.put(ids[1], page2);
		pagesOnHD.put(ids[2], page3);
		pagesOnHD.put(ids[3], page4);
		pagesOnHD.put(ids[4], page5);

		byte[] somePage = {1, 2, 1, 2, 1, 2};

		for (int i = 5; i < SIZE; i++) {
			pagesOnHD.put((long) i, new Page<byte[]>((long) i, somePage.clone()));
		}

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
//			writeToHD();
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

//		writeToHD();

		return null;
	}

	private void writeToHD() throws FileNotFoundException, IOException {
		HardDiskWriter hardDiskWriter = new HardDiskWriter(new ObjectOutputStream(new FileOutputStream(DEAFAULT_FILE_NAME)));
		hardDiskWriter.writeAll(pagesOnHD);
		hardDiskWriter.close();
	}

	private void readFromHD()  throws FileNotFoundException, IOException{
		if (firstRead) {
			HardDiskReader hardDiskReader = new HardDiskReader(new ObjectInputStream(new FileInputStream(DEAFAULT_FILE_NAME)));
			pagesOnHD = hardDiskReader.readAll();
			firstRead = false;
			hardDiskReader.close();
		}
	}

	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
