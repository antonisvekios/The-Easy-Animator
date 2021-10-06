package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents an interface for shapes. This interface is used for the animation and includes set
 * methods.
 */
public interface IShape extends IReadableShape {

  /**
   * Sets the position of this shape to the given position.
   *
   * @param p a Position2D (x,y) that will be the new position of this shape.
   */

  void setPosition(IPosition2D p);

  /**
   * Sets the color of the shape to the given color.
   *
   * @param color the RGB color that will be the new color of this shape.
   */
  void setColor(Color color);


  /**
   * Sets the width of this shape to the given integer.
   *
   * @param width the width of this shape as an integer value.
   */
  void setWidth(int width);

  /**
   * Sets the height of this shape to the given integer.
   *
   * @param height the height of this shape as an integer value.
   */
  void setHeight(int height);

  /**
   * Set the x for the IShape.
   */
  void setX(int x);

  /**
   * Set the y for the IShape.
   */
  void setY(int y);
}
