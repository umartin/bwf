/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.purplescout.purplemow.bwf.dsp.impl;

import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import se.purplescout.purplemow.bwf.dsp.Filter;
import se.purplescout.purplemow.bwf.dsp.Oscillator;
import se.purplescout.purplemow.bwf.dsp.PLL;
import se.purplescout.purplemow.bwf.wave.WaveFileUtil;

/**
 *
 * @author martin
 */
public class SimplePLLTest {

	@Test
	public void test1() throws IOException, UnsupportedAudioFileException {
		PLL pll = new SimplePLL(44100.0D);
//		Oscillator oscillator = new SquareWaveOscillator();
//		oscillator.setFrequency(10.0D);

		double[] in = genTest();
		double[] oscOut = new double[in.length];
		double[] fmOut = new double[in.length];
		double[] amOut = new double[in.length];

		for (int i = 0; i < in.length; i++) {
//			double osc = oscillator.getValue();
			PLL.Output out = pll.pll(in[i]);

			oscOut[i] = out.getOscillator();
			fmOut[i] = out.getFm();
			amOut[i] = out.getAm();

//			oscillator.step();
		}

		WaveFileUtil.write16BitMonoWave("/home/martin/osc.wav", oscOut);
		WaveFileUtil.write16BitMonoWave("/home/martin/am.wav", amOut);
		WaveFileUtil.write16BitMonoWave("/home/martin/fm.wav", fmOut);
	}

	double[] genTest() throws IOException {
//		boolean[] data = new boolean[100];
//		Random rnd = new Random();
//		for (int i = 0; i < data.length; i++) {
//			data[i] = rnd.nextBoolean();
//		}


		double[] samples = new double[1024];
		double[] sine = getSine(samples.length);
		double[] data = getData(samples.length);
		double[] noise = getNoise(samples.length);

		for (int i = 0; i < samples.length; i++) {
			samples[i] = sine[i] * data[i] + noise[i];
		}

//		for (int i = 0; i < data.length; i++) {
//			for (int l = 0; l < 10; l++) {
//				 double val = Math.sin(((double)l) * 2.0D * Math.PI * 0.1D);
//				 if (data[i]) {
//					 val = -1.0D * val;
//				 }
//				 samples[i * 10 + l] = val;
//			}
//		}
		WaveFileUtil.write16BitMonoWave("/home/martin/in.wav", samples);
		WaveFileUtil.write16BitMonoWave("/home/martin/sine.wav", sine);
		WaveFileUtil.write16BitMonoWave("/home/martin/data.wav", data);

		return samples;
	}
	
	double[] getData(int samples) {
		double[] data = new double[samples];
		Random rnd = new Random();
		boolean val = true;
		for (int i = 0; i < data.length; i++) {
			if (i % 10 == 0 && i > 20) {
				val = rnd.nextBoolean();
			}
			data[i] = val ? 1.0D : -1.0D;
		}
		return data;
	}

	double[] getSine(int samples) {
		double[] out = new double[samples];
		for (int l = 0; l < samples; l++) {
				out[l] = Math.sin(((double)l) * 2.0D * Math.PI * 0.1D);
		}
		return out;
	}

	double[] getNoise(int samples) {
		double[] out = new double[samples];
		Random rnd = new Random();
		for (int l = 0; l < samples; l++) {
			out[l] = rnd.nextDouble() * 0.2D;
		}
		return out;
	}
}
