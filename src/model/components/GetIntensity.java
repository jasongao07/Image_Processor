package model.components;

import model.image.IPixel;

/**
 * This class implements the component getter and serves as a function object, where it ouputs
 * the value of the intensity of the rgb values.
 */
public class GetIntensity implements ComponentGetter {

  /**
   * Gets the component value of the intensity of the rgb values.
   *
   * @param pixel the pixel to get the component of
   * @return intensity of the rgb values
   */
  public int componentValue(IPixel pixel) {
    return pixel.getIntensity();
  }
}
