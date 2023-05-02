package model.image;

/**
 * The PixelImpl that implements and IPixel. This class represents an 8-bit
 */
public class PixelImpl implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * The constructor for the pixelImpl that takes in the rgb values.
   *
   * @param red   the red value that is in the range [0, 255]
   * @param green the green value that is in the range [0, 255]
   * @param blue  the blue value that is in the range [0, 255]
   */
  public PixelImpl(int red, int green, int blue) {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException(String.format("Invalid rgb value " +
              "red: %d green: %d blue: %d", red, green, blue));
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getValue() {
    return Math.max(Math.max(this.red, this.green), this.blue);
  }

  @Override
  public int getIntensity() {
    return (int) Math.round((this.red + this.green + this.blue) / 3.0);
  }

  @Override
  public int getLuma() {
    return (int) Math.round(0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof IPixel)) {
      return false;
    }
    IPixel that = (IPixel) other;
    return this.getRed() == that.getRed()
            && this.getGreen() == that.getGreen()
            && this.getBlue() == that.getBlue();
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.getRed()) + Integer.hashCode(this.getGreen())
            + Integer.hashCode(this.getBlue());
  }
}
