package com.hit.memoryunits;

import static org.junit.Assert.assertEquals;

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
		
		//RAM testerRAM=new RAM(4);
		
		//Check addPage(
		
		byte[] b=new byte[9];
		//Add default
		for(int i=0;i<9;i++){
			b[i]=(byte) (i + 1);
		}
		
		
		
		//System.out.println();
		
		
		//assertEquals(testerPageCopy.equals(testerPageCopy),true);
		
		
		//System.out.println(testerRAM);
	}
	
	
	@Test
	public void testHardDisk()
	{
		
	}
	
	
	@Test
	public void testMemoryManagementUnit()
	{
		
	}
	
	
	
	
}
