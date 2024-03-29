package se.purplescout.purplemow.bwf.dsp;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Martin Andersson
 */
public class BitOutputStream extends OutputStream {

	private final ShiftRegister sr = new ShiftRegister(ChannelCodec.BITS_PER_BYTE);
	private final ChannelCodec cc = new ChannelCodec();
	private final OutStream<Boolean> bitWriter;

	public BitOutputStream(OutStream<Boolean> bitWriter) {
		this.bitWriter = bitWriter;
	}

	@Override
	public void write(int data) throws IOException {

		sr.setData(cc.encode(data));

		for (int i = 0; i < sr.getSizeInBits(); i++) {
			bitWriter.write(sr.poll());
		}
	}
}
