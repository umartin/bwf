package se.purplescout.purplemow.bwf.dsp;

import java.util.LinkedList;

/**
 *
 * @author Martin Andersson
 */
public class ShiftRegister {

	private LinkedList<Boolean> data = new LinkedList<Boolean>();

	public ShiftRegister(int sizeInBits) {
		for (int i = 0; i < sizeInBits; i++) {
			data.add(Boolean.FALSE);
		}
	}

	public void push(boolean value) {
		data.addLast(value);
		data.removeFirst();
	}

	public boolean poll() {
		data.addLast(Boolean.FALSE);
		return data.pollFirst();
	}

	public void setData(int value) {
		for (int i = 0; i < data.size(); i++) {
			boolean val = (1 << i & value) != 0;
			data.set(data.size() - 1 - i, val);
		}
	}

	public int getData() {
		int result = 0;
		for (int i = 0; i < data.size(); i++)
		{
			int value = (data.get(data.size() - 1 - i) ? 1 : 0) << i;
			result = result | value;
		}

		return result;
	}

	public int getSizeInBits() {
		return data.size();
	}
}
