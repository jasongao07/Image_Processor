package controller.commandv1;

import java.util.Scanner;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This class represents the green-component command, and runs the designated methods in the
 * model for a green greyscale.
 */
public class GreenComponent implements ICommand {
  @Override
  public void runCommand(Scanner sc, ImageProcessorView view, ImageProcessorModel model) {
    String target;
    String name;
    try {
      target = sc.next();
      name = sc.next();
      model.getComponent("green", target, name);
    } catch (IllegalArgumentException e) {
      view.renderMessage("Invalid image name.\n");
      return;
    }
    view.renderMessage("Successfully created " + name + "\n");
  }
}
