package se.purplescout.purplemow.bwf.dsp;

import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class SquareWaveOscillatorTest {

	@Test
	public void test1() {
		SquareWaveOscillator oscillator = new SquareWaveOscillator(44100.0D);
		oscillator.setFrequency(10.0D);

		for (int i = 0; i < 40; i++) {
			System.out.println(oscillator.getValue() + " 90: " + oscillator.get90DegreesValue());
			oscillator.step();
		}
	}
}
