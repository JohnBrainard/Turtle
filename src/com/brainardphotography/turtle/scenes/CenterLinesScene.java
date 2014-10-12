package com.brainardphotography.turtle.scenes;

import com.beust.jcommander.internal.Lists;
import com.brainardphotography.turtle.objects.CenterLinesObject;
import com.brainardphotography.turtle.objects.TurtleObject;

import java.util.List;

/**
 * Created by johnbrainard on 10/12/14.
 */
public class CenterLinesScene extends WorldScene{

	@Override
	public List<TurtleObject> createObjects() {
		List<TurtleObject> objects = Lists.newArrayList();
		objects.add(new CenterLinesObject(getCanvas()));
		return objects;
	}
}
