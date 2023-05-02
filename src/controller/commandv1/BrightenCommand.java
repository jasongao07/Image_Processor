package controller.commandv1;

import java.util.Scanner;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This class represents the brighten command and runs the designated methods in the model class
 * given the specific inputs.
 */
public class BrightenCommand implements ICommand {
  @Override
  public void runCommand(Scanner sc, ImageProcessorView view, ImageProcessorModel model) {
    String target;
    String name;
    int delta;
    try {
      if (sc.hasNextInt()) {
        delta = sc.nextInt();
      } else {
        view.renderMessage("Invalid arguments." + "\n");
        return;
      }
      target = sc.next();
      name = sc.next();
      model.changeBrightness(delta, target, name);
    } catch (IllegalArgumentException e) {
      view.renderMessage("Invalid arguments." + "\n");
      return;
    }
    view.renderMessage("Successfully created " + name + "\n");
  }
}
