package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public class SinglePoleLPIIRFilter {

	private final double damping;
	private double lastVal;

	public SinglePoleLPIIRFilter(double sampleFreq, double filterFreq) {
		double rc = 1.0D / (2.0D * Math.PI * filterFreq);
		double dt = 1.0D / sampleFreq;
		damping = dt / (rc + dt);
	}

	public double filter(double input) {
		lastVal = lastVal + damping * (input - lastVal);
		return lastVal;
	}
}
