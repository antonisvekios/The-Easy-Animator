package cs3500.animator.model;

/**
 * Represents an IPosition2D interface to provide and set the position information.
 */
public interface IPosition2D {

  /**
   * Return the x coordinate of this position.
   *
   * @return the value of the x coordinate as an integer.
   */
  int getX();

  /**
   * Return the y coordinate of this position.
   *
   * @return the value of the y coordinate as an integer.
   */
  int getY();

  /**
   * Set the X for the position.
   */
  void setX(int x);

  /**
   * Set the Y for the Position.
   */
  void setY(int y);
}
