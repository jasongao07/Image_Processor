package controller.commandv2;

import java.util.Scanner;

import model.ImageProcessorModelV2;
import view.ImageProcessorViewV2;

/**
 * This interface represents the new commands that are introduced in the v2 update.
 */
public interface ICommandV2 {

  void runCommand(Scanner sc, ImageProcessorViewV2 view, ImageProcessorModelV2 model);
}
