package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Represent a frame show the animation which allows user to some interaction.
 */
public class InteractiveViewFrame extends JFrame implements IAnimationView, ActionListener {
  private IAnimatorModel model;
  int tickMultiplier;
  private InteractiveViewPanel panel;
  private JLabel label;
  private JTextArea textArea;

  /**
   * Construct a frame show the animation which allows user to some interaction..
   */
  public InteractiveViewFrame() {
    super("Animation");
    label = new JLabel("Status");
    textArea = new JTextArea(1, 8);
  }

  /**
   * Construct a interactive frame with given panel.
   */
  public InteractiveViewFrame(InteractiveViewPanel p) {
    super("Animation");
    label = new JLabel("Status");
    textArea = new JTextArea(1, 8);
    panel = p;
  }

  @Override
  public void addSlowMotion(List<int[]> intervals) {
    panel.addSlowMotion(intervals);
  }

  @Override
  public void run() {

    setSize(new Dimension(this.model.getXBounds(), this.model.getYBounds()));
    setLocation(0,0);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new FlowLayout());

    this.panel = new InteractiveViewPanel();
    panel.addModel(this.model);
    panel.setSpeed(10);
    panel.initial();
    this.panel.setLayout(null);

    this.refresh();
    this.add(BorderLayout.BEFORE_FIRST_LINE, label);

    JButton startButton = new JButton("Start");
    startButton.setActionCommand("start");
    startButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, startButton);

    JButton pauseButton = new JButton("pause");
    pauseButton.setActionCommand("pause");
    pauseButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, pauseButton);

    JButton resumeButton = new JButton("resume");
    resumeButton.setActionCommand("resume");
    resumeButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, resumeButton);

    JButton restartButton = new JButton("restart");
    restartButton.setActionCommand("restart");
    restartButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, restartButton);

    JButton loopButton = new JButton("loop");
    loopButton.setActionCommand("loop");
    loopButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, loopButton);

    textArea.setBorder(BorderFactory.createTitledBorder("Set Speed"));
    this.add(BorderLayout.BEFORE_FIRST_LINE, textArea);

    JButton setSpeedButton = new JButton("Update Speed");
    setSpeedButton.setActionCommand("updateSpeed");
    setSpeedButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, setSpeedButton);

    JButton fillButton = new JButton("fillOrNot");
    fillButton.setActionCommand("fillOrNot");
    fillButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, fillButton);

    JButton discreteButton = new JButton("discrete");
    discreteButton.setActionCommand("discrete");
    discreteButton.addActionListener(this);
    this.add(BorderLayout.BEFORE_FIRST_LINE, discreteButton);

    add(BorderLayout.CENTER, new JScrollPane(panel));

    pack();
  }

  /**
   * Refresh the label to show the current status of panel.
   */
  private void refresh() {
    List status = this.panel.getStatus();
    this.label.setText("Start: " + status.get(3) + ",\n " + "Loop: " + status.get(4)
        + ",\n " + "Speed: " + status.get(1) + ",\n " + "Stop: " + status.get(2) + ",\n "
        + "Fill: " + status.get(5) + ",\n" + "Discrete: " + status.get(6));
  }

  @Override
  public void addModel(IAnimatorModel newModel) {
    this.model = newModel;
  }

  @Override
  public void setTicksMultiplier(int newTickMultiplier) {
    this.tickMultiplier = newTickMultiplier;
  }

  @Override
  public void actionPerformed(ActionEvent command) {
    switch (command.getActionCommand()) {
      case "start":
        panel.start();
        this.refresh();
        break;
      case "pause":
        panel.setStop(true);
        this.refresh();
        break;
      case "resume":
        panel.setStop(false);
        this.refresh();
        break;
      case "restart":
        panel.restart();
        this.refresh();
        break;
      case "loop":
        panel.loop();
        this.refresh();
        break;
      case "updateSpeed":
        Scanner s = new Scanner(this.textArea.getText());
        try {
          panel.updateSpeed(s.nextInt());
        } catch (InputMismatchException e) {
          System.out.println(e);
        }
        this.refresh();
        break;
      case "fillOrNot":
        panel.changeFillOrNot();
        this.refresh();
        break;
      case "discrete":
        panel.changeDiscrete();
        this.refresh();
        break;
      default:
        break;
    }
  }
}
