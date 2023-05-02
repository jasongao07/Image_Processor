package controller.commandv1;

import java.util.Scanner;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This class represents the save command, and runs the designated methods in the
 * model.
 */
public class SaveCommand implements ICommand {

  @Override
  public void runCommand(Scanner sc, ImageProcessorView view, ImageProcessorModel model) {
    String ppm;
    String name;
    ppm = sc.next();
    name = sc.next();
    writeFile(view, ppm, name);
  }

  /**
   * Creates a ppm file, if the given ppm string is not a valid ppm file, then it displays
   * an invalid message.
   *
   * @param view the given view
   * @param ppm  the ppm file
   * @param name the name of the new name
   */
  protected void writeFile(ImageProcessorView view, String ppm, String name) {
    try {
      view.toPPM(name, ppm);
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.renderMessage("Invalid arguments." + "\n");
      return;
    }
    view.renderMessage("Successfully saved " + name + " to " + ppm + "\n");
  }
}


