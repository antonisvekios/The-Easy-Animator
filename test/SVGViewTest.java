import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.ITextView;
import cs3500.animator.view.SVGView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Test;

/**
 * Tests for all public methods of {@link cs3500.animator.view.SVGView}.
 */
public class SVGViewTest {

  private AnimationModelImpl.AnimationModelBuilderImpl builder;
  private IAnimatorModel model;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  //The initial condition for testing the Animator SVG View tests
  private void initialCondition() {
    this.builder = new AnimationModelImpl.AnimationModelBuilderImpl();
    System.setOut(new PrintStream(outContent));
  }

  @Test(expected = IllegalArgumentException.class)
  //testing the SVGView constructor when provided a value of zero for ticks per second.
  public void testSVGViewConstructorZeroTicks() {
    StringBuilder out = new StringBuilder();
    this.initialCondition();
    model = builder.build();
    SVGView view = new SVGView(model, 0, out);
  }

  @Test(expected = IllegalArgumentException.class)
  //testing the SVGView constructor when provided a negative value for ticks per second.
  public void testSVGViewConstructorNegativeTicks() {
    StringBuilder out = new StringBuilder();
    this.initialCondition();
    model = builder.build();
    SVGView view = new SVGView(model, -1, out);
  }

  @Test
  //testing the get Text method for the view of an empty constructor (i.e. with no shapes
  //or motions declared).
  public void testGetTextEmptyModel() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();
    this.model = this.builder.build();
    ITextView view = new SVGView(model, 1, out);
    assertEquals(view.getText(), "");
    view.run();
    assertEquals(view.getText(),
        "<svg width='1000' height='600' version='1.1' xmlns='http://www.w3.org/2000/svg'>"
            + "\n</svg>");
  }

  @Test
  //testing the get text method for a model with several declared shapes and motion commands.
  public void testGetText() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();
    this.builder.declareShape("R", "Rectangle")
        .addMotion("R", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
            10, 10, 30, 0, 255, 0)
        .addMotion("R", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
            20, 10, 30, 0, 255, 0)
        .declareShape("C", "Ellipse")
        .addMotion("C", 0, 1, 1, 20, 20, 255, 0, 0, 10, 20,
            20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    ITextView view = new SVGView(model, 1, out);
    view.run();
    assertEquals(view.getText(),
        "<svg width='1000' height='600' version='1.1' xmlns='http://www.w3.org/2000/svg'>"
            + "\n"
            + "<rect id='R' x='10' y='10' width='10' height='30' f"
            + "ill='rgb(0,255,0)' visibility='hidden'>"
            + "\n"
            + "<set attributeType='xml' attributeName='visibility' begin='0.000000s' to='visible'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='x' begin='0ms' dur="
            + "'5000ms' from='10' to='20' fill='freeze'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='y' begin='5000ms' dur="
            + "'5000ms' from='10' to='20' fill='freeze'/>"
            + "\n"
            + "</rect>"
            + "\n"
            + "<ellipse id='C' cx='11' cy='11' rx='10' ry='10' fill='rgb"
            + "(255,0,0)' visibility='hidden'>"
            + "\n"
            + "<set attributeType='xml' attributeName='visibility' b"
            + "egin='0.000000s' to='visible'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='cx' begin="
            + "'0ms' dur='10000ms' from='11' to='30' fill='freeze'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='cy' begin"
            + "='0ms' dur='10000ms' from='11' to='30' fill='freeze'/>"
            + "\n"
            + "</ellipse>"
            + "\n"
            + "</svg>");
  }

  @Test
  //testing the get text method for a model with several declared shapes and motion commands
  //including a plus sign
  public void testGetTextPlusSign() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();
    this.builder.declareShape("P1", "Plus")
        .addMotion("P1", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
            10, 10, 30, 0, 255, 0)
        .addMotion("P1", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
            20, 10, 30, 0, 255, 0)
        .declareShape("P2", "Plus")
        .addMotion("P2", 0, 1, 1, 20, 20, 255, 0, 0, 10, 20,
            20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    ITextView view = new SVGView(model, 1, out);
    view.run();
    assertEquals(view.getText(),
        "<svg width='1000' height='600' version='1.1' xmlns='http://www.w3.org/2000/svg'>"
            + "\n"
            + "<polygon id='P1' points='12,10 17,10 17,12 20,12 20,17 17,17 17,20 12,20 12,17 10,17"
            + " 10,12 12,12' fill='rgb(0,255,0)' visibility='hidden'>"
            + "\n"
            + "<set attributeType='xml' attributeName='visibility' begin='0.000000s' to='visible'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='points' begin='0ms' dur='5000ms' "
            + "from='12,10 17,10 17,12 20,12 20,17 17,17 17,20 12,20 12,17 10,17 10,12 12,12' "
            + "to='22,10 27,10 27,12 30,12 30,17 27,17 27,20 22,20 22,17 20,17 20,12 22,12' "
            + "fill='freeze'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='points' begin='5000ms' dur='5000ms' "
            + "from='22,10 27,10 27,12 30,12 30,17 27,17 27,20 22,20 22,17 20,17 20,12 22,12' "
            + "to='22,20 27,20 27,22 30,22 30,27 27,27 27,30 22,30 22,27 20,27 20,22 22,22' "
            + "fill='freeze'/>"
            + "\n"
            + "</polygon>"
            + "\n"
            + "<polygon id='P2' points='6,1 16,1 16,6 21,6 21,16 16,16 16,21 6,21 6,16 1,16 1,6 6,"
            + "6' fill='rgb(255,0,0)' visibility='hidden'>"
            + "\n"
            + "<set attributeType='xml' attributeName='visibility' b"
            + "egin='0.000000s' to='visible'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='points' begin='0ms' dur='10000ms' "
            + "from='6,1 16,1 16,6 21,6 21,16 16,16 16,21 6,21 6,16 1,16 1,6 6,6' "
            + "to='25,20 35,20 35,25 40,25 40,35 35,35 35,40 25,40 25,35 20,35 20,25 25,25' "
            + "fill='freeze'/>"
            + "\n"
            + "<animate attributeType='xml' attributeName='points' begin='0ms' dur='10000ms' "
            + "from='6,1 16,1 16,6 21,6 21,16 16,16 16,21 6,21 6,16 1,16 1,6 6,6' "
            + "to='25,20 35,20 35,25 40,25 40,35 35,35 35,40 25,40 25,35 20,35 20,25 25,25' "
            + "fill='freeze'/>"
            + "\n"
            + "</polygon>"
            + "\n"
            + "</svg>");
  }
}

