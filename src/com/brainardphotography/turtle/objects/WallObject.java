package com.brainardphotography.turtle.objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by johnbrainard on 10/5/14.
 */
public class WallObject extends TurtleObject {
	private static Random random = new Random();

	public enum Orientation {
		Horizontal,
		Vertical;

		static final List<Orientation> VALUES = Arrays.asList(Orientation.values());

		static Orientation random() {
			int index = random.nextInt(values().length);
			return values()[index];
		}
	};

	private Rectangle2D rect;

	public WallObject(double x, double y, double width, double height) {
		this.rect = new Rectangle2D(x, y, width, height);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.setFill(Color.BISQUE);
		gc.fillRect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
		gc.restore();
	}

	@Override
	public Rectangle2D getRect() {
		return rect;
	}

	public static WallObject randomWall(Canvas canvas) {
		Orientation orientation = Orientation.random();
		double width = orientation == Orientation.Horizontal ? random.nextDouble() * (canvas.getWidth() / 2.0) : 40.0;
		double height = orientation == Orientation.Vertical ? random.nextDouble() * (canvas.getHeight() / 2.0) : 40.0;

		double x = random.nextDouble() * (canvas.getWidth() - (width * 2)) + height;
		double y = random.nextDouble() * (canvas.getHeight() - (height * 2)) + height;

		return new WallObject(x, y, width, height);
	}
}
