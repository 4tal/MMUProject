package com.hit.memoryunits;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.hit.algorithms.LRUAlgoCacheImpl;
import org.junit.Assert;
import org.junit.Test;

public class MemoryManagementUnitTest {

	@Test
	public void testMMU() throws IOException {
		MemoryManagementUnit mmu = new MemoryManagementUnit(4, new LRUAlgoCacheImpl<>(4));
		Page<byte[]>[] mmuPages;
		Long[] ids = new Long[8];
		ids[0] = 1L;
		ids[1] = 2L;
		ids[2] = 3L;
		ids[3] = 4L;
		ids[4] = 5L;
		ids[5] = 6L;
		ids[6] = 7L;
		//duplicate
		ids[7] = 1L;

		mmuPages = mmu.getPages(ids);

		byte[] byte1 = "sometotest1".getBytes();
		byte[] byte2 = "sometotest2".getBytes();
		byte[] byte3 = "sometotest3".getBytes();
		byte[] byte4 = "sometotest4".getBytes();
		byte[] byte5 = "sometotest5".getBytes();

		Page<byte[]> page1 = new Page<>(1L, byte1);
		Page<byte[]> page2 = new Page<>(2L, byte2);
		Page<byte[]> page3 = new Page<>(3L, byte3);
		Page<byte[]> page4 = new Page<>(4L, byte4);
		Page<byte[]> page5 = new Page<>(5L, byte5);

		Assert.assertEquals(byte1, mmuPages[0]);
		Assert.assertEquals(byte2, mmuPages[1]);
		Assert.assertEquals(byte3, mmuPages[2]);
		Assert.assertEquals(byte4, mmuPages[3]);
		Assert.assertEquals(byte3, mmuPages[4]);
		Assert.assertEquals(byte5, mmuPages[5]);

		for (Page<byte[]> page : mmuPages) {
			System.out.println(page.getContent());
		}

	}
}
