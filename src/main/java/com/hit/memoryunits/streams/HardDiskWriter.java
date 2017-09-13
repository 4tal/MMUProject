package com.hit.memoryunits.streams;

import com.hit.memoryunits.Page;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class HardDiskWriter implements Closeable {
	private final ObjectOutputStream outputStream;

	public HardDiskWriter(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public boolean writeAll(Map<Long, Page<byte[]>> pages) throws IOException {
		boolean isSucceed = false;
		//try with resources automatically do "finally close"
		try{
			for (Page<byte[]> pageToWrite : pages.values()) {
				outputStream.writeObject(pageToWrite);
			}

			isSucceed = true;
		}catch (IOException e) {
			isSucceed = false;
			e.printStackTrace();
		} finally {
			outputStream.close();
			return isSucceed;
		}
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
}
