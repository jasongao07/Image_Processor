package histogram;

import java.awt.geom.Line2D;
import java.util.List;
import model.image.IImage;

/**
 * This interface represents a histogram drawer.
 */
public interface IHistogramDrawer {

  /**
   * Gets the red lines of an image based on the histogram of (value, frequency).
   * @param image the given image
   * @return the red lines of an image
   */
  List<Line2D> getRedLines(IImage image);

  /**
   * Gets the green lines of an image based on the histogram of (value, frequency).
   * @param image the given image
   * @return the green lines of an image
   */
  List<Line2D> getGreenLines(IImage image);

  /**
   * Gets the blue lines of an image based on the histogram of (value, frequency).
   * @param image the given image
   * @return the blue lines of an image
   */
  List<Line2D> getBlueLines(IImage image);

  /**
   * Gets the intensity lines of an image based on the histogram of (value, frequency).
   * @param image the given image
   * @return the intensity lines of an image
   */
  List<Line2D> getIntensityLines(IImage image);

}
