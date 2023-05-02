package view;

/**
 * The interface for a view listener.
 */
public interface ViewListener {

  /**
   * Loads an image.
   */
  void loadImageEvent();

  /**
   * Saves an image.
   */
  void saveImageEvent();

  /**
   * Changes the current image being displayed.
   *
   * @param imageName the new image
   */
  void changeImageEvent(String imageName);

  /**
   * Flips an image horizontally.
   */
  void horizontalFlip();

  /**
   * Flips an image vertically.
   */
  void verticalFlip();

  /**
   * Gets the red component of an image.
   */
  void redComponent();

  /**
   * Gets the green component of an image.
   */
  void greenComponent();

  /**
   * Gets the blue component of an image.
   */
  void blueComponent();

  /**
   * Gets the Value component of an image.
   */
  void valueComponent();

  /**
   * Gets the Intensity component of an image.
   */
  void intensityComponent();

  /**
   * Gets the Luma component of an image.
   */
  void lumaComponent();

  /**
   * Changes the brightness of an image.
   */
  void changeBrightness();

  /**
   * Blurs an image.
   */
  void blur();

  /**
   * Sharpens an image.
   */
  void sharpen();

  /**
   * Applies Sepia to an image.
   */
  void sepia();

  /**
   * Applies greyscale to an image.
   */
  void greyscale();
}
