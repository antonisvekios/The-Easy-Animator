import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.animator.model.Position2D;
import org.junit.Test;

/** Tests for all public methods of {@link cs3500.animator.model.Position2D}. */
public class Position2DTest {
  Position2D p1 = new Position2D(1, 1);
  Position2D p2 = new Position2D(100, 100);

  @Test (expected = IllegalArgumentException.class)
  //Test for the Position2D constructor when given negative x value
  public void invariantOfPosition2dTest() {
    p1 = new Position2D(-1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test for the Position2D constructor when given negative y value
  public void invariantOfPosition2dTest2() {
    p1 = new Position2D(1, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test for the Position2D constructor when given negative x and y values
  public void invariantOfPosition2dTest3() {
    p1 = new Position2D(-1, -1);
  }

  @Test
  //Test the getX method of Position2D
  public void getXTest() {
    assertEquals(p1.getX(), 1);
    assertEquals(p2.getX(),100);
  }

  @Test
  //Test the getY method of Position2D
  public void getYTest() {
    assertEquals(p1.getY(), 1);
    assertEquals(p2.getY(),100);
  }

  @Test
  //Test the equals method of Position2D
  public void equalsTest() {
    assertEquals(p1, p1);
    assertNotEquals(p2, p1);
    assertEquals(p1, new Position2D(1, 1));
  }
}
