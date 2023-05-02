package view;


/**
 * This interface represents the view for an image processor. This view can handle writing to
 * most common image file formats, such as jpg, jpeg, png, and bmp.
 */
public interface ImageProcessorViewV2 extends ImageProcessorView {
  /**
   * Gets an image from the model and converts it into an image file and writes it to the given
   * file destination.
   *
   * @param targetName  the target image that will be converted into the PPM file
   * @param destination the file destination of the created PPM file
   * @throws IllegalArgumentException if the target name does not exist in the model.
   */
  void toImage(String targetName, String destination) throws IllegalArgumentException;
}
