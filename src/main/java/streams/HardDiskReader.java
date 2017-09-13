package streams;

import com.hit.memoryunits.Page;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class HardDiskReader implements Closeable{

	private final ObjectInputStream inputStream;

	public HardDiskReader(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Map<Long, Page<byte[]>> readAll() throws IOException {
		Map<Long, Page<byte[]>> pages = new LinkedHashMap<>();
		Page<byte[]> page = new Page<>();

		try{
			boolean toContinue = true;
			while (toContinue) {
				page = (Page<byte[]>) inputStream.readObject();
				pages.put(page.getPageId(), page);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}

		return pages;
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}
}
