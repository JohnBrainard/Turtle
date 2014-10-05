package com.brainardphotography.turtle;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import org.codehaus.groovy.ant.Groovy;

import java.io.IOException;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class TurtleProgram implements Runnable {
	private final Turtle turtle;

	public TurtleProgram(Turtle turtle) {
		this.turtle = turtle;
	}

	@Override
	public void run() {
		turtle.turn(-90);

		while (turtle.isTurning())
			Thread.yield();

		turtle.turn(360);
		turtle.move(360);
	}

	protected Turtle getTurtle() {
		return this.turtle;
	}
}
