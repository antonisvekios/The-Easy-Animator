package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a plus sign to be displayed in the animation. INVARIANT 1: Shape type is equal to
 * "Plus". INVARIANT 2: Shape width is positive. INVARIANT 3: Shape height is positive. INVARIANT
 * 4: Shape coordinates are non-negative. INVARIANT 5: Shape color is not null.
 */
public class Plus extends AbstractShape {

  /**
   * Construct an ellipse with given fields.
   *
   * @param width The width of ellipse.
   * @param height The height of ellipse.
   * @param position The position of the ellipse.
   * @param color The color of the ellipse.
   */
  public Plus(int width, int height, Position2D position, Color color) {
    super(width, height, position, color);
    this.type = "Plus";
  }

  /**
   * Constructs an ellipse by setting all fields to their default values.
   */
  public Plus() {
    super();
    this.type = "Plus";
  }
}

