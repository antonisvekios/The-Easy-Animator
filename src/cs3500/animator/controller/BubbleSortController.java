package cs3500.animator.controller;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.Description;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.IDescription;
import cs3500.animator.model.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * A controller that extends AnimationControllerImpl (i.e. includes all functionalities of the
 * main controller), but adds support for out Bubble Sort Animation which is generated using
 * BubbleSortAlgorithm. This controller works in the same way as the normal controller, with the
 * only difference being the input. Instead of accepting a file input, it accepts an integer and
 * generates bars (rectangles) with random height values. The number of bars is equal to the integer
 * provided. The animation generated sorts this list of bars based on their height (smallest to
 * highest). It traverses the list and performs the swaps needed based on the bubble sort
 * algorithm.
 **/
public class BubbleSortController extends AnimationControllerImpl
    implements IAnimationController {
  private HashMap<Integer, Integer> mapOfLength;
  private int addition;
  private IAnimatorModel model;

  /**
   * Constructs a BubbleSortController by initializing a new animator model object.
   */
  public BubbleSortController() {
    model = new AnimationModelImpl();
  }

  /** Initializes all required objects for the bubble sort animation. Generates a set of bar
   * shapes (rectangles) placed in random positions from left to right.
   * @param size the number of bar shapes to generate
   **/
  private void initial(int size) {
    addition = 200 / size;
    mapOfLength = new LinkedHashMap<>();
    List<Integer> index = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      index.add(i);
    }

    for (int i = 1; i <= size; i++) {
      Random rd = new Random();
      int indx = rd.nextInt(index.size());
      mapOfLength.put(index.get(indx), i * addition);
      index.remove(index.get(indx));
    }


    for (int i = 0; i < size; i++) {
      int height = mapOfLength.get(i);
      int barNumber = height / addition;
      int width = 200 / size;
      model.addShape("bar" + barNumber, new Rectangle());
      model.addDescription("bar" + barNumber, new Description("rectangle", 0,
          20 + i * (width + 5), (250 - height), width, height, 100,
          100, 100, 10,
          20 + i * (width + 5), (250 - height), width, height, 100,
          100, 100
      ));
    }
  }


  /** Uses a BubbleSortAlgorithm object to determine and initialize all motions that are required to
   * display the swaps between the shapes. Each swap between two shapes requires a new Description
   * object (motion) for each shape that is moving.
   **/
  private void addDescription() {
    BubbleSortAlgorithm b = new BubbleSortAlgorithm();
    List<String> list = b.sort(mapOfLength);
    for (Object s: list) {
      Scanner scan = new Scanner((String) s);
      int key1 = scan.nextInt();
      int key2 = key1 + 1;
      boolean swap = scan.nextBoolean();
      if (swap) {
        Map<String, List<IDescription>> endState = model.getProcess();
        IDescription lastStateOfKey1 =
            endState.get("bar" + (mapOfLength.get(key1) / addition))
                .get(endState.get("bar" + (mapOfLength.get(key1) / addition)).size() - 1);
        IDescription lastStateOfKey2 =
            endState.get("bar" + (mapOfLength.get(key2) / addition))
                .get(endState.get("bar" + (mapOfLength.get(key2) / addition)).size() - 1);
        model.addDescription("bar" + (mapOfLength.get(key1) / addition),
            new Description("rectangle",
            lastStateOfKey1.getEndTime(), lastStateOfKey1.getEndX(), lastStateOfKey1.getEndY(),
            lastStateOfKey1.getEndWidth(), lastStateOfKey1.getEndHeight(), 100, 100,
            100, lastStateOfKey1.getEndTime() + 10, lastStateOfKey2.getEndX(),
            lastStateOfKey1.getEndY(), lastStateOfKey1.getEndWidth(),
            lastStateOfKey1.getEndHeight(), 100, 100, 100));
        model.addDescription("bar" + (mapOfLength.get(key2) / addition),
            new Description("rectangle",
                lastStateOfKey2.getEndTime(), lastStateOfKey2.getEndX(), lastStateOfKey2.getEndY(),
                lastStateOfKey2.getEndWidth(), lastStateOfKey2.getEndHeight(), 100, 100,
                100, lastStateOfKey2.getEndTime() + 10, lastStateOfKey1.getEndX(),
                lastStateOfKey2.getEndY(), lastStateOfKey2.getEndWidth(),
                lastStateOfKey2.getEndHeight(), 100, 100, 100));
        int value1 = mapOfLength.get(key1);
        int value2 = mapOfLength.get(key2);
        mapOfLength.replace(key1, value2);
        mapOfLength.replace(key2, value1);
      } else {
        this.checkMove("bar" + (mapOfLength.get(key1) / addition));
        this.checkMove("bar" + (mapOfLength.get(key2) / addition));
      }

      for (Map.Entry<Integer, Integer> entry : mapOfLength.entrySet()) {
        if (!(entry.getKey() == key1 || entry.getKey() == key2)) {
          this.checkMove("bar" + (entry.getValue() / addition));
        }
      }
    }
  }

  /** Extends the ending time of the last motion of the given shape to make it stay in place.
   * This method is used to prevent a shape from moving when a swap between two other shapes is
   * taking place.
   *
   * @param barName the name of the shape (bar)
   **/
  private void checkMove(String barName) {
    Map<String, List<IDescription>> map = model.getProcess();
    List<IDescription> list = map.get(barName);
    IDescription last = list.get(list.size() - 1);
    if (last.getStartX() != last.getEndX()) {
      model.addDescription(barName, new Description("rectangle",
          last.getEndTime(), last.getEndX(), last.getEndY(), last.getEndWidth(),
          last.getEndHeight(), 100, 100, 100,
          last.getEndTime() + 10, last.getEndX(), last.getEndY(), last.getEndWidth(),
          last.getEndHeight(), 100, 100, 100));
    } else {
      model.extend(barName, 10);
    }
  }

  @Override
  protected void forInCommand(String inCommand) {
    int in = Integer.parseInt(inCommand);
    if (in <= 0) {
      throw new IllegalArgumentException("Bar number cannot be zero or negative");
    }
    try {
      this.initial(in);

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    model.setBounds(0, 0, 400, 400);
    this.addDescription();
    this.view.addModel(model);
  }

  @Override
  protected void providerIn(String inCommand, int speedCommand) {
    //Do nothing.
  }
}
