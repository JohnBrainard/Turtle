package com.brainardphotography.turtle.scenes;

import com.brainardphotography.turtle.objects.TurtleObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;

/**
 * Created by johnbrainard on 10/12/14.
 */
public abstract class WorldScene {
	private Canvas canvas;

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Paint getBackground() {
		return Color.BLACK;
	}

	public abstract List<TurtleObject> createObjects();
}
