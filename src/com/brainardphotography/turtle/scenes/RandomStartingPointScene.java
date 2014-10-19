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
		Turtle turtle = new Turtle(0, 0);

		double widthRange = getWidth() - turtle.getWidth();
		double heightRange = getHeight() - turtle.getHeight();

		turtle.reset(
				Math.random() * widthRange + (turtle.getWidth() / 2),
				Math.random() * heightRange + (turtle.getHeight() / 2)
		);

		return turtle;
	}

	@Override
	protected List<TurtleObject> createObjects() {
		List<TurtleObject> objects = new ArrayList<>();
		objects.add(new CenterLinesObject(this));
		return objects;
	}
}
