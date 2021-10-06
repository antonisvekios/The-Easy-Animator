package cs3500.animator.model;

import java.util.Objects;

/**
 * This class represents a 2D position.
 */
public class Position2D implements IPosition2D {

  int x;
  int y;

  /**
   * Initialize this object to the specified position.
   *
   * @param x the x-coordinate of the position.
   * @param y the y-coordinate of the position.
   * @throws IllegalArgumentException if the coordinates provided are negative.
   */
  public Position2D(int x, int y) {

    this.x = x;
    this.y = y;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Position2D)) {
      return false;
    }
    Position2D that = (Position2D) other;
    return this.getX() == that.getX() && this.getY() == that.getY();
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.x, this.y);
  }
}


