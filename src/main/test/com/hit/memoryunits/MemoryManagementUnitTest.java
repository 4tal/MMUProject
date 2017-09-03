package com.hit.memoryunits;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

public class MemoryManagementUnitTest {
	
	@Test
	public void testPage()
	{
		Page<String> testerPage=new Page<String>(new Long(12481327),"TestString");
		Page<String> testerPageCopy=new Page<String>(new Long(10041988),"AnotherTestString");
		
		
		
		//Check getPageId:
		assertEquals(testerPage.getPageId(),new Long(12481327));
		
		//Check setPageId:
		testerPage.setId(new Long(10041988));
		assertEquals(testerPage.getPageId(),new Long(10041988));
		
		//Check getContent:
		assertEquals(testerPage.getContent(),"TestString");
		
		//Check setContent:
		testerPage.setContent("AnotherTestString");
		assertEquals(testerPage.getContent(),"AnotherTestString");
		
		
		//Check Equals:
		assertEquals(testerPageCopy.equals(testerPage),false);
		assertEquals(testerPageCopy.equals(testerPageCopy),true);
		
	}
	
	
	
	@Test
	public void testRAM()
	{
		
		byte[] bytesArray1=new byte[9];
		byte[] bytesArray2=new byte[11];
		bytesArray1[0]=80;
		bytesArray2[0]=bytesArray2[1]=bytesArray2[2]=bytesArray2[3]=16;
		
		RAM testerRAM=new RAM(4);
		
		
		Page<byte[]> testerPage1=new Page<byte[]>(new Long(12481327),bytesArray1);
		Page<byte[]> testerPage2=new Page<byte[]>(new Long(66666666),bytesArray2);
		
		Page[] pagesArray = new Page[2];
		Page[] pagesArray2 = new Page[2];
		pagesArray[0]=testerPage1;
		pagesArray[1]=testerPage2;
		
		
		//Check addPAge:
		testerRAM.addPage(testerPage1);
		assertEquals(testerRAM.getPage(new Long(12481327)).getContent(),bytesArray1);
		assertEquals(testerRAM.getRAMSize(),1);
		
		//Check remove page:
		testerRAM.removePage(testerPage1);
		assertEquals(testerRAM.getRAMSize(),0);
		
		//Check AddPages:
		testerRAM.addPages(pagesArray);
		assertEquals(testerRAM.getRAMSize(),2);
		assertEquals(testerRAM.getPage(new Long(12481327)).getContent(),bytesArray1);
		assertEquals(testerRAM.getPage(new Long(66666666)).getContent(),bytesArray2);
		
		
		//Check getPages():
		Long[] pagesIds=new Long[2];
		pagesIds[0]=new Long(12481327);
		pagesIds[1]=new Long(66666666);
		pagesArray2=testerRAM.getPages(pagesIds);
		assertEquals(pagesArray2.length,2);
		
		//Check RemovePages:
		testerRAM.removePages(pagesArray);
		assertEquals(testerRAM.getRAMSize(),0);
		
		//Check Initial capacity:
		assertEquals(testerRAM.getInitialCapacity(),4);
	}
	
	
	@Test
	public void testHardDisk() throws FileNotFoundException, IOException
	{
		byte[] bytesArray1=new byte[9];
		byte[] bytesArray2=new byte[11];
		bytesArray1[0]=80;
		bytesArray2[0]=bytesArray2[1]=bytesArray2[2]=bytesArray2[3]=16;
	
		Page<byte[]> testerPage1=new Page<byte[]>(new Long(12481327),bytesArray1);
		Page<byte[]> testerPage2=new Page<byte[]>(new Long(66666666),bytesArray2);
		
		Page[] pagesArray = new Page[2];
		//Page[] pagesArray2 = new Page[2];
		pagesArray[0]=testerPage1;
		pagesArray[1]=testerPage2;
		
		
		//Singelton: 
		HardDisk testerHardDisk=HardDisk.getInstance();
		HardDisk testerHardDisk2=HardDisk.getInstance();
		
		assertEquals(testerHardDisk,testerHardDisk2);
		
		//Read/Write Data function: (First read and then write) ===============================
		testerHardDisk.pagesOnHD.put(testerPage1.getPageId(),testerPage1);
		testerHardDisk.pagesOnHD.put(testerPage2.getPageId(),testerPage2);
		
		testerHardDisk.writeDataToHD();
		testerHardDisk.pagesOnHD=new HashMap<Long,Page<byte[]>>();
		
		//Create new HashMAP to check if it reads objects from the file into the new HashMap
		
		testerHardDisk.readDataFromHD();
		assertEquals(testerHardDisk.pagesOnHD.containsKey(testerPage2.getPageId()),true);
		//=====================================================================================
		
		//Check Page Fault:
		System.out.println(testerHardDisk2.pageFault(new Long(66666666)));
		System.out.println(testerPage2);
		
		assertEquals(testerHardDisk2.pageFault(new Long(1212121212)),null);
		//Suppose to run:::::::
		//System.out.println(testerHardDisk2.pageFault(new Long(66666666)));
		
		
		
		
		
		//Check PageReplacement:
		//testerHardDisk2.pageReplacement(testerPage2, testerPage1.getPageId());
		//System.out.println(testerHardDisk2.pageReplacement(testerPage2, testerPage2.getPageId()));
		//assertEquals()
		//System.out.println(testerPage2.getContent());
		//System.out.println(testerHardDisk.pagesOnHD.containsKey(testerPage1.getPageId()));
		//System.out.println(testerHardDisk.pagesOnHD.containsKey(testerPage2.getPageId()));
				
	}
	
	
	@Test
	public void testMemoryManagementUnit()
	{
		
	}
	
	
	
	
}
