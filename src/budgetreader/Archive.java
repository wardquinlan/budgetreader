package budgetreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Archive {
	private InputStream in;
	
	public Archive(InputStream in) {
		this.in = in;
	}
	
	public int readHeader() throws IOException {
		return readWORD();
	}
	
	public int readBYTE() throws IOException {
		return in.read();
	}
	
	public int readWORD() throws IOException {
		int b1 = in.read();
		int b2 = in.read();
		int val = b2 * 256 + b1;
		return val;
	}

	public int readLONG() throws IOException {
		int b1 = in.read();
		int b2 = in.read();
		int b3 = in.read();
		int b4 = in.read();
		int val = (b4 * 256 * 256 * 256) + (b3 * 256 * 256) + (b2 * 256) + b1;
		return val;
	}
	
	public String readString(int length) throws IOException {
		byte[] buffer = new byte[length];
		for (int i = 0; i < length; i++) {
			buffer[i] = (byte) readBYTE();
		}
		return new String(buffer);
	}
	
	public void close() throws IOException {
		in.close();
	}
}
