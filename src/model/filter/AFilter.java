package model.filter;

import java.util.ArrayList;
import java.util.List;

import model.image.IImage;
import model.image.ImageImpl;
import model.image.IPixel;
import model.image.PixelImpl;

/**
 * This class represents the abstract class AFilter, that supports the functionality filter, that
 * filters a given image by the type of filter.
 */
public abstract class AFilter implements IFilter {
  private final double[][] kernel;

  /**
   * Constructor for AFilter.
   */
  public AFilter() {
    double[][] newKernel = initialize();
    if (newKernel == null) {
      throw new IllegalArgumentException("kernel cannot be null");
    }
    if (newKernel.length % 2 == 0 || newKernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("kernel length must be odd");
    }
    this.kernel = newKernel;
  }

  /**
   * Initializes the filter.
   *
   * @return the initialized filter as a 2d array.
   */
  protected abstract double[][] initialize();

  @Override
  public IImage filter(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Invalid Image");
    }
    List<List<IPixel>> pixels = new ArrayList<>();
    for (int i = 0; i < image.getHeight(); i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < image.getWidth(); j++) {
        pixels.get(i).add(createFilteredPixel(filterPixels(image, i, j)));
      }
    }
    return new ImageImpl(pixels);
  }

  /**
   * Creates a new 2d arraylist of pixels based on the size of the filter kernel, which consists
   * of all the squares around initial image with the center at row, col.
   *
   * @param image the given image
   * @param row   the current row of the image
   * @param col   the current column of the image
   * @return the newly created 2d arraylist representing the squares around the center of the
   *         kernel for the original image.
   */
  protected List<List<IPixel>> filterPixels(IImage image, int row, int col) {
    List<List<IPixel>> pixels = new ArrayList<>();
    int distanceFromCenterRow = kernel.length / 2;
    int distanceFromCenterCol = kernel[0].length / 2;
    for (int i = 0; i < kernel.length; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < kernel[0].length; j++) {
        // checks if the kernel's topmost row is negative.
        if (row - distanceFromCenterRow + i < 0
                // checks if the kernel's leftmost column is negative
                || col - distanceFromCenterCol + j < 0
                // checks if the kernel's bottommost row is longer than the height of the image
                || row - distanceFromCenterRow + i >= image.getHeight()
                // checks if the kernel's rightmost column is longer than the width of the image
                || col - distanceFromCenterCol + j >= image.getWidth()) {
          pixels.get(i).add(new PixelImpl(0, 0, 0));
        } else {
          pixels.get(i).add(image.getPixelAt(row + i - distanceFromCenterRow,
                  col + j - distanceFromCenterCol));
        }
      }
    }
    return pixels;
  }

  /**
   * Creates a filtered pixel by doing calculations on the 2d arraylist with the kernel that is
   * given.
   *
   * @param pixels the given list of list of Ipixels
   * @return the filtered pixel
   */
  protected IPixel createFilteredPixel(List<List<IPixel>> pixels) {
    double red = 0.0;
    double green = 0.0;
    double blue = 0.0;
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel[0].length; j++) {
        red += pixels.get(i).get(j).getRed() * kernel[i][j];
        green += pixels.get(i).get(j).getGreen() * kernel[i][j];
        blue += pixels.get(i).get(j).getBlue() * kernel[i][j];
      }
    }
    red = Math.max(Math.min(Math.round(red), 255), 0);
    green = Math.max(Math.min(Math.round(green), 255), 0);
    blue = Math.max(Math.min(Math.round(blue), 255), 0);
    return new PixelImpl((int) red, (int) green, (int) blue);
  }
}
