package se.purplescout.purplemow.bwf.dsp;

/**
 * Protocol:
 * 6 bit header 111110
 * 4 bit data XXXX
 * 1 bit 0
 * 4 bit data XXXX
 * 1 bit 0
 *
 * @author Martin Andersson
 */
public class ChannelCodec {

	public static final int BITS_PER_BYTE = 16;

	// 1111 1000 0000 0000
	private static final int HEADER_MASK = 0xf800;

	// 1111 1100 0010 0001
	private static final int VALIDATION_MASK = 0xfc21;

	boolean isValid(int data) {
		return (data & VALIDATION_MASK) == HEADER_MASK;
	}

	public int decode(int data) {
		int decodedData = (data >> 2 & 0xf0) | (data >> 1 & 0x0f);
		return isValid(data) ? decodedData : -1;
	}

	public int encode(int data) {
		data = (data & 0xf0) << 2 | (data & 0x0f) << 1;
		data = data | HEADER_MASK;
		return data;
	}
}
