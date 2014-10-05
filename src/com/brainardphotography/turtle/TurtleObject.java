package com.brainardphotography.turtle;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by johnbrainard on 10/4/14.
 */
public abstract class TurtleObject {
	/**
	 * Called during the draw procedure to calculate the object's new position. A call to <code>predicate</code>
	 * is used to determine if the new position is legal. If <code>predicate</code> returns false, the new rectangle
	 * should be discarded.
	 *
	 * @param predicate Call to determine if the new rectangle is allowed
	 */
	public void updatePosition(Predicate<Rectangle2D> predicate) {
		// Do nothing... Base object doesn't need to move
	}

	abstract void draw(GraphicsContext gc);

	public Rectangle2D getRect() {
		return Rectangle2D.EMPTY;
	}
}
