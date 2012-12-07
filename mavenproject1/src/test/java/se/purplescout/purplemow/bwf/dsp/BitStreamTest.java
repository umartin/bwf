package se.purplescout.purplemow.bwf.dsp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class BitStreamTest {
	Boolean[] bits = new Boolean[]{true, true, true, true, true, false, false, true, true, false, false, false, false, false, true, false};
	String message = "a";

	@Test
	public void testOutputStream() throws IOException {
		final List<Boolean> result = new ArrayList<Boolean>();

		BitOutputStream out = new BitOutputStream(new OutStream<Boolean>() {

			public void write(Boolean value) {
				result.add(value);
			}
		});

		Writer writer = new OutputStreamWriter(out);
		writer.write(message);
		writer.flush();

		Assert.assertArrayEquals(bits, result.toArray(new Boolean[] {}));
	}

	@Test
	public void testInputStream() throws IOException {
		BitInputStream in = new BitInputStream();

		for (boolean b : bits) {
			in.write(b);
		}

		Assert.assertEquals(1, in.available());
		Assert.assertEquals(97, in.read());

		for (boolean b : bits) {
			in.write(b);
		}
		for (boolean b : bits) {
			in.write(b);
		}
		Assert.assertEquals(2, in.available());
		Assert.assertEquals(97, in.read());
		Assert.assertEquals(97, in.read());
		Assert.assertEquals(0, in.available());
	}

	@Test
	public void testStream() throws IOException {
		String msg = "aalkjs slkdjf ij wie\n";

		BitInputStream in = new BitInputStream();
		BitOutputStream out = new BitOutputStream(in);

		Writer writer = new OutputStreamWriter(out);
		writer.write(msg);
		writer.flush();

		Assert.assertEquals(msg.length(), in.available());
		
		Reader reader = new InputStreamReader(in);
		char[] buffer = new char[msg.length()];
		reader.read(buffer);

		StringBuilder sb = new StringBuilder();
		sb.append(buffer);

		String receivedMessage = sb.toString();


		Assert.assertEquals(msg, receivedMessage);
	}

}
