package model;

import model.image.IImage;

/**
 * This interface represents the mutating operations that can be used to monitor the state of an
 * image, including operations such as loading, saving, flipping, and brightening/darkening
 * images. The representation of an image is that of a list of (list of IPixels).
 */
public interface ImageProcessorModel extends ImageProcessorModelReadOnly {

  /**
   * Loads an image from the given file path, and renames it as the given imageName.
   *
   * @param image the image that will be loaded
   * @param name  the new image name
   */
  void loadImage(IImage image, String name);

  /**
   * Flips the targetImage horizontally and renames it as newImage.
   *
   * @param targetImage the image to be flipped
   * @param name        the flipped image name
   * @throws IllegalArgumentException for when target image is not a valid image
   */
  void horizontalFlip(String targetImage, String name) throws IllegalArgumentException;

  /**
   * Flips the targetImage vertically and renames it as newImage.
   *
   * @param targetImage the image to be flipped
   * @param name        the flipped image name
   * @throws IllegalArgumentException for when target image is not a valid image
   */
  void verticalFlip(String targetImage, String name) throws IllegalArgumentException;

  /**
   * Changes the targetImage by the delta and renames it as newImage.
   *
   * @param delta       the amount of brightness to be changed.
   * @param targetImage the image to be changed
   * @param name        the image name after changing the brightness
   * @throws IllegalArgumentException for when target image is not a valid image
   */
  void changeBrightness(int delta, String targetImage, String name)
          throws IllegalArgumentException;

  /**
   * Creates a grayscale image based on the ComponentName.
   *
   * @param componentName the name of component
   * @param targetImage   the image prior to the change
   * @param name          the grayscale image's name after created from the component
   * @throws IllegalArgumentException for when target image is not a valid image
   *                                  or if the component is an invalid component.
   */
  void getComponent(String componentName, String targetImage, String name)
          throws IllegalArgumentException;
}


