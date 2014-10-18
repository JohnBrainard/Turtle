package com.brainardphotography.turtle.objects;

import com.brainardphotography.turtle.scenes.WorldScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by johnbrainard on 10/12/14.
 */
public class CenterLinesObject extends TurtleObject {
	private final WorldScene scene;
	public CenterLinesObject(WorldScene scene) {
		this.scene = scene;
	}

	@Override
	public void draw(GraphicsContext gc) {
		double centerX = scene.getWidth() / 2;
		double centerY = scene.getHeight() / 2;

		gc.save();
		gc.setStroke(Color.GRAY);
		gc.strokeLine(0, centerY, scene.getWidth(), centerY);
		gc.strokeLine(centerX, 0, centerX, scene.getHeight());
		gc.restore();
	}
}
