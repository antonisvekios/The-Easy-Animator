package cs3500.animator.model;

import java.util.List;
import java.util.Map;

/**
 * Represents an interface for creating and using animations.
 * Consists of methods that return information on
 * the current state of the model.
 */
public interface IAnimatorModel {

  /**
   * Remove the given shape.
   *
   * @param shapeName The name of the shape will be removed.
   */
  void removeShape(String shapeName);

  /**
   * Adds the given shape to the Animator Model.
   *
   * @param shapeName The name of the shape.
   * @param iShape    The shape object to be added to the animator.
   * @throws IllegalArgumentException If this shape already exists in the model.
   */
  void addShape(String shapeName, IShape iShape) throws IllegalArgumentException;

  /**
   * Add a description for specific shape to the Animator Model.
   *
   * @param shapeName The name of the shape.
   * @param command   The description object associated with the shape.
   * @throws IllegalArgumentException if the starting and ending times of the description are
   *                                  invalid in terms of the Animator Model.
   */
  void addDescription(String shapeName, IDescription command) throws IllegalArgumentException;

  /**
   * Runs the Animator Model.
   */
  void run();

  /**
   * Get the left top x.
   *
   * @return The x of left top of animation.
   */
  int getXBounds();

  /**
   * Get the left top y.
   *
   * @return The y of left top of animation.
   */
  int getYBounds();

  /**
   * Get the width of dimension.
   *
   * @return The width of dimension of animation.
   */
  int getWidthBounds();

  /**
   * Get the height of dimension.
   *
   * @return The height of dimension of animation.
   */
  int getHeightBounds();

  /**
   * Set the background information of animation.
   *
   * @param x The left top x.
   * @param y The left top y.
   * @param width The width of dimension.
   * @param height The height of dimension.
   * @throws IllegalArgumentException Throw an IllegalArgumentException when width or height
   *          is negative.
   */
  void setBounds(int x, int y, int width, int height) throws IllegalArgumentException;

  /**
   * Sets the current tick of the animation to the given one.
   *
   * @param tick the current tick of the animation as an integer.
   * @throws IllegalArgumentException if the tick provided is negative or exceeds the running time
   *                                  of the Animator Model.
   */
  void setCurrentTick(int tick) throws IllegalArgumentException;

  /**
   * Returns all shapes which are part of the model. Gets a map that connects the shape names to the
   * matching shape objects.
   *
   * @return the map connecting shape names to shape objects.
   */
  Map<String, IShape> getShapes();

  /**
   * Returns all commands/processes which are part of the animator model. Gets a map that connects
   * the shape names to the matching commands (Description objects).
   *
   * @return the map connecting shape names to their Description/command.
   */
  Map<String, List<IDescription>> getProcess();

  /**
   * Get the tick that the animation end.
   *
   * @return Return the tick that animation end.
   */
  int getEndTick();

  /**
   *  Get the max x the shape in animation will reach.
   *
   * @return Return the max x the shape in animation will reach.
   */
  int maxX();

  /**
   *  Get the max y the shape in animation will reach.
   *
   * @return Return the max y the shape in animation will reach.
   */
  int maxY();

  /**
   * Get the current tick of the model.
   *
   * @return Return the current tick of the model.
   */
  int getCurrentTick();

  /**
   * Extend the endTick of last description.
   *
   * @param name  The name of shape whose description will be extended
   * @param extendTime How long will be extended.
   */
  void extend(String name, int extendTime);

  /**
   * Initial the position of shapes when this animation need to be looped.
   */
  void initial();

  /**
   * Get the stop time for every description without repetition.
   */
  List<Integer> stopTime();
}
