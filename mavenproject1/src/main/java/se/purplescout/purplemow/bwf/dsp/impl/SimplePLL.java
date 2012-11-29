package se.purplescout.purplemow.bwf.dsp.impl;

import se.purplescout.purplemow.bwf.dsp.Filter;
import se.purplescout.purplemow.bwf.dsp.Oscillator;
import se.purplescout.purplemow.bwf.dsp.PLL;
import se.purplescout.purplemow.bwf.dsp.PhaseDetector;

/**
 *
 * @author Martin Andersson
 */
public class SimplePLL implements PLL {

	private Oscillator oscillator;
	private Filter filter1;
	private Filter filter2;
	private Filter filter3;

	private final double initialCycleTime = 10.0D;

	public SimplePLL(double sampleFreq) {
		oscillator = new SquareWaveOscillator(sampleFreq);
		oscillator.setFrequency(initialCycleTime);
		filter1 = new SinglePoleLPIIRFilter(sampleFreq, 2500.0D);
		filter2 = new SinglePoleLPIIRFilter(sampleFreq, 2500.0D);
		filter3 = new SinglePoleLPIIRFilter(sampleFreq, 10000.0D);
	}

	public Output pll(double input) {
		double fm = fmPath(input);
		double am = amPath(input);
		double corr = am * fm;
		corr = filter3.filter(corr);
		corr = fm;
		Output output = new Output(oscillator.getValue(), fm, am);
		oscillator.setFrequency(initialCycleTime + (initialCycleTime * corr * 0.2D));
		oscillator.step();
		return output;
	}

	double fmPath(double input) {
		return filter1.filter(input * oscillator.get90DegreesValue());
	}

	double amPath(double input) {
		return filter2.filter(input * oscillator.getValue());
	}
}
