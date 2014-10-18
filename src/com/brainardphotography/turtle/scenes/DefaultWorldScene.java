package com.brainardphotography.turtle.scenes;

import com.brainardphotography.turtle.Turtle;
import com.brainardphotography.turtle.objects.TurtleObject;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by johnbrainard on 10/17/14.
 */
public class DefaultWorldScene implements WorldScene {
	private List<TurtleObject> worldObjects;
	private Canvas canvas;
	private Turtle turtle;

	@Override
	public void addWorldObject(TurtleObject worldObject) {
		worldObjects.add(worldObject);
	}

	@Override
	public void addWorldObjects(Collection<TurtleObject> worldObjects) {
		this.worldObjects.addAll(worldObjects);
	}

	@Override
	public double getWidth() {
		return canvas.getWidth();
	}

	@Override
	public double getHeight() {
		return canvas.getHeight();
	}

	@Override
	public Turtle getTurtle() {
		return turtle;
	}

	@Override
	public void initialize(Canvas canvas) {
		this.canvas = canvas;
		this.worldObjects = new ArrayList<>();
	}

	@Override
	public void reset() {
		this.turtle = createTurtle();
		turtle.setObjectConsumer(o -> addWorldObject(o));

		worldObjects.clear();
		worldObjects.addAll(createObjects());
		drawScene(canvas.getGraphicsContext2D());
	}

	@Override
	public void draw() {
		worldObjects.stream().forEach(o -> {
			o.updatePosition(rect -> checkRect(rect));
		});
		turtle.updatePosition(rect -> checkRect(rect));

		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawScene(gc);
	}

	protected void drawScene(GraphicsContext gc) {
		gc.save();
		drawBackground(gc);
		gc.restore();

		gc.save();
		for (TurtleObject object : worldObjects) {
			object.draw(gc);
		}
		gc.restore();

		gc.save();
		turtle.draw(gc);
		gc.restore();
	}

	protected void drawBackground(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	protected Turtle createTurtle() {
		return new Turtle(getWidth() / 2.0, getHeight() / 2.0);
	}

	protected List<TurtleObject> createObjects() {
		return Collections.emptyList();
	}

	private boolean checkRect(Rectangle2D rect) {
		Rectangle2D canvasRect = new Rectangle2D(0, 0, canvas.getWidth(), canvas.getHeight());

		if (!canvasRect.contains(rect))
			return false;

		if (worldObjects.stream().anyMatch(o -> o.getRect() != null && o.getRect().intersects(rect)))
			return false;

		return true;
	}
}
