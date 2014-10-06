package com.brainardphotography.turtle;

import javafx.scene.canvas.Canvas;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class TurtleProgram implements Runnable {
	private final Turtle turtle;
	private final Canvas canvas;

	public TurtleProgram(Turtle turtle, Canvas canvas) {
		this.turtle = turtle;
		this.canvas = canvas;
	}
	private Consumer<Exception> errorConsumer;

	@Override
	public void run() {
		turtle.turn(-90);

		while (turtle.isTurning())
			Thread.yield();

		turtle.turn(360);
		turtle.move(360);
	}

	public void setErrorConsumer(Consumer<Exception> errorConsumer) {
		this.errorConsumer = errorConsumer;
	}

	protected void handleError(Exception exception) {
		exception.printStackTrace(System.err);
		if (this.errorConsumer != null)
			this.errorConsumer.accept(exception);
	}

	protected Turtle getTurtle() {
		return this.turtle;
	}
	protected Canvas getCanvas() { return this.canvas; }
}
