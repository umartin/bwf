package se.purplescout.purplemow.bwf.dsp;

/**
 *
 * @author Martin Andersson
 */
public interface OutStream<T> {

	void write(T value);
}
