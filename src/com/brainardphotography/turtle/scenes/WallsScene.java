package com.brainardphotography.turtle.scenes;

import com.brainardphotography.turtle.objects.TurtleObject;
import com.brainardphotography.turtle.objects.WallObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnbrainard on 10/12/14.
 */
public class WallsScene extends DefaultWorldScene {
	@Override
	public List<TurtleObject> createObjects() {
		List<TurtleObject> objects = new ArrayList<>();

		double wallHeight = getHeight() / 2 - 100;
		objects.add(new WallObject(getWidth() / 2 - 20, 0, 40, wallHeight));
		objects.add(new WallObject(getWidth() / 2 - 20, getHeight() - wallHeight, 40, wallHeight));
		return objects;
	}
}
