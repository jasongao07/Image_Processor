package model.image;

import java.util.List;

/**
 * Represents an Image. The image must be nonnull, not contain any nulls, and must not be
 * empty, and the image must be rectangular. It must also have a valid width and height
 * (nonnegative).
 */
public interface IImage {

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Gets the pixel at the given row, column of this image.
   *
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the pixel at the given row, col
   */
  IPixel getPixelAt(int row, int col);

  /**
   * Gets the image.
   *
   * @return the image
   */
  List<List<IPixel>> getImage();
}
