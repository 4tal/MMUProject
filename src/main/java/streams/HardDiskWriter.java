package streams;

import com.hit.memoryunits.Page;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class HardDiskWriter extends ObjectOutputStream {
	private final ObjectOutputStream outputStream;

	public HardDiskWriter(ObjectOutputStream outputStream) throws IOException {
		this.outputStream = outputStream;
	}

	public void writeAll(Map<Long, Page<byte[]>> pages) throws IOException {
		try{
			for (Page<byte[]> pageToWrite : pages.values()) {
				outputStream.writeObject(pageToWrite);
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
}
