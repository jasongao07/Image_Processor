package controller.commandv1;

import java.util.Scanner;

import model.ImageProcessorModel;
import utils.PPMUtil;
import view.ImageProcessorView;

/**
 * This method represents the load command, and runs the designated methods in the
 * model.
 */
public class LoadCommand implements ICommand {

  @Override
  public void runCommand(Scanner sc, ImageProcessorView view, ImageProcessorModel model) {
    String ppm;
    String name;
    try {
      ppm = sc.next();
      name = sc.next();
      model.loadImage(PPMUtil.readPPM(ppm), name);
    } catch (IllegalArgumentException e) {
      String errorMessage = e.getMessage();
      if (errorMessage.equals("Not a PPM file!")) {
        view.renderMessage("Given file is not a PPM file" + "\n");
      } else {
        view.renderMessage("Invalid image path." + "\n");
      }
      return;
    }
    view.renderMessage("Image successfully loaded as " + name + "\n");
  }
}
