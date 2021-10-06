package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;
import java.io.IOException;
import java.util.List;

/**
 * Represents a view interface used to display the Animator Model.
 */
public interface IAnimationView {

  /**
   * Add the slow motion interval for view.
   */
  void addSlowMotion(List<int[]> intervals);

  /**
   * Make the view generate the output.
   */
  void run() throws IOException;

  /**
   * Add the model to the view.
   *
   * @param model The model will be added.
   */
  void addModel(IAnimatorModel model);

  /**
   * Set the TicksMultiplier to the view, which will represent the speed of animation.
   *
   * @param ticksPerSecond The ticks per second the animation will pass.
   */
  void setTicksMultiplier(int ticksPerSecond);
}
