package com.brainardphotography.turtle.objects;

import com.brainardphotography.turtle.objects.TurtleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by johnbrainard on 10/4/14.
 */
public class TurtlePoopObject extends TurtleObject {
	private Image image;
	private double x;
	private double y;

	public TurtlePoopObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.image = new Image(getClass().getResourceAsStream("Poop.png"));
	}

	@Override
	public void draw(GraphicsContext gc) {
		double width = image.getWidth();
		double height = image.getHeight();

		gc.drawImage(this.image, x - (width / 2), y - (height / 2));
	}
}
