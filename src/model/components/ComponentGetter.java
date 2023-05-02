package model.components;

import model.image.IPixel;

/**
 * This interface represents the components that will be used in the ImageProcessorModel. The
 * componentGetter has a single method: componentValue which will be used to get the value of
 * this component.
 */
public interface ComponentGetter {

  /**
   * Gets the value of this component.
   *
   * @param pixel the pixel to get the component of
   * @return the value of the component
   */
  int componentValue(IPixel pixel);
}
