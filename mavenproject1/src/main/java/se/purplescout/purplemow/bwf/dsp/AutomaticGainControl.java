package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public class AutomaticGainControl {

	private final SinglePoleLPIIRFilter filter;
	private double gain = 1.0D;

	public AutomaticGainControl(double sampleFrequency, double filterFrequency ) {
		filter = new SinglePoleLPIIRFilter(sampleFrequency, filterFrequency);
	}

	public double process(double sample) {
		calculateGain(sample);
		return sample * gain;
	}

	private void calculateGain(double sample) {
		double avg = filter.filter(Math.abs(sample));
		gain = 1.0D / avg;
	}

	public double getGain() {
		return gain;
	}
}
