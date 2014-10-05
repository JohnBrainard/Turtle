package com.brainardphotography.turtle;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class GroovyTurtleProgram extends TurtleProgram {
	private final File scriptLocation;

	public GroovyTurtleProgram(Turtle turtle, File scriptLocation) {
		super(turtle);

		this.scriptLocation = scriptLocation;
	}

	@Override
	public void run() {
		CompilerConfiguration cc = new CompilerConfiguration();
		cc.setScriptBaseClass(TurtleScript.class.getName());
		GroovyShell shell = new GroovyShell(cc);
		shell.setVariable("turtle", getTurtle());
		try {
			shell.run(scriptLocation, new String[] {});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
