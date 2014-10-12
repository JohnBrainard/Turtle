package com.brainardphotography.turtle.objects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by johnbrainard on 10/12/14.
 */
public class CenterLinesObject extends TurtleObject {
	private final Canvas canvas;
	public CenterLinesObject(Canvas canvas)
	{
		this.canvas = canvas;
	}

	@Override
	public void draw(GraphicsContext gc) {
		double centerX = canvas.getWidth() / 2;
		double centerY = canvas.getHeight() / 2;

		gc.save();
		gc.setStroke(Color.GRAY);
		gc.strokeLine(0, centerY, canvas.getWidth(), centerY);
		gc.strokeLine(centerX, 0, centerX, canvas.getHeight());
		gc.restore();
	}
}
