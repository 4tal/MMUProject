package com.hit.memoryunits;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.hit.algorithms.LRUAlgoCacheImpl;

public class MMUTest {

	@Test
	public void testMMU() throws IOException {
		MemoryManagementUnit mmu = new MemoryManagementUnit(5, new LRUAlgoCacheImpl<>(5));
		
		Long[] ids = new Long[] {0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L};
		
		Page<byte[]>[] returnPages = mmu.getPages(ids);
		
		@SuppressWarnings("unchecked")
		Page<byte[]>[] expectedPages = new Page[9];
		
		Long val = 0L;
		
		for(int i = 0; i < expectedPages.length; i++) {
			expectedPages[i] = new Page<>(val, val.toString().getBytes());
			++val;
		} 
		for(int i = 0; i < returnPages.length; i++) {
			assertEquals(expectedPages[i].getPageId(), returnPages[i].getPageId());
		}
	}
}
