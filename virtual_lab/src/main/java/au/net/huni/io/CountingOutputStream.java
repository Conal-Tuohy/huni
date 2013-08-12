package au.net.huni.io;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends OutputStream {

	private int characterCount = 0;
	@Override
	public void write(int b) throws IOException {
		characterCount++;
	}

	public int getCount() {
		return characterCount;
	}
}
