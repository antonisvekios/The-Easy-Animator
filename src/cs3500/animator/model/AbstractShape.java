package cs3500.animator.model;

import java.awt.Color;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 * Represents an abstract implementation of a shape consisting of all common methods across
 * different shapes in the animation.
 * INVARIANT 1: Shape type is one of: "Ellipse", "Rectangle".
 * INVARIANT 2: Shape width is positive.
 * INVARIANT 3: Shape height is positive.
 * INVARIANT 4: Shape coordinates are non-negative.
 * INVARIANT 5: Shape color is not null.
 */
public abstract class AbstractShape implements IShape {

  protected String type;
  protected IPosition2D position;
  protected int width;
  protected int height;
  protected Color color;

  /**
   * Constructs an abstract shape based on the arguments provided.
   * @param width the width of the shape as an integer.
   * @param height the height of the shape as an integer.
   * @param position the position of the shape as a Position2D object.
   * @param color the color of the shape as a color object.
   * @throws IllegalArgumentException if width or height values provided are negative.
   */
  protected AbstractShape(int width, int height, Position2D position, Color color) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Cannot have a negative width or height");
    }
    this.position = position;
    this.width = width;
    this.height = height;
    this.color = requireNonNull(color);
  }

  /**
   * Constructs an an abstract shape by initializing all shape parameters to their default values.
   */
  protected AbstractShape() {
    this.width = 1;
    this.height = 1;
    this.position = null;
    this.color = new Color(0x000000);
  }


  @Override
  public String getShapeType() {
    return this.type;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public IPosition2D getPosition() {
    return this.position;
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  @Override
  public void setColor(Color color) {
    this.color = requireNonNull(color);
  }

  @Override
  public void setPosition(IPosition2D position) {
    this.position = position;
  }

  @Override
  public void setX(int x) {
    this.position.setX(x);
  }

  @Override
  public void setY(int y) {
    this.position.setY(y);
  }

  @Override
  public void setWidth(int width) {
    if (width < 0) {
      throw new IllegalArgumentException("Provided negative value for width");
    }
    this.width = width;
  }

  @Override
  public void setHeight(int height) {
    if (height < 0) {
      throw new IllegalArgumentException("Provided negative value for height");
    }
    this.height = height;
  }

  @Override
  public String toString() {
    String x;
    String y;
    if (this.position == null) {
      x = "null";
      y = "null";
    } else {
      x = Integer.toString(this.position.getX());
      y = Integer.toString(this.position.getY());
    }
    return this.type + "  " + x + "  " + y + "  "
        + this.width + "  " + this.height + "  "
        + this.color.getRed() + "  " + this.color.getGreen() + "  " + this.color.getBlue();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof AbstractShape)) {
      return false;
    }
    AbstractShape that = (AbstractShape) other;
    return this.type.equals(that.getShapeType())
        && this.position == that.getPosition()
        && this.width == that.getWidth()
        && this.height == that.getHeight()
        && this.color.getRGB() == that.getColor().getRGB();
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.type.hashCode(), this.width, this.height, this.color);
  }
}

