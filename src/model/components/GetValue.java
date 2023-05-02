package model.components;

import model.image.IPixel;

/**
 * This class implements the component getter and serves as a function object, where it ouputs
 * the value of the Value of the rgb values.
 */
public class GetValue implements ComponentGetter {

  /**
   * Gets the value component of the rgb values.
   *
   * @param pixel the pixel to get the component of
   * @return the value component of the rgb values
   */
  public int componentValue(IPixel pixel) {
    return pixel.getValue();
  }
}
