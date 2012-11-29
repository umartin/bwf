package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public interface Oscillator {

	void setFrequency(double cycleTime);

	void step();

	double getValue();

	double get90DegreesValue();
}
