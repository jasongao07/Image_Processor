package controller.commandv1;

import java.util.Scanner;

import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This interface represents the commands that the user can input, and has one method: running
 * that command.
 */
public interface ICommand {

  /**
   * This method runs the command.
   * @param sc the given scanner
   * @param view the given view
   * @param model the given model
   */
  void runCommand(Scanner sc, ImageProcessorView view, ImageProcessorModel model);
}
