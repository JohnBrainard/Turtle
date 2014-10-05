package com.brainardphotography.turtle;

import groovy.lang.Script;

/**
 * Created by johnbrainard on 10/4/14.
 */
public abstract class TurtleScript extends Script {
	private Turtle turtle = null;

	private Turtle getTurtle() {
		if (turtle == null)
			turtle = (Turtle) getBinding().getVariable("turtle");

		return turtle;
	}

	public void move(Integer value) {
		Turtle turtle = getTurtle();
		turtle.move(value);
		while (turtle.isMoving())
			Thread.yield();
	}

	public void run(int value) {
		Turtle turtle = getTurtle();
		turtle.run(value);
		while (turtle.isMoving())
			Thread.yield();
	}

	public void turn(Integer value) {
		Turtle turtle = getTurtle();
		turtle.turn(value);
		while (turtle.isTurning())
			Thread.yield();
	}

	public void turnAndMove(int turnAmount, int moveAmount) {
		Turtle turtle = getTurtle();
		turtle.turn(turnAmount);
		turtle.move(moveAmount);
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

	public boolean isMoving() {
		return getTurtle().isMoving();
	}

	public boolean isTurning() {
		return getTurtle().isTurning();
	}
}
