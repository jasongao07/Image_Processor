package controller.commandv2;

import java.util.Scanner;

import model.ImageProcessorModelV2;
import view.ImageProcessorViewV2;

/**
 * This class represents the Sepia command that creates a Sepia transformation given the user
 * inputs.
 */
public class SepiaCommand implements ICommandV2 {
  @Override
  public void runCommand(Scanner sc, ImageProcessorViewV2 view, ImageProcessorModelV2 model) {
    String target;
    String name;
    try {
      target = sc.next();
      name = sc.next();
      model.applyColorTransformation("sepia", target, name);
    } catch (IllegalArgumentException e) {
      view.renderMessage("Invalid image name.\n");
      return;
    }
    view.renderMessage("Successfully created " + name + "\n");
  }
}
