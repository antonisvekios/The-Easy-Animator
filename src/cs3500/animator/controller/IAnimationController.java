package cs3500.animator.controller;

import java.io.IOException;
import java.util.List;

/**
 * Represent a controller to control the model and view to create animation.
 */
public interface IAnimationController {

  /**
   * Read the command and create the animation.
   *
   * @param commandList The command String used to create animation.
   */
  void readCommand(List<String> commandList) throws IOException;
}
