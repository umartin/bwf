package se.purplescout.purplemow.bwf.dsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin Andersson
 */
public class Receiver {

	public interface DataReceivedEventListener {
		void onDataReceived(int data, double signalStrength);
	}

	private final CostasLoop pll;
	private final AutomaticGainControl acg;
	private final BitInputStream stream = new BitInputStream();
	private double cycleValue = 0.0D;
	private boolean prevOsc;

	private List<DataReceivedEventListener> eventListeners = new ArrayList<DataReceivedEventListener>();

	public Receiver(double sampleFreq, double baseFreq) {
		pll = new CostasLoop(sampleFreq, baseFreq);
		acg = new AutomaticGainControl(sampleFreq, baseFreq / 20.0D);
	}

	public void write(double sample) {
		Output out = pll.pll(acg.process(sample));

		if (!prevOsc && asBool(out.getOscillator())) {
			stream.write(asBool(cycleValue));

			sendEventIfDataReady();

			cycleValue = 0.0D;
		}
		prevOsc = asBool(out.getOscillator());
		cycleValue += out.getAm();
	}

	private void sendEventIfDataReady() {
		try {
			if (stream.available() == 0) {
				return;
			}
			int data = stream.read();
			double signalStrength = 1.0D / acg.getGain();
			
			for (DataReceivedEventListener listener : eventListeners) {
				listener.onDataReceived(data, signalStrength);
			}

		} catch (IOException exc) {
			// Do nothing. Will never happen.
		}
	}

	public void addDataReceivedListener(DataReceivedEventListener listener) {
		eventListeners.add(listener);
	}

	private boolean asBool(double value) {
		return value > 0.0D;
	}

}
