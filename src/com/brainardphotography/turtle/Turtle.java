package com.brainardphotography.turtle;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class Turtle extends TurtleObject {
	private Range moveRange = Range.emptyRange();
	private Range angleRange = Range.emptyRange();
	private double x;
	private double y;
	private double angle;
	private double width;
	private double height;

	private Image turtle;
	private Consumer<TurtleObject> objectConsumer;

	public Turtle(double x, double y) {
		turtle = new Image(getClass().getResourceAsStream("Turtle@2x.png"));

		this.x = x;
		this.y = y;
		this.width = turtle.getWidth();
		this.height = turtle.getHeight();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return turtle.getWidth();
	}

	public double getHeight() {
		return turtle.getHeight();
	}

	public double getAngle() {
		return angle;
	}

	public void setObjectConsumer(Consumer<TurtleObject> objectConsumer) {
		this.objectConsumer = objectConsumer;
	}

	@Override
	public void updatePosition(Predicate<Rectangle2D> predicate) {
		Range moveRange = this.moveRange.copy();
		Range angleRange = this.angleRange.copy();

		double newX = x;
		double newY = y;
		double newAngle = angleRange.nextOrCurrent();
		if (moveRange.hasNext()) {
			double r = Math.toRadians(newAngle);
			newX = x + Math.sin(r) * moveRange.getStepAmount();
			newY = y + Math.cos(r) * moveRange.getStepAmount();

			moveRange.next();
		}

		Rectangle2D position = new Rectangle2D(newX - width / 2, newY - height / 2, width, height);
		if (predicate.test(position)) {
			x = newX;
			y = newY;
			angle = newAngle;
			this.moveRange = moveRange;
			this.angleRange = angleRange;
		} else {
			this.moveRange = Range.emptyRange();
			this.angleRange = Range.emptyRange();
			System.out.println("Can't go there!:(");
		}
	}

	public void draw(GraphicsContext gc) {
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

	public void reset(double x, double y) {
		moveRange = Range.emptyRange();
		angleRange = Range.emptyRange();
		this.x = x;
		this.y = y;
	}
}
