package se.purplescout.purplemow.bwf.dsp;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class SquareWaveOscillatorTest {

	private static final double SAMPLE_FREQ = 44100.0D;

	@Test
	public void test1() {
		double testFreq = 4410.0D;

		SquareWaveOscillator oscillator = new SquareWaveOscillator(SAMPLE_FREQ, testFreq);

		double[] samples = new double[100000];

		for (int i = 0; i < samples.length; i++) {
			samples[i] = oscillator.getValue();
			oscillator.step();
		}

		int freq = TestUtil.countFrequency(SAMPLE_FREQ, samples);

		Assert.assertEquals(testFreq, (double) freq, 10.0D);
	}
}
