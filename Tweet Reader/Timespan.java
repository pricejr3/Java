/*
 Jarred Price

 */

package twitter;

import java.time.Instant;

/**
 * Immutable datatype representing an interval starting from one date/time and
 * ending at a later date/time. The interval includes its endpoints.
 * 
 * DO NOT CHANGE THIS CLASS.
 */
public class Timespan {

	private final Instant start;
	private final Instant end;

	/**
	 * Make a Timespan.
	 * 
	 * @param start
	 *            starting date/time
	 * @param end
	 *            ending date/time. Requires end >= start.
	 */
	public Timespan(Instant start, Instant end) {
		if (start.isAfter(end)) {
			throw new IllegalArgumentException("requires start <= end");
		}
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the starting point of the interval
	 */
	public Instant getStart() {
		return start;
	}

	/**
	 * @return the ending point of the interval
	 */
	public Instant getEnd() {
		return end;
	}

}
