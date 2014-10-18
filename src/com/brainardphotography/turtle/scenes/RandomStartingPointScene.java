package com.brainardphotography.turtle.scenes;

import com.brainardphotography.turtle.Turtle;
import com.brainardphotography.turtle.objects.CenterLinesObject;
import com.brainardphotography.turtle.objects.TurtleObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnbrainard on 10/17/14.
 */
public class RandomStartingPointScene extends DefaultWorldScene {

	@Override
	protected Turtle createTurtle() {
		return new Turtle(
				Math.random() * getWidth(),
				Math.random() * getHeight());
	}

	@Override
	protected List<TurtleObject> createObjects() {
		List<TurtleObject> objects = new ArrayList<>();
		objects.add(new CenterLinesObject(this));
		return objects;
	}
}
