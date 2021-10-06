import cs3500.animator.controller.BubbleSortAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Tests for the sorting method of {@link cs3500.animator.controller.BubbleSortAlgorithm}.
 */
public class BubbleTest {

  @Test
  //testing the sort method when provided an empty map.
  public void testSortEmptyMap() {
    BubbleSortAlgorithm b = new BubbleSortAlgorithm();
    HashMap<Integer, Integer> m = new LinkedHashMap<>();
    assertEquals(b.sort(m), new ArrayList<String>());
  }

  @Test
  //testing the sort method when only provided a map of 1 element (i.e. no sorting can take place).
  public void testSortSingleElement() {
    BubbleSortAlgorithm b = new BubbleSortAlgorithm();
    HashMap<Integer, Integer> m = new LinkedHashMap<>();
    m.put(0, 1000);
    assertEquals(b.sort(m), new ArrayList<String>());
  }

  @Test
  //testing the sort method for a map that is already sorted according to height values.
  public void testSortAlreadySorted() {
    BubbleSortAlgorithm b = new BubbleSortAlgorithm();
    HashMap<Integer, Integer> m = new LinkedHashMap<>();
    m.put(0, 100);
    m.put(1, 200);
    m.put(2, 300);
    assertEquals(b.sort(m), Arrays.asList("0 false", "1 false"));
    m.put(3, 400);
    assertEquals(b.sort(m), Arrays.asList("0 false", "1 false", "2 false"));
  }

  @Test
  //testing the sort method for an unsorted map of index and height values.
  public void testSortUnsorted() {
    BubbleSortAlgorithm b = new BubbleSortAlgorithm();
    HashMap<Integer, Integer> m = new LinkedHashMap<>();
    m.put(0, 10);
    m.put(1, 3);
    m.put(2, 7);
    assertEquals(b.sort(m), Arrays.asList("0 true", "1 true", "0 false", "1 false"));
    m.put(3, 15);
    assertEquals(b.sort(m),
        Arrays.asList("0 true", "1 true", "2 false", "0 false", "1 false", "2 false"));
  }
}