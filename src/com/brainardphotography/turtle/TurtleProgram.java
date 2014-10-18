package com.brainardphotography.turtle;

import com.brainardphotography.turtle.scenes.WorldScene;
import javafx.scene.canvas.Canvas;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class TurtleProgram implements Runnable {
	private Consumer<Exception> errorConsumer;
	private WorldScene scene;
	private Turtle turtle;

	public TurtleProgram() {
	}

	public WorldScene getScene() {
		return scene;
	}

	public void setScene(WorldScene scene) {
		this.scene = scene;
		this.turtle = scene.getTurtle();
	}

	public void setErrorConsumer(Consumer<Exception> errorConsumer) {
		this.errorConsumer = errorConsumer;
	}


	@Override
	public void run() {
		turtle.turn(-90);

		while (turtle.isTurning())
			Thread.yield();

		turtle.turn(360);
		turtle.move(360);
	}

	protected void handleError(Exception exception) {
		exception.printStackTrace(System.err);
		if (this.errorConsumer != null)
			this.errorConsumer.accept(exception);
	}
}
