package com.hit.processes;

import java.util.Arrays;
import java.util.List;

public class ProcessCycle {
	private List<Long> pages;
	private List<byte[]> data;
	private int sleepMs;

	public ProcessCycle(List<Long> pages, int sleepMs, List<byte[]> data) {
		setPages(pages);
		setData(data);
		setSleepMs(sleepMs);
	}

	/**
	 * 
	 * @return the pages in the process cycle
	 */
	public List<Long> getPages() {
		return pages;
	}

	/**
	 * 
	 * @param pages the pages to set in the process cycle
	 */
	public void setPages(List<Long> pages) {
		this.pages = pages;
	}

	/**
	 * 
	 * @return the data of the process cycle
	 */
	public List<byte[]> getData() {
		return data;
	}

	/**
	 * 
	 * @param data the data to set to process cycle
	 */
	public void setData(List<byte[]> data) {
		this.data = data;
	}
	
	
	/**
	 * 
	 * @return the sleep time in ms of the process cycle
	 */
	public int getSleepMs() {
		return sleepMs;
	}

	/**
	 * 
	 * @param sleepMs the sleep time in ms for the process cycle to sleep
	 */
	public void setSleepMs(int sleepMs) {
		this.sleepMs = sleepMs;
	}
	
	@Override
	 public String toString() {
		StringBuilder sb = new StringBuilder();
	 
		for(int i = 0; i < data.size(); i++) {
			sb.append(Arrays.toString(data.get(i)));
		}
	 
		return sb.toString();
	 }
}
