package controller.commandv2;

import java.util.Scanner;

import model.ImageProcessorModelV2;
import view.ImageProcessorViewV2;

/**
 * This class represents the greyscale command that creates a greyscale given the user inputs.
 */
public class GreyscaleCommand implements ICommandV2 {
  @Override
  public void runCommand(Scanner sc, ImageProcessorViewV2 view, ImageProcessorModelV2 model) {
    String target;
    String name;
    try {
      target = sc.next();
      name = sc.next();
      model.applyColorTransformation("greyscale", target, name);
    } catch (IllegalArgumentException e) {
      view.renderMessage("Invalid image name.\n");
      return;
    }
    view.renderMessage("Successfully created " + name + "\n");
  }
}
