package com.brainardphotography.turtle;

import groovy.lang.GroovyShell;
import javafx.scene.canvas.Canvas;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class GroovyTurtleProgram extends TurtleProgram {
	private final File scriptLocation;

	public GroovyTurtleProgram(File scriptLocation) {
		super();

		this.scriptLocation = scriptLocation;
	}

	@Override
	public void run() {
		CompilerConfiguration cc = new CompilerConfiguration();
		cc.setScriptBaseClass(TurtleScript.class.getName());
		GroovyShell shell = new GroovyShell(cc);
		shell.setVariable("scene", getScene());
		try {
			TurtleScript script = (TurtleScript) shell.parse(scriptLocation);
			script.run();
		} catch (Exception e) {
			handleError(e);
		}
	}
}
