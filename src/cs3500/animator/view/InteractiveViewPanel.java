package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.IShape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Represent a panel draw the animation.
 */
public class InteractiveViewPanel extends JPanel implements ActionListener, Scrollable {

  public IAnimatorModel model;
  private int tick;
  private int ticksMultiplier;
  private Timer timer;
  private boolean stop;
  private boolean start;
  private boolean loop;
  private boolean fillOrNot;
  private boolean discrete;
  private int saveSpeed;
  private int stopTime;
  private List<int[]> slowMotion;
  private boolean slow;

  /**
   * Construct a VisualAnimationPanel.
   */
  public InteractiveViewPanel() {
    ticksMultiplier = 1;
    tick = 1;
    stop = false;
    start = false;
    loop = false;
    fillOrNot = true;
    discrete = false;
    saveSpeed = 0;
    stopTime = 0;
    slowMotion = new ArrayList<>();
    slow = false;
  }


  /**
   * Get a list show certain status of this panel.
   *
   * @return A list contains the status information of panel.
   */
  public List getStatus() {
    List result = new ArrayList();
    result.add(tick);
    result.add(ticksMultiplier);
    result.add(stop);
    result.add(start);
    result.add(loop);
    result.add(fillOrNot);
    result.add(discrete);
    result.add(slow);
    return result;
  }

  /**
   * Change the animation pattern between fill or notFill mode.
   */
  public void changeFillOrNot() {
    fillOrNot = !fillOrNot;
  }

  /**
   * Change the discrete boolean to change the animation play mode between continue and discrete.
   */
  public void changeDiscrete() {
    if (!discrete) {
      tick = getNextTime(model.stopTime(), tick);
      saveSpeed = ticksMultiplier;
      updateSpeed(10);
      discrete = !discrete;
    } else {
      discrete = !discrete;
      updateSpeed(saveSpeed);
    }
  }

  /**
   * Get next stop time for overall pattern animation.
   *
   * @param timeList    The time list should stop for overall animation.
   * @param currentTime The current tick.
   * @return The time tick should jump to to show the overall animatino.
   */
  private int getNextTime(List<Integer> timeList, int currentTime) {
    int result = 0;
    for (int i : timeList) {
      if (currentTime == 0) {
        result = 0;
      } else if (i > currentTime) {
        result = i;
        break;
      }
    }
    return result;
  }

  /**
   * Add slow motion interval for panel animation.
   *
   * @param intervals The list show the interval for slow motion.
   */
  public void addSlowMotion(List<int[]> intervals) {
    slowMotion = intervals;
  }

  /**
   * Set the time speed.
   *
   * @param newTicksMultiplier The time tick represent the speed (how many tick per second).
   */
  public void setSpeed(int newTicksMultiplier) {
    if (newTicksMultiplier <= 0) {
      throw new IllegalArgumentException("The speed cannot be 0 or negative");
    }

    this.ticksMultiplier = newTicksMultiplier;
  }

  /**
   * Update the speed and update the timer.
   *
   * @param newTicksMultiplier The new speed.
   */
  public void updateSpeed(int newTicksMultiplier) {
    if (newTicksMultiplier <= 0) {
      throw new IllegalArgumentException("The speed cannot be 0 or negative");
    }

    if (!discrete && !slow) {
      this.ticksMultiplier = newTicksMultiplier;
      this.timer.setDelay(1000 / this.ticksMultiplier);
    }
  }

  /**
   * Make the animation start to play.
   */
  public void start() {
    start = true;
  }

  /**
   * Initialize the whole panel and make the timer run.
   */
  public void initial() {
    setPreferredSize(new Dimension(this.model.maxX() + 50, this.model.maxY() + 50));
    setLocation(this.model.getXBounds(), this.model.getYBounds());
    this.timer = new Timer(1000 / this.ticksMultiplier, this);
    timer.setInitialDelay(0);
    timer.start();
  }

  /**
   * Add the model to the panel.
   *
   * @param newModel The model used to draw.
   */
  public void addModel(IAnimatorModel newModel) {
    this.model = newModel;
  }

  /**
   * Trans the stop status of panel according to the given input.
   *
   * @param s boolean Should the animation stop or continue.
   */
  public void setStop(boolean s) {
    stop = s;
  }

  /**
   * Change the status of loop, true to false or false to true.
   */
  public void loop() {
    loop = !loop;
  }

  /**
   * Make the tick restart from beginning and make it start to play.
   */
  public void restart() {
    this.tick = 0;
    this.start = true;
  }

  /**
   * Check if the tick is in the start tick of certain slow motion.
   *
   * @return If the current tick is at start tick of certain slow motion.
   */
  private boolean reachBeginSlow() {
    boolean result = false;
    for (int[] i : slowMotion) {
      if (tick == i[0]) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Check if the tick is in the end tick of certain slow motion.
   *
   * @return If the current tick is at end tick of certain slow motion.
   */
  private boolean reachEndSlow() {
    boolean result = false;
    for (int[] i : slowMotion) {
      if (tick == i[1]) {
        result = true;
      }
    }
    return result;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g);
    model.setCurrentTick(tick);
    model.run();

    if (start) {
      if (!stop) {

        if (!discrete) {

          if (!slowMotion.isEmpty()) {
            if (reachBeginSlow()) {
              saveSpeed = ticksMultiplier;
              this.updateSpeed(5);
              slow = true;
            } else if (reachEndSlow()) {
              this.ticksMultiplier = saveSpeed;
              this.timer.setDelay(1000 / this.ticksMultiplier);
              slow = false;
            }
          }

          if (tick < model.getEndTick()) {
            if (tick == 0) {
              model.initial();
            }

            tick++;
          } else if (tick >= model.getEndTick()) {
            tick = 0;
            start = loop;
          }

        } else {
          if (tick < model.getEndTick()) {
            if (stopTime == 20) {
              stopTime = 0;
              tick = this.getNextTime(model.stopTime(), tick);
            }
            stopTime++;
          } else {
            if (stopTime == 20) {
              start = loop;
              tick = 0;
            } else {
              stopTime++;
            }
          }
        }
      }
    }

    if (this.model.getShapes() == null) {
      return;
    } else {
      for (Map.Entry<String, IShape> entry : this.model.getShapes().entrySet()) {
        if (fillOrNot) {
          this.drawFillShape(g2, entry.getValue());
        } else {
          this.drawOutShape(g2, entry.getValue());
        }
      }
    }

  }

  /**
   * Draw the given shape to the panel.
   *
   * @param g     The graphics g used to draw.
   * @param shape The shape will be draw in the panel.
   */
  private void drawFillShape(Graphics g, IShape shape) {
    Color color = shape.getColor();
    if (shape.getPosition() != null) {
      int x = shape.getPosition().getX();
      int y = shape.getPosition().getY();
      int width = shape.getWidth();
      int height = shape.getHeight();

      switch (shape.getShapeType()) {
        case "Ellipse":
          g.setColor(color);
          g.fillOval(x, y, width, height);
          g.drawOval(x, y, width, height);
          break;
        case "Rectangle":
          g.setColor(color);
          g.fillRect(x, y, width, height);
          g.drawRect(x, y, width, height);
          break;
        case "Plus":
          g.setColor(color);
          int width1 = width;
          int height2 = height;
          int height1 = height / 2;
          int width2 = width / 2;
          int x1 = x;
          int y1 = y + height / 4;
          int x2 = x + width / 4;
          int y2 = y;
          g.fillRect(x1, y1, width1, height1);
          g.fillRect(x2, y2, width2, height2);
          g.drawRect(x1, y1, width1, height1);
          g.drawRect(x2, y2, width2, height2);
          break;
        default:
          return;
      }
    }
  }

  /**
   * Draw the given shape outline to the panel.
   *
   * @param g     The graphics g used to draw.
   * @param shape The shape will be draw in the panel.
   */
  private void drawOutShape(Graphics g, IShape shape) {
    Color color = shape.getColor();
    if (shape.getPosition() != null) {
      int x = shape.getPosition().getX();
      int y = shape.getPosition().getY();
      int width = shape.getWidth();
      int height = shape.getHeight();

      switch (shape.getShapeType()) {
        case "Ellipse":
          g.setColor(color);
          g.drawOval(x, y, width, height);
          break;
        case "Rectangle":
          g.setColor(color);
          g.drawRect(x, y, width, height);
          break;
        case "Plus":
          g.setColor(color);
          g.drawLine(x + (width / 3), y, x + ((2 * width) / 3), y);
          g.drawLine(x + (width / 3), y, x + (width / 3), y + (height / 3));
          g.drawLine(x + (width / 3), y + (height / 3), x, y + (height / 3));
          g.drawLine(x, y + (height / 3), x, y + ((2 * height) / 3));
          g.drawLine(x, y + ((2 * height) / 3), x + (width / 3), y
              + ((2 * height) / 3));
          g.drawLine(x + (width / 3), y + ((2 * height) / 3), x
              + (width / 3), y + height);
          g.drawLine(x + (width / 3), y + height, x + ((2 * width) / 3), y + height);
          g.drawLine(x + ((2 * width) / 3), y + height, x + ((2 * width) / 3), y
              + ((2 * height) / 3));
          g.drawLine(x + ((2 * width) / 3), y + ((2 * height) / 3), x + width, y
              + ((2 * height) / 3));
          g.drawLine(x + width, y + ((2 * height) / 3), x + width, y + (height) / 3);
          g.drawLine(x + width, y + (height) / 3, x + ((2 * width) / 3),
              y + (height) / 3);
          g.drawLine(x + ((2 * width) / 3), y + (height) / 3, x + ((2 * width) / 3), y);
          break;
        default:
          return;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
  }

  @Override
  public Dimension getPreferredScrollableViewportSize() {
    return new Dimension(this.model.getWidthBounds(), this.model.getHeightBounds());
  }

  @Override
  public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    if (orientation == SwingConstants.HORIZONTAL) {
      return this.model.getWidthBounds() / 10;
    } else {
      return this.model.getHeightBounds() / 10;
    }
  }

  @Override
  public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    if (orientation == SwingConstants.HORIZONTAL) {
      return this.model.getWidthBounds() / 10;
    } else {
      return this.model.getHeightBounds() / 10;
    }
  }

  @Override
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  @Override
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }
}