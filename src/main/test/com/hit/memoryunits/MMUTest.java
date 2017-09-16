package com.hit.memoryunits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.hit.algorithms.LRUAlgoCacheImpl;
import org.junit.Assert;
import org.junit.Test;

public class MMUTest {

	@Test
	public void testMMU() throws IOException {
		MemoryManagementUnit mmu = new MemoryManagementUnit(4, new LRUAlgoCacheImpl<>(4));
		Page<byte[]>[] mmuPages;
		Long[] ids = new Long[5];
		ids[0] = new Long(1);
		ids[1] = new Long(2);
		ids[2] = new Long(3);
		ids[3] = new Long(4);
		ids[4] = new Long(5);




		byte[] byte1 = "sometotest1".getBytes();
		byte[] byte2 = "sometotest2".getBytes();
		byte[] byte3 = "sometotest3".getBytes();
		byte[] byte4 = "sometotest4".getBytes();
		byte[] byte5 = "sometotest5".getBytes();



		Page<byte[]> page1 = new Page<>(ids[0], byte1);
		Page<byte[]> page2 = new Page<>(ids[1], byte2);
		Page<byte[]> page3 = new Page<>(ids[2], byte3);
		Page<byte[]> page4 = new Page<>(ids[3], byte4);
		Page<byte[]> page5 = new Page<>(ids[4], byte5);


		mmuPages = mmu.getPages(ids);

		assertEquals(page1.getPageId(), mmuPages[0].getPageId());
		assertEquals(page2.getPageId(), mmuPages[1].getPageId());
		assertEquals(page3.getPageId(), mmuPages[2].getPageId());
		assertEquals(page4.getPageId(), mmuPages[3].getPageId());
		assertEquals(page5.getPageId(), mmuPages[4].getPageId());

		Page<byte[]>[] mmuPage;
		Long[] id = new Long[1];
		id[0] = 1500L;
		mmuPage = mmu.getPages(id);

		//this page does not exist should return null
		assertEquals(null, mmuPage[0]);
		
		

	}
}
