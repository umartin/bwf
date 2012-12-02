package se.purplescout.purplemow.bwf.dsp;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Andersson
 */
public class BitInputStream extends InputStream implements BitWriter {

	private final ShiftRegister sr = new ShiftRegister(16);
	private final ChannelCodec cc = new ChannelCodec();
	private BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

	@Override
	public int available() throws IOException {
		return queue.size();
	}

	@Override
	public int read() throws IOException {
		try {
			return queue.take();
		} catch (InterruptedException ex) {
			throw new IOException(ex);
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int read = 0;
		while (available() > 0 && read < len) {
			b[off++] = (byte) read();
			read++;
		}
		return read;
	}

	public void write(boolean value) {
		sr.push(value);

		int data = sr.getData();

		if (cc.isValid(data)) {
			queue.add(cc.decode(data));
		}
	}
}
