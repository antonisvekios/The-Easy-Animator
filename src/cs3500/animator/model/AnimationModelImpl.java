package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an implementation of the Animator Model. Consists of methods that allow the mutation
 * of this model. INVARIANT 1: current tick is non-negative. INVARIANT 2: end tick is greater or
 * equal to the current tick.
 */
public final class AnimationModelImpl implements IAnimatorModel {

  private int currentTick;
  private HashMap<String, IShape> shapes;
  private HashMap<String, List<IDescription>> commands;
  private int xBounds;
  private int yBounds;
  private int widthBounds;
  private int heightBounds;

  /**
   * Constructs an AnimationModelImpl with given endTick.
   */
  public AnimationModelImpl() {

    this.currentTick = 0;
    this.shapes = new LinkedHashMap<>();
    this.commands = new LinkedHashMap<>();
    this.xBounds = 0;
    this.yBounds = 0;
    this.widthBounds = 0;
    this.heightBounds = 0;
  }

  /**
   * Construct an AnimationModelImpl with given field.
   *
   * @param shapes The map contains the shape information.
   * @param commands The map contains the description information.
   * @param xBounds The x of left top.
   * @param yBounds The y of left top.
   * @param widthBounds The width of dimension.
   * @param heightBounds The height of dimension.
   */
  public AnimationModelImpl(LinkedHashMap<String, IShape> shapes,
      LinkedHashMap<String, List<IDescription>> commands, int xBounds, int yBounds, int widthBounds,
      int heightBounds) {
    this.shapes = shapes;
    this.commands = commands;
    this.xBounds = xBounds;
    this.yBounds = yBounds;
    this.widthBounds = widthBounds;
    this.heightBounds = heightBounds;
  }

  @Override
  public int getXBounds() {
    return this.xBounds;
  }

  @Override
  public int getYBounds() {
    return this.yBounds;
  }

  @Override
  public int getWidthBounds() {
    return this.widthBounds;
  }

  @Override
  public int getHeightBounds() {
    return this.heightBounds;
  }

  @Override
  public void setBounds(int x, int y, int width, int height) throws IllegalArgumentException {
    this.xBounds = x;
    this.yBounds = y;
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height are invalid");
    }
    this.widthBounds = width;
    this.heightBounds = height;
  }

  @Override
  public int getEndTick() {
    int endTick = 0;
    for (Map.Entry<String, List<IDescription>> entry : commands.entrySet()) {
      List<IDescription> listOfDescription = entry.getValue();
      int privateEndTick = 0;
      for (IDescription d : listOfDescription) {
        privateEndTick = d.getEndTime();
      }

      if (privateEndTick > endTick) {
        endTick = privateEndTick;
      }
    }
    return endTick;
  }

  @Override
  public void addShape(String shapeName, IShape iShape) throws IllegalArgumentException {
    if (shapeName.equals("")) {
      throw new IllegalArgumentException("Provided invalid shape name");
    }
    if (this.shapes.containsKey(shapeName)) {
      throw new IllegalArgumentException("This shape already exists.");
    }

    if (iShape.getPosition() != null) {
      throw new IllegalArgumentException("The initial position must be null");
    }

    shapes.put(shapeName, iShape);
    commands.put(shapeName, new ArrayList<>());
  }

  @Override
  public void addDescription(String shapeName, IDescription description)
      throws IllegalArgumentException {
    List<IDescription> descriptionList = commands.get(shapeName);

    if (commands.get(shapeName).isEmpty()) {
      commands.get(shapeName).add(description);
    } else {
      if (description.getStartTime()
          == descriptionList.get(descriptionList.size() - 1).getEndTime()) {
        commands.get(shapeName).add(description);
      } else {
        throw new IllegalArgumentException("The start time is incorrect. "
            + "The start time of new description should"
            + " be same as the end time of last description");
      }
    }
  }

  @Override
  public void run() {
    for (Map.Entry<String, List<IDescription>> entry : commands.entrySet()) {
      List<IDescription> listOfDescription = entry.getValue();
      if (listOfDescription.get(listOfDescription.size() - 1).getEndTime() < currentTick) {
        this.shapes.get(entry.getKey()).setPosition(null);
      } else {
        for (IDescription d : listOfDescription) {
          if (d.getStartTime() <= this.currentTick
              && d.getEndTime() >= this.currentTick) {
            this.changeColor(entry.getKey(), d.getStartColor(), d.getEndColor(), d.getStartTime(),
                d.getEndTime());
            this.changeSize(entry.getKey(), d.getStartWidth(), d.getStartHeight(),
                d.getEndWidth(), d.getEndHeight(), d.getStartTime(), d.getEndTime());
            Position2D start = new Position2D(d.getStartX(), d.getStartY());
            Position2D end = new Position2D(d.getEndX(), d.getEndY());
            this.move(entry.getKey(), start, end,
                d.getStartTime(), d.getEndTime());
          }
        }
      }
    }
  }

  @Override
  public void initial() {
    for (Map.Entry<String, IShape> entry : shapes.entrySet()) {
      entry.getValue().setPosition(null);
    }
  }

  @Override
  public int maxX() {
    int result = 0;
    for (Map.Entry<String, List<IDescription>> entry : commands.entrySet()) {
      List<IDescription> listOfDescription = entry.getValue();
      for (IDescription d : listOfDescription) {
        if (d.getStartX() + d.getStartWidth() > result) {
          result = d.getStartX() + d.getStartWidth();
        }

        if (d.getEndX() + d.getEndWidth() > result) {
          result = d.getEndX() + d.getEndWidth();
        }
      }
    }
    return result;
  }

  @Override
  public int maxY() {
    int result = 0;
    for (Map.Entry<String, List<IDescription>> entry : commands.entrySet()) {
      List<IDescription> listOfDescription = entry.getValue();
      for (IDescription d : listOfDescription) {
        if (d.getStartY() + d.getStartHeight() > result) {
          result = d.getStartY() + d.getStartHeight();
        }

        if (d.getEndY() + d.getEndHeight() > result) {
          result = d.getEndY() + d.getEndHeight();
        }
      }
    }
    return result;
  }

  @Override
  public void setCurrentTick(int tick) throws IllegalArgumentException {
    if (tick < 0) {
      throw new IllegalArgumentException("The tick provided is negative");
    } else if (tick > this.getEndTick()) {
      throw new IllegalArgumentException("The animation is over");
    }
    this.currentTick = tick;
  }

  @Override
  public Map<String, IShape> getShapes() {
    LinkedHashMap<String, IShape> output = new LinkedHashMap<>();
    for (String key : shapes.keySet()) {
      output.put(key, shapes.get(key));
    }
    return output;
  }

  @Override
  public void removeShape(String shapeName) {
    if (!this.shapes.containsKey(shapeName)) {
      throw new IllegalArgumentException("The shape name provided does not exist");
    }
    this.shapes.remove(shapeName);
    this.commands.remove(shapeName);
  }

  @Override
  public Map<String, List<IDescription>> getProcess() {
    LinkedHashMap<String, List<IDescription>> output = new LinkedHashMap<>();
    for (String key : this.commands.keySet()) {
      List<IDescription> shapeCommands = new ArrayList<IDescription>(this.commands.get(key));
      output.put(key, shapeCommands);
    }
    return output;
  }

  /**
   * Changes the color of the given shape and sets it to the color provided.
   *
   * @param shapeName The name of the shape to be changed.
   * @param starColor The beginning color of the shape.
   * @param endColor  The ending color of the shape.
   * @param startTime The starting time of the operation.
   * @param endTime   The ending time of the operation.
   */
  private void changeColor(String shapeName, Color starColor, Color endColor,
      int startTime, int endTime) {
    float proportion = this.getProportion(startTime, endTime);
    int r = Math.round((endColor.getRed() - starColor.getRed()) * proportion)
        + starColor.getRed();
    int g = Math.round((endColor.getGreen() - starColor.getGreen()) * proportion)
        + starColor.getGreen();
    int b = Math.round((endColor.getBlue() - starColor.getBlue()) * proportion)
        + starColor.getBlue();

    shapes.get(shapeName).setColor(new Color(r, g, b));
  }

  /**
   * Changes the size of the given shape and sets it to the dimensions provided.
   *
   * @param shapeName   The name of the shape to be changed.
   * @param startWidth  The beginning width value of the shape.
   * @param startHeight The beginning height value of the shape.
   * @param endWidth    The ending width value of the shape.
   * @param endHeight   The ending height value of the shape.
   * @param startTime   The starting time of the operation.
   * @param endTime     The ending time of the operation.
   */
  private void changeSize(String shapeName, int startWidth, int startHeight,
      int endWidth, int endHeight, int startTime, int endTime) {

    float proportion = this.getProportion(startTime, endTime);

    int newWidth = Math.round((endWidth - startWidth) * proportion) + startWidth;
    int newHeight = Math.round((endHeight - startHeight) * proportion) + startHeight;

    shapes.get(shapeName).setWidth(newWidth);
    shapes.get(shapeName).setHeight(newHeight);
  }

  /**
   * Moves the given shape to the given position.
   *
   * @param shapeName     The name of the shape to be moved.
   * @param startPosition The starting position of the shape.
   * @param endPosition   The ending position of the shape.
   * @param startTime     The starting time of the operation.
   * @param endTime       The ending time of the operation.
   */
  private void move(String shapeName, Position2D startPosition, Position2D endPosition,
      int startTime, int endTime) {
    if (endPosition == null) {
      shapes.get(shapeName).setPosition(null);
    } else {
      float proportion = this.getProportion(startTime, endTime);

      int x =
          ((int) ((endPosition.getX() - startPosition.getX()) * proportion)) + startPosition.getX();
      int y =
          ((int) ((endPosition.getY() - startPosition.getY()) * proportion)) + startPosition.getY();
      IPosition2D newPosition = new Position2D(x, y);
      shapes.get(shapeName).setPosition(newPosition);
    }
  }

  @Override
  public int getCurrentTick() {
    return this.currentTick;
  }

  @Override
  public void extend(String name, int extendTime) {
    commands.get(name).get(commands.get(name).size() - 1).extend(extendTime);
  }

  /**
   * Determines the proportion of change that should be made in this tick.
   *
   * @param startTime the starting time of the operation.
   * @param endTime   the ending time of the operation.
   * @return the proportion of change to be applied in this tick.
   */
  private float getProportion(int startTime, int endTime) {
    int totalTick = (endTime - startTime);
    int soFarTick = (this.currentTick - startTime);
    return (float) soFarTick / totalTick;
  }

  /**
   * Get the index of the command according to given start time.
   *
   * @param list The list of description.
   * @param startTime The start time.
   * @return An int show the index of command.
   */
  private static int getCommandIndex(List<IDescription> list, int startTime) {
    if (list.isEmpty()) {
      return 0;
    }

    int indexMin = 0;
    int indexMax = list.size() - 1;

    while (indexMin < indexMax) {
      int indexMiddle = (indexMin + indexMax) / 2;
      int middleTime = list.get(indexMiddle).getStartTime();

      if (startTime == middleTime) {
        return indexMiddle;
      } else if (startTime > middleTime) {
        indexMin = indexMiddle + 1;
      } else {
        indexMax = indexMiddle - 1;
      }
    }

    if (startTime < list.get(indexMin).getStartTime()) {
      return indexMin - 1;
    } else {
      return indexMin;
    }
  }

  @Override
  public List<Integer> stopTime() {
    HashSet<Integer> timeSet = new HashSet<>();
    for (Map.Entry<String, List<IDescription>> entry : commands.entrySet()) {
      for (IDescription d : entry.getValue()) {
        timeSet.add(d.getStartTime());
        timeSet.add(d.getEndTime());
      }
    }
    List<Integer> result = new ArrayList<Integer>(timeSet);
    Collections.sort(result);
    return result;
  }

  /**
   * Represent an implement of AnimationBuilder。 Consist of methods
   * to build a model.
   */
  public static final class AnimationModelBuilderImpl implements AnimationBuilder<IAnimatorModel> {

    private LinkedHashMap<String, List<IDescription>> commands;
    private LinkedHashMap<String, IShape> shapes;
    private int x = 0; //leftmost x
    private int y = 0; //topmost y
    private int width = 1000;
    private int height = 600;

    /**
     * Construct an AnimationModelBuilderImpl to build a model.
     */
    public AnimationModelBuilderImpl() {
      this.commands = new LinkedHashMap<>();
      this.shapes = new LinkedHashMap<>();
    }

    @Override
    public IAnimatorModel build() {
      for (String key : this.commands.keySet()) {
        if (!this.shapes.containsKey(key)) {
          throw new IllegalStateException("A command must be associated with a valid shape");
        }
      }
      return new AnimationModelImpl(this.shapes, this.commands, this.x, this.y,
          this.width, this.height);
    }

    @Override
    public AnimationBuilder<IAnimatorModel> setBounds(int x, int y, int width, int height) {
      if (width < 0 || height < 0) {
        throw new IllegalArgumentException("Cannot have a negative width or height");
      }
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> declareShape(String name, String type) {
      if (this.shapes.containsKey(name)) {
        throw new IllegalArgumentException("A shape with this name already exists");
      }
      IShape s;
      switch (type.toLowerCase()) {
        case "rectangle":
          s = new Rectangle();
          break;
        case "ellipse":
          s = new Ellipse();
          break;
        case "plus":
          s = new Plus();
          break;
        default:
          s = null;
      }
      if (s == null) {
        throw new IllegalArgumentException("Invalid shape type");
      }
      this.shapes.put(name, s);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) throws IllegalArgumentException {
      String type = "motion";
      Description command = new Description(type, t1, x1, y1, w1, h1, r1, g1, b1,
          t2, x2, y2, w2, h2, r2, g2, b2);
      if (this.commands.containsKey(name)) {
        List<IDescription> list = this.commands.get(name);
        if (list.get(list.size() - 1).getEndTime() == t1) {
          this.commands.get(name)
              .add(getCommandIndex(this.commands.get(name), command.getStartTime()) + 1, command);
        } else {
          throw new IllegalArgumentException("The start time of new descpriton must equals the "
              + "end time of last description.");
        }
      } else {
        this.commands.put(name, new ArrayList<>(Arrays.asList(command)));
      }
      return this;
    }
  }
}
