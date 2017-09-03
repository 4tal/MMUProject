package com.hit.memoryunits;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RAM implements Serializable{

	private static final long serialVersionUID = 1L;
	private int initialCapacity;
	//need to change it to private:
	private Map<Long, Page<byte[]>> pages;

	public RAM(int initialCapacity){
		setInitialCapacity(initialCapacity);
		pages = new LinkedHashMap<Long,Page<byte[]>>();
	}

	public void addPage(Page<byte[]> inputPage){
		pages.put(inputPage.getPageId(), inputPage);
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
		//Added step first add to array list (only if exist), than to Page<byte[]>
		List<Page<byte[]>> returnedPages=new ArrayList<Page<byte[]>>();
		for(int i = 0; i < pageIds.length; i++)
		{
			if(pages.get(pageIds[i])!=null)
			{
				returnedPages.add(this.getPage(pageIds[i]));
				//returnedPages.add(pages.get(pageIds[i]));
			}
			//returnedPages.add(this.)
			//returnPages[i] = pages.get(pageIds[i]);
		}
		
		Page<byte[]>[] returnPages = new Page[returnedPages.size()];
		
		for(int i=0;i<returnedPages.size();i++)
		{
			returnPages[i]=(Page<byte[]>) returnedPages.get(i);
		}

		return returnPages;
	}
	
	public int getRAMSize()
	{
		return this.pages.size();
	}
	
	public void removePage(Page<byte[]> removePage){
		pages.remove(removePage.getPageId());
	}
	public void removePages(Page<byte[]>[] removePages) {
		for (Page<byte[]> removePage : removePages) {
			pages.remove(removePage.getPageId());
		}
	}
	public void setInitialCapacity(int initialCapacity){
		this.initialCapacity = initialCapacity;
	}

	public void setPages(Map<Long,Page<byte[]>> pages){
		this.pages = pages;
	}
	
}
