package model.image;

/**
 * This interface represents a 24-bit pixel made up of 8 bits of red, green, and blue.
 * All values are represented as integers from 0, 255. This interface supports
 * functionality such as getting the individual bit values and getting the
 * Value, Intensity, and Luma of this pixel.
 */
public interface IPixel {

  /**
   * Gets the red component of the pixel.
   *
   * @return the integer value from 0, 255 of the red component
   */
  int getRed();

  /**
   * Gets the green component of the pixel.
   *
   * @return the integer value from 0, 255 of the green component
   */
  int getGreen();

  /**
   * Gets the blue component of the pixel.
   *
   * @return the integer value from 0, 255 of the blue component
   */
  int getBlue();

  /**
   * Gets the maximum value of the 3 components of each pixel.
   *
   * @return
   */
  int getValue();

  /**
   * Gets the average of the 3 components of each pixel.
   *
   * @return
   */
  int getIntensity();

  /**
   * Gets the weighted sum of the 3 components of each pixel.
   *
   * @return
   */
  int getLuma();

}
