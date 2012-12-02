package se.purplescout.purplemow.bwf.dsp;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Martin Andersson
 */
public class ShiftRegisterTest {

	@Test
	public void test1() {
		ShiftRegister sr = new ShiftRegister(4);

		Assert.assertEquals(0, sr.getData());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(0, sr.getData());

		sr = new ShiftRegister(4);
		sr.setData(1);
		Assert.assertEquals(1, sr.getData());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(true, sr.poll());

		sr = new ShiftRegister(4);
		sr.setData(8);
		Assert.assertEquals(8, sr.getData());
		Assert.assertEquals(true, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());
		Assert.assertEquals(false, sr.poll());

		sr = new ShiftRegister(4);
		sr.push(false);
		sr.push(false);
		sr.push(false);
		sr.push(true);
		Assert.assertEquals(1, sr.getData());
	}
}
