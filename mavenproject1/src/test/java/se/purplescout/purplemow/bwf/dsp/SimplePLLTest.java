package se.purplescout.purplemow.bwf.dsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Before;
import org.junit.Test;
import se.purplescout.purplemow.bwf.wave.WaveFileUtil;

/**
 *
 * @author Martin Andersson
 */
public class SimplePLLTest {

	private static final double SAMPLE_FREQ = 44100.0D;

	List<Boolean> dataList = new ArrayList<Boolean>();

	double[] in = new double[1024];
	double[] sine = getSine(in.length);
	double[] data = getData(in.length);
	double[] noise = getNoise(in.length);


	@Before
	public void setup() throws IOException {

		for (int i = 0; i < in.length; i++) {
			in[i] = sine[i] * data[i] + noise[i];
		}

		WaveFileUtil.write16BitMonoWave("/home/martin/in.wav", in);
		WaveFileUtil.write16BitMonoWave("/home/martin/sine.wav", sine);
		WaveFileUtil.write16BitMonoWave("/home/martin/data.wav", data);
	}

	@Test
	public void test1() throws IOException, UnsupportedAudioFileException {
		SimplePLL pll = new SimplePLL(SAMPLE_FREQ, 4410.0D);

		double[] oscOut = new double[in.length];
		double[] fmOut = new double[in.length];
		double[] amOut = new double[in.length];

		for (int i = 0; i < in.length; i++) {
			Output out = pll.pll(in[i]);

			oscOut[i] = out.getOscillator();
			fmOut[i] = out.getFm();
			amOut[i] = out.getAm();
		}

		List<Boolean> result = parseData(oscOut, amOut);

		System.out.println("result" + result);
		System.out.println("expected" + dataList);

		WaveFileUtil.write16BitMonoWave("/home/martin/osc.wav", oscOut);
		WaveFileUtil.write16BitMonoWave("/home/martin/am.wav", amOut);
		WaveFileUtil.write16BitMonoWave("/home/martin/fm.wav", fmOut);
	}

	List<Boolean> parseData(double[] osc, double[] fm) {
		List<Boolean> data = new ArrayList<Boolean>();
		for (int i = 1; i < osc.length; i++) {
			if (osc[i - 1] > 0 && osc[i] < 0) {
				data.add(fm[i] > 0.0D);
			}
		}
		return data;
	}
	
	double[] getData(int samples) {
		double[] data = new double[samples];
		Random rnd = new Random();
		boolean val = true;
		dataList.add(val);
		for (int i = 0; i < data.length; i++) {
			if (i % 10 == 0 && i > 20) {
				val = rnd.nextBoolean();
				dataList.add(val);
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
