package com.hit.memoryunits;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class RAM implements Serializable{
	private int initialCapacity;
	private Map<Long, Page<byte[]>> pages;

	public RAM(int initialCapacity){
		setInitialCapacity(initialCapacity);
		pages = new LinkedHashMap<>();
	}

	public void addPage(Page<byte[]> addPage){
		pages.put(addPage.getPageId(), addPage);
	}
	public void addPages(Page<byte[]>[] addPages){
		for (Page<byte[]> addPage : addPages) {
			pages.put(addPage.getPageId(), addPage);
		}
	}
	
	public int getInitialCapacity(){
		return initialCapacity;
	}
	
	public Page<byte[]> getPage(Long pageId){
		return pages.get(pageId);
	}
	public Map<Long,Page<byte[]>> getPages(){
		return pages;
	}
	
	public Page<byte[]>[] getPages(Long[] pageIds){
		Page<byte[]>[] returnPages = new Page[pageIds.length];
		int count = 0
				;
		for(int i = 0; i < pageIds.length; i++)
		{
			returnPages[i] = pages.get(pageIds[i]);
		}

		return returnPages;
	}
	
	public void removePage(Page<byte[]> removePage){
		pages.remove(removePage);
	}
	public void removePages(Page<byte[]>[] removePages) {
		for (Page<byte[]> removePage : removePages) {
			pages.remove(removePage);
		}
	}
	public void setInitialCapacity(int initialCapacity){
		this.initialCapacity = initialCapacity;
	}

	public void setPages(Map<Long,Page<byte[]>> pages){
		this.pages = pages;
	}
}
