package se.purplescout.purplemow.bwf.dsp.impl;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Test;
import se.purplescout.purplemow.bwf.dsp.Filter;
import se.purplescout.purplemow.bwf.dsp.Oscillator;
import se.purplescout.purplemow.bwf.wave.WaveFileUtil;


/**
 *
 * @author Martin Andersson
 */
public class SimplePoleLPIIRFilterTest {

//	@Test
	public void test1() {
		Filter filter = new SinglePoleLPIIRFilter(0.025D);
		Oscillator oscillator = new SquareWaveOscillator(44100.0D);
		oscillator.setFrequency(10.0D);

		for (int i = 0; i < 40; i++) {
			double osc = oscillator.getValue();
			double out = filter.filter(osc);
			oscillator.step();
			System.out.println("in: " + osc + " out: " + out);
		}
	}
	
	@Test
	public void test() throws IOException, UnsupportedAudioFileException {
		Filter filter = new SinglePoleLPIIRFilter(44100.0D, 500.0D);
		double[] samples = WaveFileUtil.read16BitMonoWave("/home/martin/noise.wav");
		double[] out = new double[samples.length];
		for (int i = 0; i < samples.length; i++) {
			out[i] = filter.filter(samples[i]);
		}
		WaveFileUtil.write16BitMonoWave("/home/martin/filter.wav", out);
	}
}
