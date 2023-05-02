package model.filter;

import model.image.IImage;

/**
 * This interface represents an image filter that applies a filter.
 */
public interface IFilter {

  /**
   * Returns an image processed through this filter.
   * @param image the given image
   * @return processed image
   */
  IImage filter(IImage image);
}
