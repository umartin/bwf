package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public class Output {
	private final double oscillator;
	private final double fm;
	private final double am;

	public Output(double oscillator, double fm, double am) {
		this.oscillator = oscillator;
		this.fm = fm;
		this.am = am;
	}

	public double getAm() {
		return am;
	}

	public double getFm() {
		return fm;
	}

	public double getOscillator() {
		return oscillator;
	}

	@Override
	public String toString() {
		return "Am: " + am + " Fm: " + fm + " Osc: " + oscillator;
	}

}
