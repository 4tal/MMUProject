package com.hit.processes;

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

	public List<Long> getPages() {
		return pages;
	}

	public void setPages(List<Long> pages) {
		this.pages = pages;
	}

	public List<byte[]> getData() {
		return data;
	}

	public void setData(List<byte[]> data) {
		this.data = data;
	}
	
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (int i = 0; i < data.size(); i++) {
			stringBuilder.append(data.get(i).toString());
		}
		
		return stringBuilder.toString();
	}

	public int getSleepMs() {
		return sleepMs;
	}

	public void setSleepMs(int sleepMs) {
		this.sleepMs = sleepMs;
	}
}
