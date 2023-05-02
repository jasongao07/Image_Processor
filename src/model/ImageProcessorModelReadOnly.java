package model;

import model.image.IImage;

/**
 * Represents the non-mutating methods of an ImageProcessorModel.
 */
public interface ImageProcessorModelReadOnly {

  /**
   * Returns the image with the given name.
   *
   * @param targetImage the given name of the image
   * @return the 2d list of IPixels representing the image
   * @throws IllegalArgumentException for when target image is not a valid image
   */
  IImage saveImage(String targetImage) throws IllegalArgumentException;
}
