package model.filter;

/**
 * This class represents the Gaussian blur filter which blurs the given image by a set matrix.
 */
public class GaussianBlurFilter extends AFilter {

  /**
   * initializes the gaussian blur matrix.
   *
   * @return the new 2d array of values
   */
  protected double[][] initialize() {
    double[][] gaussian = new double[][]{
            {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0},
            {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0},
            {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}};
    return gaussian;
  }
}
