package model.colortransformation;

import model.image.IPixel;
import model.image.PixelImpl;

/**
 * This class represents an abstract class A Color Transformation that supports the functionality
 * transform that applies a 3x3 transformation of the pixel given the type of transformation.
 */
public abstract class AColorTransformation implements IColorTransformation {
  private final double[][] transformation;

  /**
   * Constructor for abstract color transformation class.
   */
  public AColorTransformation() {
    double[][] newTransformation = initialize();
    if (newTransformation == null) {
      throw new IllegalArgumentException("Transformation must be non null");
    }
    if (newTransformation.length != 3 || newTransformation[0].length != 3) {
      throw new IllegalArgumentException("Transformation must be a 3x3 matrix");
    }
    this.transformation = newTransformation;
  }

  /**
   * Initialize the color transformation.
   *
   * @return the initialized 2d list
   */
  protected abstract double[][] initialize();

  /**
   * Applies the color transformation on this given pixel.
   *
   * @param pixel the given pixel
   * @return the new transformed pixel
   */
  public IPixel transform(IPixel pixel) {
    if (pixel == null) {
      throw new IllegalArgumentException("Pixel cannot be null");
    }
    int red = (int) Math.round(transformation[0][0] * pixel.getRed()
            + transformation[0][1] * pixel.getGreen()
            + transformation[0][2] * pixel.getBlue());
    int green = (int) Math.round(transformation[1][0] * pixel.getRed()
            + transformation[1][1] * pixel.getGreen()
            + transformation[1][2] * pixel.getBlue());
    int blue = (int) Math.round(transformation[2][0] * pixel.getRed()
            + transformation[2][1] * pixel.getGreen()
            + transformation[2][2] * pixel.getBlue());
    red = Math.max(Math.min(red, 255), 0);
    green = Math.max(Math.min(green, 255), 0);
    blue = Math.max(Math.min(blue, 255), 0);
    return new PixelImpl(red, green, blue);
  }
}
