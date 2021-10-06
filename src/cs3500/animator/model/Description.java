package cs3500.animator.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents the description of a shape in the animation. Consists of all variables associated with
 * a shape, including beginning and ending tick, position, width, height, and color. INVARIANT 1:
 * startTime is positive. INVARIANT 2: endTime is greater than startTime. INVARIANT 3: startWidth is
 * positive. INVARIANT 4: startHeight is positive. INVARIANT 5: endWidth is positive. INVARIANT 6:
 * endHeight is positive.
 */
public class Description implements IDescription {

  private final String type;
  protected final int startTime;
  private final int startX;
  private final int startY;
  private final int startWidth;
  private final int startHeight;
  private final Color startColor;
  protected int endTime;
  private final int endX;
  private final int endY;
  private final int endWidth;
  private final int endHeight;
  private final Color endColor;

  /**
   * Construct a description with given fields.
   *
   * @param type The type of shape which the description apply to.
   * @param t1 The start time.
   * @param x1 The start x.
   * @param y1 The start y.
   * @param w1 The start width.
   * @param h1 The start height.
   * @param r1 The start r of RGB.
   * @param g1 The start g of RGB.
   * @param b1 The start b of RGB.
   * @param t2 The end time.
   * @param x2 The end x.
   * @param y2 The end y.
   * @param w2 The end width.
   * @param h2 The end height.
   * @param r2 The end r of RGB.
   * @param g2 The end g of RGB.
   * @param b2 The end b of RGB.
   */
  public Description(String type, int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    if (t1 > t2 || t1 < 0) {
      throw new IllegalArgumentException("Provided invalid start or end times");
    }

    if (w1 < 0 || w2 < 0) {
      throw new IllegalArgumentException("Provided invalid width");
    }

    if (h1 < 0 || h2 < 0) {
      throw new IllegalArgumentException("Provided invalid height");
    }

    if (r1 < 0 || r2 < 0 || g1 < 0 || g2 < 0 || b1 < 0 || b2 < 0
        || r1 > 255 || r2 > 255 || g1 > 255 || g2 > 255 || b1 > 255 || b2 > 255) {
      throw new IllegalArgumentException("Provided invalid RGB values");
    }
    this.type = type;
    this.startTime = t1;
    this.startX = x1;
    this.startY = y1;
    this.startWidth = w1;
    this.startHeight = h1;
    this.startColor = new Color(r1, g1, b1);
    this.endTime = t2;
    this.endX = x2;
    this.endY = y2;
    this.endWidth = w2;
    this.endHeight = h2;
    this.endColor = new Color(r2, g2, b2);
  }

  /**
   * Get the type of description.
   *
   * @return The type of description.
   */
  public String getType() {
    return this.type;
  }

  /**
   * Returns the beginning tick of the description.
   *
   * @return the beginning tick as an integer.
   */
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Get the start x of description.
   *
   * @return The start x of description.
   */
  public int getStartX() {
    return startX;
  }

  /**
   * Get the start y of description.
   *
   * @return The start y of description.
   */
  public int getStartY() {
    return startY;
  }

  /**
   * Returns the beginning width of the description.
   *
   * @return the beginning width of the description as an integer.
   */
  public int getStartWidth() {
    return this.startWidth;
  }

  /**
   * Returns the beginning height of the description.
   *
   * @return the beginning height of the description as an integer.
   */
  public int getStartHeight() {
    return this.startHeight;
  }

  /**
   * Returns the starting color of the description.
   *
   * @return the starting color of the description as a Color object.
   */
  public Color getStartColor() {
    return this.startColor;
  }

  /**
   * Returns the ending tick of the description.
   *
   * @return the ending tick as an integer.
   */
  public int getEndTime() {
    return this.endTime;
  }

  /**
   * Get the end x of description.
   *
   * @return The end x of description.
   */
  public int getEndX() {
    return endX;
  }

  /**
   * Get the end y of description.
   *
   * @return The end y of description.
   */
  public int getEndY() {
    return endY;
  }

  /**
   * Returns the ending width of the description.
   *
   * @return the ending width of the description as an integer.
   */
  public int getEndWidth() {
    return this.endWidth;
  }

  /**
   * Returns the ending height of the description.
   *
   * @return the ending height of the description as an integer.
   */
  public int getEndHeight() {
    return this.endHeight;
  }

  /**
   * Returns the ending color of the description.
   *
   * @return the ending color of the description as a Color object.
   */
  public Color getEndColor() {
    return this.endColor;
  }

  /**
   * Extend the endTick of description.
   */
  public void extend(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Cannot extend description by negative time");
    }
    this.endTime = this.endTime + tick;
  }

  @Override
  public String toString() {
    return this.startTime + "  " + this.endTime + " " + this.startWidth + " " + this.endWidth
        + " " + this.startHeight + " " + this.endHeight + " " + this.startX + " "
        + this.startY + " " + this.endX + " " + this.endY
        + " " + this.startColor.getRed() + " " + this.startColor.getGreen() + " "
        + this.startColor.getBlue() + " " + this.endColor.getRed() + " " + this.endColor.getGreen()
        + " " + this.endColor.getBlue();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Description)) {
      return false;
    }
    Description that = (Description) other;
    return this.startTime == that.getStartTime()
        && this.endTime == that.getEndTime()
        && this.startWidth == that.getStartWidth()
        && this.startHeight == that.getStartHeight()
        && this.endWidth == that.getEndWidth()
        && this.endHeight == that.getEndHeight()
        && this.startColor.equals(that.getStartColor())
        && this.endColor.equals(that.getEndColor())
        && this.startX == that.startX
        && this.startY == that.startY
        && this.endX == that.endX
        && this.endY == that.endY;
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(this.startTime,
            this.startX,
            this.startY,
            this.startWidth,
            this.startHeight,
            this.startColor.hashCode(),
            this.endTime,
            this.endX,
            this.endY,
            this.endWidth,
            this.endHeight,
            this.endColor.hashCode());
  }
}
