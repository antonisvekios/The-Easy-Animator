package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a rectangle to be displayed in the animation. INVARIANT 1: Shape type is equal to
 * "Rectangle". INVARIANT 2: Shape width is positive. INVARIANT 3: Shape height is positive.
 * INVARIANT 4: Shape coordinates are non-negative. INVARIANT 5: Shape color is not null.
 */
public class Rectangle extends AbstractShape {

  /**
   * Construct a Rectangle with given field.
   *
   * @param width The width of the rectangle.
   * @param height The height of the rectangle.
   * @param position The position of the rectangle.
   * @param color The color of the rectangle.
   */
  public Rectangle(int width, int height, Position2D position, Color color) {
    super(width, height, position, color);
    this.type = "Rectangle";
  }

  /**
   * Constructs a rectangle by setting all fields to their default values.
   */
  public Rectangle() {
    super();
    this.type = "Rectangle";
  }
}