package com.brainardphotography.turtle;

import com.brainardphotography.turtle.scenes.WorldScene;
import groovy.lang.Script;
import javafx.scene.canvas.Canvas;

import java.math.BigDecimal;

/**
 * Created by johnbrainard on 10/4/14.
 */
public abstract class TurtleScript extends Script {
	private Turtle turtle = null;
	private WorldScene scene = null;

	private Turtle getTurtle() {
		if (turtle == null)
			turtle = getScene().getTurtle();
		return turtle;
	}

	private WorldScene getScene() {
		if (scene == null)
			scene = (WorldScene) getBinding().getVariable("scene");
		return scene;
	}

	public void move(Number value) {
		Turtle turtle = getTurtle();
		turtle.move(value.intValue());
		while (turtle.isMoving())
			Thread.yield();
	}

	public void run(Number value) {
		Turtle turtle = getTurtle();
		turtle.run(value.intValue());
		while (turtle.isMoving())
			Thread.yield();
	}

	public void turn(Number value) {
		Turtle turtle = getTurtle();
		turtle.turn(value.intValue());
		while (turtle.isTurning())
			Thread.yield();
	}

	public void turnAndMove(Number turnAmount, Number moveAmount) {
		Turtle turtle = getTurtle();
		turtle.turn(turnAmount.intValue());
		turtle.move(moveAmount.intValue());
		while (turtle.isTurning() || turtle.isMoving())
			Thread.yield();
	}

	public void rest(int value) {
		long restUntil = System.currentTimeMillis() + (value * 1000);
		while (System.currentTimeMillis() < restUntil)
			Thread.yield();
	}

	public void poop() {
		getTurtle().poop();
	}

	public void waitForTurn() {
		Turtle turtle = getTurtle();
		while (turtle.isTurning()) {
			Thread.yield();
		}
	}

	public void waitForMove() {
		Turtle turtle = getTurtle();
		while (turtle.isMoving()) {
			Thread.yield();
		}
	}

	public int getWorldWidth() {
		return new Double(getScene().getWidth()).intValue();
	}

	public int getWorldHeight() {
		return new Double(getScene().getHeight()).intValue();
	}

	public int getTurtleWidth() {
		return new Double(getTurtle().getWidth()).intValue();
	}

	public int getTurtleHeight() {
		return new Double(getTurtle().getHeight()).intValue();
	}

	public int getAngle() {
		return new Double(getTurtle().getAngle()).intValue();
	}

	public int getX() {
		return new Double(getTurtle().getX()).intValue();
	}

	public int getY() {
		return new Double(getTurtle().getY()).intValue();
	}

	public boolean isMoving() {
		return getTurtle().isMoving();
	}

	public boolean isTurning() {
		return getTurtle().isTurning();
	}

	public void alert(String message) {
		getScene().alert(message);
	}

	public Number abs(Number value) {
		if (value instanceof Double)
			return Math.abs(value.doubleValue());
		else if (value instanceof Long)
			return Math.abs(value.longValue());
		else if (value instanceof Float)
			return Math.abs(value.floatValue());
		else if (value instanceof Integer)
			return Math.abs(value.intValue());
		else
			alert("Can't get absolute value of: " + value);
		return 0;
	}

	public Number remainder(Number dividend, Number divisor) {
		if (dividend instanceof Double)
			return dividend.doubleValue() % divisor.doubleValue();
		else if (dividend instanceof Long)
			return dividend.longValue() % divisor.longValue();
		else if (dividend instanceof Float)
			return dividend.floatValue() % divisor.floatValue();
		else if (dividend instanceof Integer)
			return dividend.intValue() % divisor.intValue();
		return 0;
	}
}
