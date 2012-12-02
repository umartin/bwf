package se.purplescout.purplemow.bwf;

import java.io.IOException;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import se.purplescout.purplemow.bwf.wave.WaveFileUtil;

/**
 *
 * @author Martin Andersson
 */
public class GenDataTest {

	public GenDataTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

//	@Test
	public void testGenSignal() throws IOException {
		boolean[] data = new boolean[100];
		Random rnd = new Random();
		for (int i = 0; i < data.length; i++) {
			data[i] = rnd.nextBoolean();
		}


		double[] samples = new double[1000];

		for (int i = 0; i < data.length; i++) {
			for (int l = 0; l < 10; l++) {
				 double val = Math.sin(((double)l) * 2.0D * Math.PI * 0.1D);
				 if (data[i]) {
					 val = -1.0D * val;
				 }
				 samples[i * 10 + l] = val;
			}
		}

		WaveFileUtil.write16BitMonoWave("/home/martin/in.wav", samples);
	}
}
