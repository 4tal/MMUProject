package com.hit.memoryunits;

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

	public Page<byte[]>[] getPages(java.lang.Long[] pageIds){
		List<Page<byte[]>> returnedPages=new ArrayList<Page<byte[]>>();
		for(int i=0;i<pageIds.length;i++)
		{
			if(this.ram.getPage(pageIds[i])!=null)
			{
				returnedPages.add(this.ram.getPage(pageIds[i]));
			}
			else
			{
				//Add Logic for what to do when the page is not in the RAM;
			}
		}
		return this.getPages(pageIds);
	}


}