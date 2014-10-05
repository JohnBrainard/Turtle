package com.brainardphotography.turtle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.function.Consumer;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class Turtle extends Canvas {
	private Range moveRange = Range.emptyRange();
	private Range angleRange = Range.emptyRange();
	private double x;
	private double y;

	private Image turtle;
	private Consumer<TurtleObject> objectConsumer;

	public Turtle() {
		turtle = new Image(getClass().getResourceAsStream("Turtle@2x.png"));
	}

	public void setObjectConsumer(Consumer<TurtleObject> objectConsumer) {
		this.objectConsumer = objectConsumer;
	}

	public void draw(GraphicsContext gc) {
		double width = turtle.getWidth();
		double height = turtle.getHeight();

		double angle = angleRange.getCurrent();
		angle = angleRange.nextOrCurrent();
		if (moveRange.hasNext()) {
			double r = Math.toRadians(angle);
			x = x + Math.sin(r) * moveRange.getStepAmount();
			y = y + Math.cos(r) * moveRange.getStepAmount();
			moveRange.next();
		}

		gc.save();

		Rotate r = Transform.rotate(-angle, x, y);
		gc.transform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

		gc.drawImage(turtle, x - (width / 2), y - (width / 2), turtle.getWidth(), turtle.getHeight());
		gc.restore();
	}

	public void move(int amount) {
		moveRange = new Range(moveRange.getCurrent(), moveRange.getCurrent() + amount, amount);
	}

	public void run(int amount) {
		moveRange = new Range(moveRange.getCurrent(), moveRange.getCurrent() + amount, amount / 2);
	}

	public void turn(double amount) {
		angleRange = new Range(angleRange.getCurrent(), angleRange.getCurrent() + amount, (int)Math.abs(amount));
		System.out.println("Set turn to: " + angleRange.toString());
	}

	public boolean isMoving() {
		return moveRange.hasNext();
	}

	public boolean isTurning() {
		return angleRange.hasNext();
	}

	public void poop() {
		double r = Math.toRadians(angleRange.getCurrent());
		double x = this.x - Math.sin(r) * 50;
		double y = this.y - Math.cos(r) * 50;

		objectConsumer.accept(new TurtlePoop(x, y));
	}

	public void reset() {
		moveRange = Range.emptyRange();
		angleRange = Range.emptyRange();
		x = 0;
		y = 0;
	}
}
