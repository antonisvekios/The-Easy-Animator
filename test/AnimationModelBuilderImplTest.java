
import cs3500.animator.model.AnimationModelImpl.AnimationModelBuilderImpl;
import cs3500.animator.model.Description;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Rectangle;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for all public methods of {@link
 * cs3500.animator.model.AnimationModelImpl.AnimationModelBuilderImpl}.
 */
public class AnimationModelBuilderImplTest {
  AnimationModelBuilderImpl builder = new AnimationModelBuilderImpl();

  private void initial() {
    builder = new AnimationModelBuilderImpl();
  }

  @Test (expected = IllegalArgumentException.class)
  //Test the exception for wrong width input for SetBounds method.
  public void setBoundsTest1() {
    initial();
    builder.setBounds(1, 1, -1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test the exception for wrong height input for SetBounds method.
  public void setBoundsTest2() {
    initial();
    builder.setBounds(1, 1, 1, -1);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test the exception for add an existed shape to the builder.
  public void declareShapeTest1() {
    initial();
    builder.declareShape("r", "rectangle");
    builder.declareShape("r", "ellipse");
  }

  @Test (expected = IllegalArgumentException.class)
  //Test the exception for the wrong time description
  public void addMotionExceptionTest() {
    initial();
    builder.declareShape("r", "rectangle");
    builder.addMotion("r", 0,1,2,100,200,0,0,0,
        100,100,200,200,100,255,0,0);
    builder.addMotion("r", 10000,1,2,100,200,0,0,0,
        100,100,200,200,100,255,0,0);
  }

  @Test
  //Test if the build method and any other add method works well.
  public void buildAndOtherMethodTest() {
    initial();
    builder.setBounds(1, 1, 1, 1);
    builder.declareShape("r", "rectangle");
    builder.addMotion("r", 0,1,2,100,200,0,0,0,
        100,100,200,200,100,255,0,0);
    IAnimatorModel model = builder.build();
    assertEquals(model.getXBounds(), 1);
    assertEquals(model.getYBounds(), 1);
    assertEquals(model.getWidthBounds(), 1);
    assertEquals(model.getHeightBounds(), 1);
    HashMap<String, IShape> map1;
    map1 = new LinkedHashMap<>();
    map1.put("r", new Rectangle());
    assertEquals(model.getShapes().get("r").getShapeType(), map1.get("r").getShapeType());
    assertEquals(model.getShapes().get("r").getWidth(), map1.get("r").getWidth());
    assertEquals(model.getShapes().get("r").getHeight(), map1.get("r").getHeight());
    assertEquals(model.getShapes().get("r").getColor(), map1.get("r").getColor());
    assertEquals(model.getShapes().get("r").getPosition(), map1.get("r").getPosition());
    assertEquals(model.getProcess().get("r").get(0),
        new Description("r", 0,1,2,100,200,0,0,0,
            100,100,200,200,100,255,0,0));
  }
}
