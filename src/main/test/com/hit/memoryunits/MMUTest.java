package com.hit.memoryunits;

import static org.junit.Assert.assertEquals;

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

		Page<byte[]> p1 = new Page(ids[0], byte1);


		Page<byte[]> page1 = new Page<>(ids[0], byte1);
		Page<byte[]> page2 = new Page<>(ids[1], byte2);
		Page<byte[]> page3 = new Page<>(ids[2], byte3);
		Page<byte[]> page4 = new Page<>(ids[3], byte4);
		Page<byte[]> page5 = new Page<>(ids[4], byte5);


		mmuPages = mmu.getPages(ids);

		System.out.println("+++++++++++++++");
		System.out.println(page1.getPageId() + "    " + mmuPages[0].getPageId());

		/*
		Assert.assertEquals(page2, mmuPages[1]);
		Assert.assertEquals(page3, mmuPages[2]);
		Assert.assertEquals(page4, mmuPages[3]);
		Assert.assertEquals(page5, mmuPages[4]);

		for (Page<byte[]> page : mmuPages) {
			System.out.println(page.getContent());
		}*/

	}
}
