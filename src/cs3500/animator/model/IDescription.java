package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents an IDescription interface to provide and set the information of motion.
 */
public interface IDescription {

  /**
   * Get the type of description.
   *
   * @return The type of description.
   */
  String getType();

  /**
   * Returns the beginning tick of the description.
   *
   * @return the beginning tick as an integer.
   */
  int getStartTime();

  /**
   * Get the start x of description.
   *
   * @return The start x of description.
   */
  int getStartX();

  /**
   * Get the start y of description.
   *
   * @return The start y of description.
   */
  int getStartY();

  /**
   * Returns the beginning width of the description.
   *
   * @return the beginning width of the description as an integer.
   */
  int getStartWidth();

  /**
   * Returns the beginning height of the description.
   *
   * @return the beginning height of the description as an integer.
   */
  int getStartHeight();

  /**
   * Returns the starting color of the description.
   *
   * @return the starting color of the description as a Color object.
   */
  Color getStartColor();

  /**
   * Returns the ending tick of the description.
   *
   * @return the ending tick as an integer.
   */
  int getEndTime();

  /**
   * Get the end x of description.
   *
   * @return The end x of description.
   */
  int getEndX();

  /**
   * Get the end y of description.
   *
   * @return The end y of description.
   */
  int getEndY();

  /**
   * Returns the ending width of the description.
   *
   * @return the ending width of the description as an integer.
   */
  int getEndWidth();

  /**
   * Returns the ending height of the description.
   *
   * @return the ending height of the description as an integer.
   */
  int getEndHeight();

  /**
   * Returns the ending color of the description.
   *
   * @return the ending color of the description as a Color object.
   */
  Color getEndColor();

  /**
   * Extend the endTick of description.
   */
  void extend(int tick);
}
