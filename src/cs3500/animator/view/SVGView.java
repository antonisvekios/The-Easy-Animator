package cs3500.animator.view;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.IDescription;
import cs3500.animator.model.IShape;
import java.io.Flushable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represent a SVGView implement the ITextView. It will present the animation in svg format.
 */
public class SVGView implements ITextView {

  private Appendable out;
  public IAnimatorModel model;
  private int ticksMultiplier;
  private int x;
  private int y;

  /**
   * Construct a SVGView with initial parameter.
   */
  public SVGView(Appendable out) {
    this.out = out;
    this.model = new AnimationModelImpl();
    this.ticksMultiplier = 1;
    this.x = 0;
    this.y = 0;
  }

  /**
   * Construct SVGView with given model and speed.
   *
   * @param model          The model of this view.
   * @param ticksPerSecond The speed of this view.
   */
  public SVGView(IAnimatorModel model, int ticksPerSecond, Appendable out) {
    if (ticksPerSecond < 1) {
      throw new IllegalArgumentException("Cannot pass a negative ticks per second");
    }
    this.model = model;
    this.out = out;
    this.ticksMultiplier = 1000 / ticksPerSecond;
    this.x = model.getXBounds();
    this.y = model.getYBounds();
  }

  @Override
  public void addSlowMotion(List<int[]> intervals) {
    //It do nothing in this view.
  }

  @Override
  public void addModel(IAnimatorModel model) {
    this.model = model;
    this.x = model.getXBounds();
    this.y = model.getYBounds();
  }

  @Override
  public void setTicksMultiplier(int ticksPerSecond) {
    if (ticksPerSecond < 1) {
      throw new IllegalArgumentException("Cannot pass a negative ticks per second");
    }
    this.ticksMultiplier = 1000 / ticksPerSecond;
  }

  @Override
  public void run() throws IOException {
    String shapeType;
    StringBuilder s = new StringBuilder();
    appendInput("<svg width='" + model.getWidthBounds() + "' height='"
        + model.getHeightBounds() + "' version='1.1' "
        + "xmlns='http://www.w3.org/2000/svg'>\n", s);

    for (Map.Entry<String, IShape> entry : model.getShapes().entrySet()) {
      List<IDescription> commands = model.getProcess().get(entry.getKey());
      switch (entry.getValue().getShapeType()) {
        case "Rectangle":
          shapeType = "rect";
          break;
        case "Ellipse":
          shapeType = "ellipse";
          break;
        case "Plus":
          shapeType = "polygon";
          break;
        default:
          shapeType = "";
      }
      this.appendInput("<" + shapeType + " id='"
          + entry.getKey(), s);
      this.appendInput(this.getShapeString(commands.get(0), shapeType), s);
      this.appendInput(this.getCommandsString(commands, shapeType), s);
      this.appendInput("</" + shapeType + ">\n", s);
    }
    appendInput("</svg>", s);
    this.out.append(s);
  }

  /**
   * Append the input to out put.
   *
   * @param input The input will be append.
   */
  private void appendInput(String input, Appendable app) {
    try {
      app.append(input);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot transmit output to appendable");
    }
  }

  /**
   * Trans the description to string in svg format.
   *
   * @param list The list of description.
   * @param type The type of shape.
   * @return The string describe the animation.
   */
  private String getCommandsString(List<IDescription> list, String type) {
    StringBuilder str = new StringBuilder();
    String end = "' fill='freeze'/>\n";
    String start;

    for (IDescription command : list) {
      start = "<animate attributeType='xml' attributeName='";
      if (command.getStartX() != command.getEndX()) {
        if (type.equals("rect")) {
          str.append(start).append("x' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartX() - this.x)
              .append("' to='").append(command.getEndX() - this.x).append(end);
        } else if (type.equals("ellipse")) {
          str.append(start).append("cx' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartX() - this.x + command.getStartWidth() / 2)
              .append("' to='")
              .append(command.getEndX() - this.x + command.getEndWidth() / 2)
              .append(end);
        } else {
          str.append(start).append("points' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(this.getPlusSignPoints(command))
              .append("' to='").append(this.movePlusSignPoints(command)).append(end);
        }
      }
      if (command.getStartY() != command.getEndY()) {
        if (type.equals("rect")) {
          str.append(start).append("y' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartY() - this.y)
              .append("' to='")
              .append(command.getEndY() - this.y)
              .append(end);
        } else if (type.equals("ellipse")) {
          str.append(start).append("cy' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartY() - this.y + command.getStartHeight() / 2)
              .append("' to='")
              .append(command.getEndY() - this.y + command.getEndHeight() / 2)
              .append(end);
        } else {
          str.append(start).append("points' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(this.getPlusSignPoints(command))
              .append("' to='").append(this.movePlusSignPoints(command)).append(end);
        }
      }
      if (command.getStartHeight() != command.getEndHeight()) {
        if (type.equals("rect")) {
          str.append(start).append("height' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartHeight())
              .append("' to='").append(command.getEndHeight()).append(end);
        } else if (type.equals("ellipse")) {
          str.append(start).append("ry' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartHeight() / 2)
              .append("' to='").append(command.getEndHeight() / 2).append(end);
        } else {
          str.append(start).append("points' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(this.getPlusSignPoints(command))
              .append("' to='").append(this.movePlusSignPoints(command)).append(end);
        }
      }
      if (command.getStartWidth() != command.getEndWidth()) {
        if (type.equals("rect")) {
          str.append(start).append("width' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartWidth())
              .append("' to='").append(command.getEndWidth()).append(end);
        } else if (type.equals("ellipse")) {
          str.append(start).append("ry' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(command.getStartWidth() / 2)
              .append("' to='").append(command.getEndWidth() / 2).append(end);
        } else {
          str.append(start).append("points' begin='")
              .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
              .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
              .append("ms' ")
              .append("from='")
              .append(this.getPlusSignPoints(command))
              .append("' to='").append(this.movePlusSignPoints(command)).append(end);
        }
      }
      if (command.getStartColor().getRed() != command.getEndColor().getRed()
          || command.getStartColor().getGreen() != command.getEndColor().getGreen()
          || command.getStartColor().getBlue() != command.getEndColor().getBlue()) {
        str.append(start).append("fill' begin='")
            .append(command.getStartTime() * ticksMultiplier).append("ms' dur='")
            .append((command.getEndTime() - command.getStartTime()) * ticksMultiplier)
            .append("ms' ")
            .append("from='rgb(")
            .append(command.getStartColor().getRed()).append(",")
            .append(command.getStartColor().getGreen()).append(",")
            .append(command.getStartColor().getBlue()).append(")' to='rgb(")
            .append(command.getEndColor().getRed()).append(",")
            .append(command.getEndColor().getGreen()).append(",")
            .append(command.getEndColor().getBlue()).append(")").append(end);
      }
    }
    return str.toString();
  }

  /**
   * Get a string describe the shape.
   *
   * @param command The description describe the motion of shapes.
   * @param type    The type of shape.
   * @return
   */
  private String getShapeString(IDescription command, String type) {
    StringBuilder str = new StringBuilder();
    switch (type) {
      case "rect":
        str = new StringBuilder("' x='").append(command.getStartX() - this.x)
            .append("' y='").append(command.getStartY() - this.y).append("' width='")
            .append(command.getStartWidth()).append("' height='")
            .append(command.getStartHeight())
            .append("' fill='rgb(").append(command.getStartColor().getRed()).append(",")
            .append(command.getStartColor().getGreen()).append(",")
            .append(command.getStartColor().getBlue())
            .append(")' visibility='hidden'>\n")
            .append(String.format("<set attributeType='xml' attributeName='visibility' "
                    + "begin='%fs' to='visible'/>\n",
                (double) this.model.getCurrentTick() * ticksMultiplier));
        break;
      case "ellipse":
        str = new StringBuilder("' cx='")
            .append(command.getStartX() - this.x + command.getStartWidth() / 2)
            .append("' cy='")
            .append(command.getStartY() - this.y + command.getStartHeight() / 2)
            .append("' rx='")
            .append(command.getStartWidth() / 2).append("' ry='")
            .append(command.getStartHeight() / 2)
            .append("' fill='rgb(").append(command.getStartColor().getRed()).append(",")
            .append(command.getStartColor().getGreen()).append(",")
            .append(command.getStartColor().getBlue())
            .append(")' visibility='hidden'>\n")
            .append(String.format("<set attributeType='xml' attributeName='visibility' "
                    + "begin='%fs' to='visible'/>\n",
                (double) this.model.getCurrentTick() * ticksMultiplier));
        break;
      case "polygon":
        str = new StringBuilder("' points='")
            .append(this.getPlusSignPoints(command))
            .append("' fill='rgb(").append(command.getStartColor().getRed()).append(",")
            .append(command.getStartColor().getGreen()).append(",")
            .append(command.getStartColor().getBlue())
            .append(")' visibility='hidden'>\n")
            .append(String.format("<set attributeType='xml' attributeName='visibility' "
                    + "begin='%fs' to='visible'/>\n",
                (double) this.model.getCurrentTick() * ticksMultiplier));
        break;
      default:
        return str.toString();
    }
    return str.toString();
  }

  /**
   * Get the plus sign in svg.
   * @param command The description include the point of plus sign.
   * @return The string in XML describe the point of plus sign.
   */
  private String getPlusSignPoints(IDescription command) {
    int side = command.getStartWidth();
    StringBuilder str = new StringBuilder();
    str.append(side / 4 + command.getStartX() - this.x).append(",")
        .append(command.getStartY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getStartX() - this.x).append(",")
        .append(command.getStartY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getStartX() - this.x).append(",")
        .append(side / 4 + command.getStartY() - this.y).append(" ")
        .append(side + command.getStartX() - this.x).append(",")
        .append(side / 4 + command.getStartY() - this.y).append(" ")
        .append(side + command.getStartX() - this.x).append(",")
        .append(side * 3 / 4 + command.getStartY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getStartX() - this.x).append(",")
        .append(side * 3 / 4 + command.getStartY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getStartX() - this.x).append(",")
        .append(side + command.getStartY() - this.y).append(" ")
        .append(side / 4 + command.getStartX() - this.x).append(",")
        .append(side + command.getStartY() - this.y).append(" ")
        .append(side / 4 + command.getStartX() - this.x).append(",")
        .append(side * 3 / 4 + command.getStartY() - this.y).append(" ")
        .append(command.getStartX() - this.x).append(",")
        .append(side * 3 / 4 + command.getStartY() - this.y).append(" ")
        .append(command.getStartX() - this.x).append(",")
        .append(side / 4 + command.getStartY() - this.y).append(" ")
        .append(side / 4 + command.getStartX() - this.x).append(",")
        .append(side / 4 + command.getStartY() - this.y);
    return str.toString();
  }

  /**
   * Move the plus sign in svg.
   * @param command The description include the move of plus sign.
   * @return The string in XML describe the move of plus sign.
   */
  private String movePlusSignPoints(IDescription command) {
    int side = command.getEndWidth();
    StringBuilder str = new StringBuilder();
    str.append(side / 4 + command.getEndX() - this.x).append(",")
        .append(command.getEndY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getEndX() - this.x).append(",")
        .append(command.getEndY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getEndX() - this.x).append(",")
        .append(side / 4 + command.getEndY() - this.y).append(" ")
        .append(side + command.getEndX() - this.x).append(",")
        .append(side / 4 + command.getEndY() - this.y).append(" ")
        .append(side + command.getEndX() - this.x).append(",")
        .append(side * 3 / 4 + command.getEndY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getEndX() - this.x).append(",")
        .append(side * 3 / 4 + command.getEndY() - this.y).append(" ")
        .append(side * 3 / 4 + command.getEndX() - this.x).append(",")
        .append(side + command.getEndY() - this.y).append(" ")
        .append(side / 4 + command.getEndX() - this.x).append(",")
        .append(side + command.getEndY() - this.y).append(" ")
        .append(side / 4 + command.getEndX() - this.x).append(",")
        .append(side * 3 / 4 + command.getEndY() - this.y).append(" ")
        .append(command.getEndX() - this.x).append(",")
        .append(side * 3 / 4 + command.getEndY() - this.y).append(" ")
        .append(command.getEndX() - this.x).append(",")
        .append(side / 4 + command.getEndY() - this.y).append(" ")
        .append(side / 4 + command.getEndX() - this.x).append(",")
        .append(side / 4 + command.getEndY() - this.y);
    return str.toString();
  }

  @Override
  public String getText() {
    return this.out.toString();
  }

  @Override
  public void flush() {
    try {
      ((Flushable) this.out).flush();
    } catch (IOException | ClassCastException e) {
      System.out.println(e.getMessage());
    }
  }
}
