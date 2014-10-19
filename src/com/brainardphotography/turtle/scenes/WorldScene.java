package com.brainardphotography.turtle.scenes;

import com.brainardphotography.turtle.Turtle;
import com.brainardphotography.turtle.objects.TurtleObject;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collection;
import java.util.List;

/**
 * Created by johnbrainard on 10/12/14.
 */
public interface WorldScene {
	public void addWorldObject(TurtleObject worldObject);
	public void addWorldObjects(Collection<TurtleObject> worldObjects);

	public double getWidth();
	public double getHeight();

	public void initialize(Canvas canvas);
	public void draw();
	public void reset();

	public Turtle getTurtle();
	public void alert(String message);
}
