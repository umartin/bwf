package se.purplescout.purplemow.bwf.dsp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class CostasLoopTest {

	private static final double SAMPLE_FREQ = 44100.0D;

	private static final String MESSAGE = "Hello bwf!";

	List<Boolean> dataList = new ArrayList<Boolean>();

	double[] in = new double[2048];


	@Before
	public void setup() throws IOException {

		for (int i = 0; i < 10; i++) {
			dataList.add(Boolean.TRUE);
		}

		Writer writer = new OutputStreamWriter(new BitOutputStream(new OutStream<Boolean>() {

			public void write(Boolean value) {
				dataList.add(value);
			}
		}));
		writer.write(MESSAGE);
		writer.flush();

		double[] sine = getSine(in.length);
		double[] data = getData(in.length);
		double[] noise = getNoise(in.length);

		for (int i = 0; i < in.length; i++) {
			in[i] = sine[i] * data[i] + noise[i];
		}
	}

	@Test
	public void test2() throws IOException {
		final List<Byte> bytes = new ArrayList<Byte>();
		Receiver rcv = new Receiver(SAMPLE_FREQ, 4410.0D);

		rcv.addDataReceivedListener(new Receiver.DataReceivedEventListener() {

			public void onDataReceived(int data, double signalStrength) {
				bytes.add((byte) data);
			}
		});

		for (double i : in) {
			rcv.write(i);
		}

		byte[] b = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) {
			b[i] = bytes.get(i);
		}
		String result = new String(b);
		System.out.println(result);
		Assert.assertEquals(MESSAGE, result);
	}
	
	double[] getData(int samples) {
		double[] data = new double[samples];

		boolean val = true;
		Iterator<Boolean> it = dataList.iterator();
		for (int i = 0; i < data.length; i++) {
			if (i % 10 == 0 && i > 20) {
				if (it.hasNext()) {
					val = it.next();
				} else {
					val = true;
				}
			}
			data[i] = val ? 1.0D : -1.0D;
		}
		return data;
	}

	double[] getSine(int samples) {
		double[] out = new double[samples];
		for (int l = 0; l < samples; l++) {
				out[l] = Math.sin(((double)l) * 2.0D * Math.PI * 0.1D);
		}
		return out;
	}

	double[] getNoise(int samples) {
		double[] out = new double[samples];
		Random rnd = new Random();
		for (int l = 0; l < samples; l++) {
			out[l] = rnd.nextDouble() * 0.2D;
		}
		return out;
	}
}
