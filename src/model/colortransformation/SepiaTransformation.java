package model.colortransformation;

/**
 * This class represents a Sepia transformation.
 */
public class SepiaTransformation extends AColorTransformation {

  @Override
  protected double[][] initialize() {
    double[][] transformation = new double[][]{{.393, .769, .189},
                                               {.349, .686, .168},
                                               {.272, .534, .131}};
    return transformation;
  }
}