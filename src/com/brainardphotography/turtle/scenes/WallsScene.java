package com.brainardphotography.turtle.scenes;

import com.beust.jcommander.internal.Lists;
import com.brainardphotography.turtle.objects.TurtleObject;
import com.brainardphotography.turtle.objects.WallObject;
import javafx.scene.canvas.Canvas;

import java.util.List;

/**
 * Created by johnbrainard on 10/12/14.
 */
public class WallsScene extends WorldScene {
	@Override
	public List<TurtleObject> createObjects() {
		List<TurtleObject> objects = Lists.newArrayList();
		Canvas canvas = getCanvas();

		double wallHeight = canvas.getHeight() / 2 - 100;
		objects.add(new WallObject(canvas.getWidth() / 2 - 20, 0, 40, wallHeight));
		objects.add(new WallObject(canvas.getWidth() / 2 - 20, canvas.getHeight() - wallHeight, 40, wallHeight));
		return objects;
	}
}
