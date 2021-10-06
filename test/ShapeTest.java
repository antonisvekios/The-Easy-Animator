import cs3500.animator.model.Position2D;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.Ellipse;
import cs3500.animator.model.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * Tests for all public methods of {@link cs3500.animator.model.Ellipse} and {@link
 * cs3500.animator.model.Rectangle}.
 */
public class ShapeTest {

  private Rectangle rectangle = new Rectangle(2, 3, new Position2D(8, 8), Color.GREEN);
  private Ellipse ellipse = new Ellipse(3, 3, new Position2D(1, 1), Color.RED);

  @Test(expected = NullPointerException.class)
  //testing the rectangle constructor for a null color
  public void testRectangleNullColor() {
    rectangle = new Rectangle(1, 5, new Position2D(8, 8), null);
  }

  @Test(expected = NullPointerException.class)
  //testing the ellipse constructor for a null color
  public void testEllipseNullColor() {
    ellipse = new Ellipse(3, 3, new Position2D(1, 1), null);
  }

  @Test
  //testing the getShapeType method
  public void testGetShapeType() {
    assertEquals(ellipse.getShapeType(), "Ellipse");
    assertEquals(rectangle.getShapeType(), "Rectangle");
  }

  @Test
  //testing the getColor method
  public void testGetColor() {
    assertEquals(ellipse.getColor(), Color.RED);
    assertEquals(rectangle.getColor(), Color.GREEN);
  }

  @Test
  //testing the getPosition method
  public void testGetPosition() {
    assertEquals(ellipse.getPosition(), new Position2D(1, 1));
    assertEquals(rectangle.getPosition(), new Position2D(8, 8));
  }

  @Test
  //testing the getHeight method
  public void testGetHeight() {
    assertEquals(ellipse.getHeight(), 3);
    assertEquals(rectangle.getHeight(), 3);
  }

  @Test
  //testing the getWidth method
  public void testGetWidth() {
    assertEquals(ellipse.getWidth(), 3);
    assertEquals(rectangle.getWidth(), 2);
  }

  @Test
  //testing the setWidth method
  public void testSetWidth() {
    this.ellipse.setWidth(6);
    assertEquals(ellipse.getWidth(), 6);
    this.rectangle.setWidth(15);
    assertEquals(rectangle.getWidth(), 15);
    this.rectangle.setWidth(0);
    assertEquals(rectangle.getWidth(), 0);
    this.ellipse.setWidth(0);
    assertEquals(this.ellipse.getWidth(),0);
  }

  @Test(expected = IllegalArgumentException.class)
  //testing setWidth for a negative width in a rectangle
  public void testInvalidSetWidthRectangle() {
    this.rectangle.setWidth(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  //testing setWidth for a negative width in an ellipse
  public void testInvalidSetWidthEllipse() {
    this.ellipse.setWidth(-3);
  }

  @Test
  //testing the setHeight method
  public void testSetHeight() {
    this.ellipse.setHeight(6);
    assertEquals(ellipse.getHeight(), 6);
    this.rectangle.setHeight(15);
    assertEquals(rectangle.getHeight(), 15);
    this.rectangle.setHeight(0);
    assertEquals(rectangle.getHeight(), 0);
    this.ellipse.setHeight(0);
    assertEquals(this.ellipse.getHeight(),0);
  }

  @Test(expected = IllegalArgumentException.class)
  //testing setHeight for a negative height in a rectangle
  public void testInvalidSetHeightRectangle() {
    this.rectangle.setHeight(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  //testing setHeight for a negative height in an ellipse
  public void testInvalidSetHeightEllipse() {
    this.ellipse.setHeight(-3);
  }

  @Test
  //testing the setPosition method
  public void testSetPosition() {
    assertEquals(ellipse.getPosition(), new Position2D(1, 1));
    this.ellipse.setPosition(new Position2D(18, 18));
    assertEquals(ellipse.getPosition(), new Position2D(18, 18));
    assertEquals(rectangle.getPosition(), new Position2D(8, 8));
    this.rectangle.setPosition(new Position2D(35, 10));
    assertEquals(rectangle.getPosition(), new Position2D(35, 10));
  }

  @Test
  //testing the setColor method
  public void testSetColor() {
    assertEquals(ellipse.getColor(), Color.RED);
    this.ellipse.setColor(Color.BLUE);
    assertEquals(ellipse.getColor(), Color.BLUE);
    assertEquals(rectangle.getColor(), Color.GREEN);
    this.rectangle.setColor(Color.GRAY);
    assertEquals(rectangle.getColor(), Color.GRAY);
  }

  @Test(expected = NullPointerException.class)
  //testing setColor for a null color in a rectangle
  public void testInvalidSetColorRectangle() {
    this.rectangle.setColor(null);
  }

  @Test(expected = NullPointerException.class)
  //testing setColor for a null color in an ellipse
  public void testInvalidSetColorEllipse() {
    this.ellipse.setColor(null);
  }
}
