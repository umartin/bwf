package se.purplescout.purplemow.bwf.wave;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Martin Andersson
 */
public class WaveFileUtil {

	private WaveFileUtil() {
	}

	public static double[] read16BitMonoWave(String file) throws IOException, UnsupportedAudioFileException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));

		System.out.println("" + ais.getFormat());
		byte[] frame = new byte[ais.getFormat().getFrameSize()];
		double[] samples = new double[(int)ais.getFrameLength()];
		int index = 0;
		while(ais.read(frame) != -1) {
			samples[index++] = toDouble(frame);
		}

		return samples;
	}

	private static double toDouble(byte[] frame) {
		short i = 0;
		i |= frame[1] & 0xFF;
		i <<= 8;
		i |= frame[0] & 0xFF;
		double ret = (double)i / Short.MAX_VALUE;
		return ret;
	}

	private static byte[] toByteArray(double sample) {
		short i = (short)(sample * Short.MAX_VALUE);
		byte[] frame = new byte[2];
		frame[1] = (byte)(i >>> 8);
		frame[0] = (byte)(i);
		return frame;
	}

	public static void write16BitMonoWave(String file, double[] samples) throws IOException {
		byte[] bytes = new byte[samples.length * 2];
		for (int i = 0; i < samples.length; i++) {
			byte[] frame = toByteArray(samples[i]);
			bytes[i * 2] = frame[0];
			bytes[(i * 2) + 1] = frame[1];
		}

		InputStream is = new ByteArrayInputStream(bytes);
		AudioInputStream ais = new AudioInputStream(is, new AudioFormat(44100.0f, 16, 1, true, false), samples.length);
		AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(file));
	}

}
