package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Represents the class for the visual view of the animation. Draws an image onto the canvas and
 * displays the shapes in the animation.
 */
public class VisualAnimationView extends JFrame implements IAnimationView {
  public IAnimatorModel model;
  int tickMultiplier;
  public VisualAnimationPanel panel;

  /**
   * Construct a frame with title Animation.
   */
  public VisualAnimationView() {
    super("Animation");
  }

  @Override
  public void addSlowMotion(List<int[]> intervals) {
    //It do nothing in this view.
  }

  @Override
  public void run() {

    setSize(new Dimension(this.model.getXBounds(), this.model.getYBounds()));
    setLocation(0,0);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());
    this.panel = new VisualAnimationPanel();
    panel.addModel(this.model);
    panel.setSpeed(10);
    panel.initial();
    add(BorderLayout.CENTER, new JScrollPane(panel));
    setLocationRelativeTo(null);


    pack();
    this.setVisible(true);
  }

  @Override
  public void addModel(IAnimatorModel newModel) {
    this.model = newModel;
  }

  @Override
  public void setTicksMultiplier(int newTickMultiplier) {
    this.tickMultiplier = newTickMultiplier;
  }
}
