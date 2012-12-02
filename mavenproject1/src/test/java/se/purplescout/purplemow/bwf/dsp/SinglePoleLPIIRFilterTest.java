package se.purplescout.purplemow.bwf.dsp;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class SinglePoleLPIIRFilterTest {

	private static final double SAMPLE_FREQ = 44100.0D;
	private static final double OSC_FREQ = 1000.D / 2.0D;

	@Test
	public void testFilter() {
		SinglePoleLPIIRFilter filter = new SinglePoleLPIIRFilter(SAMPLE_FREQ, OSC_FREQ);

		double level = doFilter(filter, TestUtil.generateSine(SAMPLE_FREQ, OSC_FREQ / 4.0D, 44100));
		Assert.assertEquals(0.0D, level, 0.5D);

		level = doFilter(filter, TestUtil.generateSine(SAMPLE_FREQ, OSC_FREQ / 2.0D, 44100));
		Assert.assertEquals(-1.0D, level, 0.5D);

		level = doFilter(filter, TestUtil.generateSine(SAMPLE_FREQ, OSC_FREQ, 44100));
		Assert.assertEquals(-3.0D, level, 0.5D);

		level = doFilter(filter, TestUtil.generateSine(SAMPLE_FREQ, OSC_FREQ * 2.0D, 44100));
		Assert.assertEquals(-7.0D, level, 0.5D);

		level = doFilter(filter, TestUtil.generateSine(SAMPLE_FREQ, OSC_FREQ * 4.0D, 44100));
		Assert.assertEquals(-12.0D, level, 1.0D);
	}

	private double doFilter(SinglePoleLPIIRFilter filter, double[] signal) {
		double[] out = new double[signal.length];
		for (int i = 0; i < signal.length; i++) {
			out[i] = filter.filter(signal[i]);
		}
		return TestUtil.asDecibel(out);
	}
}
