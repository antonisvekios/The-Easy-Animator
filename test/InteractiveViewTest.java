import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.Description;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.Rectangle;
import cs3500.animator.view.InteractiveViewFrame;
import cs3500.animator.view.InteractiveViewPanel;
import java.awt.event.ActionEvent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests for all public methods of {@link cs3500.animator.view.InteractiveViewFrame} and
 * {@link cs3500.animator.view.InteractiveViewPanel}.. */
public class InteractiveViewTest {
  InteractiveViewPanel p = new InteractiveViewPanel();
  InteractiveViewFrame f = new InteractiveViewFrame();

  private void initial() {
    p = new InteractiveViewPanel();
    f = new InteractiveViewFrame(p);
  }

  @Test
  //Test for setSpeed method for InteractiveViewPanel
  public void setSpeed() {
    initial();
    p.setSpeed(1);
    assertEquals(p.getStatus().get(1), 1);
    p.setSpeed(10);
    assertEquals(p.getStatus().get(1), 10);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test for exception for setSpeed
  public void exceptionSetSpeedTest1() {
    initial();
    p.setSpeed(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test for exception for setSpeed
  public void exceptionSetSpeedTest2() {
    initial();
    p.setSpeed(0);
  }

  @Test
  //Test for updateSpeed method for InteractiveViewPanel
  public void updateSpeed() {
    initial();
    p.addModel(new AnimationModelImpl());
    p.setSpeed(100);
    p.initial();
    p.updateSpeed(1);
    assertEquals(p.getStatus().get(1), 1);
    p.updateSpeed(10);
    assertEquals(p.getStatus().get(1), 10);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test for exception for updateSpeed
  public void exceptionUpdateSpeedTest1() {
    initial();
    p.updateSpeed(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  //Test for exception for setSpeed
  public void exceptionUpdateSpeedTest2() {
    initial();
    p.updateSpeed(0);
  }

  @Test
  //Test for start method for panel
  public void startTest() {
    initial();
    assertEquals(p.getStatus().get(3), false);
    p.start();
    assertEquals(p.getStatus().get(3), true);
  }

  @Test
  //Test for setStop method for panel
  public void setStopTest() {
    initial();
    assertEquals(p.getStatus().get(2), false);
    p.setStop(true);
    assertEquals(p.getStatus().get(2), true);
    p.setStop(false);
    assertEquals(p.getStatus().get(2), false);
  }

  @Test
  //Test for loop method for panel
  public void loopTest() {
    initial();
    assertEquals(p.getStatus().get(4), false);
    p.loop();
    assertEquals(p.getStatus().get(4), true);
    p.loop();
    assertEquals(p.getStatus().get(4), false);
  }

  @Test
  //Test for restart method for panel
  public void restartTest() {
    initial();
    assertEquals(p.getStatus().get(3), false);
    p.restart();
    assertEquals(p.getStatus().get(0), 0);
    assertEquals(p.getStatus().get(3), true);
  }

  @Test
  //Test for method changeFillOrNot for panel
  public void fillTest() {
    initial();
    assertEquals(p.getStatus().get(5), true);
    p.changeFillOrNot();
    assertEquals(p.getStatus().get(5), false);
    p.changeFillOrNot();
    assertEquals(p.getStatus().get(5), true);
  }

  @Test
  //Test for method changeDiscrete method for panel.
  public void changeDiscreteTest() {
    initial();
    IAnimatorModel m = new AnimationModelImpl();
    m.addShape("r", new Rectangle());
    m.addDescription("r", new Description("motion", 0, 1, 2, 100,
        200, 0, 0, 0, 1,
        100, 200, 200, 100, 255, 0, 0));
    m.addDescription("r", new Description("motion", 1, 1, 2, 100,
        200, 0, 0, 0, 1,
        100, 200, 200, 100, 255, 0, 0));
    p.addModel(m);
    p.initial();
    assertEquals(p.getStatus().get(1), 1);
    assertEquals(p.getStatus().get(6), false);
    p.changeDiscrete();
    assertEquals(p.getStatus().get(1), 10);
    assertEquals(p.getStatus().get(6), true);
    p.changeDiscrete();
    assertEquals(p.getStatus().get(1), 1);
    assertEquals(p.getStatus().get(6), false);
  }

  @Test
  //Test lock for updateSpeed
  public void lockForUpdateSpeedTest() {
    initial();
    assertEquals(p.getStatus().get(1), 1);
    IAnimatorModel m = new AnimationModelImpl();
    m.addShape("r", new Rectangle());
    m.addDescription("r", new Description("motion", 0, 1, 2, 100,
        200, 0, 0, 0, 1,
        100, 200, 200, 100, 255, 0, 0));
    m.addDescription("r", new Description("motion", 1, 1, 2, 100,
        200, 0, 0, 0, 1,
        100, 200, 200, 100, 255, 0, 0));
    p.addModel(m);
    p.initial();
    p.updateSpeed(10);
    assertEquals(p.getStatus().get(1), 10);
    p.changeDiscrete();
    p.updateSpeed(100);
    assertEquals(p.getStatus().get(1), 10);
  }

  @Test
  //Test for actionPerformed for frame
  public void actionPerformedTest() {
    initial();
    f.actionPerformed(new ActionEvent(0, 0, "start"));
    assertEquals(p.getStatus().get(3), true);
    assertEquals(p.getStatus().get(2), false);
    f.actionPerformed(new ActionEvent(0, 0, "pause"));
    assertEquals(p.getStatus().get(2), true);
    f.actionPerformed(new ActionEvent(0, 0, "resume"));
    assertEquals(p.getStatus().get(2), false);
    initial();
    f.actionPerformed(new ActionEvent(0, 0, "restart"));
    assertEquals(p.getStatus().get(3), true);
    assertEquals(p.getStatus().get(0), 0);
    assertEquals(p.getStatus().get(4), false);
    f.actionPerformed(new ActionEvent(0, 0, "loop"));
    assertEquals(p.getStatus().get(4), true);
    f.actionPerformed(new ActionEvent(0, 0, "loop"));
    assertEquals(p.getStatus().get(4), false);
    f.actionPerformed(new ActionEvent(0, 0, "fillOrNot"));
    assertEquals(p.getStatus().get(5), false);
    f.actionPerformed(new ActionEvent(0, 0, "fillOrNot"));
    assertEquals(p.getStatus().get(5), true);
    assertEquals(p.getStatus().get(1), 1);
    IAnimatorModel m = new AnimationModelImpl();
    m.addShape("r", new Rectangle());
    m.addDescription("r", new Description("motion", 0, 1, 2, 100,
        200, 0, 0, 0, 1,
        100, 200, 200, 100, 255, 0, 0));
    m.addDescription("r", new Description("motion", 1, 1, 2, 100,
        200, 0, 0, 0, 1,
        100, 200, 200, 100, 255, 0, 0));
    p.addModel(m);
    p.initial();
    f.actionPerformed(new ActionEvent(0, 0, "discrete"));
    assertEquals(p.getStatus().get(1), 10);
    assertEquals(p.getStatus().get(6), true);
    f.actionPerformed(new ActionEvent(0, 0, "discrete"));
    assertEquals(p.getStatus().get(1), 1);
    assertEquals(p.getStatus().get(6), false);
  }
}
