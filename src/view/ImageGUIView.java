package view;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.List;

import model.image.IImage;

/**
 * This interface represents a gui view of our image processor.
 */
public interface ImageGUIView extends ViewEmitterFunctionality {
  /**
   * Displays the given image to the user.
   *
   * @param image the given image
   */
  void displayImage(IImage image);

  /**
   * Displays the given error message to the user.
   *
   * @param message the given error message
   */
  void displayErrorMessage(String message);

  /**
   * Adds an image to the view with the given name.
   *
   * @param name the given name
   */
  void addImage(String name);

  /**
   * Determines whether or not to show this view.
   *
   * @param show whether to show or not
   */
  void showGUI(boolean show);

  /**
   * Adds the given lines to display on the histogram.
   *
   * @param color the color for the lines to be drawn in
   * @param lines the given list of lines
   */
  void addLines(Color color, List<Line2D> lines);

  void clearLines();

  /**
   * Prompts the user for the filepath of a file to laod.
   *
   * @return the filepath
   */
  String getLoadFilePath();

  /**
   * Prompts the user for the filepath of a file to save.
   *
   * @return the filepath
   */
  String getSaveFilePath();

  /**
   * Prompts the user for an input.
   *
   * @param message the message displayed to the user
   * @return the user input
   */
  String getUserInput(String message);

  String getCurrentImage();


}
