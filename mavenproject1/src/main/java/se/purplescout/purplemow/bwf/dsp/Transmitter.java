package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public class Transmitter implements OutStream<Boolean> {

	private final int samplesPerCycle;
	private final OutStream<Double> writeTo;

	public Transmitter(int samplesPerCycle, OutStream<Double> writeTo) {
		this.samplesPerCycle = samplesPerCycle;
		this.writeTo = writeTo;
	}

	@Override
	public void write(Boolean value) {
		double val = toDouble(value);
		int i = 0;
		
		for (; i < (samplesPerCycle / 2); i++) {
			writeTo.write(val);
		}

		val = toDouble(!value);

		for (; i < samplesPerCycle; i++) {
			writeTo.write(val);
		}
	}

	double toDouble(boolean value) {
		return value ? -1.0D : 1.0D;
	}
}
