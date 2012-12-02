package se.purplescout.purplemow.bwf.dsp;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class ChannelCodecTest {

	@Test
	public void testEncode() {
		ChannelCodec cc = new ChannelCodec();

		int encoded = cc.encode(0);
		Assert.assertEquals(0xf800, encoded);
	}

	public void testDecode() {
		ChannelCodec cc = new ChannelCodec();

		int[] data = new int[256];
		int[] encoded = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = i;
			encoded[i] = cc.encode(data[i]);
		}

		int[] decoded = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			decoded[i] = cc.decode(encoded[i]);
		}

		Assert.assertArrayEquals(data, decoded);
	}
}
