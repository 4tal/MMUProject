package com.hit.memoryunits;

public class HardDisk extends java.lang.Object{
	Page<byte[]> m_arr;
	
	//Need to add try and catch;
	
	public Page<byte[]> pageFault(java.lang.Long pageId){
		return m_arr;
	}
	
	
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, java.lang.Long moveToRamId){
		return m_arr;
	}
	
	

}
