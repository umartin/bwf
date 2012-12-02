package se.purplescout.purplemow.bwf.dsp;

import java.util.Arrays;
import junit.framework.Assert;

/**
 *
 * @author Martin Andersson
 */
public class TestUtil {

	public static int countFrequency(double sampleFreq, double[] samples) {
		int count = countFrequency(samples);
		return (int) (count * (sampleFreq / samples.length));
	}

	private static int countFrequency(double[] samples) {
		int count = 0;
		for (int i = 1; i < samples.length; i++) {
			if (samples[i - 1] > 0 && samples[i] < 0) {
				count++;
			}
		}
		return count;
	}

	public static double calculateAvg(double[] samples) {
		double avg = calcSum(samples);
		return avg /= (double) samples.length;
	}

	private static double calcSum(double[] samples) {
		if (samples.length == 1) {
			return Math.abs(samples[0]);
		} else {
			int middle = samples.length / 2;
			double[] lower = Arrays.copyOfRange(samples, 0, middle);
			double[] upper = Arrays.copyOfRange(samples, middle, samples.length);

			Assert.assertEquals(samples.length, lower.length + upper.length);
			return calcSum(lower) + calcSum(upper);
		}
	}

	public static double asDecibel(double... value) {
		return 20.0D * Math.log10(calculateAvg(value));
	}

	public static double[] generateSine(double sampleFreq, double oscFreq, int nbrOfSamples) {
		double[] samples = new double[nbrOfSamples];

		for (int i = 0; i < samples.length; i++) {
			samples[i] = Math.sin(oscFreq / sampleFreq * ((double) i) * 2.0D * Math.PI);
		}

		double average = 1.0D / calculateAvg(samples);
		for (int i = 0; i< samples.length; i++) {
			samples[i] = samples[i] * average;
		}
		return samples;
	}

	public static boolean withinRange(double expected, double actual, double tolerance) {
		return Math.abs((expected - actual) / expected) < tolerance;
	}

}
