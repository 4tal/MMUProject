package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class HardDisk {

	private static HardDisk instance = new HardDisk();
	private Map<Long,Page<byte[]>> pages;
	
	//Need to add try and catch;


	private HardDisk() {
		this.pages = new LinkedHashMap<>();
	}

	public Page<byte[]> pageFault(Long pageId) throws FileNotFoundException, IOException{

		if(pages.containsKey(pageId)) {
			//TODO add missing part, I thing we should write to HD here before return statement
			return pages.get(pageId);
		}

		return null;
	}

	public static HardDisk getInstance() {
		return instance;

	}

	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId) throws FileNotFoundException, IOException{
		//TODO Complete implementation
	}


	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
