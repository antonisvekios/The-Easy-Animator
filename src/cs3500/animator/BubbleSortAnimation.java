package cs3500.animator;

import cs3500.animator.controller.BubbleSortController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class used to get the command and run the Bubble Sort Animation.
 */
public final class BubbleSortAnimation {

  /**
   * The main method run the bubble sort animation with given command.
   *
   * @param args The command
   */
  public static void main(String[] args) {
    List<String> argsList = new ArrayList<>(Arrays.asList(args));
    try {
      new BubbleSortController().readCommand(argsList);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}