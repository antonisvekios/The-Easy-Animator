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
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Represent a panel draw the animation.
 */
public class VisualAnimationPanel extends JPanel implements ActionListener, Scrollable {
  public IAnimatorModel model;
  private int tick;
  private int ticksMultiplier;


  /**
   * Construct a VisualAnimationPanel.
   */
  public VisualAnimationPanel() {
    tick = 1;
  }

  /**
   * Set the time speed.
   *
   * @param newTicksMultiplier The time tick represent the speed (how many tick per second).
   */
  public void setSpeed(int newTicksMultiplier) {
    this.ticksMultiplier = newTicksMultiplier;
  }


  /**
   * Initialize the whole panel and make the timer run.
   */
  public void initial() {
    Timer timer;
    setPreferredSize(new Dimension(this.model.maxX() + 50, this.model.maxY() + 50));
    setLocation(this.model.getXBounds(), this.model.getYBounds());
    timer = new Timer(1000 / this.ticksMultiplier, this);
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

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g);
    model.setCurrentTick(tick);
    model.run();

    if (tick < model.getEndTick()) {
      tick++;
    }

    if (this.model.getShapes() == null) {
      return;
    } else {
      for (Map.Entry<String, IShape> entry : this.model.getShapes().entrySet()) {
        this.drawShape(g2, entry.getValue());
      }
    }

  }

  /**
   * Draw the given shape to the panel.
   *
   * @param g  The graphics g used to draw.
   * @param shape The shape will be draw in the panel.
   */
  private void drawShape(Graphics g, IShape shape) {
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
          int width1 = width ;
          int height2 = height;
          int height1 = height / 2;
          int width2 = width / 2;
          int x1 = x;
          int y1 = y + height / 4;
          int x2 = x + width / 4;
          int y2 = y;
          g.fillRect(x1, y1, width1, height1);
          g.fillRect(x2, y2, width2, height2);
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
