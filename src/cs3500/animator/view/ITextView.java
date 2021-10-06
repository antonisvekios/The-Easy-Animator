package cs3500.animator.view;

/**
 * Represent a ITextView that is a view in text format.
 */
public interface ITextView extends IAnimationView {

  /**
   * Get the string view from ITextView.
   *
   * @return A string represent the animation in text format.
   */
  String getText();

  /**
   * Flushes the produced result from the view into the appendable field of this view.
   */
  void flush();
}
