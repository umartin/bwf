/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.purplescout.purplemow.bwf.dsp.impl;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import se.purplescout.purplemow.bwf.dsp.Oscillator;
import se.purplescout.purplemow.bwf.dsp.PLL;

/**
 *
 * @author martin
 */
public class SquareWaveOscillatorTest {

	@Test
	public void test1() {
		Oscillator oscillator = new SquareWaveOscillator(44100.0D);
		oscillator.setFrequency(10.0D);

		for (int i = 0; i < 40; i++) {
			System.out.println(oscillator.getValue() + " 90: " + oscillator.get90DegreesValue());
			oscillator.step();
		}
	}
}
