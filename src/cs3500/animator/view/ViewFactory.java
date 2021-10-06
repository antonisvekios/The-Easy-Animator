package cs3500.animator.view;

/**
 * A creator to build specific view.
 */
public class ViewFactory {

  /**
   * A constructor to call a new IAnimationView.
   */
  public ViewFactory() {
    //It does not have any field
  }

  /**
   * A enum to present ViewType.
   */
  public enum ViewType {
    text("text"),
    svg("svg"),
    visual("visual"),
    interactive("interactive");

    private final String value;
    ViewType(String value) {
      this.value = value;
    }


    @Override
    public String toString() {
      return this.value;
    }
  }

  /**
   * Use the type to create a animation view .
   *
   * @param type  The type you want for the view.
   * @return PyramidSolitaireModel   The animation view according to the given type.
   */
  public static IAnimationView create(ViewType type, Appendable out) {
    switch (type) {
      case text: return new TextualView(out);
      case svg: return new SVGView(out);
      case visual: return new VisualAnimationView();
      case interactive: return new InteractiveViewFrame();
      default: return null;
    }
  }
}
