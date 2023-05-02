package controller.commandv2;

import java.util.Scanner;

import model.ImageProcessorModelV2;
import utils.CommonImageUtil;
import utils.PPMUtil;
import view.ImageProcessorViewV2;

/**
 * This class represents the updated load command which incorporates the new image formats,
 * along with the old ppm format.
 */
public class LoadCommandV2 implements ICommandV2 {

  @Override
  public void runCommand(Scanner sc, ImageProcessorViewV2 view, ImageProcessorModelV2 model) {
    String filepath;
    String name;
    filepath = sc.next();
    name = sc.next();
    readFile(model, view, filepath, name);
  }

  protected void readFile(ImageProcessorModelV2 model, ImageProcessorViewV2 view, String filepath,
                          String name) {
    try {
      model.loadImage(CommonImageUtil.readImage(filepath), name);
    } catch (IllegalArgumentException e) {
      String errorMessage = e.getMessage();
      if (errorMessage.equals("Filepath is not an image")) {
        try {
          model.loadImage(PPMUtil.readPPM(filepath), name);
        } catch (IllegalArgumentException j) {
          view.renderMessage("not a valid image file" + "\n");
          return;
        }
      } else {
        view.renderMessage("Invalid image path." + "\n");
        return;
      }
    }
    view.renderMessage("Image successfully loaded as " + name + "\n");
  }
}
