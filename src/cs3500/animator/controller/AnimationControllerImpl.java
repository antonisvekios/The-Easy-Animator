package cs3500.animator.controller;

import cs3500.animator.model.AnimationModelImpl.AnimationModelBuilderImpl;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.ITextView;
import cs3500.animator.view.InteractiveViewFrame;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.ViewFactory.ViewType;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;


/**
 * Represent an abstract class consisting of commonly used methods across different controller
 * implementations of this animator.
 */
public abstract class AnimationControllerImpl implements IAnimationController {

  private AnimationBuilder<IAnimatorModel> builder;
  protected IAnimationView view;

  /**
   * Construct an AnimationControllerIml with initial builder.
   */
  public AnimationControllerImpl() {
    this.builder = new AnimationModelBuilderImpl();
  }

  @Override
  public void readCommand(List<String> commandList) throws IOException {
    boolean in = false;
    boolean out = false;
    boolean speed = false;
    boolean view = false;
    boolean error = false;
    boolean slow = false;

    String inCommand = "";
    String outCommand = "";
    int speedCommand = 1;
    String viewCommand = "";
    String slowCommand = "";
    for (int index = 0; index < commandList.size() - 1; index = index + 2) {
      String commandTitle = commandList.get(index);
      String specificCommand = commandList.get(index + 1);
      if (this.rightCommand(commandTitle)) {
        switch (commandTitle) {
          case "-in":
            if (in) {
              error = true;
            } else {
              in = true;
            }
            inCommand = specificCommand;
            break;
          case "-out":
            if (out) {
              error = true;
            } else {
              out = true;
            }
            outCommand = specificCommand;
            break;
          case "-speed":
            if (speed) {
              error = true;
            } else {
              speed = true;
            }
            speedCommand = Integer.parseInt(specificCommand);
            break;
          case "-view":
            if (view) {
              error = true;
            } else {
              view = true;
            }
            viewCommand = specificCommand;
            break;
          case "-slow":
            if (slow) {
              error = true;
            } else {
              slow = true;
            }
            slowCommand = specificCommand;
            break;
          default:
            return;
        }
      } else {
        error = true;
      }
    }
    if (viewCommand.equals("visual")) {
      error = out;
    }
    if (!view || !in) {
      error = true;
    }
    if (error) {
      JOptionPane.showMessageDialog(new Frame(),
          "This command is invalid.",
          "Command error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    } else {
      if (!viewCommand.equals("provider")) {
        if (!out) {
          this.view = new ViewFactory().create(ViewType.valueOf(viewCommand), System.out);
        } else {
          this.view = new ViewFactory().create(ViewType.valueOf(viewCommand),
              new FileWriter(outCommand));
        }
        assert this.view != null;
        this.view.setTicksMultiplier(speedCommand);
        forInCommand(inCommand);
        List<int[]> slowMotion = this.slowTimeList(slowCommand);

        generateOutPut(out, outCommand, slowMotion);
      } else {
        providerIn(inCommand, speedCommand);
      }
    }
  }

  /**
   * Read from a file to get the time period for slow motion.
   */
  private List<int[]> slowTimeList(String slowCommand) {
    File file = new File(slowCommand);
    Scanner s = new Scanner("");
    List<int[]> result = new ArrayList<int[]>();
    try {
      FileReader reader = new FileReader(file);
      s = new Scanner(reader);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

    while (s.hasNextLine()) {
      String command = s.nextLine();
      Scanner commandScanner = new Scanner(command);
      int[] commandInArray = new int[2];
      int startTime = commandScanner.nextInt();
      int endTime = commandScanner.nextInt();

      if (result.size() > 0 && startTime <= result.get(result.size() - 1)[1]) {
        throw new IllegalArgumentException("The slow motion interval is overlap or not in order");
      }

      commandInArray[0] = startTime;
      commandInArray[1] = endTime;
      result.add(commandInArray);
    }

    return result;
  }

  /**
   * Generate the output for the provider view.
   *
   * @param inCommand The inCommand from the user.
   * @param speedCommand The speed of the view.
   */
  protected void providerIn(String inCommand, int speedCommand) throws FileNotFoundException {
    //Do nothing.
  }


  /**
   * Interprets the input command by associating a file to a view and a model.
   *
   * @param inCommand the input command (file source) to read from.
   */
  protected void forInCommand(String inCommand) {
    File file = new File(inCommand);
    try {
      FileReader reader = new FileReader(file);
      this.view.addModel(AnimationReader.parseFile(reader, this.builder));
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Generate the output according to the command requirement.
   *
   * @param out        The boolean that if there is given output file.
   * @param outCommand The file you need to output.
   */
  private void generateOutPut(boolean out, String outCommand, List<int[]> slowMotion)
      throws IOException {
    this.view.run();
    if (this.view instanceof ITextView) {
      ((ITextView) this.view).flush();
      System.exit(0);
    } else if (this.view instanceof InteractiveViewFrame) {
      this.view.addSlowMotion(slowMotion);
      ((Frame) this.view).setVisible(true);
    }
  }

  /**
   * Decide if the command is invalid.
   *
   * @param command The command to validate.
   * @return true if the command is valid, false otherwise.
   */
  private boolean rightCommand(String command) {
    return (command.equals("-in")
        || command.equals("-view")
        || command.equals("-out")
        || command.equals("-speed"))
        || command.equals("-slow");
  }
}
