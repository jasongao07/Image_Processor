package model.components;

import model.image.IPixel;

/**
 * This class implements the component getter and serves as a function object, where it ouputs
 * the value of the red pixel.
 */
public class GetRed implements ComponentGetter {

  /**
   * Gets the component value of the red pixel.
   *
   * @param pixel the pixel to get the component of
   * @return the component value of the red pixel
   */
  public int componentValue(IPixel pixel) {
    return pixel.getRed();
  }
}
