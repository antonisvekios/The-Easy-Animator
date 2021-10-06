package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents a read only version of a shape. Includes all get methods for each field of a shape.
 */
public interface IReadableShape {

  /**
   * Returns the type of the shape.
   *
   * @return the type of the shape as a string.
   */
  String getShapeType();

  /**
   * Returns the width of the shape.
   *
   * @return the width of the shape as an integer.
   */
  int getWidth();

  /**
   * Returns the height of the shape.
   *
   * @return the height of the shape as an integer.
   */
  int getHeight();

  /**
   * Returns the current position of the shape.
   *
   * @return the current position of the shape as a Position2D (x,y).
   */
  IPosition2D getPosition();

  /**
   * Returns the color of the shape.
   *
   * @return the color of the shape as a Color object.
   */
  Color getColor();
}