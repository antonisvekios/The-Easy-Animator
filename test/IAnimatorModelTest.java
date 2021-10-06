
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.Description;
import cs3500.animator.model.Ellipse;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for all public methods of {@link cs3500.animator.model.IAnimatorModel}.
 */
public class IAnimatorModelTest {

  IAnimatorModel m = new AnimationModelImpl();
  Description d1;
  Description d2;

  //The initial condition for testing the Animator Model test
  private void initialCondition() {
    m = new AnimationModelImpl();
    d1 = new Description("motion", 0, 1, 2, 100, 200, 0, 0, 0, 100,
        100, 200, 200, 100, 255, 0, 0);
    d2 = new Description("motion", 100, 1, 2, 100, 200, 0, 0, 0, 300,
        100, 200, 200, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  //testing addShape method for duplicates
  public void addShapeTestDuplicates1() {
    initialCondition();
    m.addShape("C", new Rectangle());
    m.addShape("C", new Ellipse());
  }

  @Test(expected = IllegalArgumentException.class)
  //testing addShape method for duplicates
  public void addShapeTestDuplicates2() {
    initialCondition();
    m.addShape("C", new Rectangle());
    m.addShape("C", new Ellipse());
  }

  @Test(expected = IllegalArgumentException.class)
  //Non-null position shape test
  public void addShapeMethodExceptionTest1() {
    initialCondition();
    m.addShape("R", new Rectangle(1, 1, new Position2D(1, 1),
        new Color(0, 0, 0)));
  }

  @Test(expected = IllegalArgumentException.class)
  //Non-null position shape test
  public void addShapeMethodExceptionTest2() {
    initialCondition();
    m.addShape("C", new Ellipse(1, 1, new Position2D(100, 100),
        new Color(1, 1, 1)));
  }

  @Test
  //Test for addShape method
  public void addShapeTest() {
    initialCondition();
    //At first, the number of shape is 0
    assertEquals(m.getShapes().size(), 0);
    assertEquals(m.getProcess().size(), 0);
    m.addShape("R", new Rectangle());
    m.addShape("C", new Ellipse());
    assertEquals(m.getShapes().size(), 2);
    assertEquals(m.getProcess().size(), 2);
    assertEquals(m.getShapes().get("R"), new Rectangle());
    assertEquals(m.getShapes().get("C"), new Ellipse());
    assertEquals(m.getProcess().get("R"), new ArrayList<Description>());
    assertEquals(m.getProcess().get("C"), new ArrayList<Description>());
  }

  @Test(expected = IllegalArgumentException.class)
  //Non-continuous description test
  public void addDescriptionMethodExceptionTest3() {
    initialCondition();
    m.addShape("R", new Rectangle());
    m.addDescription("R", new Description("motion", 0, 1, 2, 100, 200,
        0, 0, 0, 100, 100, 200, 200, 100, 255, 0, 0));
    m.addDescription("R", new Description("motion", 120, 1, 2, 100, 200,
        0, 0, 0, 200, 100, 200, 200, 100, 255, 0, 0));
  }

  @Test
  //Test the addDescription method
  public void addDescriptionTest() {
    initialCondition();
    m.addShape("R", new Rectangle());
    m.addDescription("R", d1);
    m.addDescription("R", d2);
    assertEquals(m.getProcess().get("R").get(0), d1);
    assertEquals(m.getProcess().get("R").get(1), d2);
  }

  @Test
  //Test the getShapes method
  public void getShapesTest() {
    initialCondition();
    LinkedHashMap<String, IShape> map = new LinkedHashMap<>();
    map.put("R", new Rectangle());
    map.put("C", new Ellipse());
    m.addShape("R", new Rectangle());
    m.addShape("C", new Ellipse());
    assertEquals(m.getShapes(), map);

    initialCondition();
    LinkedHashMap<String, IShape> map2 = new LinkedHashMap<>();
    map2.put("R", new Rectangle());
    map2.put("C", new Rectangle());
    m.addShape("R", new Rectangle());
    m.addShape("C", new Rectangle());
    assertEquals(m.getShapes(), map2);
  }

  @Test
  //Test for getDescription method
  public void getDescriptionTest() {
    initialCondition();
    m.addShape("R", new Rectangle());
    m.addDescription("R", d1);
    m.addDescription("R", d2);
    assertEquals(m.getProcess().get("R").get(0), d1);
    assertEquals(m.getProcess().get("R").get(1), d2);

    initialCondition();
    m.addShape("R", new Rectangle());
    m.addShape("C", new Rectangle());
    m.addDescription("R", d1);
    m.addDescription("C", d2);
    assertEquals(m.getProcess().get("R").get(0), d1);
    assertEquals(m.getProcess().get("C").get(0), d2);
  }

  @Test(expected = IllegalArgumentException.class)
  //Test the exception of setEndTick when the given currentTick is negative
  public void exceptionOfSetCurrentTickTest1() {
    initialCondition();
    m.setCurrentTick(-100);
  }

  @Test(expected = IllegalArgumentException.class)
  //Test the exception of setEndTick when the given currentTick is negative
  public void exceptionOfSetCurrentTickTest2() {
    initialCondition();
    m.setCurrentTick(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  //Test the exception of setEndTick when the given currentTick is larger than endTick
  public void exceptionOfSetCurrentTickTest3() {
    initialCondition();
    m.setCurrentTick(500);
  }

  @Test(expected = IllegalArgumentException.class)
  //Test the exception of setEndTick when the given currentTick is larger than endTick
  public void exceptionOfSetCurrentTickTest4() {
    initialCondition();
    m.setCurrentTick(1000);
  }

  @Test
  //This test is both for run and setCurrentTick method
  public void runAndSetCurrentTickTest() {
    initialCondition();
    m.addShape("R", new Rectangle());
    m.addDescription("R", d1);
    m.addDescription("R", d2);
    LinkedHashMap<String, IShape> map = new LinkedHashMap<>();
    map.put("R", new Rectangle());
    assertEquals(m.getShapes(), map);
    m.setCurrentTick(100);
    m.run();
    map = new LinkedHashMap<>();
    map.put("R", new Rectangle(100, 200, new Position2D(1, 2),
        new Color(0, 0, 0)));
    assertEquals(m.getShapes().get("R").getShapeType(), map.get("R").getShapeType());
    assertEquals(m.getShapes().get("R").getWidth(), map.get("R").getWidth());
    assertEquals(m.getShapes().get("R").getHeight(), map.get("R").getHeight());
    assertEquals(m.getShapes().get("R").getColor(), map.get("R").getColor());
    assertEquals(m.getShapes().get("R").getPosition(), map.get("R").getPosition());

    m.setCurrentTick(99);
    m.run();
    map = new LinkedHashMap<>();
    map.put("R", new Rectangle(199, 101, new Position2D(99, 198),
        new Color(252, 0, 0)));
    assertEquals(m.getShapes().get("R").getShapeType(), map.get("R").getShapeType());
    assertEquals(m.getShapes().get("R").getWidth(), map.get("R").getWidth());
    assertEquals(m.getShapes().get("R").getHeight(), map.get("R").getHeight());
    assertEquals(m.getShapes().get("R").getColor(), map.get("R").getColor());
    assertEquals(m.getShapes().get("R").getPosition(), map.get("R").getPosition());

    initialCondition();
    m.addShape("R", new Rectangle());
    m.addShape("C", new Ellipse());
    m.addDescription("R", d1);
    m.addDescription("C", d2);
    m.setCurrentTick(99);
    m.run();
    map = new LinkedHashMap<>();
    map.put("R", new Rectangle(199, 101, new Position2D(99, 198),
        new Color(252, 0, 0)));
    assertEquals(m.getShapes().get("R").getShapeType(), map.get("R").getShapeType());
    assertEquals(m.getShapes().get("R").getWidth(), map.get("R").getWidth());
    assertEquals(m.getShapes().get("R").getHeight(), map.get("R").getHeight());
    assertEquals(m.getShapes().get("R").getColor(), map.get("R").getColor());
    assertEquals(m.getShapes().get("R").getPosition(), map.get("R").getPosition());
    m.setCurrentTick(100);
    m.run();
    map = new LinkedHashMap<>();
    map.put("C", new Ellipse(100, 200, new Position2D(1, 2),
        new Color(0, 0, 0)));
    assertEquals(m.getShapes().get("C").getShapeType(), map.get("C").getShapeType());
    assertEquals(m.getShapes().get("C").getWidth(), map.get("C").getWidth());
    assertEquals(m.getShapes().get("C").getHeight(), map.get("C").getHeight());
    assertEquals(m.getShapes().get("C").getColor(), map.get("C").getColor());
    assertEquals(m.getShapes().get("C").getPosition(), map.get("C").getPosition());
  }

  @Test(expected = IllegalArgumentException.class)
  //Test for wrong bounds.
  public void wrongSetBoundsTest1() {
    initialCondition();
    m.setBounds(1, 1, -100, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  //Test for wrong bounds.
  public void wrongSetBoundsTest2() {
    initialCondition();
    m.setBounds(1, 1, 1, -100);
  }

  @Test
  //Test for getXBounds method.
  public void getXBoundsTest() {
    initialCondition();
    m.setBounds(1, 1, 1, 1);
    assertEquals(m.getXBounds(), 1);
    initialCondition();
    m.setBounds(2, 1, 1, 1);
    assertEquals(m.getXBounds(), 2);
  }

  @Test
  //Test for getYBounds method.
  public void getYBoundsTest() {
    initialCondition();
    m.setBounds(1, 1, 1, 1);
    assertEquals(m.getYBounds(), 1);
    initialCondition();
    m.setBounds(1, 2, 1, 1);
    assertEquals(m.getYBounds(), 2);
  }

  @Test
  //Test for getWidthBounds method.
  public void getWidthBoundsTest() {
    initialCondition();
    m.setBounds(1, 1, 1, 1);
    assertEquals(m.getWidthBounds(), 1);
    initialCondition();
    m.setBounds(1, 1, 2, 1);
    assertEquals(m.getWidthBounds(), 2);
  }

  @Test
  //Test for getHeightBounds method.
  public void getHeightBoundsTest() {
    initialCondition();
    m.setBounds(1, 1, 1, 1);
    assertEquals(m.getHeightBounds(), 1);
    initialCondition();
    m.setBounds(1, 1, 1, 2);
    assertEquals(m.getHeightBounds(), 2);
  }

  @Test
  //Test for getEndTick method.
  public void getEndTickTest() {
    initialCondition();
    m.addShape("r", new Rectangle());
    m.addDescription("r", d1);
    assertEquals(m.getEndTick(), 100);
    m.addDescription("r", d2);
    assertEquals(m.getEndTick(), 300);
  }

  @Test(expected = IllegalArgumentException.class)
  //Test for invalid remove.
  public void wrongRemoveShape() {
    initialCondition();
    m.removeShape("r");
  }

  @Test
  //Test for removeShape method.
  public void removeShapeTest() {
    initialCondition();
    m.addShape("r", new Rectangle());
    m.removeShape("r");
    assertEquals(m.getShapes().isEmpty(), true);
  }

  @Test
  //Test for method extend.
  public void extendTest() {
    initialCondition();
    m.addShape("r", new Rectangle());
    m.addDescription("r", d1);
    assertEquals(m.getProcess().get("r").get(0).getEndTime(), 100);
    m.extend("r", 100);
    assertEquals(m.getProcess().get("r").get(0).getEndTime(), 200);
    m.extend("r", 100);
    assertEquals(m.getProcess().get("r").get(0).getEndTime(), 300);
  }

  @Test
  //Test for initial method.
  public void initialTest() {
    initialCondition();
    m.addShape("r", new Rectangle());
    m.addDescription("r", d1);
    m.setCurrentTick(100);
    m.run();
    assertEquals(m.getShapes().get("r").getPosition(), new Position2D(100, 200));
    m.initial();
    assertEquals(m.getShapes().get("r").getPosition(), null);
    m.addDescription("r", d2);
    m.setCurrentTick(300);
    m.run();
    assertEquals(m.getShapes().get("r").getPosition(), new Position2D(100, 200));
    m.initial();
    assertEquals(m.getShapes().get("r").getPosition(), null);
  }

  @Test
  //Test for stopTime method.
  public void stopTimeTest() {
    initialCondition();
    assertEquals(m.stopTime(), new ArrayList<>());
    m.addShape("r", new Rectangle());
    m.addDescription("r", d1);
    assertEquals(m.stopTime(), Arrays.asList(0,100));
    m.addDescription("r", d2);
    assertEquals(m.stopTime(), Arrays.asList(0,100,300));
  }
}
