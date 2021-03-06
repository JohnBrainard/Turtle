package com.brainardphotography.turtle.scenes;

import com.brainardphotography.turtle.objects.CenterLinesObject;
import com.brainardphotography.turtle.objects.TurtleObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnbrainard on 10/12/14.
 */
public class CenterLinesScene extends DefaultWorldScene {
	@Override
	public List<TurtleObject> createObjects() {
		List<TurtleObject> objects = new ArrayList<>();
		objects.add(new CenterLinesObject(this));
		return objects;
	}
}
