package cs3500.animator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class including the main sorting method that is used to programmatically generate the motions
 * required for our bubble sort animation.
 **/
public class BubbleSortAlgorithm {

  /**
   * Constructs a BubbleSortAlgorithm object to be used for sorting a map that keeps track of shape
   * indexes and height values.
   */
  public BubbleSortAlgorithm() {
    //An empty constructor;
    //We do not combine the only method to controller because we may have any other algorithms
    //that may work in same way.
  }

  /**
   * Accepts a map of indexes associated with height values of shapes. Uses bubble sort to determine
   * what swaps need to be made in order to sort the map based on height (smallest to largest).
   * Returns a list of strings that determines which shape positions need to be swapped, e.g.
   * [0 true, 1 true, 2 false, 0 false, ...] means swap the shapes at positions 0 and 1, but not 2.
   *
   * @param m the map of indexes associated with shape height values
   **/
  public List<String> sort(HashMap<Integer, Integer> m) {
    boolean completed = false;
    HashMap<Integer, Integer> result = new LinkedHashMap<>();
    List<String> output = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
      result.put(entry.getKey(), entry.getValue());
    }

    while (!completed) {
      for (int i = 0; i <= result.size() - 2; i++) {
        boolean swap = false;
        int i1 = result.get(i);
        int i2 = result.get(i + 1);
        if (i1 > i2) {
          swap = true;
          result.replace(i, i2);
          result.replace(i + 1, i1);
        }
        output.add(i + " " + swap);
      }

      completed = result.equals(m);
      m = new LinkedHashMap<>();
      for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
        m.put(entry.getKey(), entry.getValue());
      }
    }
    return output;
  }
}
