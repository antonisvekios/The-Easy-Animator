import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.TextualView;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests for all public methods of {@link cs3500.animator.view.TextualView}.
 */
public class TextualViewTest {

  private AnimationModelImpl.AnimationModelBuilderImpl builder;
  private IAnimatorModel model;

  //The initial condition for testing the Textual View tests
  private void initialCondition() {
    this.builder = new AnimationModelImpl.AnimationModelBuilderImpl();
  }

  @Test
  //testing the get text method for an empty view (i.e. no shapes and no motions).
  public void testGetTextEmptyModel() {
    StringBuilder out = new StringBuilder();
    this.initialCondition();
    this.model = this.builder.build();
    TextualView view = new TextualView(model,1,out);
    assertEquals(view.getText(), "");
  }

  @Test
  //testing the get text method for a model with several declared shapes and motion commands.
  public void testGetText() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();

    this.builder.declareShape("R", "rectangle");
    this.builder.addMotion("R", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
        10, 10, 30, 0, 255, 0);
    this.builder.addMotion("R", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
        20, 10, 30, 0, 255, 0);
    this.builder.declareShape("C", "ellipse");
    this.builder.addMotion("C", 0, 1, 1, 20, 20, 255, 0, 0, 10, 20,
        20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    TextualView view = new TextualView(model,1,out);
    assertEquals(view.getText(), "");
    view.run();
    assertEquals(view.getText(), "shape R rectangle\n"
        + "motion\tR\t0.00\t10\t10\t10\t30\t0\t255\t0\t\t5.00\t20\t10\t10\t30\t0\t255\t0\n"
        + "motion\tR\t5.00\t20\t10\t10\t30\t0\t255\t0\t\t10.00\t20\t20\t10\t30\t0\t255\t0\n"
        + "shape C ellipse\n"
        + "motion\tC\t0.00\t1\t1\t20\t20\t255\t0\t0\t\t10.00\t20\t20\t20\t20\t255\t0\t0\n");
  }

  @Test
  //testing the run method for an empty view (i.e. no shapes and no motions).
  public void testRunEmptyModel() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();

    this.model = this.builder.build();
    TextualView view = new TextualView(model,1,out);
    view.run();
    assertEquals(view.getText(), "");
  }

  @Test
  //testing the run method for a model with several declared shapes and motion commands.
  public void testRun1() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();

    this.builder.declareShape("R", "rectangle")
        .addMotion("R", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
            10, 10, 30, 0, 255, 0)
        .addMotion("R", 5, 20, 10, 10, 30, 0, 255, 0, 10, 20,
            20, 10, 30, 0, 255, 0)
        .declareShape("C", "ellipse")
        .addMotion("C", 0, 1, 1, 20, 20, 255, 0, 0, 10, 20,
            20, 20, 20, 255, 0, 0);
    this.model = this.builder.build();
    TextualView view = new TextualView(model,1,out);
    view.run();
    assertEquals(view.getText(), "shape R rectangle\n"
        + "motion\tR\t0.00\t10\t10\t10\t30\t0\t255\t0\t\t5.00\t20\t10\t10\t30\t0\t255\t0\n"
        + "motion\tR\t5.00\t20\t10\t10\t30\t0\t255\t0\t\t10.00\t20\t20\t10\t30\t0\t255\t0\n"
        + "shape C ellipse\n"
        + "motion\tC\t0.00\t1\t1\t20\t20\t255\t0\t0\t\t10.00\t20\t20\t20\t20\t255\t0\t0\n");
  }

  @Test
  //testing the run method for a model with only one declared shape
  //and two motion commands for the shape.
  public void testRun2() throws IOException {
    StringBuilder out = new StringBuilder();
    this.initialCondition();

    this.builder.declareShape("R", "rectangle")
        .addMotion("R", 0, 10, 10, 10, 30, 0, 255, 0, 5, 20,
            10, 10, 30, 0, 255, 0)
        .addMotion("R", 5, 20, 10, 10, 30, 0, 255, 0, 8, 20,
            10, 10, 30, 0, 0, 255);
    this.model = this.builder.build();
    TextualView view = new TextualView(model,5,out);
    view.run();
    assertEquals("shape R rectangle\n"
            + "motion\tR\t0.00\t10\t10\t10\t30\t0\t255\t0\t\t1.00\t20\t10\t10\t30\t0\t255\t0\n"
            + "motion\tR\t1.00\t20\t10\t10\t30\t0\t255\t0\t\t1.60\t20\t10\t10\t30\t0\t0\t255\n",
        view.getText());
  }

  @Test
  //testing the run method for a model with several declared plus sign shapes and motions.
  public void testRun3() throws IOException {
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
    TextualView view = new TextualView(model,1,out);
    view.run();
    assertEquals(view.getText(), "shape P1 plus\n"
        + "motion\tP1\t0.00\t10\t10\t10\t30\t0\t255\t0\t\t5.00\t20\t10\t10\t30\t0\t255\t0\n"
        + "motion\tP1\t5.00\t20\t10\t10\t30\t0\t255\t0\t\t10.00\t20\t20\t10\t30\t0\t255\t0\n"
        + "shape P2 plus\n"
        + "motion\tP2\t0.00\t1\t1\t20\t20\t255\t0\t0\t\t10.00\t20\t20\t20\t20\t255\t0\t0\n");
  }
}