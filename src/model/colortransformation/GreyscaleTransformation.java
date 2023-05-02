package model.colortransformation;

/**
 * This class represents a greyscale transformation.
 */
public class GreyscaleTransformation extends AColorTransformation {

  @Override
  protected double[][] initialize() {
    double[][] transformation = new double[][]{{.2126, .7152, .07220},
                                               {.2126, .7152, .07220},
                                               {.2126, .7152, .07220}};
    return transformation;
  }
}
