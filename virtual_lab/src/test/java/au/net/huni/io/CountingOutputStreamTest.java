package au.net.huni.io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class CountingOutputStreamTest {

	@Test
	public void testWriteIntForNoOutput() throws IOException {
		CountingOutputStream outputStream = null;
		try {
			outputStream = new CountingOutputStream();
			assertEquals(0, outputStream.getCount());
		} finally {
			outputStream.close();
		}
	}

	@Test
	public void testWriteIntForOneCharacterOutput() throws IOException {
		CountingOutputStream outputStream = null;
		try {
			outputStream = new CountingOutputStream();
			outputStream.write('a');
			assertEquals(1, outputStream.getCount());
		} finally {
			outputStream.close();
		}
	}

	@Test
	public void testWriteIntForTwoCharacterOutput() throws IOException {
		CountingOutputStream outputStream = null;
		try {
			outputStream = new CountingOutputStream();
			outputStream.write('a');
			outputStream.write('b');
			assertEquals(2, outputStream.getCount());
		} finally {
			outputStream.close();
		}
	}

}
