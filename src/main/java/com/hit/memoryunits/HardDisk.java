package com.hit.memoryunits;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public final class HardDisk {

	private static final int SIZE = 1000;
	private static final String DEAFAULT_FILE_NAME = "lib/tempHD.txt"; //TODO the file name where the pages will be saved

	private static HardDisk instance = null;
	public HashMap<Long,Page<byte[]>> pagesOnHD;
	
	//Need to add try and catch;


	private HardDisk() {
		this.pagesOnHD = new HashMap<Long,Page<byte[]>>();
		readDataFromHD();
	}

	/**
	 * This method is called when a page is not in fast memory (RAM)
	 * @param pageId given Page id
	 * @return the page with the given pageId
	 * @throws FileNotFoundException indicates problem while getting the txt file where the pages saved
	 * @throws IOException
	 */
	
	public static HardDisk getInstance() {
		if(instance == null) {
			instance = new HardDisk();
		}
		return instance;
	}
	
	public Page<byte[]> pageFault(Long pageId) throws FileNotFoundException, IOException{
		if(pagesOnHD==null){
			throw new IOException();
		}
		
		if(pagesOnHD.get(pageId)!=null){
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
	 */
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId) throws FileNotFoundException, IOException{
		//TODO Complete implementation
		pagesOnHD.put(moveToHdPage.getPageId(), moveToHdPage);
		
		return pagesOnHD.get(moveToRamId);
	}

	public void readDataFromHD()
	{
		Page<byte[]> obj;
	//	System.out.println("A");
		boolean continueToRead=true;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		//System.out.println("A");
		try {
			//We can move it to other function
			File file = new File(DEAFAULT_FILE_NAME);
			//System.out.println("A");
			if(file.length() == 0)
			{
				//System.out.println("B");
				continueToRead=false;
			}
			else
			{
				//System.out.println("c");
				fin = new FileInputStream(file);
				//System.out.println("A");
				ois = new ObjectInputStream(fin); 
				//System.out.println("c");
				
			}
			
		
			//=====================================================================================
			  
			
			while(continueToRead)
			{
				
				
				//System.out.println("111");
				obj = (Page<byte[]>) ois.readObject();
				
				//System.out.println(file.length());
				//System.out.println("d");
				if(obj!=null)
				{
					//System.out.println(obj.getPageId());
					
					this.pagesOnHD.put(obj.getPageId(), obj);
					//System.out.println(pagesOnHD.size());
				}
				else
				{
					continueToRead = false;
				}
				
			}	
			//System.out.println("e");

		} 
		catch (Exception ex) 
		{
			System.out.println("in Exe");
			//ex.printStackTrace();
		}
		/*finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		*/
		
	}
	
	public void writeDataToHD()
	{
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream(DEAFAULT_FILE_NAME);
			oos = new ObjectOutputStream(fout);
		
			//HashMap<Long, byte[]> selects = new HashMap<Long, byte[]>();

		
			for(Map.Entry<Long,Page<byte[]>> entry : this.pagesOnHD.entrySet()) 
			{
				Page<byte[]> value = entry.getValue();
				oos.writeObject(value);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		//Do we need to do finally?
		/*
		finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		*/
	}
	

	//when clone method is override the singleton is more stronger because the singleton object(HardDisk) cannot be cloned
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
