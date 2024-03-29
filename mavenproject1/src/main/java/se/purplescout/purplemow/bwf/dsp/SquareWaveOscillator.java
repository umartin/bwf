package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public class SquareWaveOscillator {

	private double cycleTime;
	private double stepCounter;

	private final double sampleFreq;
	private final double baseFreq;

	public SquareWaveOscillator(double sampleFreq, double baseFreq) {
		this.sampleFreq = sampleFreq;
		this.baseFreq = baseFreq;

		cycleTime = sampleFreq / baseFreq;
	}

	public void setFrequency(double cycleTime) {
		this.cycleTime = cycleTime;
	}

	public void step() {
		stepCounter += 1 / cycleTime;
		stepCounter %= 1;
	}

	public double getValue() {
		return stepCounter < 0.5D ? 1.0D : -1.0D;
	}

	public double get90DegreesValue() {
		return stepCounter >= 0.25D && stepCounter < 0.75D ? 1.0D : -1.0D;
	}

}
