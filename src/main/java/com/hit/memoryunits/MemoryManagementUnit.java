package com.hit.memoryunits;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hit.algorithms.IAlgoCache;

public class MemoryManagementUnit extends java.lang.Object{
	//Add 2 members RAM and IAlgoCache
	//Add capability to read/Write from HD.

	private RAM ram;
	private IAlgoCache cachingStrategy;
	


	//Check the parameters:
	public MemoryManagementUnit(int ramCapacity, com.hit.algorithm.IAlgoCache<java.lang.Long,java.lang.Long> algo){
		ram=new RAM(ramCapacity);
		cachingStrategy=(IAlgoCache) algo;

	}

	public Page<byte[]>[] getPages(java.lang.Long[] pageIds) throws FileNotFoundException, IOException{
		HardDisk hardDiskInstance=HardDisk.getInstance();
		
		List<Page<byte[]>> pagesFromRam=new ArrayList<Page<byte[]>>();
		List<Page<byte[]>> pagesFromHD=new ArrayList<Page<byte[]>>();
		List<Long> notInRam=new ArrayList<Long>();
		for(int i=0;i<pageIds.length;i++)
		{
			if(this.ram.getPage(pageIds[i])!=null)
			{
				pagesFromRam.add(this.ram.getPage(pageIds[i]));
			}
			else
			{
				notInRam.add(pageIds[i]);
			}
		}
		
		for(int i=0;i<notInRam.size();i++)
		{
			pagesFromHD.add(hardDiskInstance.pageFault(notInRam.get(i)));//Get the pages from HD to RAM
		}
		
		//Page<byte[]>[] listOfPagesToReturn=new Page<byte[]>[pagesFromRam.size()+pagesFromHD.size()]; 
		
		//Remove RAM (Capacity-Not in Ram) pages.
		
		//Add function that copy the pages from both of the list to Page Array;
		
		
		return this.getPages(pageIds);
	}


}