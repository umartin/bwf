package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public class SimplePLL {

	private SquareWaveOscillator oscillator;
	private SinglePoleLPIIRFilter filter1;
	private SinglePoleLPIIRFilter filter2;
	private SinglePoleLPIIRFilter filter3;

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
