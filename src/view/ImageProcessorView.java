package view;

/**
 * The view for an image processor. This view supports the functionalities such as rendering a
 * message, and converting an image to a ppm file given its image name.
 */
public interface ImageProcessorView {

  /**
   * Gets an image from the model and converts it into a PPM file and writes it to the given file
   * destination.
   *
   * @param targetName  the target image that will be converted into the PPM file
   * @param destination the file destination of the created PPM file
   * @throws IllegalArgumentException if the target name does not exist in the model.
   */
  void toPPM(String targetName, String destination) throws IllegalArgumentException;

  /**
   * Renders a message.
   *
   * @param message the message to be rendered
   */
  void renderMessage(String message);
}
