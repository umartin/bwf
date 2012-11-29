package se.purplescout.purplemow.bwf.dsp.impl;

import se.purplescout.purplemow.bwf.dsp.Filter;

/**
 *
 * @author Martin Andersson
 */
public class SinglePoleLPIIRFilter implements Filter {

	private final double damping;
	private double lastVal;

	public SinglePoleLPIIRFilter(double damping) {
		this.damping = damping;
	}

	public SinglePoleLPIIRFilter(double sampleFreq, double filterFreq) {
		double rc = 1.0D / (2.0D * Math.PI * filterFreq);
		double dt = 1.0D / sampleFreq;
		damping = dt / (rc + dt);

		System.out.println("damping: " + damping);
	}

	public double filter(double input) {
		lastVal = lastVal + damping * (input - lastVal);
		//lastVal = damping * input + (1.0D - damping) * lastVal;
		return lastVal;
	}
}
