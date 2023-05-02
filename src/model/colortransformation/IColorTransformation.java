package model.colortransformation;

import model.image.IPixel;

/**
 * This interface represents a color transformations on each pixel.
 */
public interface IColorTransformation {

  /**
   * Applies the color transformation on this given pixel.
   *
   * @param pixel the given pixel
   * @return the new transformed pixel
   */
  IPixel transform(IPixel pixel);

}
