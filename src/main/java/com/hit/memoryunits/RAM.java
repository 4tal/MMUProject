package com.hit.memoryunits;


import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Aviad and Idan
 * The RAM class represent the ram in the MMU Simulator
 */
public class RAM {

	private int initialCapacity;
	private Map<Long, Page<byte[]>> pages;

	public RAM(int initialCapacity){
		setInitialCapacity(initialCapacity);
		pages = new HashMap<>(initialCapacity);
	}

	/**
	 *
	 * @param inputPage the page that will be added
	 */
	public void addPage(Page<byte[]> inputPage){
		pages.put(inputPage.getPageId(), inputPage);
	}

	/**
	 * added all pages at once
	 * @param addPages the pages that will be added
	 */
	public void addPages(Page<byte[]>[] addPages){
		for (Page<byte[]> addPage : addPages) {
			pages.put(addPage.getPageId(), addPage);
		}
	}

	public int getInitialCapacity(){
		return this.initialCapacity;
	}

	/**
	 * get Page by id
	 * @param pageId the id of the wanted Page
	 * @return the Page that matches the id
	 */
	public Page<byte[]> getPage(Long pageId){
		return pages.get(pageId);
	}

	/**
	 * get all the pages
	 * @return all the pages
	 */
	public Map<Long,Page<byte[]>> getPages(){
		return this.pages;
	}

	/**
	 * get multiple pages at once by array of id's
	 * @param pageIds the given id's
	 * @return all the pages that matches the id's
	 */
	public Page<byte[]>[] getPages(Long[] pageIds){
		@SuppressWarnings("unchecked")
		Page<byte[]>[] pagesResult = new Page[pageIds.length];

		for (int i = 0; i < pageIds.length; i++) {
			pagesResult[i] = pages.get(pageIds[i]);
		}

		return pagesResult;
	}

	/**
	 * remove specific Page
	 * @param removePage the Page to remove
	 */
	public void removePage(Page<byte[]> removePage){
		pages.remove(removePage.getPageId());
	}

	/**
	 * remove bunch of Pages at once
	 * @param removePages the Pages to remove
	 */
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

	public int getRAMSize() {
		return pages.size();
	}
}
