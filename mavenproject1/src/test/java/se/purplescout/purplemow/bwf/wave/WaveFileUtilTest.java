/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.purplescout.purplemow.bwf.wave;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author martin
 */
public class WaveFileUtilTest {

	public WaveFileUtilTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testImport() throws IOException, UnsupportedAudioFileException {
		double [] samples = WaveFileUtil.read16BitMonoWave("/home/martin/5ksin.wav");
		for (double sample : samples) {
			System.out.println("val: " + sample);
		}
	}

	@Test
	public void testExport() throws IOException {
		double [] samples = new double[] {0.0D, 0.5D, 0.99D, 0.5D, 0.0D, -0.5D, -0.9D};
		WaveFileUtil.write16BitMonoWave("/home/martin/test.wav", samples);
	}
}
