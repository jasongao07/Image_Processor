package controller.commandv2;

import java.util.Scanner;

import controller.commandv1.SaveCommand;
import model.ImageProcessorModelV2;
import view.ImageProcessorViewV2;

/**
 * This class represents the updated save command which extends the previous save's methods. This
 * save command incorporates the new image formats, along with the old ppm format.
 */
public class SaveCommandV2 extends SaveCommand implements ICommandV2 {
  @Override
  public void runCommand(Scanner sc, ImageProcessorViewV2 view, ImageProcessorModelV2 model) {
    String destination;
    String name;
    destination = sc.next();
    name = sc.next();
    try {
      view.toImage(name, destination);
    } catch (IllegalArgumentException | IllegalStateException e) {
      writeFile(view, destination, name);
    }
    view.renderMessage("Successfully saved " + name + " to " + destination + "\n");
  }
}
