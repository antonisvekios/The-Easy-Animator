import static org.junit.Assert.assertEquals;

import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.ViewFactory.ViewType;
import cs3500.animator.view.VisualAnimationView;
import org.junit.Test;

/**
 * Tests for all public methods of {@link cs3500.animator.view.ViewFactory}.
 */
public class ViewFactoryTest {
  @Test
  public void viewTypeTest() {
    assertEquals(ViewType.svg.toString(), "svg");
    assertEquals(ViewType.text.toString(), "text");
    assertEquals(ViewType.visual.toString(), "visual");
  }

  @Test
  public void createTest() {
    StringBuilder out = new StringBuilder();
    ViewFactory factory = new ViewFactory();
    assertEquals(factory.create(ViewType.svg, out) instanceof SVGView, true);
    assertEquals(factory.create(ViewType.text, out) instanceof TextualView, true);
    assertEquals(factory.create(ViewType.visual, out) instanceof VisualAnimationView, true);
  }

}
