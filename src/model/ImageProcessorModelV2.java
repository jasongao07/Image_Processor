package model;

/**
 * This interface represents an image processor that, on top of being able to do everything
 * the original ImageProcessorModel was able to do, is also able to apply filters and color
 * transformations onto images.
 */
public interface ImageProcessorModelV2 extends ImageProcessorModel {
  /**
   * Applies a filter onto an image.
   *
   * @param filterName  the name of the filter
   * @param targetImage the image prior to the change
   * @param name        the image's name after being filtered
   * @throws IllegalArgumentException for when target image is not a valid image
   *                                  or if the filter is not a valid filter.
   */
  void applyFilter(String filterName, String targetImage, String name)
          throws IllegalArgumentException;

  /**
   * Applies a color transformation onto an image.
   *
   * @param transformationName the name of the color transformation
   * @param targetImage        the image prior to the change
   * @param name               the image's name after being transformed
   * @throws IllegalArgumentException for when target image is not a valid image
   *                                  or if the transformation is not a valid transformation.
   */
  void applyColorTransformation(String transformationName, String targetImage, String name)
          throws IllegalArgumentException;
}
