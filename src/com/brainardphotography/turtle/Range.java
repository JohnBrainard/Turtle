package com.brainardphotography.turtle;

import java.util.Iterator;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class Range implements Iterator<Double> {
	private final double from;
	private final double to;
	private final int steps;
	private double current;
	private int step = 0;

	private static final Range emptyRange = new Range(0, 0, 0);

	public Range(double from, double to, int steps) {
		this.from = from;
		this.to = to;
		this.steps = steps;
		this.current = from;
	}

	public double getFrom() {
		return from;
	}

	public double getTo() {
		return to;
	}

	public double getCurrent() {
		return current;
	}

	public double getSteps() {
		return steps;
	}

	public double getStepAmount() {
		return (to - from) / steps;
	}

	public int getStep() {
		return step;
	}

	public Double next() {
		if (!hasNext())
			throw new IllegalStateException("No values to retrieve");

		step++;
		current = ((to - from) / steps) * step + from;

		return current;
	}

	public Double nextOrCurrent() {
		if (hasNext())
			return next();

		return current;
	}

	public boolean hasNext() {
		return step < steps;
	}

	public static Range emptyRange() {
		return emptyRange;
	}

	public static void main(String[] args) {
		Range range = new Range(100, 90, 10);

		while(range.hasNext()) {
			System.out.println("Next is " + range.next());
		}
	}

	public String toString() {
		return String.format("Range[from:%f,to:%f,steps:%d,step%d,current%f]",
				from,
				to,
				steps,
				step,
				current);
	}

	public Range copy() {
		return new Range(this);
	}

	private Range(Range range) {
		this.from = range.from;
		this.to = range.to;
		this.step = range.step;
		this.steps = range.steps;
		this.current = range.current;
	}
}
