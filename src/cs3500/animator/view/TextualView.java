package cs3500.animator.view;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.IDescription;
import cs3500.animator.model.IReadableShape;
import java.io.Flushable;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Represent a TextualView implement the ITextView. It will present the animation in
 * text format.
 */
public class TextualView implements ITextView {

  private Appendable out;
  public IAnimatorModel model;
  private int ticksMultiplier;
  private static final DecimalFormat df = new DecimalFormat("0.00");

  /**
   * Construct a TextualView with default field.
   */
  public TextualView(Appendable out) {
    this.out = out;
    model = new AnimationModelImpl();
    this.ticksMultiplier = 1;
  }

  /**
   * Construct a TextualView with given model and speed.
   *
   * @param model The model will be added to the view.
   * @param ticksPerSecond The speed of animation.
   */
  public TextualView(IAnimatorModel model, int ticksPerSecond, Appendable out) {
    this.model = model;
    this.out = out;
    this.ticksMultiplier = 1000 / ticksPerSecond;
  }

  @Override
  public void addSlowMotion(List<int[]> intervals) {
    //It do nothing in this view.
  }

  @Override
  public void addModel(IAnimatorModel model) {
    this.model = model;
  }

  @Override
  public void setTicksMultiplier(int ticksPerSecond) {
    if (ticksPerSecond < 1) {
      throw new IllegalArgumentException("Cannot pass a negative ticks per second");
    }
    this.ticksMultiplier = 1000 / ticksPerSecond;
  }

  @Override
  public String getText() {
    return this.out.toString();
  }

  @Override
  public void run() throws IOException {
    StringBuilder output = new StringBuilder();
    for (Map.Entry<String, List<IDescription>> entry : this.model.getProcess().entrySet()) {
      IReadableShape shape = this.model.getShapes().get(entry.getKey());
      output.append("shape ").append(entry.getKey()).append(" ")
          .append(shape.getShapeType().toLowerCase())
          .append("\n");
      for (IDescription command : entry.getValue()) {
        String temp = command.getType() + "\t"
            + entry.getKey() + "\t"
            + df.format(command.getStartTime() * ticksMultiplier * 0.001) + "\t"
            + command.getStartX() + "\t"
            + command.getStartY() + "\t"
            + command.getStartWidth() + "\t"
            + command.getStartHeight() + "\t"
            + command.getStartColor().getRed() + "\t"
            + command.getStartColor().getGreen() + "\t"
            + command.getStartColor().getBlue() + "\t\t"
            + df.format(command.getEndTime() * ticksMultiplier * 0.001) + "\t"
            + command.getEndX() + "\t"
            + command.getEndY() + "\t"
            + command.getEndWidth() + "\t"
            + command.getEndHeight() + "\t"
            + command.getEndColor().getRed() + "\t"
            + command.getEndColor().getGreen() + "\t"
            + command.getEndColor().getBlue() + "\n";
        output.append(temp);
      }
    }

    this.out.append(output);
  }

  @Override
  public void flush() {
    try {
      ((Flushable) this.out).flush();
    } catch (IOException | ClassCastException e1) {
      System.out.println(e1.getMessage());
    }
  }
}
